package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDepartmentsRepo extends JpaRepository<CustomerDepartments, Long>{

}
