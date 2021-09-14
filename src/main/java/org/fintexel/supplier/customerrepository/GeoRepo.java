package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.GeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GeoRepo extends JpaRepository<GeoEntity, Long> {
	
	public List<GeoEntity> findByParentId(long parentId);
	
	public List<GeoEntity> findByType(String type);
	
	@Query(value = "select * from TBL_GEO where upper(NAME) = ?1" , nativeQuery = true)
	public List<GeoEntity> findByName(String name);
	
	@Query(value = "select * from TBL_GEO where upper(NAME) = ?1 and TYPE = ?2" , nativeQuery = true)
	public List<GeoEntity> findByNameWithType(String name , String type);
}
