package org.fintexel.supplier.entity;

import java.util.Date;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.fintexel.supplier.validation.FieldValidation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.JsonObject;

@Component
@Entity
@Table(name = "SUP_ADDRESS")
public class SupAddress {
	
	@Autowired
	private static FieldValidation fieldValidation;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ADDRESS_ID")
	private Long addressId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "ADDRESS_TYPE")
	private String addressType;
	
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
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ADDRESS_PROOF")
	private String addressProof;
	
	@Column(name = "ADDRESS_PROOF_PATH")
	private String addressProofPath;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;

	@Override
	public String toString() {
		return "SupAddress [addressId=" + addressId + ", supplierCode=" + supplierCode + ", addressType=" + addressType
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", postalCode=" + postalCode
				+ ", country=" + country + ", region=" + region + ", status=" + status + ", addressProof="
				+ addressProof + ", addressProofPath=" + addressProofPath + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public SupAddress(Long addressId, String supplierCode, String addressType, String address1, String address2,
			String city, int postalCode, String country, String region, String status, String addressProof,
			String addressProofPath, int createdBy, Date createdOn, int updatedBy, Date updatedOn) {
		super();
		this.addressId = addressId;
		this.supplierCode = supplierCode;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.status = status;
		this.addressProof = addressProof;
		this.addressProofPath = addressProofPath;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public SupAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public SupAddress(String supplierCode) {
		
	}
	

	
	public SupAddress(long addressId, String supplierCode, String addressType, String address1,
			String city, int postalCode, String country, String region, String addressProof,
			String addressProofPath) {
		super();
		this.addressId = addressId;
		this.supplierCode = supplierCode;
		this.addressType = addressType;
		this.address1 = address1;
//		this.address2 = address2;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.region = region;
		this.addressProof = addressProof;
		this.addressProofPath = addressProofPath;
	}

	
	public static SupAddress fromJson(String value) throws Exception {
		try {
			  JsonObject obj = (JsonObject) JsonParser.parseString(value);
		     SupAddress supAddress = new SupAddress (Long.parseLong(obj.get("addressId").toString()) , (String) obj.get("supplierCode").toString() , (String) obj.get("addressType").toString() ,
		    		(String) obj.get("address1").toString() , 
//		    		obj.get("address2").toString() ,
		    		(String) obj.get("city").toString() ,Integer.parseInt( obj.get("postalCode").toString()) ,
		    		(String) obj.get("country").toString() , (String) obj.get("region").toString() ,
		    		(String) obj.get("addressProof").toString() , (String) obj.get("addressProofPath").toString());
		     try {
				if (fieldValidation.isEmpty((String) obj.get("address2").toString())) {
					supAddress.setAddress2((String) obj.get("address2").toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		     return supAddress;
		}catch(Exception e) {
			 System.out.print("Json "+ e.toString() );
			throw new Exception();
		}
		
	}
	
	
	
	
}
