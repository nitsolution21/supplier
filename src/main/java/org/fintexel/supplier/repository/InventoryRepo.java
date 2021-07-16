package org.fintexel.supplier.repository;

import java.util.Optional;

import org.fintexel.supplier.entity.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<InventoryDetails,Long>{

	Optional<InventoryDetails> findBySupplierCode(String supplierCode);

}
