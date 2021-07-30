package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUS_DEPARTMENTS")
public class CustomerDepartments {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPARTMENT_ID") private Long departmentId;
	@Column(name = "CID") private Long cId;
	@Column(name = "DEPARTMENT_NAME") private String departmentName;
	@Column(name = "EMAIL") private String email;
	@Column(name = "PHONENO") private String phoneNo;
	@Column(name = "ALTERNATE_PHONENO") private String alternatePhoneNo;
	@Column(name = "STATUS") private String status;
	@Column(name = "CREATED_BY") private String createdBy;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "UPDATED_ON") private Date updatedOn;
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAlternatePhoneNo() {
		return alternatePhoneNo;
	}
	public void setAlternatePhoneNo(String alternatePhoneNo) {
		this.alternatePhoneNo = alternatePhoneNo;
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
		return "CustomerDepartments [departmentId=" + departmentId + ", departmentName=" + departmentName + ", email="
				+ email + ", phoneNo=" + phoneNo + ", alternatePhoneNo=" + alternatePhoneNo + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + "]";
	}
	
	
	
	
	public CustomerDepartments(Long departmentId, String departmentName, String email, String phoneNo,
			String alternatePhoneNo, String status, String createdBy, Date createdOn, String updatedBy,
			Date updatedOn) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.alternatePhoneNo = alternatePhoneNo;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	
	
	
	
	public CustomerDepartments() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
