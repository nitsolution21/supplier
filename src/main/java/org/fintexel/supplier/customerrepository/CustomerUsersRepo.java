package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.CustomerUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUsersRepo extends JpaRepository<CustomerUsers, Long>{

}
