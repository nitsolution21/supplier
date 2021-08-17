package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerFunctionalitiesMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFunctionalitiesMasterRepo extends JpaRepository<CustomerFunctionalitiesMaster, Long> {

}
