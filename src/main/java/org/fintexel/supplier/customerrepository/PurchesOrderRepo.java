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
	
	List<PurchesOrder> findBycId(int cId);
	
	public Optional<PurchesOrder> findByPoNumber(String poNumber);
	
//	@Query("select SD.supplierCompName,CP.customerName,PO.poNumber,\r\n"
//			+ "PI.POItemId,PI.itemId,PI.itemDescription,POP.pendingQty,PI.curType,PI.unitPrice,PI.itemBrandText,PI.itemCategoryText,PI.itemSubcategoryText\r\n"
//			+ "  from PurchesOrder PO \r\n"
//			+ "join PurchesOrderPending POP using(poId) \r\n"
//			+ "    left outer join CustomerProfile CP on(PO.cId=CP.cId)\r\n"
//			+ "    left outer join SupDetails SD on(SD.supplierCode=PO.supplierCode) \r\n"
//			+ "    left outer join PurchesOrderItems PI on (PI.POItemId = POP.poitemId)\r\n"
//			+ "where PO.supplierCode=?1")
//	public List<GetPendingPoResponceForSuppiler> getAllPendingPOForSuppiler(String SuppilerCode);

}
