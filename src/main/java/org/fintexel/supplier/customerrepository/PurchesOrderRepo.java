package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.PurchesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchesOrderRepo extends JpaRepository<PurchesOrder, Long>{
	
	@Query("select s from PurchesOrder s where s.status = ?1 group by s.cId")
	List<PurchesOrder> findByStatus(String status);
	
	@Query("select s from PurchesOrder s where s.status = ?1 and s.supplierCode = ?2")
	List<PurchesOrder> findByStatusWithSupplierCode(String status , String supplierCode);
	
	List<PurchesOrder> findBycId(int cId);

}
