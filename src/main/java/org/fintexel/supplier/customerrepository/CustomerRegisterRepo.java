package org.fintexel.supplier.customerrepository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRegisterRepo extends JpaRepository<CustomerRegister, Long> {
	
	public Optional<CustomerRegister> findByUsername(String username);

	public List<CustomerRegister> findBycId(long cId);
	
	public Optional<CustomerRegister> findByEmail(String email);
	
	@Query("select customer from CustomerRegister customer , CustomerUserDepartments dep where customer.userId = dep.userId and dep.departmentId = ?1")
	public List<CustomerRegister> findUserByDepartment(long departmentId);
}
