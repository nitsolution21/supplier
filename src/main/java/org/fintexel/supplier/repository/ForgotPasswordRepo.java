package org.fintexel.supplier.repository;

import java.util.Optional;

import org.fintexel.supplier.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Long> {
	public Optional<ForgotPassword> findByToken(String token);
}
