package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.SupplierInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierInvoiceItemRepo extends JpaRepository<SupplierInvoiceItem, Long>{

}
