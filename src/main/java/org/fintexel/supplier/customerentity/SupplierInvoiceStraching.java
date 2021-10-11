package org.fintexel.supplier.customerentity;

import java.util.List;

public class SupplierInvoiceStraching {
	
	private String POinvoiceNumber;
	private String POamount;
	private String POcustomerName;
	private String POdescription;
	private String POinvoiceDate1;
	private String POnumber;
	private String POregistrationNumber;
	private String POtotalAmountWithoutTax;
	private String POsupplierName;
	private String POtotalGross;
	private String POtotalSubTotal;
	private String POtotalTax;
	private List<PurchesOrderItems> supplierInvoiceItems;
	public String getPOinvoiceNumber() {
		return POinvoiceNumber;
	}
	public void setPOinvoiceNumber(String pOinvoiceNumber) {
		POinvoiceNumber = pOinvoiceNumber;
	}
	public String getPOamount() {
		return POamount;
	}
	public void setPOamount(String pOamount) {
		POamount = pOamount;
	}
	public String getPOcustomerName() {
		return POcustomerName;
	}
	public void setPOcustomerName(String pOcustomerName) {
		POcustomerName = pOcustomerName;
	}
	public String getPOdescription() {
		return POdescription;
	}
	public void setPOdescription(String pOdescription) {
		POdescription = pOdescription;
	}
	public String getPOinvoiceDate1() {
		return POinvoiceDate1;
	}
	public void setPOinvoiceDate1(String pOinvoiceDate1) {
		POinvoiceDate1 = pOinvoiceDate1;
	}
	public String getPOnumber() {
		return POnumber;
	}
	public void setPOnumber(String pOnumber) {
		POnumber = pOnumber;
	}
	public String getPOregistrationNumber() {
		return POregistrationNumber;
	}
	public void setPOregistrationNumber(String pOregistrationNumber) {
		POregistrationNumber = pOregistrationNumber;
	}
	public String getPOtotalAmountWithoutTax() {
		return POtotalAmountWithoutTax;
	}
	public void setPOtotalAmountWithoutTax(String pOtotalAmountWithoutTax) {
		POtotalAmountWithoutTax = pOtotalAmountWithoutTax;
	}
	public String getPOsupplierName() {
		return POsupplierName;
	}
	public void setPOsupplierName(String pOsupplierName) {
		POsupplierName = pOsupplierName;
	}
	public String getPOtotalGross() {
		return POtotalGross;
	}
	public void setPOtotalGross(String pOtotalGross) {
		POtotalGross = pOtotalGross;
	}
	public String getPOtotalSubTotal() {
		return POtotalSubTotal;
	}
	public void setPOtotalSubTotal(String pOtotalSubTotal) {
		POtotalSubTotal = pOtotalSubTotal;
	}
	public String getPOtotalTax() {
		return POtotalTax;
	}
	public void setPOtotalTax(String pOtotalTax) {
		POtotalTax = pOtotalTax;
	}
	public List<PurchesOrderItems> getSupplierInvoiceItems() {
		return supplierInvoiceItems;
	}
	public void setSupplierInvoiceItems(List<PurchesOrderItems> supplierInvoiceItems) {
		this.supplierInvoiceItems = supplierInvoiceItems;
	}
	@Override
	public String toString() {
		return "SupplierInvoiceStraching [POinvoiceNumber=" + POinvoiceNumber + ", POamount=" + POamount
				+ ", POcustomerName=" + POcustomerName + ", POdescription=" + POdescription + ", POinvoiceDate1="
				+ POinvoiceDate1 + ", POnumber=" + POnumber + ", POregistrationNumber=" + POregistrationNumber
				+ ", POtotalAmountWithoutTax=" + POtotalAmountWithoutTax + ", POsupplierName=" + POsupplierName
				+ ", POtotalGross=" + POtotalGross + ", POtotalSubTotal=" + POtotalSubTotal + ", POtotalTax="
				+ POtotalTax + ", supplierInvoiceItems=" + supplierInvoiceItems + "]";
	}
	public SupplierInvoiceStraching(String pOinvoiceNumber, String pOamount, String pOcustomerName,
			String pOdescription, String pOinvoiceDate1, String pOnumber, String pOregistrationNumber,
			String pOtotalAmountWithoutTax, String pOsupplierName, String pOtotalGross, String pOtotalSubTotal,
			String pOtotalTax, List<PurchesOrderItems> supplierInvoiceItems) {
		super();
		POinvoiceNumber = pOinvoiceNumber;
		POamount = pOamount;
		POcustomerName = pOcustomerName;
		POdescription = pOdescription;
		POinvoiceDate1 = pOinvoiceDate1;
		POnumber = pOnumber;
		POregistrationNumber = pOregistrationNumber;
		POtotalAmountWithoutTax = pOtotalAmountWithoutTax;
		POsupplierName = pOsupplierName;
		POtotalGross = pOtotalGross;
		POtotalSubTotal = pOtotalSubTotal;
		POtotalTax = pOtotalTax;
		this.supplierInvoiceItems = supplierInvoiceItems;
	}
	public SupplierInvoiceStraching() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
