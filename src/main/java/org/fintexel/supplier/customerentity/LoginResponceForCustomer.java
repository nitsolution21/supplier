package org.fintexel.supplier.customerentity;

import java.util.List;

public class LoginResponceForCustomer {
	private long userId;
	private String token;
	private String role;
	private  List<CustomerDepartments> customerDepartments;
	private List<CustomerFunctionalitiesMaster> functionality;
	private String access;
	private long cId;
	private String username;
	
	public LoginResponceForCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginResponceForCustomer(long userId, String token, String role,
			List<CustomerDepartments> customerDepartments, List<CustomerFunctionalitiesMaster> functionality,
			String access, long cId, String username) {
		super();
		this.userId = userId;
		this.token = token;
		this.role = role;
		this.customerDepartments = customerDepartments;
		this.functionality = functionality;
		this.access = access;
		this.cId = cId;
		this.username = username;
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

	public List<CustomerDepartments> getCustomerDepartments() {
		return customerDepartments;
	}

	public void setCustomerDepartments(List<CustomerDepartments> customerDepartments) {
		this.customerDepartments = customerDepartments;
	}

	public List<CustomerFunctionalitiesMaster> getFunctionality() {
		return functionality;
	}

	public void setFunctionality(List<CustomerFunctionalitiesMaster> functionality) {
		this.functionality = functionality;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public long getcId() {
		return cId;
	}

	public void setcId(long cId) {
		this.cId = cId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	
}
