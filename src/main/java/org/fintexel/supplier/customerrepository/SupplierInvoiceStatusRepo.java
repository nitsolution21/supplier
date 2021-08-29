package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.SupplierInvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierInvoiceStatusRepo extends JpaRepository<SupplierInvoiceStatus, Long>{

}
