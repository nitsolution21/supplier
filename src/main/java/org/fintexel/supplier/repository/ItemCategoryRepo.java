package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepo extends JpaRepository<ItemCategory,Long>{

	List<ItemCategory> findByCategoryName(String categoryName);
	
	List<ItemCategory>findBySupplierCode(String supplierCode);

}
