package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupDepartmentRepo extends JpaRepository<SupDepartment, Long> {
	List<SupDepartment> findBySupplierCode(String supplierCode);
}
