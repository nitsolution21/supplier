package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupDetailsRepo extends JpaRepository<SupDetails, String> {
	
	List<SupDetails> findByRegisterId(Long name);
	
	@Query("select supdet from SupDetails supdet where supdet.supplierCode not in (select contractCust.supplierCode from CustomerContact contractCust where contractCust.cId=?1)")
	List<SupDetails> getAllNotContactSuppiler(long cId); 
	
	Optional<SupDetails> findBySupplierCode(String supplierCode);
	
	

}
