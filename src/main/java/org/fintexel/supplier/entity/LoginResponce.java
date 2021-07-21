package org.fintexel.supplier.entity;

public class LoginResponce {
	private String username;
	private String token;
	private long registerId;
	
	public LoginResponce(String username, String token, long registerId) {
		super();
		this.username = username;
		this.token = token;
		this.registerId = registerId;
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
	
	public long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(long registerId) {
		this.registerId = registerId;
	}

	@Override
	public String toString() {
		return "LoginResponce [username=" + username + ", token=" + token + "]";
	}
	
	
	
}
