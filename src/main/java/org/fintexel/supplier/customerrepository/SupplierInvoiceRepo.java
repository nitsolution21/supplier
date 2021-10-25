package org.fintexel.supplier.customerrepository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.SupplierInvoice;
import org.fintexel.supplier.entity.SupAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierInvoiceRepo extends JpaRepository<SupplierInvoice, Long>{
	
	List<SupplierInvoice> findByPOId(Long POId);
	
	@Query(value="select * from TBL_INVOICES where PO_ID = ?1",nativeQuery = true)
	List<SupplierInvoice> findAllInvoiceBySupplier(Long id);

}
