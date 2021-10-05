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
	private String from;
	private String to;
	private String po;
	private String totalAmount;
	private String paymentTerms;
	private String comment;
	private String shipTo;
	private String billTo;
	private String deliverTo;
	private List<ItemCategory> itemCategory;
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
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
	public String getShipTo() {
		return shipTo;
	}
	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
	public String getDeliverTo() {
		return deliverTo;
	}
	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}
	public List<ItemCategory> getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(List<ItemCategory> itemCategory) {
		this.itemCategory = itemCategory;
	}
	@Override
	public String toString() {
		return "PdfGenStrachingClass [companyName=" + companyName + ", link=" + link + ", from=" + from + ", to=" + to
				+ ", po=" + po + ", totalAmount=" + totalAmount + ", paymentTerms=" + paymentTerms + ", comment="
				+ comment + ", shipTo=" + shipTo + ", billTo=" + billTo + ", deliverTo=" + deliverTo + ", itemCategory="
				+ itemCategory + "]";
	}
	public PdfGenStrachingClass(String companyName, String link, String from, String to, String po, String totalAmount,
			String paymentTerms, String comment, String shipTo, String billTo, String deliverTo,
			List<ItemCategory> itemCategory) {
		super();
		this.companyName = companyName;
		this.link = link;
		this.from = from;
		this.to = to;
		this.po = po;
		this.totalAmount = totalAmount;
		this.paymentTerms = paymentTerms;
		this.comment = comment;
		this.shipTo = shipTo;
		this.billTo = billTo;
		this.deliverTo = deliverTo;
		this.itemCategory = itemCategory;
	}
	public PdfGenStrachingClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
//	private SupBank supBank;
//	private SupDepartment supDepartment;
//	private InventoryDetails inventoryDetails;
//	private ItemSubCategory itemSubCategory;
//	private List<ItemCategory> itemCategory;
//	private String city;
//	private String country;
//	private String deliveryEmail;
//	private String deliveryName;
//	private String poNumber;
//	private String postalCode;
//	private String region;
//	private String shipcityCode;
//	private String shipcountry;
//	private String shipregion;
//	private String subaddress1;
//	private String subcity;
//	private String subcountry;
//	private String subpostalCode;
//	private String subregion;
//	private String supplierComName;
//	private String supplieraddress;
//	private String totalItemAmount;
//	private String viewComments;
	
	
	
	
	
}
