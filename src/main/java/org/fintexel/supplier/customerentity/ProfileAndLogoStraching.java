package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProfileAndLogoStraching {
	
	private int cId;
	private String companyLogoProofPath;
	private Date createdOn;
	private String createdBy;
	private String customerName;
	private String customerContact1;
	private String customerContact2;
	private int registrationType;
	private String registrationTypeName;
	private String registrationNo;
	private String status;
	private String updatedBy;
	private Date updatedOn;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getCompanyLogoProofPath() {
		return companyLogoProofPath;
	}
	public void setCompanyLogoProofPath(String companyLogoProofPath) {
		this.companyLogoProofPath = companyLogoProofPath;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerContact1() {
		return customerContact1;
	}
	public void setCustomerContact1(String customerContact1) {
		this.customerContact1 = customerContact1;
	}
	public String getCustomerContact2() {
		return customerContact2;
	}
	public void setCustomerContact2(String customerContact2) {
		this.customerContact2 = customerContact2;
	}
	public int getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(int registrationType) {
		this.registrationType = registrationType;
	}
	public String getRegistrationTypeName() {
		return registrationTypeName;
	}
	public void setRegistrationTypeName(String registrationTypeName) {
		this.registrationTypeName = registrationTypeName;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "ProfileAndLogoStraching [cId=" + cId + ", companyLogoProofPath=" + companyLogoProofPath + ", createdOn="
				+ createdOn + ", createdBy=" + createdBy + ", customerName=" + customerName + ", customerContact1="
				+ customerContact1 + ", customerContact2=" + customerContact2 + ", registrationType=" + registrationType
				+ ", registrationTypeName=" + registrationTypeName + ", registrationNo=" + registrationNo + ", status="
				+ status + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	public ProfileAndLogoStraching(int cId, String companyLogoProofPath, Date createdOn, String createdBy,
			String customerName, String customerContact1, String customerContact2, int registrationType,
			String registrationTypeName, String registrationNo, String status, String updatedBy, Date updatedOn) {
		super();
		this.cId = cId;
		this.companyLogoProofPath = companyLogoProofPath;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.customerName = customerName;
		this.customerContact1 = customerContact1;
		this.customerContact2 = customerContact2;
		this.registrationType = registrationType;
		this.registrationTypeName = registrationTypeName;
		this.registrationNo = registrationNo;
		this.status = status;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	public ProfileAndLogoStraching() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
