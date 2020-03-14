package com.mg.clog.security;

import com.mg.clog.ClogProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(ClogProperties.class)
public class WebSecurityConfig {

  public static final String BEARER_KEY = "bearer-key";
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
    this.authenticationManager = authenticationManager;
    this.securityContextRepository = securityContextRepository;
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components()
        .addSecuritySchemes(BEARER_KEY,
          new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
      .authorizeExchange()
      .pathMatchers(HttpMethod.OPTIONS).permitAll()
      // ALLOW FOR SWAGGER UI
      .pathMatchers("/swagger-ui.html").permitAll()
      .pathMatchers("/webjars/swagger-ui/**").permitAll()
      .pathMatchers("/v3/api-docs/**").permitAll()
      // ALLOW FOR ACTUATOR
      .pathMatchers("/actuator/**").permitAll()
      // ALLOW LOGIN and REGISTER
      .pathMatchers("/clog/v1/user/signin").permitAll()
      .pathMatchers("/clog/v1/user/signup").permitAll()
      // all the rest needs to be authenticated
      .anyExchange().authenticated()
      .and()
      .authenticationManager(authenticationManager)
      .securityContextRepository(securityContextRepository)
      .exceptionHandling()
      .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
      .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
      .and()
      .csrf().disable()
      .formLogin().disable()
      .httpBasic().disable()
      .build();
  }
}
