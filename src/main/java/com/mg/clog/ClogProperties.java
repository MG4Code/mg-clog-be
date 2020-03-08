package com.mg.clog;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clog")
public class ClogProperties {

  private String jwtSecret;
  private long jwtExpirationMs;

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
}
