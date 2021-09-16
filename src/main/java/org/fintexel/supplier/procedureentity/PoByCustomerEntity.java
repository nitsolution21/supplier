package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PO_BY_CUSTOMER",procedureName = "PO_BY_CUSTOMER")})
public class PoByCustomerEntity {
	
	@Id
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="Total_PO")
	private long totalPo;

	public PoByCustomerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PoByCustomerEntity(String customerName, String status, long totalPo) {
		super();
		this.customerName = customerName;
		this.status = status;
		this.totalPo = totalPo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotalPo() {
		return totalPo;
	}

	public void setTotalPo(long totalPo) {
		this.totalPo = totalPo;
	}

	@Override
	public String toString() {
		return "PoByCustomerEntity [customerName=" + customerName + ", status=" + status + ", totalPo=" + totalPo
				+ ", getCustomerName()=" + getCustomerName() + ", getStatus()=" + getStatus() + ", getTotalPo()="
				+ getTotalPo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
