package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_USER_DEPTS")
public class CustomerUserDepartments {
	
	@Id
	@Column(name = "USER_ID") 
	private long userId;
	
	@Column(name = "DEPARTMENT_ID") 
	private long departmentId;
	
	@Column(name = "CREATED_BY") 
	private String createdBy;
	
	@Column(name = "CREATED_ON") 
	private Date createdOn;
	
	@Column(name = "UPDATED_BY") 
	private String updatedBy;
	
	@Column(name = "UPDATED_ON") 
	private Date updatedOn;

	public CustomerUserDepartments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerUserDepartments(long userId, long departmentId, String createdBy, Date createdOn, String updatedBy,
			Date updatedOn) {
		super();
		this.userId = userId;
		this.departmentId = departmentId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
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
		return "CustomerUserDepartments [userId=" + userId + ", departmentId=" + departmentId + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ "]";
	}

	
	
}
