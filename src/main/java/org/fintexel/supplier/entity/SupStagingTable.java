package org.fintexel.supplier.entity;

import java.util.Date;

public class SupStagingTable {

	private String supplierCode;
	
	private int registerId;
	
	private String supplierCompName;
	
	private String supplierContact1;
	
	private String supplierContact2;
	
	private String registrationType;

	private String regristrationNo;
	
	private String status;
	
	private String remarks;
	
	private Date lastlogin;

	private int createdBy;
	
	private Date createdOn;

	private int updatedBy;
	
	private Date updatedOn;
	
	private int addressId;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private int postalCode;
	
	private String country;
	
	private String region;
	
	private String addressProof;

	private String addressProofPath;

	private int bankId;

	private String supplierCostCode;
	
	private String bankName;

	private String bankBranch;
	
	private String bankBic;

	private String bankAccountNo;
	
	private String currency;
	
	private String transilRoutingNo;
	
	private String chequeNo;
	
	private String accountHolder;

	private String swiftCode;

	private String ifscCode;
	
	private String bankEvidence;

	private String evidencePath;
	
	private int contractId;
	
	private int departmentId;
	
	private String departmentName;
	
	private String email;
	
	private String phoneno;
	
	private String alternatePhoneno;
	
	private String contractType;
	
	private int contractTerms;

	private String contractProof;
	
	private String contractLocation;

	public SupStagingTable(String supplierCode, int registerId, String supplierCompName, String supplierContact1,
			String supplierContact2, String registrationType, String regristrationNo, String status, String remarks,
			Date lastlogin, int createdBy, Date createdOn, int updatedBy, Date updatedOn, int addressId,
			String address1, String address2, String city, int postalCode, String country, String region,
			String addressProof, String addressProofPath, int bankId, String supplierCostCode, String bankName,
			String bankBranch, String bankBic, String bankAccountNo, String currency, String transilRoutingNo,
			String chequeNo, String accountHolder, String swiftCode, String ifscCode, String bankEvidence,
			String evidencePath, int contractId, int departmentId, String departmentName, String email, String phoneno,
			String alternatePhoneno, String contractType, int contractTerms, String contractProof,
			String contractLocation) {
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
		this.addressId = addressId;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.addressProof = addressProof;
		this.addressProofPath = addressProofPath;
		this.bankId = bankId;
		this.supplierCostCode = supplierCostCode;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.bankBic = bankBic;
		this.bankAccountNo = bankAccountNo;
		this.currency = currency;
		this.transilRoutingNo = transilRoutingNo;
		this.chequeNo = chequeNo;
		this.accountHolder = accountHolder;
		this.swiftCode = swiftCode;
		this.ifscCode = ifscCode;
		this.bankEvidence = bankEvidence;
		this.evidencePath = evidencePath;
		this.contractId = contractId;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.email = email;
		this.phoneno = phoneno;
		this.alternatePhoneno = alternatePhoneno;
		this.contractType = contractType;
		this.contractTerms = contractTerms;
		this.contractProof = contractProof;
		this.contractLocation = contractLocation;
	}

	public SupStagingTable() {
		super();
		// TODO Auto-generated constructor stub
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

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddressProof() {
		return addressProof;
	}

	public void setAddressProof(String addressProof) {
		this.addressProof = addressProof;
	}

	public String getAddressProofPath() {
		return addressProofPath;
	}

	public void setAddressProofPath(String addressProofPath) {
		this.addressProofPath = addressProofPath;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getSupplierCostCode() {
		return supplierCostCode;
	}

	public void setSupplierCostCode(String supplierCostCode) {
		this.supplierCostCode = supplierCostCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankBic() {
		return bankBic;
	}

	public void setBankBic(String bankBic) {
		this.bankBic = bankBic;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransilRoutingNo() {
		return transilRoutingNo;
	}

	public void setTransilRoutingNo(String transilRoutingNo) {
		this.transilRoutingNo = transilRoutingNo;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankEvidence() {
		return bankEvidence;
	}

	public void setBankEvidence(String bankEvidence) {
		this.bankEvidence = bankEvidence;
	}

	public String getEvidencePath() {
		return evidencePath;
	}

	public void setEvidencePath(String evidencePath) {
		this.evidencePath = evidencePath;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAlternatePhoneno() {
		return alternatePhoneno;
	}

	public void setAlternatePhoneno(String alternatePhoneno) {
		this.alternatePhoneno = alternatePhoneno;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public int getContractTerms() {
		return contractTerms;
	}

	public void setContractTerms(int contractTerms) {
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

	@Override
	public String toString() {
		return "SupStagingTable [supplierCode=" + supplierCode + ", registerId=" + registerId + ", supplierCompName="
				+ supplierCompName + ", supplierContact1=" + supplierContact1 + ", supplierContact2=" + supplierContact2
				+ ", registrationType=" + registrationType + ", regristrationNo=" + regristrationNo + ", status="
				+ status + ", remarks=" + remarks + ", lastlogin=" + lastlogin + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", addressId="
				+ addressId + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", postalCode="
				+ postalCode + ", country=" + country + ", region=" + region + ", addressProof=" + addressProof
				+ ", addressProofPath=" + addressProofPath + ", bankId=" + bankId + ", supplierCostCode="
				+ supplierCostCode + ", bankName=" + bankName + ", bankBranch=" + bankBranch + ", bankBic=" + bankBic
				+ ", bankAccountNo=" + bankAccountNo + ", currency=" + currency + ", transilRoutingNo="
				+ transilRoutingNo + ", chequeNo=" + chequeNo + ", accountHolder=" + accountHolder + ", swiftCode="
				+ swiftCode + ", ifscCode=" + ifscCode + ", bankEvidence=" + bankEvidence + ", evidencePath="
				+ evidencePath + ", contractId=" + contractId + ", departmentId=" + departmentId + ", departmentName="
				+ departmentName + ", email=" + email + ", phoneno=" + phoneno + ", alternatePhoneno="
				+ alternatePhoneno + ", contractType=" + contractType + ", contractTerms=" + contractTerms
				+ ", contractProof=" + contractProof + ", contractLocation=" + contractLocation + "]";
	}
	
	
	
}
