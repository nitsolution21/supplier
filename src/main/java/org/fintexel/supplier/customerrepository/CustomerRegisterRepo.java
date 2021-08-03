package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRegisterRepo extends JpaRepository<CustomerRegister, Long> {

}
