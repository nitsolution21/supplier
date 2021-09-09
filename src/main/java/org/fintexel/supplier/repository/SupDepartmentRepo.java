package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupDepartmentRepo extends JpaRepository<SupDepartment, Long> {
	List<SupDepartment> findBySupplierCode(String supplierCode);
	
	@Query(value = "select * from SUP_DEPARTMENT where SUPPLIER_CODE = ?1 and STATUS = ?2" , nativeQuery = true)
	List<SupDepartment> findBySupplierCodeWithStatus(String supplierCode , String status);
}
