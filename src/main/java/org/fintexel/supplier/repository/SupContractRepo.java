package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupContractRepo extends JpaRepository<SupContract, Long> {
	List<SupContract> findBySupplierCode(String code);
}
