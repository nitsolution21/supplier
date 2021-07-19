package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_DEPARTMENT")
public class SupDepartment {
	
	@Id
	@Column(name = "DEPARTMENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long departmentId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;
	
	@Column(name = "SUPPLIER_CONTACT1")
	private String supplierContact1;
	
	@Column(name = "SUPPLIER_CONTACT2")
	private String supplierContact2;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONENO")
	private String phoneno;
	
	@Column(name = "ALTERNATE_PHONENO")
	private String alternatePhoneno;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	public SupDepartment(long departmentId, String supplierCode, String departmentName, String supplierContact1, String supplierContact2, String email, String phoneno,
			String alternatePhoneno, int createdBy, Date createdOn, int updatedBy, Date updatedOn) {
		super();
		this.departmentId = departmentId;
		this.supplierCode = supplierCode;
		this.departmentName = departmentName;
		this.email = email;
		this.phoneno = phoneno;
		this.alternatePhoneno = alternatePhoneno;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.supplierContact1 = supplierContact1;
		this.supplierContact2 = supplierContact2;
	}

	public SupDepartment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAlternatePhoneno() {
		return alternatePhoneno;
	}

	public void setAlternatePhoneno(String alternatePhoneno) {
		this.alternatePhoneno = alternatePhoneno;
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
		return "SupDepartment [departmentId=" + departmentId + ", supplierCode=" + supplierCode + ", departmentName="
				+ departmentName + ", supplierContact1=" + supplierContact1 + ", supplierContact2=" + supplierContact2
				+ ", email=" + email + ", phoneno=" + phoneno + ", alternatePhoneno=" + alternatePhoneno
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + "]";
	}
	
	
	
	
	
}
