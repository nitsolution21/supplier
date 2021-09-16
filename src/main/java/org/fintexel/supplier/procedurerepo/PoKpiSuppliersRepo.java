package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.PoKpiSuppliersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoKpiSuppliersRepo extends JpaRepository<PoKpiSuppliersEntity, Long> {

}
