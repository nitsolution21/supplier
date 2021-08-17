package org.fintexel.supplier.customerentity;

public class CustomerRegisterRequest {
	private String email;
	private String name;
	private long role;
	private long cId;
	private long funcationality;
	private long department;
	
	public CustomerRegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerRegisterRequest(String email, String name, long role, long cId, long funcationality,
			long department) {
		super();
		this.email = email;
		this.name = name;
		this.role = role;
		this.cId = cId;
		this.funcationality = funcationality;
		this.department = department;
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

	public long getRole() {
		return role;
	}

	public void setRole(long role) {
		this.role = role;
	}

	public long getcId() {
		return cId;
	}

	public void setcId(long cId) {
		this.cId = cId;
	}

	public long getFuncationality() {
		return funcationality;
	}

	public void setFuncationality(long funcationality) {
		this.funcationality = funcationality;
	}

	public long getDepartment() {
		return department;
	}

	public void setDepartment(long department) {
		this.department = department;
	}
	
	
	
	
}
