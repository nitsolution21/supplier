package org.fintexel.supplier.customerentity;

public class LoginResponceForCustomer {
	private long userId;
	private String token;
	private String role;
	
	public LoginResponceForCustomer(long userId, String token, String role) {
		super();
		this.userId = userId;
		this.token = token;
		this.role = role;
	}

	public LoginResponceForCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
}
