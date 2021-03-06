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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUP_REGISTER")
public class VendorRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REGISTER_ID")
	private long registerId;
	
	public VendorRegister(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

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
	
	@Column(name = "FL_PROCESS_ID")
	private String processId;
	
	@Column(name = "FL_TASK_ID")
	private String taskId;
	
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

	public VendorRegister(long registerId, String email, String supplierCompName, String username, String password,
			String status, String processId, String taskId, int createdBy, Date createdOn, int updatedBy,
			Date updatedOn) {
		super();
		this.registerId = registerId;
		this.email = email;
		this.supplierCompName = supplierCompName;
		this.username = username;
		this.password = password;
		this.status = status;
		this.processId = processId;
		this.taskId = taskId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public VendorRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "VendorRegister [registerId=" + registerId + ", email=" + email + ", supplierCompName="
				+ supplierCompName + ", username=" + username + ", password=" + password + ", status=" + status
				+ ", processId=" + processId + ", taskId=" + taskId + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}

	public long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(long registerId) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	

	

	
	
}
