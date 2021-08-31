package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupAddressRepo extends JpaRepository<SupAddress, Long> {
	
	List<SupAddress> findBySupplierCode(String code);
	
	@Query(value  = "select s from SupAddress s where s.isPrimary = ?1 ORDER BY id DESC LIMIT 1" , nativeQuery = true)
	Optional<SupAddress> findBySupplierCodeWithLastRow(String code);
//	@Query("select s from SupAddress s where s.isPrimary = ?1")
//	Optional<SupAddress> findByIsPrimary(int isPrimary);
	
	@Query("select s from SupAddress s where s.isPrimary = ?1 and s.supplierCode = ?2")
	Optional<SupAddress> findByIsPrimaryWithSupplierCode(int isPrimary , String supplierCode);
}
