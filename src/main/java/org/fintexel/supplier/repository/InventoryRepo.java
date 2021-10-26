package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.InventoryGetResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepo extends JpaRepository<InventoryDetails, Long> {

	List<InventoryDetails> findBySupplierCode(String supplierCode);

	List<InventoryDetails> findByCategoryId(Long categoryId);

	@Query(value = "select SUP_INVENTORY.*,ITEM_BRAND.BRAND_NAME, ITEM_CATEGORY.CATEGORY_NAME, ITEM_SUB_CATEGORY.SUBCATEGORY_NAME from SUP_INVENTORY \r\n"
			+ "inner join ITEM_BRAND on SUP_INVENTORY.BRAND_ID=ITEM_BRAND.BRAND_ID\r\n"
			+ "inner join ITEM_CATEGORY on ITEM_CATEGORY.CATEGORY_ID=SUP_INVENTORY.CATEGORY_ID\r\n"
			+ "inner join ITEM_SUB_CATEGORY on ITEM_SUB_CATEGORY.SUBCATEGORY_ID=SUP_INVENTORY.SUBCATEGORY_ID\r\n"
			+ "where SUP_INVENTORY.SUPPLIER_CODE=?1", nativeQuery = true)
	public List<InventoryGetResponse> findInventoryDetails(String supplierCode);

}
