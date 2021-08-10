package org.fintexel.supplier.customerentity;

public class LoginResponceForCustomer {
	private long userId;
	private String token;
	private String role;
	private String department;
	private String 	functionality;
	private String access;
	private long cId;
	
	public LoginResponceForCustomer(long userId, String token, String role, String department, String functionality, String access, long cId) {
		super();
		this.userId = userId;
		this.token = token;
		this.role = role;
		this.department = department;
		this.functionality = functionality;
		this.access = access;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFunctionality() {
		return functionality;
	}

	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	
	
}
