package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PO_KPI_Suppliers",procedureName = "PO_KPI_Suppliers")})
public class PoKpiSuppliersEntity {
	
	
	@Id
	@Column(name="Total Generated Orders")
	private long totalGeneratedOrders;
	
	@Column(name="Total Confirmed Orders")
	private long totalConfirmedOrders;

	public PoKpiSuppliersEntity(long totalGeneratedOrders, long totalConfirmedOrders) {
		super();
		this.totalGeneratedOrders = totalGeneratedOrders;
		this.totalConfirmedOrders = totalConfirmedOrders;
	}

	public PoKpiSuppliersEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getTotalGeneratedOrders() {
		return totalGeneratedOrders;
	}

	public void setTotalGeneratedOrders(long totalGeneratedOrders) {
		this.totalGeneratedOrders = totalGeneratedOrders;
	}

	public long getTotalConfirmedOrders() {
		return totalConfirmedOrders;
	}

	public void setTotalConfirmedOrders(long totalConfirmedOrders) {
		this.totalConfirmedOrders = totalConfirmedOrders;
	}

	@Override
	public String toString() {
		return "PoKpiSuppliersEntity [totalGeneratedOrders=" + totalGeneratedOrders + ", totalConfirmedOrders="
				+ totalConfirmedOrders + ", getTotalGeneratedOrders()=" + getTotalGeneratedOrders()
				+ ", getTotalConfirmedOrders()=" + getTotalConfirmedOrders() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
