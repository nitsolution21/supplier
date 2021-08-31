package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCategoryRepo extends JpaRepository<ItemCategory,Long>{

	List<ItemCategory> findByCategoryName(String categoryName);
	
	List<ItemCategory>findBySupplierCode(String supplierCode);
	
	@Query(value = "SELECT * FROM flowable.ITEM_CATEGORY where SUPPLIER_CODE = ?1 and CATEGORY_ID in (select CATEGORY_ID from ITEM_SUB_CATEGORY where SUPPLIER_CODE = ?1)" , nativeQuery = true)
	List<ItemCategory>findBySupplierCodeWithHaveSubCategory(String supplierCode);
	
}
