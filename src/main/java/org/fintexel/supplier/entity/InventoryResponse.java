package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;

public class InventoryResponse {
	
	private long itemId;
	
	private String supplierCode;

	private String itemDescription;
	
	private String categoryName;
	
	private String subcategoryName;
	
	private String brandName;

	private int qty;
	
	private float unitPrice;
	
	private int sku;
	
	private long categoryId;
	
	private long brandId;
	
	private long subcategoryId;

	private String status;

	private float discount;

	private int createdBy;
	
	private Date createdOn;
	
	private int updatedBy;
	
	private Date updatedOn;

	public InventoryResponse(long itemId, String supplierCode, String itemDescription, String categoryName,
			String subcategoryName, String brandName, int qty, float unitPrice, int sku, long categoryId, long brandId,
			long subcategoryId, String status, float discount, int createdBy, Date createdOn, int updatedBy,
			Date updatedOn) {
		super();
		this.itemId = itemId;
		this.supplierCode = supplierCode;
		this.itemDescription = itemDescription;
		this.categoryName = categoryName;
		this.subcategoryName = subcategoryName;
		this.brandName = brandName;
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
	}

	public InventoryResponse() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	
	
	
}
