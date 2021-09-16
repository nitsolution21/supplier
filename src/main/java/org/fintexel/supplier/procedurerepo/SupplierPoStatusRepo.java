package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.SupplierPoStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierPoStatusRepo extends JpaRepository<SupplierPoStatusEntity, String> {

}
