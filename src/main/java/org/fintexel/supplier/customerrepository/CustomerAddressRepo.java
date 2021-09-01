package org.fintexel.supplier.customerrepository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long>{
	
	public List<CustomerAddress> findBycId(Long cId);
	
	@Query("SELECT address FROM CustomerAddress address WHERE address.cId = ?1 AND address.status = 'COMPLEATE'")
	public List<CustomerAddress> findActiveAddress(Long cId);
	
	public Optional<CustomerAddress> findByIsPrimary(int isPrimary);
	
	@Query(value = "select add.* from flowable.TBL_CUS_ADDRESS add where add.IS_PRIMARY = ?1 and add.CID = ?2 order by add.ADDRESS_ID desc limit 1", nativeQuery = true)
	public Optional<CustomerAddress> findLastCompanyPrimaryAddress(int isPrimary, long cId);
	
	
}
