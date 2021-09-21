package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.ContractAndAddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//@Repository
public interface ContractAndAddressTypeRepo extends JpaRepository<ContractAndAddressType, Long>{
	
	
	
	
	
	
	
	
	
	
	
	
	@Query(value = "select * from TBL_CUS_DEFAULTS where NAME = ?1 and upper(CID) = ?2 and TYPE = ?3" , nativeQuery = true)
	List<ContractAndAddressType> findByNameWithCId(String name , int cId , String type);

}
