package org.fintexel.supplier.customerrepository;

import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerUserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserRolesRepo extends JpaRepository<CustomerUserRoles, Long>{
	
	public Optional<CustomerUserRoles> findByUserId(long userId);
	
	public void deleteByUserId(long userId);
}
