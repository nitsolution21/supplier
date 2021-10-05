package org.fintexel.supplier.customerentity;

import java.util.List;

import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;

public class PdfGenStrachingClass {
	private String companyName;
	private String link;
	private String fromCompName;
	private String fromSupAdd;
	private String fromCity;
	private String fromPostalCode;
	private String fromReg;
	private String fromCountry;
	private String toCompName;
	private String toSubAdd;
	private String toSubcity;
	private String toSubPostalCode;
	private String toSubReg;
	private String toSubCountry;
	private String dept;
	private String catagory;
	private String subCatagory;
	private String po;
	private String totalAmount;
	private String paymentTerms;
	private String comment;
	private String shipToCode;
	private String shipToReg;
	private String shipToCountry;
	private String deliverToName;
	private String deliverToEmail;
	
	private List<RequestStrachingClass> itemCategory;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFromCompName() {
		return fromCompName;
	}

	public void setFromCompName(String fromCompName) {
		this.fromCompName = fromCompName;
	}

	public String getFromSupAdd() {
		return fromSupAdd;
	}

	public void setFromSupAdd(String fromSupAdd) {
		this.fromSupAdd = fromSupAdd;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromPostalCode() {
		return fromPostalCode;
	}

	public void setFromPostalCode(String fromPostalCode) {
		this.fromPostalCode = fromPostalCode;
	}

	public String getFromReg() {
		return fromReg;
	}

	public void setFromReg(String fromReg) {
		this.fromReg = fromReg;
	}

	public String getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
	}

	public String getToCompName() {
		return toCompName;
	}

	public void setToCompName(String toCompName) {
		this.toCompName = toCompName;
	}

	public String getToSubAdd() {
		return toSubAdd;
	}

	public void setToSubAdd(String toSubAdd) {
		this.toSubAdd = toSubAdd;
	}

	public String getToSubcity() {
		return toSubcity;
	}

	public void setToSubcity(String toSubcity) {
		this.toSubcity = toSubcity;
	}

	public String getToSubPostalCode() {
		return toSubPostalCode;
	}

	public void setToSubPostalCode(String toSubPostalCode) {
		this.toSubPostalCode = toSubPostalCode;
	}

	public String getToSubReg() {
		return toSubReg;
	}

	public void setToSubReg(String toSubReg) {
		this.toSubReg = toSubReg;
	}

	public String getToSubCountry() {
		return toSubCountry;
	}

	public void setToSubCountry(String toSubCountry) {
		this.toSubCountry = toSubCountry;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String getSubCatagory() {
		return subCatagory;
	}

	public void setSubCatagory(String subCatagory) {
		this.subCatagory = subCatagory;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getShipToCode() {
		return shipToCode;
	}

	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}

	public String getShipToReg() {
		return shipToReg;
	}

	public void setShipToReg(String shipToReg) {
		this.shipToReg = shipToReg;
	}

	public String getShipToCountry() {
		return shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public String getDeliverToName() {
		return deliverToName;
	}

	public void setDeliverToName(String deliverToName) {
		this.deliverToName = deliverToName;
	}

	public String getDeliverToEmail() {
		return deliverToEmail;
	}

	public void setDeliverToEmail(String deliverToEmail) {
		this.deliverToEmail = deliverToEmail;
	}

	public List<RequestStrachingClass> getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(List<RequestStrachingClass> itemCategory) {
		this.itemCategory = itemCategory;
	}

	@Override
	public String toString() {
		return "PdfGenStrachingClass [companyName=" + companyName + ", link=" + link + ", fromCompName=" + fromCompName
				+ ", fromSupAdd=" + fromSupAdd + ", fromCity=" + fromCity + ", fromPostalCode=" + fromPostalCode
				+ ", fromReg=" + fromReg + ", fromCountry=" + fromCountry + ", toCompName=" + toCompName + ", toSubAdd="
				+ toSubAdd + ", toSubcity=" + toSubcity + ", toSubPostalCode=" + toSubPostalCode + ", toSubReg="
				+ toSubReg + ", toSubCountry=" + toSubCountry + ", dept=" + dept + ", catagory=" + catagory
				+ ", subCatagory=" + subCatagory + ", po=" + po + ", totalAmount=" + totalAmount + ", paymentTerms="
				+ paymentTerms + ", comment=" + comment + ", shipToCode=" + shipToCode + ", shipToReg=" + shipToReg
				+ ", shipToCountry=" + shipToCountry + ", deliverToName=" + deliverToName + ", deliverToEmail="
				+ deliverToEmail + ", itemCategory=" + itemCategory + "]";
	}

	public PdfGenStrachingClass(String companyName, String link, String fromCompName, String fromSupAdd,
			String fromCity, String fromPostalCode, String fromReg, String fromCountry, String toCompName,
			String toSubAdd, String toSubcity, String toSubPostalCode, String toSubReg, String toSubCountry,
			String dept, String catagory, String subCatagory, String po, String totalAmount, String paymentTerms,
			String comment, String shipToCode, String shipToReg, String shipToCountry, String deliverToName,
			String deliverToEmail, List<RequestStrachingClass> itemCategory) {
		super();
		this.companyName = companyName;
		this.link = link;
		this.fromCompName = fromCompName;
		this.fromSupAdd = fromSupAdd;
		this.fromCity = fromCity;
		this.fromPostalCode = fromPostalCode;
		this.fromReg = fromReg;
		this.fromCountry = fromCountry;
		this.toCompName = toCompName;
		this.toSubAdd = toSubAdd;
		this.toSubcity = toSubcity;
		this.toSubPostalCode = toSubPostalCode;
		this.toSubReg = toSubReg;
		this.toSubCountry = toSubCountry;
		this.dept = dept;
		this.catagory = catagory;
		this.subCatagory = subCatagory;
		this.po = po;
		this.totalAmount = totalAmount;
		this.paymentTerms = paymentTerms;
		this.comment = comment;
		this.shipToCode = shipToCode;
		this.shipToReg = shipToReg;
		this.shipToCountry = shipToCountry;
		this.deliverToName = deliverToName;
		this.deliverToEmail = deliverToEmail;
		this.itemCategory = itemCategory;
	}

	public PdfGenStrachingClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
