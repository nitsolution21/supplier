package org.fintexel.supplier.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.procedureentity.GlobalEntity;
import org.fintexel.supplier.procedureentity.Invoice_Amount_Entity;
import org.fintexel.supplier.procedureentity.Overall_PO_Amount_Entity;
import org.fintexel.supplier.procedureentity.Pending_PO_Supplier_Entity;
import org.fintexel.supplier.procedureentity.PoAmountByStatusEntity;
import org.fintexel.supplier.procedureentity.PoByCustomerEntity;
import org.fintexel.supplier.procedureentity.PoByStatusEntity;
import org.fintexel.supplier.procedureentity.PoKpiSuppliersEntity;
import org.fintexel.supplier.procedureentity.PoStatusEntity;
import org.fintexel.supplier.procedureentity.SupplierContractEntity;
import org.fintexel.supplier.procedurerepo.GlobalRepo;
import org.fintexel.supplier.procedurerepo.InvoiceAmountRepo;
import org.fintexel.supplier.procedurerepo.OverallPoAmountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
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
	
	@GetMapping("/getOverallPoAmount")
	public List<Overall_PO_Amount_Entity> overallPoAmount(){
		LOGGER.info("Inside - ProceduresController.overallPoAmount()");
		
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
	
	@GetMapping("/getPoAmountByStatus")
	public List<PoAmountByStatusEntity> poAmountByStatus(){
		
			LOGGER.info("Inside - ProceduresController.poAmountByStatus()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("PO_Amount_By_Status").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getPoByCustomer")
	public List<PoByCustomerEntity> poByCustomer(){
		
			LOGGER.info("Inside - ProceduresController.poByCustomer()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("PO_BY_CUSTOMER").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getPoByStatus")
	public List<PoByStatusEntity> poByStatus(){
		
			LOGGER.info("Inside - ProceduresController.poByStatus()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("PO_BY_STATUS").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getPoKpiSuppliers")
	public List<PoKpiSuppliersEntity> poKpiSuppliers(){
		
			LOGGER.info("Inside - ProceduresController.poKpiSuppliers()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("PO_KPI_Suppliers").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getPoStatus")
	public List<PoStatusEntity> poStatus(){
		
			LOGGER.info("Inside - ProceduresController.poStatus()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("PO_status").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getSupplierContract")
	public List<SupplierContractEntity> SupplierContract(){
		
			LOGGER.info("Inside - ProceduresController.SupplierContract()");
		
		try {
			List resultList = entityManager.createStoredProcedureQuery("SUPPLIER_CONTRACT").getResultList();
			
			if(resultList.size()<1) {
				
				throw new VendorNotFoundException("No record found");
			}else {
				
				return resultList; 
			}
						
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getPendingPoSupplier/{poYear}/{poMonth}/{poDay}")
	public List<Pending_PO_Supplier_Entity> getPendingPoSupplier(@PathVariable String poYear, @PathVariable String poMonth, @PathVariable String poDay) {
		LOGGER.info("Inside - ProceduresController.SupplierContract()");
		try {
			StoredProcedureQuery createStoredProcedureQuery = entityManager.createStoredProcedureQuery("PENDING_PO_SUPPLIER")
					.registerStoredProcedureParameter("POYEAR", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("POMONTH", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("PODATE", String.class, ParameterMode.IN);
			
			createStoredProcedureQuery.setParameter("POYEAR", poYear);
			createStoredProcedureQuery.setParameter("POMONTH", poMonth);
			createStoredProcedureQuery.setParameter("PODATE", poDay);
			
			List resultList = createStoredProcedureQuery.getResultList();

			if (resultList.size() < 1) {
				throw new VendorNotFoundException("No record found");
			} else {
				return resultList;
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	

}
