package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.CustomerUserDepartments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserDepartmentsRepo extends JpaRepository<CustomerUserDepartments, Long> {
	
	public List<CustomerUserDepartments> findByUserId(long userId);
	
	public void deleteByUserId(long userId);
}
