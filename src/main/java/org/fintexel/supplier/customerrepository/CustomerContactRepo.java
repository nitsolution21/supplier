package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerContactRepo extends JpaRepository<CustomerContact, Long>{
	public List<CustomerContact> findBycId(Long cId);
}
