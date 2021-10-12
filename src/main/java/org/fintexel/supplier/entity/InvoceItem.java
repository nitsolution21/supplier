package org.fintexel.supplier.entity;

public class InvoceItem {
	private Long POItemId;
	private Long POId;
	private Long itemId;
	private String itemDescription;
	private int qty;
	private String curType;
	private float unitPrice;
	private float itemGross;
	private float tax;
	private float amount;
	private float itemTotal;
	private Long categoryId;
	private Long subcategoryId;
	private Long brandId;
	private String itemCategoryText;
	private String itemSubcategoryText;
	private String itemBrandText;
	private float itemShipCharges;
	private float itemHandlingCharges;
	
	public InvoceItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoceItem(Long pOItemId, Long pOId, Long itemId, String itemDescription, int qty, String curType,
			float unitPrice, float itemGross, float tax, float amount, float itemTotal, Long categoryId,
			Long subcategoryId, Long brandId, String itemCategoryText, String itemSubcategoryText, String itemBrandText,
			float itemShipCharges, float itemHandlingCharges) {
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
		this.itemShipCharges = itemShipCharges;
		this.itemHandlingCharges = itemHandlingCharges;
	}

	@Override
	public String toString() {
		return "InvoceItem [POItemId=" + POItemId + ", POId=" + POId + ", itemId=" + itemId + ", itemDescription="
				+ itemDescription + ", qty=" + qty + ", curType=" + curType + ", unitPrice=" + unitPrice
				+ ", itemGross=" + itemGross + ", tax=" + tax + ", amount=" + amount + ", itemTotal=" + itemTotal
				+ ", categoryId=" + categoryId + ", subcategoryId=" + subcategoryId + ", brandId=" + brandId
				+ ", itemCategoryText=" + itemCategoryText + ", itemSubcategoryText=" + itemSubcategoryText
				+ ", itemBrandText=" + itemBrandText + ", itemShipCharges=" + itemShipCharges + ", itemHandlingCharges="
				+ itemHandlingCharges + "]";
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
	
}
