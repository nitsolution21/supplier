package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_USER_FUNC")
public class CustomerUserFunctionaliti {
	
	@Id
	@Column(name = "USER_ID")
	private long userId;
	
	@Column(name = "F_ID")
	private long fId;
	
	@Column(name = "CREATED_BY") 
	private String createdBy;
	
	@Column(name = "CREATED_ON") 
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_ON") 
	private Date updatedOn;

	public CustomerUserFunctionaliti(long userId, long fId, String createdBy, Date createdOn, String updatedBy,
			Date updatedOn) {
		super();
		this.userId = userId;
		this.fId = fId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public CustomerUserFunctionaliti() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getfId() {
		return fId;
	}

	public void setfId(long fId) {
		this.fId = fId;
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
	
	
}
