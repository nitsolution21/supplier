package org.fintexel.supplier.procedureentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PENDING_PO_SUPPLIER",procedureName = "PENDING_PO_SUPPLIER")})
public class Pending_PO_Supplier_Entity {

	@Id
	@Column(name="Supplier")
	private String supplierCompName;
	
	@Column(name="CREATED_ON")
	private Date createdOn;
	
	@Column(name="Total_PO_Pending")
	private int totalPoPending;

	
	public Pending_PO_Supplier_Entity(String supplierCompName, Date createdOn, int totalPoPending) {
		super();
		this.supplierCompName = supplierCompName;
		this.createdOn = createdOn;
		this.totalPoPending = totalPoPending;
	}


	public Pending_PO_Supplier_Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public String getSupplierCompName() {
		return supplierCompName;
	}


	public void setSupplierCompName(String supplierCompName) {
		this.supplierCompName = supplierCompName;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public int getPoId() {
		return totalPoPending;
	}


	public void setPoId(int totalPoPending) {
		this.totalPoPending = totalPoPending;
	}


	@Override
	public String toString() {
		return "PENDING_PO_SUPPLIER [supplierCompName=" + supplierCompName + ", createdOn=" + createdOn + ", totalPoPending="
				+ totalPoPending + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
