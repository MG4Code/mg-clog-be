package com.mg.clog.user;

import com.mg.clog.ClogProperties;
import com.mg.clog.security.jwt.JwtUtils;
import com.mg.clog.security.services.UserDetailsImpl;
import com.mg.clog.user.data.model.Role;
import com.mg.clog.user.data.model.User;
import com.mg.clog.user.data.model.request.LoginRequest;
import com.mg.clog.user.data.model.request.SignupRequest;
import com.mg.clog.user.data.model.response.JwtResponse;
import com.mg.clog.user.data.model.response.MessageResponse;
import com.mg.clog.user.data.repo.UserRepository;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final ClogProperties properties;

  @Autowired
  public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils, ClogProperties properties) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
    this.properties = properties;
  }

  @PostMapping("/signin")
  public Single<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    var authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    var jwt = jwtUtils.generateJwtToken(authentication);

    var userDetails = (UserDetailsImpl) authentication.getPrincipal();
    var roles = userDetails.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    var jwtResponse = new JwtResponse(jwt,
      userDetails.getId(),
      userDetails.getUsername(),
      userDetails.getEmail(),
      roles);

    return Single.just(jwtResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Username is already taken!"));
    }
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Email is already in use!"));
    }
    if (!properties.getSecurityMapping().containsKey(signUpRequest.getEmail())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: User with email '" + signUpRequest.getEmail() + "' is not allow to register!"));
    }

    var user = new User(
      signUpRequest.getUsername(),
      signUpRequest.getEmail(),
      encoder.encode(signUpRequest.getPassword()),
      properties.getSecurityMapping().getOrDefault(signUpRequest.getEmail(), List.of(Role.ROLE_USER.name())).stream().map(Role::valueOf).collect(Collectors.toSet())
    );
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
