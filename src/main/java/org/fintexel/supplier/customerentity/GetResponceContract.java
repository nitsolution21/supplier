package org.fintexel.supplier.customerentity;

import java.util.Date;

public class GetResponceContract {
	private Long contractId;
	private Long cId;
	private String supplierCode;
	private String contractType;
	private Long contractTerms;
	private String contractProof;
	private String contractLocation;
	private String supplierCompanyName;
	private Date contractEndDate;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public GetResponceContract() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetResponceContract(Long contractId, Long cId, String supplierCode, String contractType, Long contractTerms,
			String contractProof, String contractLocation, String supplierCompanyName, Date contractEndDate,
			String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.contractId = contractId;
		this.cId = cId;
		this.supplierCode = supplierCode;
		this.contractType = contractType;
		this.contractTerms = contractTerms;
		this.contractProof = contractProof;
		this.contractLocation = contractLocation;
		this.supplierCompanyName = supplierCompanyName;
		this.contractEndDate = contractEndDate;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "GetResponceContract [contractId=" + contractId + ", cId=" + cId + ", supplierCode=" + supplierCode
				+ ", contractType=" + contractType + ", contractTerms=" + contractTerms + ", contractProof="
				+ contractProof + ", contractLocation=" + contractLocation + ", supplierCompanyName="
				+ supplierCompanyName + ", contractEndDate=" + contractEndDate + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}

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

	public String getSupplierCompanyName() {
		return supplierCompanyName;
	}

	public void setSupplierCompanyName(String supplierCompanyName) {
		this.supplierCompanyName = supplierCompanyName;
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
	
}
