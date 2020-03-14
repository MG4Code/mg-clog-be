package com.mg.clog.security;

import com.mg.clog.user.data.model.Role;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtUtil jwtUtil;

  public AuthenticationManager(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Authentication> authenticate(Authentication authentication) {
    var authToken = authentication.getCredentials().toString();
    String username;
    try {
      username = jwtUtil.getUsernameFromToken(authToken);
    } catch (Exception e) {
      username = null;
    }
    if (username != null && jwtUtil.validateToken(authToken)) {
      var claims = jwtUtil.getAllClaimsFromToken(authToken);
      List<String> rolesMap = claims.get("role", List.class);
      List<Role> roles = new ArrayList<>();
      for (String role : rolesMap) {
        roles.add(Role.valueOf(role));
      }
      var userId = claims.get("userId");
      var auth = new UsernamePasswordAuthenticationToken(
        username,
        null,
        roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
      );
      auth.setDetails(userId);
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}
