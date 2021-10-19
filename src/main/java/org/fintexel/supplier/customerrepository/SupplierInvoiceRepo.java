package org.fintexel.supplier.customerrepository;

import java.util.Optional;

import org.fintexel.supplier.customerentity.SupplierInvoice;
import org.fintexel.supplier.entity.SupAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierInvoiceRepo extends JpaRepository<SupplierInvoice, Long>{
	
	@Query("select s from SupplierInvoice s where s.POId = ?1")
	Optional<SupplierInvoice> findByPoId(Long POId);

}
