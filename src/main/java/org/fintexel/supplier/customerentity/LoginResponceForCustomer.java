package org.fintexel.supplier.customerentity;

public class LoginResponceForCustomer {
	private long userId;
	private String token;
	private String role;
	private long cId;
	
	public LoginResponceForCustomer(long userId, String token, String role, long cId) {
		super();
		this.userId = userId;
		this.token = token;
		this.role = role;
		this.cId = cId;
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

	public long getcId() {
		return cId;
	}

	public void setcId(long cId) {
		this.cId = cId;
	}
	
	
	
	
}
