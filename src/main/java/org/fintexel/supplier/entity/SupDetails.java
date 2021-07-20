package org.fintexel.supplier.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUP_DETAILS")
public class SupDetails {
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
	
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = VendorRegister.class)
//	@JoinColumn(name = "REGISTER_ID", insertable = false, updatable = false)
//	private VendorRegister user;


	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long supId) {
		this.registerId = supId;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public String getRegristrationNo() {
		return registrationNo;
	}

	public void setRegristrationNo(String registrationNo) {
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

//	public VendorRegister getUser() {
//		return user;
//	}
//
//	public void setUser(VendorRegister user) {
//		this.user = user;
//	}

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
				+ ", updatedOn=" + updatedOn ;
	}
	
	


	
	
	
	
	
}
