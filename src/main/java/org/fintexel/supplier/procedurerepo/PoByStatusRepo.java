package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.PoByStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoByStatusRepo extends JpaRepository<PoByStatusEntity, String> {

}
