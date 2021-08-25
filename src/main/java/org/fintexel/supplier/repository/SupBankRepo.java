package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupBankRepo extends JpaRepository<SupBank, Long> {
	
	List<SupBank> findBySupplierCode(String supplierCode);
	
	Optional<SupBank> findBySwiftCode(String swiftCode);
	
	@Query("select s from SupBank s where s.isPrimary = ?1")
	List<SupBank> findByIsPrimary(int key);

}
