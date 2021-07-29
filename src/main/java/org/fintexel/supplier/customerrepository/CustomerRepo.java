package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

}
