package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.ItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSubCategoryRepo extends JpaRepository<ItemSubCategory,Long>{

	List<ItemSubCategory> findBySubCategoryName(String subCategoryName);
	

}
