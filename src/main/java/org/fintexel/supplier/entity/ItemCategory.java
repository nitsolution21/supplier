package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_CATEGORY")
public class ItemCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID", nullable = false, unique = true)
	private long categoryId;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	

	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@Override
	public String toString() {
		return "ItemCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public ItemCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemCategory(long categoryId, String categoryName, int createdBy, Date createdOn, int updatedBy,
			Date updatedOn) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
