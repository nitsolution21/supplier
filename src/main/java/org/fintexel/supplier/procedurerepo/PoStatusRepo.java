package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.PoStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoStatusRepo extends JpaRepository<PoStatusEntity, Long> {

}
