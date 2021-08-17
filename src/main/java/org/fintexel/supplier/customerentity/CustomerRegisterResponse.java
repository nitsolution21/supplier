package org.fintexel.supplier.customerentity;

import java.util.Date;
import java.util.List;

public class CustomerRegisterResponse {
	private String email;
	private String name;
	private String role;
	private  List<CustomerDepartments> customerDepartments;
	private List<CustomerFunctionalitiesMaster> functionality;
	private String access;
	private long cId;
	private long userId;
	private String username;
	private String password;
	private String status;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public CustomerRegisterResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerRegisterResponse(String email, String name, String role,
			List<CustomerDepartments> customerDepartments, List<CustomerFunctionalitiesMaster> functionality,
			String access, long cId, long userId, String username, String password, String status, String createdBy,
			Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.email = email;
		this.name = name;
		this.role = role;
		this.customerDepartments = customerDepartments;
		this.functionality = functionality;
		this.access = access;
		this.cId = cId;
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	
}
