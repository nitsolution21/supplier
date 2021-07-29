package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepo extends JpaRepository<CustomerProfile, Long>{

}
