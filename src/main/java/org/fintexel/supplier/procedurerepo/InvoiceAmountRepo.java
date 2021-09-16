package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.Invoice_Amount_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceAmountRepo extends JpaRepository<Invoice_Amount_Entity, Float> {

}
