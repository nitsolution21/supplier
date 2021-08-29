package org.fintexel.supplier.customerentity;

import java.util.List;

public class InvoiceStraching {
	
	private SupplierInvoice supplierInvoice;
	private List<PurchesOrderItems> purchesOrderItems;
	public SupplierInvoice getSupplierInvoice() {
		return supplierInvoice;
	}
	public void setSupplierInvoice(SupplierInvoice supplierInvoice) {
		this.supplierInvoice = supplierInvoice;
	}
	public List<PurchesOrderItems> getPurchesOrderItems() {
		return purchesOrderItems;
	}
	public void setPurchesOrderItems(List<PurchesOrderItems> purchesOrderItems) {
		this.purchesOrderItems = purchesOrderItems;
	}
	@Override
	public String toString() {
		return "InvoiceStraching [supplierInvoice=" + supplierInvoice + ", purchesOrderItems=" + purchesOrderItems
				+ "]";
	}
	public InvoiceStraching(SupplierInvoice supplierInvoice, List<PurchesOrderItems> purchesOrderItems) {
		super();
		this.supplierInvoice = supplierInvoice;
		this.purchesOrderItems = purchesOrderItems;
	}
	public InvoiceStraching() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
