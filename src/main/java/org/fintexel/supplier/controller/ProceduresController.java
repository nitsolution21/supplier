package org.fintexel.supplier.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.procedureentity.GlobalEntity;
import org.fintexel.supplier.procedureentity.Invoice_Amount_Entity;
import org.fintexel.supplier.procedureentity.Overall_PO_Amount_Entity;
import org.fintexel.supplier.procedurerepo.GlobalRepo;
import org.fintexel.supplier.procedurerepo.InvoiceAmountRepo;
import org.fintexel.supplier.procedurerepo.OverallPoAmountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProceduresController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProceduresController.class);
	
	@Autowired
	private GlobalRepo globalRepo;
	
	@Autowired
	private EntityManager entityManager;

	
	@GetMapping("/getOpenPo")
	public List<GlobalEntity> getOpenPo() {
		
		LOGGER.info("Inside - ProceduresController.getOpenPo()");
		
		try {
			 List<GlobalEntity> openPo = globalRepo.getOpenPo();
			 
			 if(openPo.size()<1) {
				 throw new VendorNotFoundException("No record found");
			 }else {
				 return openPo;
			 }
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getInvoiceAmount")
	public List<Invoice_Amount_Entity> invoiceAmount(){
		LOGGER.info("Inside - ProceduresController.invoiceAmount()");
		try {
			List resultList = entityManager.createStoredProcedureQuery("Invoice_Amount").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No Record found in Database");
			}else {
				
				return resultList;
			}
		}
		catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/overallPoAmount")
	public List<Overall_PO_Amount_Entity> overallPoAmount(){
		LOGGER.info("Inside - ProceduresController.invoiceAmount()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("Overall_PO_Amount").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}

}
