package org.fintexel.supplier.entity;

public class ApproveMap {
	private Long id;
	private String status;
	public ApproveMap(Long id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public ApproveMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ApproveMap [id=" + id + ", status=" + status + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
