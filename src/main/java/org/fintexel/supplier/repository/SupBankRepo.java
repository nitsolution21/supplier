package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupBankRepo extends JpaRepository<SupBank, Long> {
	
	List<SupBank> findBySupplierCode(String supplierCode);

}
