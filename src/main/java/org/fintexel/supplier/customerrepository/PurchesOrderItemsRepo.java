package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.PurchesOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchesOrderItemsRepo extends JpaRepository<PurchesOrderItems, Long>{
	public List<PurchesOrderItems> findByPOId(long poId);
}
