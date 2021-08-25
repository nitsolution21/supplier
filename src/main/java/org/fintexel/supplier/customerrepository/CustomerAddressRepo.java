package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long>{
	
	public List<CustomerAddress> findBycId(Long cId);
	
	@Query("SELECT address FROM CustomerAddress address WHERE address.cId = ?1 AND address.status = 'COMPLEATE'")
	public List<CustomerAddress> findActiveAddress(Long cId);
	
	
}
