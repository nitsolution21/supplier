package org.fintexel.supplier.repository;

import org.fintexel.supplier.entity.SupAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupAddRepo extends JpaRepository<SupAddress, Long> {

}
