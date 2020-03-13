package com.mg.clog.user;

import com.mg.clog.security.WebSecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public Mono<String> allAccess() {
    return Mono.just("Public Content.");
  }

  @GetMapping("/user")
  @Operation(security = { @SecurityRequirement(name = WebSecurityConfig.BEARER_KEY) })
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Mono<String> userAccess() {
    return Mono.just("User Content.");
  }

  @GetMapping("/mod")
  @Operation(security = { @SecurityRequirement(name = WebSecurityConfig.BEARER_KEY) })
  @PreAuthorize("hasRole('MODERATOR')")
  public Mono<String> moderatorAccess() {
    return Mono.just("Moderator Board.");
  }

  @GetMapping("/admin")
  @Operation(security = { @SecurityRequirement(name = WebSecurityConfig.BEARER_KEY) })
  @PreAuthorize("hasRole('ADMIN')")
  public Mono<String> adminAccess() {
    return Mono.just("Admin Board.");
  }
}
