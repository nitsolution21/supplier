package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PO_ITEMS")
public class PurchesOrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "POITEM_ID") private Long POItemId;
	@Column(name = "PO_ID") private Long POId;
	@Column(name = "ITEM_ID") private Long itemId;
	@Column(name = "ITEM_DESCRIPTION") private String itemDescription;
	@Column(name = "ITEM_QTY") private int qty;
	@Column(name = "CUR_TYPE") private String curType;
	@Column(name = "ITEM_PRICE") private float unitPrice;
	@Column(name = "ITEM_GROSS") private float itemGross;
	@Column(name = "ITEM_TAX") private float tax;
	@Column(name = "ITEM_SUBTOTAL") private float amount;
	@Column(name = "ITEM_TOTAL") private float itemTotal;
	@Column(name = "ITEM_CATEGORY_ID") private Long categoryId;
	@Column(name = "ITEM_SUBCATEGORY_ID") private Long subcategoryId;
	@Column(name = "ITEM_BRAND_ID") private Long brandId;
	@Column(name = "ITEM_CATEGORY_TEXT") private String itemCategoryText;
	@Column(name = "ITEM_SUBCATEGORY_TEXT") private String itemSubcategoryText;
	@Column(name = "ITEM_BRAND_TEXT") private String itemBrandText;
	
	public PurchesOrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchesOrderItems(Long pOItemId, Long pOId, Long itemId, String itemDescription, int qty, String curType,
			float unitPrice, float itemGross, float tax, float amount, float itemTotal, Long categoryId,
			Long subcategoryId, Long brandId, String itemCategoryText, String itemSubcategoryText,
			String itemBrandText) {
		super();
		POItemId = pOItemId;
		POId = pOId;
		this.itemId = itemId;
		this.itemDescription = itemDescription;
		this.qty = qty;
		this.curType = curType;
		this.unitPrice = unitPrice;
		this.itemGross = itemGross;
		this.tax = tax;
		this.amount = amount;
		this.itemTotal = itemTotal;
		this.categoryId = categoryId;
		this.subcategoryId = subcategoryId;
		this.brandId = brandId;
		this.itemCategoryText = itemCategoryText;
		this.itemSubcategoryText = itemSubcategoryText;
		this.itemBrandText = itemBrandText;
	}

	@Override
	public String toString() {
		return "PurchesOrderItems [POItemId=" + POItemId + ", POId=" + POId + ", itemId=" + itemId
				+ ", itemDescription=" + itemDescription + ", qty=" + qty + ", curType=" + curType + ", unitPrice="
				+ unitPrice + ", itemGross=" + itemGross + ", tax=" + tax + ", amount=" + amount + ", itemTotal="
				+ itemTotal + ", categoryId=" + categoryId + ", subcategoryId=" + subcategoryId + ", brandId=" + brandId
				+ ", itemCategoryText=" + itemCategoryText + ", itemSubcategoryText=" + itemSubcategoryText
				+ ", itemBrandText=" + itemBrandText + "]";
	}

	public Long getPOItemId() {
		return POItemId;
	}

	public void setPOItemId(Long pOItemId) {
		POItemId = pOItemId;
	}

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

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getItemGross() {
		return itemGross;
	}

	public void setItemGross(float itemGross) {
		this.itemGross = itemGross;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(float itemTotal) {
		this.itemTotal = itemTotal;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
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
	
}
