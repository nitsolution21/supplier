package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "customer_OpenPO",procedureName = "customer_OpenPO")})
public class GlobalEntity {
	
	@Id
    @Column(name = "Open_Po")
    private long openPo;

}
