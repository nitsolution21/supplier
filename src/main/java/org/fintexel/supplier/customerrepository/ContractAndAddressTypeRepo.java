package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.ContractAndAddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractAndAddressTypeRepo extends JpaRepository<ContractAndAddressType, Long>{

}
