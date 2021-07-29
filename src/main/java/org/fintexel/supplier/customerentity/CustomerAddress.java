package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUS_ADDRESS")
public class CustomerAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ADDRESS_ID") private Long addressId;
	@Column(name = "ADDRESS_TYPE") private String addressType;
	@Column(name = "ADDRESS1") private String address1;
	@Column(name = "ADDRESS2") private String address2;
	@Column(name = "CITY") private String city;
	@Column(name = "POSTAL_CODE") private String postalCode;
	@Column(name = "COUNTRY") private String country;
	@Column(name = "REGION") private String region;
	@Column(name = "ADDRESS_PROOF") private String addressProof;
	@Column(name = "ADDRESS_PROOF_PATH") private String addressProofPath;
	@Column(name = "STATUS") private String status;
	@Column(name = "CREATED_BY") private String createdBy;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "UPDATED_ON") private Date updatedOn;
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
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Override
	public String toString() {
		return "CustomerAddress [addressId=" + addressId + ", addressType=" + addressType + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", postalCode=" + postalCode + ", country=" + country
				+ ", region=" + region + ", addressProof=" + addressProof + ", addressProofPath=" + addressProofPath
				+ ", status=" + status + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	public CustomerAddress(Long addressId, String addressType, String address1, String address2, String city,
			String postalCode, String country, String region, String addressProof, String addressProofPath,
			String status, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
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
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	public CustomerAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
