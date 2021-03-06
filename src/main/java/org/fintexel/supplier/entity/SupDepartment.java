package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.validation.FieldValidation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Entity
@Table(name = "SUP_DEPARTMENT")
public class SupDepartment {
	
	@Autowired
	private static FieldValidation fieldValidation;
	
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
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
				+ ", email=" + email + ", phoneno=" + phoneno + ", alternatePhoneno=" + alternatePhoneno + ", status="
				+ status + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + "]";
	}

	public SupDepartment(long departmentId, String supplierCode, String departmentName, String supplierContact1,
			String supplierContact2, String email, String phoneno, String alternatePhoneno, String status,
			int createdBy, Date createdOn, int updatedBy, Date updatedOn) {
		super();
		this.departmentId = departmentId;
		this.supplierCode = supplierCode;
		this.departmentName = departmentName;
		this.supplierContact1 = supplierContact1;
		this.supplierContact2 = supplierContact2;
		this.email = email;
		this.phoneno = phoneno;
		this.alternatePhoneno = alternatePhoneno;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public SupDepartment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public SupDepartment(long departmentId, String supplierCode, String departmentName, String supplierContact1,
			 String email, String phoneno, String status) {
		super();
		this.departmentId = departmentId;
		this.supplierCode = supplierCode;
		this.departmentName = departmentName;
		this.supplierContact1 = supplierContact1;
//		this.supplierContact2 = supplierContact2;
		this.email = email;
		this.phoneno = phoneno;
//		this.alternatePhoneno = alternatePhoneno;
		this.status = status;

	}
	
	public SupDepartment(String supplierCode) {
		
	}
	
	
	public static SupDepartment fromJson(String value) {
		 JsonObject obj = (JsonObject) JsonParser.parseString(value);
		 System.out.println(value);
		 try {
			 SupDepartment department =  new SupDepartment (Long.parseLong(obj.get("departmentId").toString()) ,
		    		 obj.get("supplierCode").toString(), (String) obj.get("departmentName").toString(),
		    		(String) obj.get("supplierContact1").toString(),// (String) obj.get("supplierContact2").toString(),
		    		(String) obj.get("email").toString(), (String) obj.get("phoneno").toString(),
//		    		(String) obj.get("alternatePhoneno").toString(),
		    		(String) obj.get("status").toString());
			 try {
				if (fieldValidation.isEmpty((String) obj.get("supplierContact2").toString()) && fieldValidation.isEmpty((String) obj.get("alternatePhoneno").toString())) {
					department.setAlternatePhoneno((String) obj.get("alternatePhoneno").toString());
					department.setSupplierContact2((String) obj.get("supplierContact2").toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			 
			 return department;
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
}
