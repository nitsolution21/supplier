package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.Pending_PO_Supplier_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingPoSupplierRepo extends JpaRepository<Pending_PO_Supplier_Entity, String> {

}
