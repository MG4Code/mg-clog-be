package com.mg.clog.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

  private final AuthenticationManager authenticationManager;

  public SecurityContextRepository(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange swe) {
    var request = swe.getRequest();
    var authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      var authToken = authHeader.substring(7);
      var auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
      return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
    } else {
      return Mono.empty();
    }
  }

}
