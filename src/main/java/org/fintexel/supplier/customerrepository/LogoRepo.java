package org.fintexel.supplier.customerrepository;

import org.fintexel.supplier.customerentity.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepo extends JpaRepository<Logo, Integer>{

}
