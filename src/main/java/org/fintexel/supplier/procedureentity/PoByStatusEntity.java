package org.fintexel.supplier.procedureentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PO_BY_STATUS",procedureName = "PO_BY_STATUS")})
public class PoByStatusEntity {

	@Id
	@Column(name="status")
	private String status;
	
	@Column(name="CREATED_ON")
	private Date createdOn;
	
	@Column(name="Total")
	private float total;

	public PoByStatusEntity(String status, Date createdOn, float total) {
		super();
		this.status = status;
		this.createdOn = createdOn;
		this.total = total;
	}

	public PoByStatusEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PoByStatusEntity [status=" + status + ", createdOn=" + createdOn + ", total=" + total + ", getStatus()="
				+ getStatus() + ", getCreatedOn()=" + getCreatedOn() + ", getTotal()=" + getTotal() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
