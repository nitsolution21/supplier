package org.fintexel.supplier.customerrepository;

import java.util.List;

import org.fintexel.supplier.customerentity.GeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoRepo extends JpaRepository<GeoEntity, Long> {
	
	public List<GeoEntity> findByParentId(long parentId);
	
	public List<GeoEntity> findByType(String type);
}
