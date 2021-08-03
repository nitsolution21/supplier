package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private int createdBy;
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
	@Override
	public String toString() {
		return "UploadEntity [slno=" + slno + ", email=" + email + ", supplierCode=" + supplierCode + ", registerId="
				+ registerId + ", supplierCompName=" + supplierCompName + ", registrationType=" + registrationType
				+ ", registrationNo=" + registrationNo + ", status=" + status + ", costCenter=" + costCenter
				+ ", remarks=" + remarks + ", lastlogin=" + lastlogin + ", createdOn=" + createdOn + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", addressId=" + addressId
				+ ", addressType=" + addressType + ", address1=" + address1 + ", address2=" + address2 + ", city="
				+ city + ", postalCode=" + postalCode + ", country=" + country + ", region=" + region
				+ ", addressProof=" + addressProof + ", addressProofPath=" + addressProofPath + "]";
	}
	
	
	
	
}
