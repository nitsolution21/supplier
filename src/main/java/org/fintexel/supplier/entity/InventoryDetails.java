package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_INVENTORY")
public class InventoryDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", nullable = false, unique = true)
	private long itemId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;
	
	@Column(name = "QTY")
	private int qty;
	
	@Column(name = "UNIT_PRICE")
	private float unitPrice;
	 
	@Column(name = "SKU")
	private int sku;
	
	@Column(name = "CATEGORY_ID")
	private int categoryId;
	
	@Column(name = "BRAND_ID")
	private int brandId;
	
	@Column(name = "SUBCATEGORY_ID")
	private int subcategoryId;
	  
	@Column(name = "STATUS")
	private String status;
	  
	@Column(name = "DISCOUNT")
	private float discount;
	  
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(int subcategoryId) {
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

	@Override
	public String toString() {
		return "InventoryDetails [itemId=" + itemId + ", supplierCode=" + supplierCode + ", itemDescription="
				+ itemDescription + ", qty=" + qty + ", unitPrice=" + unitPrice + ", sku=" + sku + ", categoryId="
				+ categoryId + ", brandId=" + brandId + ", subcategoryId=" + subcategoryId + ", status=" + status
				+ ", discount=" + discount + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + ", getItemId()=" + getItemId() + ", getSupplierCode()="
				+ getSupplierCode() + ", getItemDescription()=" + getItemDescription() + ", getQty()=" + getQty()
				+ ", getUnitPrice()=" + getUnitPrice() + ", getSku()=" + getSku() + ", getCategoryId()="
				+ getCategoryId() + ", getBrandId()=" + getBrandId() + ", getSubcategoryId()=" + getSubcategoryId()
				+ ", getStatus()=" + getStatus() + ", getDiscount()=" + getDiscount() + ", getCreatedBy()="
				+ getCreatedBy() + ", getCreatedOn()=" + getCreatedOn() + ", getUpdatedBy()=" + getUpdatedBy()
				+ ", getUpdatedOn()=" + getUpdatedOn() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public InventoryDetails(long itemId, String supplierCode, String itemDescription, int qty, float unitPrice, int sku,
			int categoryId, int brandId, int subcategoryId, String status, float discount, int createdBy,
			Date createdOn, int updatedBy, Date updatedOn) {
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
	}

	public InventoryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		
}
