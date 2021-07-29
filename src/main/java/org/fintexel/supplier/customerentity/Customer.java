package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUSTOMERS")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CID") private Long cId;
	@Column(name = "EMAIL") private String email;
	@Column(name = "NAME") private String name;
	@Column(name = "USERNAME") private String username;
	@Column(name = "PASSWORD") private String password;
	@Column(name = "ROLE") private String role;
	@Column(name = "STATUS") private String status;
	@Column(name = "CREATED_BY") private String createdBy;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "UPDATED_ON") private Date updatedOn;

	
	
	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
		return "Customer [cId=" + cId + ", email=" + email + ", name=" + name + ", username=" + username + ", password="
				+ password + ", role=" + role + ", status=" + status + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}

	
	
	public Customer(Long cId, String email, String name, String username, String password, String role, String status,
			String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.cId = cId;
		this.email = email;
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
