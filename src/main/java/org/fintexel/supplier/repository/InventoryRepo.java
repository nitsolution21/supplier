package org.fintexel.supplier.repository;

import org.fintexel.supplier.entity.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<InventoryDetails,Long>{

}
