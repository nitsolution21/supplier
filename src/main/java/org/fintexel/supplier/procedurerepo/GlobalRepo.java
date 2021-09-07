package org.fintexel.supplier.procedurerepo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.fintexel.supplier.procedureentity.GlobalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalRepo {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<GlobalEntity> getOpenPo(){
		return entityManager.createStoredProcedureQuery("customer_OpenPO").getResultList();
	}

}
