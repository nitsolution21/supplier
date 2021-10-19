package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fintexel.supplier.entity.SupDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
public class VendorsStretchingClass {
	private String supplierCode;
	private Long registerId;
	private String supplierCompName;
	private String registrationType;
	private String registrationNo; 
	private String status;
	private String costCenter;
	private String remarks;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastlogin;
	private int createdBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	private int updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	private String supplier;
	private CustomerContact customerContact;
	
	public VendorsStretchingClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VendorsStretchingClass(String supplierCode, Long registerId, String supplierCompName,
			String registrationType, String registrationNo, String status, String costCenter, String remarks,
			Date lastlogin, int createdBy, Date createdOn, int updatedBy, Date updatedOn, String supplier,
			CustomerContact customerContact) {
		super();
		this.supplierCode = supplierCode;
		this.registerId = registerId;
		this.supplierCompName = supplierCompName;
		this.registrationType = registrationType;
		this.registrationNo = registrationNo;
		this.status = status;
		this.costCenter = costCenter;
		this.remarks = remarks;
		this.lastlogin = lastlogin;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.supplier = supplier;
		this.customerContact = customerContact;
	}

	@Override
	public String toString() {
		return "VendorsStretchingClass [supplierCode=" + supplierCode + ", registerId=" + registerId
				+ ", supplierCompName=" + supplierCompName + ", registrationType=" + registrationType
				+ ", registrationNo=" + registrationNo + ", status=" + status + ", costCenter=" + costCenter
				+ ", remarks=" + remarks + ", lastlogin=" + lastlogin + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", supplier=" + supplier
				+ ", customerContact=" + customerContact + "]";
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public String getSupplierCompName() {
		return supplierCompName;
	}

	public void setSupplierCompName(String supplierCompName) {
		this.supplierCompName = supplierCompName;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public CustomerContact getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(CustomerContact customerContact) {
		this.customerContact = customerContact;
	}

}
