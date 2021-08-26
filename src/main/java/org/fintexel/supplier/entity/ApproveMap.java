package org.fintexel.supplier.entity;

public class ApproveMap {
	private Long id;
	private String status;
	private String comment;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ApproveMap [id=" + id + ", status=" + status + ", comment=" + comment + "]";
	}
	public ApproveMap(Long id, String status, String comment) {
		super();
		this.id = id;
		this.status = status;
		this.comment = comment;
	}
	public ApproveMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
