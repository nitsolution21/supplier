package org.fintexel.supplier.entity;

public class LoginResponce {
	private String username;
	private String token;
	private long registerId;
	private String supplierCompanyName;
	
	public LoginResponce(String username, String token, long registerId, String supplierCompanyName) {
		super();
		this.username = username;
		this.token = token;
		this.registerId = registerId;
		this.supplierCompanyName = supplierCompanyName;
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
	
	public String getSupplierCompanyName() {
		return supplierCompanyName;
	}

	public void setSupplierCompanyName(String supplierCompanyName) {
		this.supplierCompanyName = supplierCompanyName;
	}

	@Override
	public String toString() {
		return "LoginResponce [username=" + username + ", token=" + token + ", registerId="+registerId+", supplierCompanyName"+supplierCompanyName+"]";
	}
	
	
	
}
