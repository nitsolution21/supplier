package org.fintexel.supplier.repository;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupRequest;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupRequestRepo extends JpaRepository<SupRequest, Long>{
	@Query("select s from SupRequest s where s.status = ?1 group by s.supplierCode")
	List<SupRequest> findAllWithStatus(String status);
	
	
	@Query("select s from SupRequest s where s.status = ?1 and s.supplierCode = ?2")
	List<SupRequest> findAllStatus(String status, String supplierCode);
	
	
//	@Query("select s from SupRequest s where s.statu = ?1 and s.supplierCode = ?2")

}
