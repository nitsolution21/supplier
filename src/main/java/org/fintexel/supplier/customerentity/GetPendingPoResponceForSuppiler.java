package org.fintexel.supplier.customerentity;

public class GetPendingPoResponceForSuppiler {
	private String supplierCompName;
	private String customerName;
	private String poNumber;
	private long poitemId;
	private long itemId;
	private String itemDescription;
	private int pendingQty;
	private String curType;
	private float itemPrice;
	private String itemBrandText;
	private String itemCategoryText;
	private String itemSubcategoryText;
	public GetPendingPoResponceForSuppiler() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GetPendingPoResponceForSuppiler(String supplierCompName, String customerName, String poNumber, long poitemId,
			long itemId, String itemDescription, int pendingQty, String curType, float itemPrice, String itemBrandText,
			String itemCategoryText, String itemSubcategoryText) {
		super();
		this.supplierCompName = supplierCompName;
		this.customerName = customerName;
		this.poNumber = poNumber;
		this.poitemId = poitemId;
		this.itemId = itemId;
		this.itemDescription = itemDescription;
		this.pendingQty = pendingQty;
		this.curType = curType;
		this.itemPrice = itemPrice;
		this.itemBrandText = itemBrandText;
		this.itemCategoryText = itemCategoryText;
		this.itemSubcategoryText = itemSubcategoryText;
	}
	@Override
	public String toString() {
		return "GetPendingPoResponceForSuppiler [supplierCompName=" + supplierCompName + ", customerName="
				+ customerName + ", poNumber=" + poNumber + ", poitemId=" + poitemId + ", itemId=" + itemId
				+ ", itemDescription=" + itemDescription + ", pendingQty=" + pendingQty + ", curType=" + curType
				+ ", itemPrice=" + itemPrice + ", itemBrandText=" + itemBrandText + ", itemCategoryText="
				+ itemCategoryText + ", itemSubcategoryText=" + itemSubcategoryText + "]";
	}
	public String getSupplierCompName() {
		return supplierCompName;
	}
	public void setSupplierCompName(String supplierCompName) {
		this.supplierCompName = supplierCompName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public long getPoitemId() {
		return poitemId;
	}
	public void setPoitemId(long poitemId) {
		this.poitemId = poitemId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public int getPendingQty() {
		return pendingQty;
	}
	public void setPendingQty(int pendingQty) {
		this.pendingQty = pendingQty;
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
	public String getItemBrandText() {
		return itemBrandText;
	}
	public void setItemBrandText(String itemBrandText) {
		this.itemBrandText = itemBrandText;
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
	
}
