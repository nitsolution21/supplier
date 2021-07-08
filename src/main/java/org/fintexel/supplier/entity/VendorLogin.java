package org.fintexel.supplier.entity;

public class VendorLogin {
	private String username;
	private String password;
	
	public VendorLogin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public VendorLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "VendorLogin [username=" + username + ", password=" + password + "]";
	}
	
	
}
