package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_DETAILS")
public class SupDetails {
	@Id
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	@Column(name = "REGISTER_ID")
	private int registerId;
	
	@Column(name = "SUPPLIER_COMP_NAME")
	private String supplierCompName;
	
	@Column(name = "SUPPLIER_CONTACT1")
	private String supplierContact1;
	
	@Column(name = "SUPPLIER_CONTACT2")
	private String supplierContact2;
	
	@Column(name = "REGISTRATION_TYPE")
	private String registrationType;
	
	@Column(name = "REGRISTRATION_NO")
	private String regristrationNo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "LASTLOGIN")
	private Date lastlogin;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	@Override
	public String toString() {
		return "SupDetails [supplierCode=" + supplierCode + ", registerId=" + registerId + ", supplierCompName="
				+ supplierCompName + ", supplierContact1=" + supplierContact1 + ", supplierContact2=" + supplierContact2
				+ ", registrationType=" + registrationType + ", regristrationNo=" + regristrationNo + ", status="
				+ status + ", remarks=" + remarks + ", lastlogin=" + lastlogin + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}

	public SupDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SupDetails(String supplierCode, int registerId, String supplierCompName, String supplierContact1,
			String supplierContact2, String registrationType, String regristrationNo, String status, String remarks,
			Date lastlogin, int createdBy, Date createdOn, int updatedBy, Date updatedOn, String sUPPLIER_CODE) {
		super();
		this.supplierCode = supplierCode;
		this.registerId = registerId;
		this.supplierCompName = supplierCompName;
		this.supplierContact1 = supplierContact1;
		this.supplierContact2 = supplierContact2;
		this.registrationType = registrationType;
		this.regristrationNo = regristrationNo;
		this.status = status;
		this.remarks = remarks;
		this.lastlogin = lastlogin;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public int getRegisterId() {
		return registerId;
	}

	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}

	public String getSupplierCompName() {
		return supplierCompName;
	}

	public void setSupplierCompName(String supplierCompName) {
		this.supplierCompName = supplierCompName;
	}

	public String getSupplierContact1() {
		return supplierContact1;
	}

	public void setSupplierContact1(String supplierContact1) {
		this.supplierContact1 = supplierContact1;
	}

	public String getSupplierContact2() {
		return supplierContact2;
	}

	public void setSupplierContact2(String supplierContact2) {
		this.supplierContact2 = supplierContact2;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getRegristrationNo() {
		return regristrationNo;
	}

	public void setRegristrationNo(String regristrationNo) {
		this.regristrationNo = regristrationNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
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

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	
	
}
