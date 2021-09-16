package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "SUPPLIER_CONTRACT",procedureName = "SUPPLIER_CONTRACT")})
public class SupplierContractEntity {

	
	@Id
	@Column(name="SUPPLIER")
	private String supplier;
	
	@Column(name="Range")
	private String range;

	public SupplierContractEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SupplierContractEntity(String supplier, String range) {
		super();
		this.supplier = supplier;
		this.range = range;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@Override
	public String toString() {
		return "SupplierContractEntity [supplier=" + supplier + ", range=" + range + ", getSupplier()=" + getSupplier()
				+ ", getRange()=" + getRange() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
