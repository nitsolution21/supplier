package org.fintexel.supplier.repository;

import java.util.Optional;

import org.fintexel.supplier.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepo extends JpaRepository<LoginLog, Long>{
	Optional<LoginLog> findByRegisterId(Long registerId);
}
