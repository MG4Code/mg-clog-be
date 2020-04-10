package com.mg.clog.user;

import com.mg.clog.ClogProperties;
import com.mg.clog.exception.AccessDeniedException;
import com.mg.clog.security.JwtUtil;
import com.mg.clog.user.data.model.Role;
import com.mg.clog.user.data.model.User;
import com.mg.clog.user.data.model.request.LoginRequest;
import com.mg.clog.user.data.model.request.SignupRequest;
import com.mg.clog.user.data.model.response.JwtResponse;
import com.mg.clog.user.data.model.response.MessageResponse;
import com.mg.clog.user.data.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("clog/v1/user")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtUtil jwtUtil;
  private final ClogProperties properties;

  @Autowired
  public UserController(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil, ClogProperties properties) {
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.jwtUtil = jwtUtil;
    this.properties = properties;
  }

  @GetMapping("/{userId}")
  public Mono<User> getUserName(@PathVariable("userId") String userId) {
    return userRepository.findById(userId);
  }

  @PostMapping("/signin")
  public Mono<ResponseEntity<?>> login(@Valid @RequestBody LoginRequest loginRequest) {
    return userRepository.findByUsername(loginRequest.getUsername()).map((userDetails) -> {
      if (encoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
        var jwt = jwtUtil.generateToken(userDetails);
        var claims = jwtUtil.getAllClaimsFromToken(jwt);
        var roles = userDetails.getRoles().stream()
          .map(Enum::name)
          .collect(Collectors.toList());

        var response = new JwtResponse(jwt,
          userDetails.getId(),
          userDetails.getUsername(),
          userDetails.getEmail(),
          new Date((Integer) claims.get("exp")),
          roles);

        return ResponseEntity.ok(response);
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  @PostMapping("/signup")
  public Mono<ResponseEntity<MessageResponse>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return Mono.zip(
      userRepository.existsByUsername(signUpRequest.getUsername()),
      userRepository.existsByEmail(signUpRequest.getEmail()),
      (userNameExists, emailExists) -> {
        if (userNameExists) {
          throw new AccessDeniedException("Error: Username is already taken!");
        }
        if (emailExists) {
          throw new AccessDeniedException("Error: Email is already in use!");
        }
        if (!properties.getSecurityMapping().containsKey(signUpRequest.getEmail())) {
          throw new AccessDeniedException("Error: User with email '" + signUpRequest.getEmail() + "' is not allow to register!");
        }
        return new User(
          signUpRequest.getUsername(),
          signUpRequest.getEmail(),
          encoder.encode(signUpRequest.getPassword()),
          properties.getSecurityMapping().getOrDefault(signUpRequest.getEmail(), List.of(Role.ROLE_USER.name())).stream().map(Role::valueOf).collect(Collectors.toSet())
        );
      }
    ).flatMap(userRepository::save)
      .map(s -> ResponseEntity.ok(new MessageResponse("User registered successfully!")))
      ;
  }
}
