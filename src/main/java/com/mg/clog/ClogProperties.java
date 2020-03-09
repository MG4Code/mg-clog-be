package com.mg.clog;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "clog")
public class ClogProperties {

  private String jwtSecret;
  private long jwtExpirationMs;
  private Map<String, List<String>> securityMapping = new HashMap<>();

  public String getJwtSecret() {
    return jwtSecret;
  }

  public ClogProperties setJwtSecret(String jwtSecret) {
    this.jwtSecret = jwtSecret;
    return this;
  }

  public long getJwtExpirationMs() {
    return jwtExpirationMs;
  }

  public ClogProperties setJwtExpirationMs(long jwtExpirationMs) {
    this.jwtExpirationMs = jwtExpirationMs;
    return this;
  }

  public Map<String, List<String>> getSecurityMapping() {
    return securityMapping;
  }

  public ClogProperties setSecurityMapping(Map<String, List<String>> securityMapping) {
    this.securityMapping = securityMapping;
    return this;
  }
}
