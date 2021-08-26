package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDepartmentsRepo extends JpaRepository<CustomerDepartments, Long>{
	
	public List<CustomerDepartments> findBycId(Long cId);
}
