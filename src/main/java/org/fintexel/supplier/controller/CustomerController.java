package org.fintexel.supplier.controller;

import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.fintexel.supplier.customerentity.CustomerContact;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.validation.FieldValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class CustomerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	
	@Autowired
	CustomerAddressRepo customerAddressRepo;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private CustomerContactRepo customerContactRepo;
	
	@PostMapping("/customer/address")
	public CustomerAddress createCustomerAddress(@RequestBody CustomerAddress customerAddress) {
		
		LOGGER.info("Inside - CustomerController.createCustomerAddress()");
		
		try {
			CustomerAddress filterCustomerAddress = new CustomerAddress();
			if(
				(fieldValidation.isEmpty(customerAddress.getcId())) && (fieldValidation.isEmpty(customerAddress.getAddressType())) &&
				(fieldValidation.isEmpty(customerAddress.getAddress1())) && (fieldValidation.isEmpty(customerAddress.getAddress2())) &&
				(fieldValidation.isEmpty(customerAddress.getCity())) && (fieldValidation.isEmpty(customerAddress.getPostalCode())) &&
				(fieldValidation.isEmpty(customerAddress.getCountry())) && (fieldValidation.isEmpty(customerAddress.getRegion())) &&
				(fieldValidation.isEmpty(customerAddress.getAddressProof())) && (fieldValidation.isEmpty(customerAddress.getAddressProofPath())) &&
				(fieldValidation.isEmpty(customerAddress.getStatus()))
			  ){
				
				
					filterCustomerAddress.setcId(customerAddress.getcId());
					filterCustomerAddress.setAddressType(customerAddress.getAddressType());
					filterCustomerAddress.setAddress1(customerAddress.getAddress1());
					filterCustomerAddress.setAddress2(customerAddress.getAddress2());
					filterCustomerAddress.setCity(customerAddress.getCity());
					filterCustomerAddress.setPostalCode(customerAddress.getPostalCode());
					filterCustomerAddress.setCountry(customerAddress.getCountry());
					filterCustomerAddress.setRegion(customerAddress.getRegion());
					filterCustomerAddress.setAddressProof(customerAddress.getAddressProof());
					filterCustomerAddress.setAddressProofPath(customerAddress.getAddressProofPath());
					filterCustomerAddress.setStatus("COMPLEATE");
//					filterCustomerAddress.setCreatedBy(customerAddress.getCreatedBy());
//					filterCustomerAddress.setCreatedOn(customerAddress.getCreatedOn());
//					filterCustomerAddress.setUpdatedBy(customerAddress.getUpdatedBy());
//					filterCustomerAddress.setUpdatedOn(customerAddress.getUpdatedOn());
					return customerAddressRepo.save(filterCustomerAddress);
				
			}else {
				throw new VendorNotFoundException("Validation error");
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	
	@GetMapping("/customer/address/{id}")
	public CustomerAddress getCustomerAddress(@PathVariable Long id) {
		
		LOGGER.info("Inside - CustomerController.getCustomerAddress()");
		
		try {
			
			Optional<CustomerAddress> findById = customerAddressRepo.findById(id);
			if(findById.isPresent()) {
				
				return findById.get();
				
			}else {
				
				throw new VendorNotFoundException("No Data Found");
				
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	@PutMapping("/customer/address/{id}")
	public CustomerAddress putCustomerAddress(@PathVariable Long id , @RequestBody CustomerAddress customerAddress) {
		
		LOGGER.info("Inside - CustomerController.putCustomerAddress()");
		
		try {
			CustomerAddress filterCustomerAddress = new CustomerAddress();
			if(
				(fieldValidation.isEmpty(customerAddress.getcId())) && (fieldValidation.isEmpty(customerAddress.getAddressType())) &&
				(fieldValidation.isEmpty(customerAddress.getAddress1())) && (fieldValidation.isEmpty(customerAddress.getAddress2())) &&
				(fieldValidation.isEmpty(customerAddress.getCity())) && (fieldValidation.isEmpty(customerAddress.getPostalCode())) &&
				(fieldValidation.isEmpty(customerAddress.getCountry())) && (fieldValidation.isEmpty(customerAddress.getRegion())) &&
				(fieldValidation.isEmpty(customerAddress.getAddressProof())) && (fieldValidation.isEmpty(customerAddress.getAddressProofPath())) &&
				(fieldValidation.isEmpty(customerAddress.getStatus()))
			  ){
						
				Optional<CustomerAddress> findById = customerAddressRepo.findById(id);
				if(findById.isPresent()) {
					
					filterCustomerAddress.setAddressId(findById.get().getAddressId());
					filterCustomerAddress.setcId(customerAddress.getcId());
					filterCustomerAddress.setAddressType(customerAddress.getAddressType());
					filterCustomerAddress.setAddress1(customerAddress.getAddress1());
					filterCustomerAddress.setAddress2(customerAddress.getAddress2());
					filterCustomerAddress.setCity(customerAddress.getCity());
					filterCustomerAddress.setPostalCode(customerAddress.getPostalCode());
					filterCustomerAddress.setCountry(customerAddress.getCountry());
					filterCustomerAddress.setRegion(customerAddress.getRegion());
					filterCustomerAddress.setAddressProof(customerAddress.getAddressProof());
					filterCustomerAddress.setAddressProofPath(customerAddress.getAddressProofPath());
//					filterCustomerAddress.setStatus(customerAddress.getStatus());
//					filterCustomerAddress.setCreatedBy(customerAddress.getCreatedBy());
//					filterCustomerAddress.setCreatedOn(customerAddress.getCreatedOn());
//					filterCustomerAddress.setUpdatedBy(customerAddress.getUpdatedBy());
//					filterCustomerAddress.setUpdatedOn(customerAddress.getUpdatedOn());
					return customerAddressRepo.save(filterCustomerAddress);
					
				}else {
					
					throw new VendorNotFoundException("No Data Found");
					
				}
				
			}else {
				throw new VendorNotFoundException("Validation error");
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}
	
	
	
	@DeleteMapping("/customer/address/{id}")
	public CustomerAddress deleteCustomerAddress(@PathVariable Long id) {
		LOGGER.info("Inside - CustomerController.deleteCustomerAddress()");
		
		try {
			
			Optional<CustomerAddress> findById = customerAddressRepo.findById(id);
			if(findById.isPresent()) {
				
				CustomerAddress customerAddress = findById.get();
				if(customerAddress.getStatus().equals("deleted")) {
					throw new VendorNotFoundException("Data Already Deleted");
				}else {
					customerAddress.setStatus("DELETED");
					return customerAddressRepo.save(customerAddress);
				}
			}else {
				
				throw new VendorNotFoundException("No Data Found");
				
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	@PostMapping("/customer/contact")
	public CustomerContact createCustomerContact(@RequestBody CustomerContact customerContact) {
		LOGGER.info("Inside - CustomerController.createCustomerContact()");
		try {
			if(
				(fieldValidation.isEmpty(customerContact.getcId()))  && (fieldValidation.isEmpty(customerContact.getSupplierCode())) &&
				(fieldValidation.isEmpty(customerContact.getContractType()))  && (fieldValidation.isEmpty(customerContact.getContractTerms())) &&
				(fieldValidation.isEmpty(customerContact.getContractProof()))  && (fieldValidation.isEmpty(customerContact.getContractLocation())) 
			) {
				CustomerContact filterCustomerContact = new CustomerContact();
				filterCustomerContact.setcId(customerContact.getcId());
				filterCustomerContact.setSupplierCode(customerContact.getSupplierCode());
				filterCustomerContact.setContractType(customerContact.getContractType());
				filterCustomerContact.setContractTerms(customerContact.getContractTerms());
				filterCustomerContact.setContractProof(customerContact.getContractProof());
				filterCustomerContact.setContractLocation(customerContact.getContractLocation());
				
				
				return customerContactRepo.save(filterCustomerContact);
				
				
				
			}else {
				throw new VendorNotFoundException("Validation error");
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/customer/contact/{id}")
	public CustomerContact getCustomerContact(@PathVariable Long id) {
		LOGGER.info("Inside - CustomerController.getCustomerContact()");
		try {
			
			Optional<CustomerContact> findById = customerContactRepo.findById(id);
			if(findById.isPresent()) {
				return findById.get();
			}else {
				throw new VendorNotFoundException("Data not exist");
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	
	@PutMapping("/customer/contact/{id}")
	public CustomerContact putCustomerContact(@PathVariable Long id , @RequestBody CustomerContact customerContact) {
		LOGGER.info("Inside - CustomerController.putCustomerContact()");
		
		try {
			
			Optional<CustomerContact> findById = customerContactRepo.findById(id);
			if(findById.isPresent()) {
				if(
						(fieldValidation.isEmpty(customerContact.getcId()))  && (fieldValidation.isEmpty(customerContact.getSupplierCode())) &&
						(fieldValidation.isEmpty(customerContact.getContractType()))  && (fieldValidation.isEmpty(customerContact.getContractTerms())) &&
						(fieldValidation.isEmpty(customerContact.getContractProof()))  && (fieldValidation.isEmpty(customerContact.getContractLocation())) 
					) {
						CustomerContact filterCustomerContact = new CustomerContact();
						filterCustomerContact.setContractId(findById.get().getContractId());
						filterCustomerContact.setcId(customerContact.getcId());
						filterCustomerContact.setSupplierCode(customerContact.getSupplierCode());
						filterCustomerContact.setContractType(customerContact.getContractType());
						filterCustomerContact.setContractTerms(customerContact.getContractTerms());
						filterCustomerContact.setContractProof(customerContact.getContractProof());
						filterCustomerContact.setContractLocation(customerContact.getContractLocation());
						
						
						return customerContactRepo.save(filterCustomerContact);
						
						
						
					}else {
						throw new VendorNotFoundException("Validation error");
					}
			}else {
				throw new VendorNotFoundException("Data not exist");
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
}
