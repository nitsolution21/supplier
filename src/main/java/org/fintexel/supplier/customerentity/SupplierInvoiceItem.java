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
	@Column(name = "ITEM_QTY") private int itemQty;
	@Column(name = "ITEM_PRICE") private float itemPrice;
	@Column(name = "ITEM_GROSS") private float itemGross;
	@Column(name = "ITEM_TAX") private float itemTax;
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
	public float getItemGross() {
		return itemGross;
	}
	public void setItemGross(float itemGross) {
		this.itemGross = itemGross;
	}
	public float getItemTax() {
		return itemTax;
	}
	public void setItemTax(float itemTax) {
		this.itemTax = itemTax;
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
		return "SupplierInvoiceItem [invId=" + invId + ", poitemId=" + poitemId + ", itemQty=" + itemQty
				+ ", itemPrice=" + itemPrice + ", itemGross=" + itemGross + ", itemTax=" + itemTax + ", itemSubtotal="
				+ itemSubtotal + ", itemTotal=" + itemTotal + "]";
	}
	public SupplierInvoiceItem(Long invId, Long poitemId, int itemQty, float itemPrice, float itemGross, float itemTax,
			float itemSubtotal, float itemTotal) {
		super();
		this.invId = invId;
		this.poitemId = poitemId;
		this.itemQty = itemQty;
		this.itemPrice = itemPrice;
		this.itemGross = itemGross;
		this.itemTax = itemTax;
		this.itemSubtotal = itemSubtotal;
		this.itemTotal = itemTotal;
	}
	public SupplierInvoiceItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
