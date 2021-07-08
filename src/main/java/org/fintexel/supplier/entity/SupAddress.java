package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_ADDRESS")
public class SupAddress {
	
	@Id
	@Column(name = "ADDRESS_ID")
	private int addressId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "ADDRESS1")
	private String address1;
	
	@Column(name = "ADDRESS2")
	private String address2;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "POSTAL_CODE")
	private int postalCode;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "REGION")
	private String region;
	
	@Column(name = "ADDRESS_PROOF")
	private String addressProof;
	
	@Column(name = "ADDRESS_PROOF_PATH")
	private String addressProofPath;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	public SupAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public SupAddress(int addressId, String supplierCode, String address1, String address2, String city, int postalCode,
			String country, String region, String addressProof, String addressProofPath, int createdBy, Date createdOn,
			int updatedBy, Date updatedOn) {
		super();
		this.addressId = addressId;
		this.supplierCode = supplierCode;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.addressProof = addressProof;
		this.addressProofPath = addressProofPath;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}



	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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


	@Override
	public String toString() {
		return "SupAddress [addressId=" + addressId + ", supplierCode=" + supplierCode + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", postalCode=" + postalCode + ", country=" + country
				+ ", region=" + region + ", addressProof=" + addressProof + ", addressProofPath=" + addressProofPath
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + "]";
	}
	
	
	
	

	
	
	
	
	
	
	
}
