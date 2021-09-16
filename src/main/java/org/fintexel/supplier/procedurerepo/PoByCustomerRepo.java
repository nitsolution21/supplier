package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.PoByCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoByCustomerRepo extends JpaRepository<PoByCustomerEntity, String> {

}
