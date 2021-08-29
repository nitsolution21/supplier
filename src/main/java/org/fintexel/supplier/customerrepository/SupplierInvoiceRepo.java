package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.SupplierInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierInvoiceRepo extends JpaRepository<SupplierInvoice, Long>{

}
