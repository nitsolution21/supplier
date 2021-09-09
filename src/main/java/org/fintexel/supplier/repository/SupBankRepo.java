package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupBankRepo extends JpaRepository<SupBank, Long> {
	
	List<SupBank> findBySupplierCode(String supplierCode);
	
	Optional<SupBank> findBySwiftCode(String swiftCode);
	
	@Query(value = "select s from SupBank s where s.isPrimary = ?1 and s.supplierCode = ?2 ORDER BY s.bankId DESC LIMIT 1" , nativeQuery = true)
	Optional<SupBank> findBySupplierCodeWithLastRow(int isPrimary , String supplierCode);
	
//	@Query("select s from SupBank s where s.isPrimary = ?1")
//	Optional<SupBank> findByIsPrimary(int key);
	
	@Query("select s from SupBank s where s.isPrimary = ?1 and s.supplierCode = ?2")
	Optional<SupBank> findByIsPrimaryWithSupplierCode(int key , String supplierCode);
	
	@Query(value = "select * from SUP_BANK where SUPPLIER_CODE = ?1 and STATUS = ?2" , nativeQuery = true)
	List<SupBank> findBySupplierCodeWithStatus(String supplierCode , String status);
}
