package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "SUP_REQUESTS")
public class SupRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REQ_ID")
	private Long reqId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "TABLE_NAME")
	private String tableName;
	
	@Column(name = "_ID")
	private Long id;
	
	@Column(name = "OLD_VALUE")
	private String oldValue;
	
	@Column(name = "NEW_VALUE")
	private String newValue;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "APPROVED_BY")
	private int approvedBy;
	
	@Column(name = "APPROVED_ON")
	private Date approvedOn;

	public SupRequest(Long reqId, String supplierCode, String tableName, Long id, String oldValue, String newValue,
			String status, String comment, int createdBy, Date createdOn, int approvedBy, Date approvedOn) {
		super();
		this.reqId = reqId;
		this.supplierCode = supplierCode;
		this.tableName = tableName;
		this.id = id;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.status = status;
		this.comment = comment;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.approvedBy = approvedBy;
		this.approvedOn = approvedOn;
	}

	public SupRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getReqId() {
		return reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	@Override
	public String toString() {
		return "SupRequest [reqId=" + reqId + ", supplierCode=" + supplierCode + ", tableName=" + tableName + ", id="
				+ id + ", oldValue=" + oldValue + ", newValue=" + newValue + ", status=" + status + ", comment="
				+ comment + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", approvedBy=" + approvedBy
				+ ", approvedOn=" + approvedOn + "]";
	}
	
	
	
	
	
}
