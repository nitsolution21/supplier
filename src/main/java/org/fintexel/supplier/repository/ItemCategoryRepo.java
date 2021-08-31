package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCategoryRepo extends JpaRepository<ItemCategory,Long>{

	List<ItemCategory> findByCategoryName(String categoryName);
	
	List<ItemCategory>findBySupplierCode(String supplierCode);
	
	@Query("select SC from ItemSubCategory SC, ItemCategory IC where SC.categoryId = IC.categoryId and IC.supplierCode = ?1")
	List<ItemCategory>findBySupplierCodeWithHaveSubCategory(String supplierCode);
	
}
