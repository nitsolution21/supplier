package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.SupplierInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierInvoiceItemRepo extends JpaRepository<SupplierInvoiceItem, Long>{
	
	List<SupplierInvoiceItem> findByInvId(long invId);
}
