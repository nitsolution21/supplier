package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_INVOICE_STATUSES")
public class SupplierInvoiceStatus {
	
	@Id
	@Column(name = "INV_ID") private Long invId;
	@Column(name = "INV_STATUS") private String invStatus;
	@Column(name = "COMMENT") private String comment;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "BY_WHOM") private String byWhom;
	@JsonFormat(pattern = "YY-MM-DD")
	@Column(name = "UPDATED_ON") private Date updatedOn;
	public Long getInvId() {
		return invId;
	}
	public void setInvId(Long invId) {
		this.invId = invId;
	}
	public String getInvStatus() {
		return invStatus;
	}
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getByWhom() {
		return byWhom;
	}
	public void setByWhom(String byWhom) {
		this.byWhom = byWhom;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "SupplierInvoiceStatus [invId=" + invId + ", invStatus=" + invStatus + ", comment=" + comment
				+ ", updatedBy=" + updatedBy + ", byWhom=" + byWhom + ", updatedOn=" + updatedOn + "]";
	}
	public SupplierInvoiceStatus(Long invId, String invStatus, String comment, String updatedBy, String byWhom,
			Date updatedOn) {
		super();
		this.invId = invId;
		this.invStatus = invStatus;
		this.comment = comment;
		this.updatedBy = updatedBy;
		this.byWhom = byWhom;
		this.updatedOn = updatedOn;
	}
	public SupplierInvoiceStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
