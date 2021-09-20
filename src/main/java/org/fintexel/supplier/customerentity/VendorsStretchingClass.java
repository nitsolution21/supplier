package org.fintexel.supplier.customerentity;

import javax.persistence.Entity;

import org.fintexel.supplier.entity.SupDetails;
public class VendorsStretchingClass {
	private SupDetails supDetails;
	private String supplier;
	public SupDetails getSupDetails() {
		return supDetails;
	}
	public void setSupDetails(SupDetails supDetails) {
		this.supDetails = supDetails;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Override
	public String toString() {
		return "VendorsStretchingClass [supDetails=" + supDetails + ", supplier=" + supplier + "]";
	}
	public VendorsStretchingClass(SupDetails supDetails, String supplier) {
		super();
		this.supDetails = supDetails;
		this.supplier = supplier;
	}
	public VendorsStretchingClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
