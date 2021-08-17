package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.CustomerUserFunctionaliti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserFunctionalitiRepo extends JpaRepository<CustomerUserFunctionaliti, Long> {
	
	List<CustomerUserFunctionaliti> findByUserId(long userId);
}
