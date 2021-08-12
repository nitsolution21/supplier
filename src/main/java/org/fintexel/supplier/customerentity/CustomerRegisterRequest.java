package org.fintexel.supplier.customerentity;

public class CustomerRegisterRequest {
	private String email;
	private String name;
	private long role;
	private long cId;
//	private long de
	
	public CustomerRegisterRequest(String email, String name, long role, long cId) {
		super();
		this.email = email;
		this.name = name;
		this.role = role;
		this.cId = cId;
	}

	public CustomerRegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
}
