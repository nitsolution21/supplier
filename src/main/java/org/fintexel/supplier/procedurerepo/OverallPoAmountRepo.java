package org.fintexel.supplier.procedurerepo;

import org.fintexel.supplier.procedureentity.Overall_PO_Amount_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverallPoAmountRepo extends JpaRepository<Overall_PO_Amount_Entity, Float> {

}
