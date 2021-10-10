package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_INVOICES_ITEMS")
public class SupplierInvoiceItem {
	
	@Id
	@Column(name = "INV_ID") private Long invId;
	@Column(name = "POITEM_ID") private Long poitemId;
	@Column(name = "ITEM_CATEGORY_NAME") private String itemCategoryName;
	@Column(name = "ITEM_SUBCATEGORY_NAME") private String itemSubcategoryName;
	@Column(name = "ITEM_DESCRIPTION") private String ItemDescription;

	@Column(name = "ITEM_QTY") private int itemQty;
	@Column(name = "ITEM_PRICE") private float itemPrice;
//	@Column(name = "ITEM_GROSS") private float itemGross;
	@Column(name = "ITEM_TAX") private float itemTax;
	@Column(name = "ITEM_SHIP_CHARGES") private float itemShipCharges;
	@Column(name = "ITEM_HANDLING_CHARGES") private float itemHandlingCharges;
	@Column(name = "ITEM_SUBTOTAL") private float itemSubtotal;
	@Column(name = "ITEM_TOTAL") private float itemTotal;
	public Long getInvId() {
		return invId;
	}
	public void setInvId(Long invId) {
		this.invId = invId;
	}
	public Long getPoitemId() {
		return poitemId;
	}
	public void setPoitemId(Long poitemId) {
		this.poitemId = poitemId;
	}
	public String getItemCategoryName() {
		return itemCategoryName;
	}
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	public String getItemSubcategoryName() {
		return itemSubcategoryName;
	}
	public void setItemSubcategoryName(String itemSubcategoryName) {
		this.itemSubcategoryName = itemSubcategoryName;
	}
	public String getItemDescription() {
		return ItemDescription;
	}
	public void setItemDescription(String itemDescription) {
		ItemDescription = itemDescription;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public float getItemTax() {
		return itemTax;
	}
	public void setItemTax(float itemTax) {
		this.itemTax = itemTax;
	}
	public float getItemShipCharges() {
		return itemShipCharges;
	}
	public void setItemShipCharges(float itemShipCharges) {
		this.itemShipCharges = itemShipCharges;
	}
	public float getItemHandlingCharges() {
		return itemHandlingCharges;
	}
	public void setItemHandlingCharges(float itemHandlingCharges) {
		this.itemHandlingCharges = itemHandlingCharges;
	}
	public float getItemSubtotal() {
		return itemSubtotal;
	}
	public void setItemSubtotal(float itemSubtotal) {
		this.itemSubtotal = itemSubtotal;
	}
	public float getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(float itemTotal) {
		this.itemTotal = itemTotal;
	}
	@Override
	public String toString() {
		return "SupplierInvoiceItem [invId=" + invId + ", poitemId=" + poitemId + ", itemCategoryName="
				+ itemCategoryName + ", itemSubcategoryName=" + itemSubcategoryName + ", ItemDescription="
				+ ItemDescription + ", itemQty=" + itemQty + ", itemPrice=" + itemPrice + ", itemTax=" + itemTax
				+ ", itemShipCharges=" + itemShipCharges + ", itemHandlingCharges=" + itemHandlingCharges
				+ ", itemSubtotal=" + itemSubtotal + ", itemTotal=" + itemTotal + "]";
	}
	public SupplierInvoiceItem(Long invId, Long poitemId, String itemCategoryName, String itemSubcategoryName,
			String itemDescription, int itemQty, float itemPrice, float itemTax, float itemShipCharges,
			float itemHandlingCharges, float itemSubtotal, float itemTotal) {
		super();
		this.invId = invId;
		this.poitemId = poitemId;
		this.itemCategoryName = itemCategoryName;
		this.itemSubcategoryName = itemSubcategoryName;
		ItemDescription = itemDescription;
		this.itemQty = itemQty;
		this.itemPrice = itemPrice;
		this.itemTax = itemTax;
		this.itemShipCharges = itemShipCharges;
		this.itemHandlingCharges = itemHandlingCharges;
		this.itemSubtotal = itemSubtotal;
		this.itemTotal = itemTotal;
	}
	public SupplierInvoiceItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
