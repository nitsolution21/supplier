package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.PurchesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchesOrderRepo extends JpaRepository<PurchesOrder, Long>{
	
	@Query("select s from PurchesOrder s where s.status = ?1 group by s.cId")
	List<PurchesOrder> findByStatus(String status);
	
<<<<<<< HEAD
	List<PurchesOrder> findByCId(int id);
=======
	List<PurchesOrder> findBycId(int cId);
>>>>>>> ec37a930d1bb9222c04fa408bea26152f2c4deac

}
