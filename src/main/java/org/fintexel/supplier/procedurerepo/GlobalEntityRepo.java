package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.GlobalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalEntityRepo extends JpaRepository<GlobalEntity, Long>{
	

}
