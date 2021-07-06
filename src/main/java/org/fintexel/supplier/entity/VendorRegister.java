package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_REGISTER")
public class VendorRegister {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REGISTER_ID")
	private int registerId;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "SUPPLIER_COMP_NAME")
	private String supplierCompName;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;
	
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
	
	public int getRegisterId() {
		return registerId;
	}
	public void setRegisterId(int registerId) {
		this.registerId = registerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSupplierCompName() {
		return supplierCompName;
	}
	public void setSupplierCompName(String supplierCompName) {
		this.supplierCompName = supplierCompName;
	}
	public String getStatus() {
		return status;
	}
	@Override
	public String toString() {
		return "Vendor [registerId=" + registerId + ", email=" + email + ", supplierCompName=" + supplierCompName
				+ ", status=" + status + "]";
	}
	public VendorRegister() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public VendorRegister(int registerId, String email, String supplierCompName, String status) {
		super();
		this.registerId = registerId;
		this.email = email;
		this.supplierCompName = supplierCompName;
		this.status = status;
	}
	
	
}
