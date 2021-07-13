package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.SupDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupDetailsRepo extends JpaRepository<SupDetails, String> {
	
	List<SupDetails> findByRegisterId(Long name);
	

}
