package org.fintexel.supplier.entity;

public class LoginResponce {
	private String username;
	private String token;
	
	public LoginResponce(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}

	public LoginResponce() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponce [username=" + username + ", token=" + token + "]";
	}
	
	
	
}
