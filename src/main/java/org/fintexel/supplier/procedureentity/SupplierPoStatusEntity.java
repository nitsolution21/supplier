package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "SUPPLIER_PO_STATUS",procedureName = "SUPPLIER_PO_STATUS")})
public class SupplierPoStatusEntity {
	
	
	@Id
	@Column(name="Supplier")
	private String supplier;
	
	@Column(name="CREATED_ON")
	private String createdOn;
	
	@Column(name="Total_PO_Pending")
	private long totalPoPending;

	public SupplierPoStatusEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SupplierPoStatusEntity(String supplier, String createdOn, long totalPoPending) {
		super();
		this.supplier = supplier;
		this.createdOn = createdOn;
		this.totalPoPending = totalPoPending;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public long getTotalPoPending() {
		return totalPoPending;
	}

	public void setTotalPoPending(long totalPoPending) {
		this.totalPoPending = totalPoPending;
	}

	@Override
	public String toString() {
		return "SupplierPoStatus [supplier=" + supplier + ", createdOn=" + createdOn + ", totalPoPending="
				+ totalPoPending + ", getSupplier()=" + getSupplier() + ", getCreatedOn()=" + getCreatedOn()
				+ ", getTotalPoPending()=" + getTotalPoPending() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	

}
