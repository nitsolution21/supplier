package org.fintexel.supplier.customerrepository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerContactRepo extends JpaRepository<CustomerContact, Long>{
	public List<CustomerContact> findBycId(Long cId);
	
	@Query("select contact from CustomerContact contact where contact.cId = ?1 and contact.supplierCode = ?2")
	public Optional<CustomerContact> findContactTrams(Long cId, String supplierCode);
}
