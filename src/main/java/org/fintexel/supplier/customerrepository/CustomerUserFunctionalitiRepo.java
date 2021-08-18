package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.CustomerUserFunctionaliti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserFunctionalitiRepo extends JpaRepository<CustomerUserFunctionaliti, Long> {
	
	public List<CustomerUserFunctionaliti> findByUserId(long userId);
	
	public void deleteByUserId(long userId);
}
