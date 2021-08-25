package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupAddressRepo extends JpaRepository<SupAddress, Long> {
	
	List<SupAddress> findBySupplierCode(String code);
	@Query("select s from SupBank where s.isPrimary = ?1")
	List<SupAddress> findByIsPrimary(int key);
}
