package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long>{

}
