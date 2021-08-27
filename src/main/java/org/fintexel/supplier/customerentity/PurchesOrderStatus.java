package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_PO_STATUSES")
public class PurchesOrderStatus {
	@Id
	@Column(name = "PO_ID") private Long POId;	
	@Column(name = "PO_STATUS") private String POStatus;
	@Column(name = "COMMENT") private String comment;
	@Column(name = "UPDATED_BY") private String update;
	@Column(name = "BY_WHOM") private String byWhom;
	@JsonFormat(pattern = "YY-MM-DD")
	@Column(name = "UPDATED_ON") Date updateBy;
	public Long getPOId() {
		return POId;
	}
	public void setPOId(Long pOId) {
		POId = pOId;
	}
	public String getPOStatus() {
		return POStatus;
	}
	public void setPOStatus(String pOStatus) {
		POStatus = pOStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getByWhom() {
		return byWhom;
	}
	public void setByWhom(String byWhom) {
		this.byWhom = byWhom;
	}
	public Date getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Date updateBy) {
		this.updateBy = updateBy;
	}
	@Override
	public String toString() {
		return "PurchesOrderStatus [POId=" + POId + ", POStatus=" + POStatus + ", comment=" + comment + ", update="
				+ update + ", byWhom=" + byWhom + ", updateBy=" + updateBy + "]";
	}
	public PurchesOrderStatus(Long pOId, String pOStatus, String comment, String update, String byWhom, Date updateBy) {
		super();
		POId = pOId;
		POStatus = pOStatus;
		this.comment = comment;
		this.update = update;
		this.byWhom = byWhom;
		this.updateBy = updateBy;
	}
	public PurchesOrderStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
