package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUS_PROFILE")
public class CustomerProfile {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CID") private Long cId;
	@Column(name = "CUSTOMER_NAME") private String customerName;
	@Column(name = "CUSTOMER_CONTACT1") private String customerContact1;
	@Column(name = "CUSTOMER_CONTACT2") private String customerContact2;
	@Column(name = "REGISTRATION_TYPE") private int registrationType;
	@Column(name = "REGRISTRATION_NO") private String registrationNo;
	@Column(name = "STATUS") private String status;
	@Column(name = "CREATED_BY") private String createdBy;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "UPDATED_ON") private Date updatedOn;
	
	public CustomerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerProfile(Long cId, String customerName, String customerContact1, String customerContact2,
			int registrationType, String registrationNo, String status, String createdBy, Date createdOn,
			String updatedBy, Date updatedOn) {
		super();
		this.cId = cId;
		this.customerName = customerName;
		this.customerContact1 = customerContact1;
		this.customerContact2 = customerContact2;
		this.registrationType = registrationType;
		this.registrationNo = registrationNo;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact1() {
		return customerContact1;
	}

	public void setCustomerContact1(String customerContact1) {
		this.customerContact1 = customerContact1;
	}

	public String getCustomerContact2() {
		return customerContact2;
	}

	public void setCustomerContact2(String customerContact2) {
		this.customerContact2 = customerContact2;
	}

	public int getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(int registrationType) {
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
		return "CustomerProfile [cId=" + cId + ", customerName=" + customerName + ", customerContact1="
				+ customerContact1 + ", customerContact2=" + customerContact2 + ", registrationType=" + registrationType
				+ ", registrationNo=" + registrationNo + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	
	
	
	
	
	
	
}
