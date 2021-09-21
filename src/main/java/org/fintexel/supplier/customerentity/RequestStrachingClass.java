package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RequestStrachingClass {
	
	private Long contractId;
	private Long cId;
	private String supplierCode;
	private String contractType;
	private Long contractTerms;
	private String contractProof;
	private String contractLocation;
	@JsonFormat(pattern="yyyy-MM-dd") private Date contractEndDate;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	private boolean other;
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Long getContractTerms() {
		return contractTerms;
	}
	public void setContractTerms(Long contractTerms) {
		this.contractTerms = contractTerms;
	}
	public String getContractProof() {
		return contractProof;
	}
	public void setContractProof(String contractProof) {
		this.contractProof = contractProof;
	}
	public String getContractLocation() {
		return contractLocation;
	}
	public void setContractLocation(String contractLocation) {
		this.contractLocation = contractLocation;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	public boolean isOther() {
		return other;
	}
	public void setOther(boolean other) {
		this.other = other;
	}
	@Override
	public String toString() {
		return "RequestStrachingClass [contractId=" + contractId + ", cId=" + cId + ", supplierCode=" + supplierCode
				+ ", contractType=" + contractType + ", contractTerms=" + contractTerms + ", contractProof="
				+ contractProof + ", contractLocation=" + contractLocation + ", contractEndDate=" + contractEndDate
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", other=" + other + "]";
	}
	public RequestStrachingClass(Long contractId, Long cId, String supplierCode, String contractType,
			Long contractTerms, String contractProof, String contractLocation, Date contractEndDate, String createdBy,
			Date createdOn, String updatedBy, Date updatedOn, boolean other) {
		super();
		this.contractId = contractId;
		this.cId = cId;
		this.supplierCode = supplierCode;
		this.contractType = contractType;
		this.contractTerms = contractTerms;
		this.contractProof = contractProof;
		this.contractLocation = contractLocation;
		this.contractEndDate = contractEndDate;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.other = other;
	}
	public RequestStrachingClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}