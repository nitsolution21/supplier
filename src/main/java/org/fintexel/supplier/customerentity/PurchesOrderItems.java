package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PO_ITEMS")
public class PurchesOrderItems {

	@Id
	@Column(name = "PO_ID") private Long POId;
	@Column(name = "ITEM_ID") private Long itemId;
	@Column(name = "ITEM_DESCRIPTION") private String itemDescription;
	@Column(name = "ITEM_QTY") private int itemQty;
	@Column(name = "CUR_TYPE") private String curType;
	@Column(name = "ITEM_PRICE") private float itemPrice;
	@Column(name = "ITEM_SUBTOTAL") private float itemSubtotal;
	@Column(name = "ITEM_TAX") private float itemTax;
	@Column(name = "ITEM_DIS_PER") private float itemDisPer;
	@Column(name = "ITEM_DIS_CUR") private float itemDisCur;
	@Column(name = "ITEM_CATEGORY_ID") private Long itemCategoryId;
	@Column(name = "ITEM_SUBCATEGORY_ID") private Long itemSubcategoryId;
	@Column(name = "ITEM_BRAND_ID") private Long itemBrandId;
	@Column(name = "ITEM_CATEGORY_TEXT") private String itemCategoryText;
	@Column(name = "ITEM_SUBCATEGORY_TEXT") private String itemSubcategoryText;
	@Column(name = "ITEM_BRAND_TEXT") private String itemBrandText;
	@Column(name = "ITEM_TOTAL") private float itemTotal;
	public Long getPOId() {
		return POId;
	}
	public void setPOId(Long pOId) {
		POId = pOId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public String getCurType() {
		return curType;
	}
	public void setCurType(String curType) {
		this.curType = curType;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public float getItemSubtotal() {
		return itemSubtotal;
	}
	public void setItemSubtotal(float itemSubtotal) {
		this.itemSubtotal = itemSubtotal;
	}
	public float getItemTax() {
		return itemTax;
	}
	public void setItemTax(float itemTax) {
		this.itemTax = itemTax;
	}
	public float getItemDisPer() {
		return itemDisPer;
	}
	public void setItemDisPer(float itemDisPer) {
		this.itemDisPer = itemDisPer;
	}
	public float getItemDisCur() {
		return itemDisCur;
	}
	public void setItemDisCur(float itemDisCur) {
		this.itemDisCur = itemDisCur;
	}
	public Long getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(Long itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	public Long getItemSubcategoryId() {
		return itemSubcategoryId;
	}
	public void setItemSubcategoryId(Long itemSubcategoryId) {
		this.itemSubcategoryId = itemSubcategoryId;
	}
	public Long getItemBrandId() {
		return itemBrandId;
	}
	public void setItemBrandId(Long itemBrandId) {
		this.itemBrandId = itemBrandId;
	}
	public String getItemCategoryText() {
		return itemCategoryText;
	}
	public void setItemCategoryText(String itemCategoryText) {
		this.itemCategoryText = itemCategoryText;
	}
	public String getItemSubcategoryText() {
		return itemSubcategoryText;
	}
	public void setItemSubcategoryText(String itemSubcategoryText) {
		this.itemSubcategoryText = itemSubcategoryText;
	}
	public String getItemBrandText() {
		return itemBrandText;
	}
	public void setItemBrandText(String itemBrandText) {
		this.itemBrandText = itemBrandText;
	}
	public float getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(float itemTotal) {
		this.itemTotal = itemTotal;
	}
	@Override
	public String toString() {
		return "PurchesOrderItems [POId=" + POId + ", itemId=" + itemId + ", itemDescription=" + itemDescription
				+ ", itemQty=" + itemQty + ", curType=" + curType + ", itemPrice=" + itemPrice + ", itemSubtotal="
				+ itemSubtotal + ", itemTax=" + itemTax + ", itemDisPer=" + itemDisPer + ", itemDisCur=" + itemDisCur
				+ ", itemCategoryId=" + itemCategoryId + ", itemSubcategoryId=" + itemSubcategoryId + ", itemBrandId="
				+ itemBrandId + ", itemCategoryText=" + itemCategoryText + ", itemSubcategoryText="
				+ itemSubcategoryText + ", itemBrandText=" + itemBrandText + ", itemTotal=" + itemTotal + "]";
	}
	public PurchesOrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
