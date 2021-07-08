package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUP_REGISTER")
public class VendorRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
