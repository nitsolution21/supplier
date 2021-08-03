package org.fintexel.supplier.entity;

import java.util.Date;

public class SubCategoryResponse {
	private long subCategoryId;
	private String categoryName;
	private String subCategoryName;
	private String supplierCode;
	private long categoryId;
	private int createdBy;
	private Date createdOn;
	private int updatedBy;
	private Date updatedOn;
	
	public SubCategoryResponse(long subCategoryId, String categoryName, String subCategoryName, String supplierCode,
			long categoryId, int createdBy, Date createdOn, int updatedBy, Date updatedOn) {
		super();
		this.subCategoryId = subCategoryId;
		this.categoryName = categoryName;
		this.subCategoryName = subCategoryName;
		this.supplierCode = supplierCode;
		this.categoryId = categoryId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public SubCategoryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
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
