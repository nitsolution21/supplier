package org.fintexel.supplier.repository.flowablerepo;

import java.util.Optional;

import org.fintexel.supplier.entity.flowableentity.FlowableForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlowableFormRepo extends JpaRepository<FlowableForm, String> {
//	 @Query("select ID_ from flowable.ACT_FO_FORM_DEFINITION where KEY_ = 'frmforgotpwdsupplier' and VERSION_=(select max(VERSION_) from flowable.ACT_FO_FORM_DEFINITION where KEY_ = 'frmforgotpwdsupplier');")
	 @Query("select s from FlowableForm s where s.key = ?1 and s.version=(select max(p.version) from FlowableForm p where p.key=?1)")
	 Optional<FlowableForm> findByFromId(String Key);
}
