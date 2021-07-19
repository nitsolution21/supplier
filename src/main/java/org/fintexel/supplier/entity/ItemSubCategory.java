package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_SUB_CATEGORY")
public class ItemSubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBCATEGORY_ID", nullable = false, unique = true)
	private long subCategoryId;
	
	@Column(name = "SUBCATEGORY_NAME")
	private String subCategoryName;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "CATEGORY_ID")
	private long categoryId;

	public long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
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
	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ItemSubCategory [subCategoryId=" + subCategoryId + ", subCategoryName=" + subCategoryName
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", supplierCode=" + supplierCode + ", categoryId="+categoryId+"]";
	}

	public ItemSubCategory(long subCategoryId, String subCategoryName, int createdBy, Date createdOn, int updatedBy,
			Date updatedOn, String supplierCode, long categoryId) {
		super();
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.supplierCode = supplierCode;
		this.categoryId = categoryId;
	}

	public ItemSubCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
