package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class UploadEntity {

	
	private String slno;
	
	private String email;
	

	private String supplierCode;
	private Long registerId;
	private String supplierCompName;
	private String registrationType;
	private String registrationNo; 
	private String status;
	private String costCenter;
	private String remarks;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date lastlogin;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date createdOn;
	private int createdBy;
	@Column(name = "UPDATED_BY") private int updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date updatedOn;
	
	
	private Long addressId;
	private String addressType;
	private String address1;
	private String address2;
	private String city;
	private int postalCode;
	private String country;
	private String region;
	private String addressProof;
	private String addressProofPath;
	
	
	
	private long bankId;
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
	
	
	
	private long departmentId;
	private String departmentName;
	private String supplierContact1;
	private String supplierContact2;
	private String phoneno;
	private String supEmail;
	private String alternatePhoneno;
	
	

	private Long contractId;
	private String contractType;
	private int contractTerms;
	private String contractProof;
	private String contractLocation;
	
	
	
	private String countryName;
	private String regionName;
	
	
	
	private int cId;
	private String name;
	private String type;
	private int order;
	
	
	
	
	
	public String getSlno() {
		return slno;
	}
	public void setSlno(String slno) {
		this.slno = slno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
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
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
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
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
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
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getSupEmail() {
		return supEmail;
	}
	public void setSupEmail(String supEmail) {
		this.supEmail = supEmail;
	}
	public String getAlternatePhoneno() {
		return alternatePhoneno;
	}
	public void setAlternatePhoneno(String alternatePhoneno) {
		this.alternatePhoneno = alternatePhoneno;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
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
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
	
	
	@Override
	public String toString() {
		return "UploadEntity [slno=" + slno + ", email=" + email + ", supplierCode=" + supplierCode + ", registerId="
				+ registerId + ", supplierCompName=" + supplierCompName + ", registrationType=" + registrationType
				+ ", registrationNo=" + registrationNo + ", status=" + status + ", costCenter=" + costCenter
				+ ", remarks=" + remarks + ", lastlogin=" + lastlogin + ", createdOn=" + createdOn + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", addressId=" + addressId
				+ ", addressType=" + addressType + ", address1=" + address1 + ", address2=" + address2 + ", city="
				+ city + ", postalCode=" + postalCode + ", country=" + country + ", region=" + region
				+ ", addressProof=" + addressProof + ", addressProofPath=" + addressProofPath + ", bankId=" + bankId
				+ ", bankName=" + bankName + ", bankBranch=" + bankBranch + ", bankBic=" + bankBic + ", bankAccountNo="
				+ bankAccountNo + ", currency=" + currency + ", transilRoutingNo=" + transilRoutingNo + ", chequeNo="
				+ chequeNo + ", accountHolder=" + accountHolder + ", swiftCode=" + swiftCode + ", ifscCode=" + ifscCode
				+ ", bankEvidence=" + bankEvidence + ", evidencePath=" + evidencePath + ", departmentId=" + departmentId
				+ ", departmentName=" + departmentName + ", supplierContact1=" + supplierContact1
				+ ", supplierContact2=" + supplierContact2 + ", phoneno=" + phoneno + ", supEmail=" + supEmail
				+ ", alternatePhoneno=" + alternatePhoneno + ", contractId=" + contractId + ", contractType="
				+ contractType + ", contractTerms=" + contractTerms + ", contractProof=" + contractProof
				+ ", contractLocation=" + contractLocation + ", countryName=" + countryName + ", regionName="
				+ regionName + ", cId=" + cId + ", name=" + name + ", type=" + type + ", order=" + order + "]";
	}
	
	
	
	public UploadEntity(String slno, String email, String supplierCode, Long registerId, String supplierCompName,
			String registrationType, String registrationNo, String status, String costCenter, String remarks,
			Date lastlogin, Date createdOn, int createdBy, int updatedBy, Date updatedOn, Long addressId,
			String addressType, String address1, String address2, String city, int postalCode, String country,
			String region, String addressProof, String addressProofPath, long bankId, String bankName,
			String bankBranch, String bankBic, String bankAccountNo, String currency, String transilRoutingNo,
			String chequeNo, String accountHolder, String swiftCode, String ifscCode, String bankEvidence,
			String evidencePath, long departmentId, String departmentName, String supplierContact1,
			String supplierContact2, String phoneno, String supEmail, String alternatePhoneno, Long contractId,
			String contractType, int contractTerms, String contractProof, String contractLocation, String countryName,
			String regionName, int cId, String name, String type, int order) {
		super();
		this.slno = slno;
		this.email = email;
		this.supplierCode = supplierCode;
		this.registerId = registerId;
		this.supplierCompName = supplierCompName;
		this.registrationType = registrationType;
		this.registrationNo = registrationNo;
		this.status = status;
		this.costCenter = costCenter;
		this.remarks = remarks;
		this.lastlogin = lastlogin;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.addressId = addressId;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.addressProof = addressProof;
		this.addressProofPath = addressProofPath;
		this.bankId = bankId;
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
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.supplierContact1 = supplierContact1;
		this.supplierContact2 = supplierContact2;
		this.phoneno = phoneno;
		this.supEmail = supEmail;
		this.alternatePhoneno = alternatePhoneno;
		this.contractId = contractId;
		this.contractType = contractType;
		this.contractTerms = contractTerms;
		this.contractProof = contractProof;
		this.contractLocation = contractLocation;
		this.countryName = countryName;
		this.regionName = regionName;
		this.cId = cId;
		this.name = name;
		this.type = type;
		this.order = order;
	}
	
	
	
	public UploadEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	
	
	
	
	
}
