package org.fintexel.supplier.entity;

public class CustomeResponseEntity {
	private String status;
	private String reason;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "ResponseEntity [status=" + status + ", reason=" + reason + "]";
	}
	public CustomeResponseEntity(String status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}
	public CustomeResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
