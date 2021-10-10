package org.fintexel.supplier.customerrepository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.GetPendingPoResponceForSuppiler;
import org.fintexel.supplier.customerentity.PurchesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchesOrderRepo extends JpaRepository<PurchesOrder, Long>{
	
	@Query("select s from PurchesOrder s where s.status = ?1 group by s.cId")
	List<PurchesOrder> findByStatus(String status);
	
	@Query("select s from PurchesOrder s where s.status = ?1 and s.supplierCode = ?2")
	List<PurchesOrder> findByStatusWithSupplierCode(String status , String supplierCode);
	
	@Query("select s from PurchesOrder s where s.supplierCode = ?1")
	List<PurchesOrder> findByhSupplierCode(String supplierCode);
	
	List<PurchesOrder> findBycId(int cId);
	
	public Optional<PurchesOrder> findByPoNumber(String poNumber);
	
	@Query(value="select SD.SUPPLIER_COMP_NAME,CP.CUSTOMER_NAME,PO.PO_NUMBER,\r\n"
			+ "PI.POITEM_ID,PI.ITEM_ID,PI.ITEM_DESCRIPTION,POP.PENDING_QTY,PI.CUR_TYPE,PI.ITEM_PRICE,PI.ITEM_BRAND_TEXT,PI.ITEM_CATEGORY_TEXT,PI.ITEM_SUBCATEGORY_TEXT\r\n"
			+ "  from TBL_PO PO \r\n"
			+ "join TBL_PO_PENDINGS POP using(PO_ID) \r\n"
			+ "    left outer join TBL_CUS_PROFILE CP on(PO.CID=CP.CID)\r\n"
			+ "    left outer join SUP_DETAILS SD on(SD.SUPPLIER_CODE=PO.SUPPLIER_CODE) \r\n"
			+ "    left outer join TBL_PO_ITEMS PI on (PI.POITEM_ID = POP.POITEM_ID)\r\n"
			+ "where PO.SUPPLIER_CODE=?1", nativeQuery=true)
	public List<GetPendingPoResponceForSuppiler> getAllPendingPOForSuppiler(String SuppilerCode);

}
