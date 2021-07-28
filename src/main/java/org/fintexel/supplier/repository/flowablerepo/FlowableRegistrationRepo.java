package org.fintexel.supplier.repository.flowablerepo;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlowableRegistrationRepo extends JpaRepository<FlowableRegistration, String>{
	 @Query("select s from FlowableRegistration s where s.key = ?1 and s.version=(select max(p.version) from FlowableRegistration p where p.key=?1)")
	 Optional<FlowableRegistration> findByAuthorAndTitle(String key);
}
