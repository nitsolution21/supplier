package org.fintexel.supplier.customerentity;

import java.util.Date;

public class CustomerAddressResponse {
	private Long addressId;
	private Long cId;
	private String addressType;
	private String address1;
	private String address2;
	private String city;
	private String postalCode;
	private String country;
	private String region;
	private long regionId;
	private String addressProof;
	private String addressProofPath;
	private String status;
	private int isPrimary;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	public CustomerAddressResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerAddressResponse(Long addressId, Long cId, String addressType, String address1, String address2,
			String city, String postalCode, String country, String region, long regionId, String addressProof,
			String addressProofPath, String status, int isPrimary, String createdBy, Date createdOn, String updatedBy,
			Date updatedOn) {
		super();
		this.addressId = addressId;
		this.cId = cId;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.regionId = regionId;
		this.addressProof = addressProof;
		this.addressProofPath = addressProofPath;
		this.status = status;
		this.isPrimary = isPrimary;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "CustomerAddressResponse [addressId=" + addressId + ", cId=" + cId + ", addressType=" + addressType
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", postalCode=" + postalCode
				+ ", country=" + country + ", region=" + region + ", regionId=" + regionId + ", addressProof="
				+ addressProof + ", addressProofPath=" + addressProofPath + ", status=" + status + ", isPrimary="
				+ isPrimary + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + "]";
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
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
	public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
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
	public int getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
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
