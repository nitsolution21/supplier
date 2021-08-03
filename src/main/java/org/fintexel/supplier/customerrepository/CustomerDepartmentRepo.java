package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDepartmentRepo extends JpaRepository<CustomerDepartment, Long>{

}
