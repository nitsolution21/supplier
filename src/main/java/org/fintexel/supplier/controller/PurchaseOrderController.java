package org.fintexel.supplier.controller;


import java.util.ArrayList;
import java.util.List;

import org.fintexel.supplier.customerentity.CustomerContact;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer/po")
public class PurchaseOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
	
	@Autowired
	private GetCustomerDetails getCustomerDetails;
	
	@Autowired
	private CustomerContactRepo customerContactRepo;
	
	@Autowired
	private SupDetailsRepo supDetailsRepo;
	
	@PostMapping("/getContractSuppliers")
	public void getContractSuppliers(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getContractSuppliers()");
		
		try {
			
			long cIdFromToken = getCustomerDetails.getCIdFromToken(token);
			if(cIdFromToken == -1) {
				throw new VendorNotFoundException("Customer Data Not Found");
			}else {
				
				List<CustomerContact> customerContactList = customerContactRepo.findBycId(cIdFromToken);
				
				if(customerContactList.size()>0) {
					List<SupDetails> listSupplierDetails = new ArrayList<>();
					
					for(CustomerContact obj : customerContactList) {
						SupDetails supDetails = supDetailsRepo.findById(obj.getSupplierCode()).get();
						listSupplierDetails.add(supDetails);
					}
					
				}else {
					throw new VendorNotFoundException("No Contract Found With This Customer");
				}
				
			}

		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}
	
	
	@PostMapping("/getContractSupplierDetails/{code}")
	public void getContractSupplierDetails(@PathVariable("code") String code , @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getContractSuppliers()");
		
		try {
			
			long cIdFromToken = getCustomerDetails.getCIdFromToken(token);
			if(cIdFromToken == -1) {
				throw new VendorNotFoundException("Customer Data Not Found");
			}else {
				
				List<CustomerContact> customerContactList = customerContactRepo.findBycId(cIdFromToken);
				
				if(customerContactList.size()>0) {
				
					
				}else {
					throw new VendorNotFoundException("No Contract Found With This Customer");
				}
				
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	
}
