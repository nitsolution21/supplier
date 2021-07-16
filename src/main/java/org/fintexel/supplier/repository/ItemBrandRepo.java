package org.fintexel.supplier.repository;

import java.util.List;

import org.fintexel.supplier.entity.ItemBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBrandRepo extends JpaRepository<ItemBrand, Long> {

	List<ItemBrand> findByBrandName(String brandName);

}
