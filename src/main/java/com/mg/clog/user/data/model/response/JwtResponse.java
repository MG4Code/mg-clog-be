package com.mg.clog.user.data.model.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

  public String getToken() {
    return token;
  }

  public JwtResponse setToken(String token) {
    this.token = token;
    return this;
  }

  public String getType() {
    return type;
  }

  public JwtResponse setType(String type) {
    this.type = type;
    return this;
  }

  public String getId() {
    return id;
  }

  public JwtResponse setId(String id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public JwtResponse setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public JwtResponse setEmail(String email) {
    this.email = email;
    return this;
  }

  public List<String> getRoles() {
    return roles;
  }

  public JwtResponse setRoles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  @Override
  public String toString() {
    return "JwtResponse{" +
      "token='" + token + '\'' +
      ", type='" + type + '\'' +
      ", id='" + id + '\'' +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", roles=" + roles +
      '}';
  }
}
