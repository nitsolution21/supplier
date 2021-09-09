package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupAddressRepo extends JpaRepository<SupAddress, Long> {
	
	List<SupAddress> findBySupplierCode(String code);
	
	@Query(value  = "select * from SUP_ADDRESS where IS_PRIMARY = ?1 and SUPPLIER_CODE = ?2 ORDER BY ADDRESS_ID DESC LIMIT 1" , nativeQuery = true)
	Optional<SupAddress> findBySupplierCodeWithLastRow(int isPrimary , String supplierCode);
//	@Query("select s from SupAddress s where s.isPrimary = ?1")
//	Optional<SupAddress> findByIsPrimary(int isPrimary);
	
	@Query("select s from SupAddress s where s.isPrimary = ?1 and s.supplierCode = ?2")
	Optional<SupAddress> findByIsPrimaryWithSupplierCode(int isPrimary , String supplierCode);
	
	
	@Query(value = "select * from SUP_ADDRESS where SUPPLIER_CODE = ?1 and STATUS = ?2" , nativeQuery = true)
	List<SupAddress> findBySupplierCodeWithStatus(String supplierCode , String status);
}
