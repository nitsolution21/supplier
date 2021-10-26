package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;


public class InventoryGetResponse {
//	@Column(name = "ITEM_ID")
	private long itemId;
//	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
//	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;
//	@Column(name = "QTY")
	private int qty;
//	@Column(name = "UNIT_PRICE")
	private float unitPrice;
//	@Column(name = "SKU")
	private int sku;
//	@Column(name = "CATEGORY_ID")
	private long categoryId;
//	@Column(name = "BRAND_ID")
	private long brandId;
//	@Column(name = "SUBCATEGORY_ID")
	private long subcategoryId;
//	@Column(name = "STATUS")
	private String status;
//	@Column(name = "DISCOUNT")
	private float discount;
//	@Column(name = "CREATED_BY")
	private int createdBy;
//	@Column(name = "CREATED_ON")
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
//	@Column(name = "UPDATED_BY")
	private int updatedBy;
//	@Column(name = "UPDATED_ON")
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
//	@Column(name = "BRAND_NAME")
	private String brandName;
//	@Column(name = "CATEGORY_NAME")
	private String categoryName;
//	@Column(name = "SUBCATEGORY_NAME")
	private String subcategoryName;
	public InventoryGetResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InventoryGetResponse(long itemId, String supplierCode, String itemDescription, int qty, float unitPrice,
			int sku, long categoryId, long brandId, long subcategoryId, String status, float discount, int createdBy,
			Date createdOn, int updatedBy, Date updatedOn, String brandName, String categoryName,
			String subcategoryName) {
		super();
		this.itemId = itemId;
		this.supplierCode = supplierCode;
		this.itemDescription = itemDescription;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.sku = sku;
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.subcategoryId = subcategoryId;
		this.status = status;
		this.discount = discount;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.subcategoryName = subcategoryName;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getSku() {
		return sku;
	}
	public void setSku(int sku) {
		this.sku = sku;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public long getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	
}
