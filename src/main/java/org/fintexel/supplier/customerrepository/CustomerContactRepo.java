package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerContactRepo extends JpaRepository<CustomerContact, Long>{

}
