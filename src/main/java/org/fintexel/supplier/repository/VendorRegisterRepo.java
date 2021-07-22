package org.fintexel.supplier.repository;


import java.util.Optional;

import org.fintexel.supplier.entity.VendorRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRegisterRepo extends JpaRepository<VendorRegister,Long>{
	
	public Optional<VendorRegister> findByUsername(String userName);
	
	public Optional<VendorRegister> findByEmail(String email);
}
