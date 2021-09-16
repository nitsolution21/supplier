package org.fintexel.supplier.procedureentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PO_Amount_By_Status",procedureName = "PO_Amount_By_Status")})
public class PoAmountByStatusEntity {
	
	@Id
	@Column(name="status")
	private String status;
	
	@Column(name="CREATED_ON")
	private Date createdOn;
	
	@Column(name="Total")
	private float total;

	public PoAmountByStatusEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PoAmountByStatusEntity(String status, Date createdOn, float total) {
		super();
		this.status = status;
		this.createdOn = createdOn;
		this.total = total;
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
		return "PoAmountByStatusEntity [status=" + status + ", createdOn=" + createdOn + ", total=" + total
				+ ", getStatus()=" + getStatus() + ", getCreatedOn()=" + getCreatedOn() + ", getTotal()=" + getTotal()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
