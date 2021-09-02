package org.fintexel.supplier.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fintexel.supplier.validation.FieldValidation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
@Entity
@Table(name = "SUP_DETAILS")
public class SupDetails {
	@Autowired
	private static FieldValidation fieldValidation;
	
	@Id
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "REGISTER_ID" )
	private Long registerId;
	
	@Column(name = "SUPPLIER_COMP_NAME")
	private String supplierCompName;
	
	@Column(name = "REGISTRATION_TYPE")
	private String registrationType;
	
	@Column(name = "REGRISTRATION_NO")
	private String registrationNo; 
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name="COST_CENTER")
	private String costCenter;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "LAST_LOGIN",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastlogin;
	
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

	public SupDetails(String supplierCode, Long registerId, String supplierCompName, String registrationType,
			String registrationNo, String status, String costCenter, String remarks, Date lastlogin, int createdBy,
			Date createdOn, int updatedBy, Date updatedOn) {
		super();
		this.supplierCode = supplierCode;
		this.registerId = registerId;
		this.supplierCompName = supplierCompName;
		this.registrationType = registrationType;
		this.registrationNo = registrationNo;
		this.status = status;
		this.costCenter = costCenter;
		this.remarks = remarks;
		this.lastlogin = lastlogin;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public SupDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SupDetails [supplierCode=" + supplierCode + ", registerId=" + registerId + ", supplierCompName="
				+ supplierCompName + ", registrationType=" + registrationType + ", registrationNo=" + registrationNo
				+ ", status=" + status + ", costCenter=" + costCenter + ", remarks=" + remarks + ", lastlogin="
				+ lastlogin + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + "]";
	}
	
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = VendorRegister.class)
//	@JoinColumn(name = "REGISTER_ID", insertable = false, updatable = false)
//	private VendorRegister user;



	public SupDetails(String supplierCode, Long registerId, String supplierCompName, String registrationType,
			String registrationNo, String status ) {
		super();
		this.supplierCode = supplierCode;
		this.registerId = registerId;
		this.supplierCompName = supplierCompName;
		this.registrationType = registrationType;
		this.registrationNo = registrationNo;
		this.status = status;
		
		
//		this.remarks = remarks;
//		this.lastlogin = lastLogin;
	}
	
	
	public static SupDetails fromJson(String value) throws Exception {
		 System.out.println("ok  " +value);
		 JsonObject obj = (JsonObject) JsonParser.parseString(value);
		 System.out.println("ok  " +obj);
		 try {
			  SupDetails supDetails = new SupDetails ((String) obj.get("supplierCode").toString().replace("\"", ""),
			    		Long.parseLong((String) obj.get("registerId").toString())  , (String) obj.get("supplierCompName").toString().replace("\"", "") ,
			    		(String) obj.get("registrationType").toString().replace("\"", "") , (String) obj.get("registrationNo").toString().replace("\"", "") ,
			    		(String) obj.get("status").toString().replace("\"", "") 
			    		);
				DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime lastLoginNow = LocalDateTime.now();
				  Date lastLogin=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastLoginNow.format(lastLogingFormat));  
			  supDetails.setLastlogin(lastLogin);
			  try {
				if(fieldValidation.isEmpty((String) obj.get("remarks").toString())) {
					supDetails.setRemarks(obj.get("remarks").toString().replace("\"", ""));
				}
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				 throw new Exception(e);
			}
			  return supDetails;
		 }catch(Exception e) {
			 System.out.println(e);
			 throw new Exception(e);
			 
		 }
		  
	}
	


	
	
	
	
	
}
