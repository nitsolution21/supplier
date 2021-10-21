package org.fintexel.supplier.customerentity;

import java.util.List;

public class InvoiceStraching {
	
	private SupplierInvoice supplierInvoice;
	private List<PurchesOrderItems> purchesOrderItems;
	private SupplierInvoiceStraching supplierInvoiceStraching;
	
	
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
	public SupplierInvoiceStraching getSupplierInvoiceStraching() {
		return supplierInvoiceStraching;
	}
	public void setSupplierInvoiceStraching(SupplierInvoiceStraching supplierInvoiceStraching) {
		this.supplierInvoiceStraching = supplierInvoiceStraching;
	}
	@Override
	public String toString() {
		return "InvoiceStraching [supplierInvoice=" + supplierInvoice + ", purchesOrderItems=" + purchesOrderItems
				+ ", supplierInvoiceStraching=" + supplierInvoiceStraching + "]";
	}
	public InvoiceStraching(SupplierInvoice supplierInvoice, List<PurchesOrderItems> purchesOrderItems,
			SupplierInvoiceStraching supplierInvoiceStraching) {
		super();
		this.supplierInvoice = supplierInvoice;
		this.purchesOrderItems = purchesOrderItems;
		this.supplierInvoiceStraching = supplierInvoiceStraching;
	}
	public InvoiceStraching() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	

}
