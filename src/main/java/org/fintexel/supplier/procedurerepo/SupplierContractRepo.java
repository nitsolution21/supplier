package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.SupplierContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierContractRepo extends JpaRepository<SupplierContractEntity, String>{

}
