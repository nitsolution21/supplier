package org.fintexel.supplier.controller;

import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.fintexel.supplier.customerentity.CustomerContact;
import org.fintexel.supplier.customerentity.CustomerProfile;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.CustomerProfileRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.CustomerUserRolesRepo;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	
	@Autowired
	CustomerAddressRepo customerAddressRepo;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private CustomerContactRepo customerContactRepo;
	
	@Autowired
	private CustomerProfileRepo customerProfileRepo;
	
	@Autowired
	private GetCustomerDetails getCustomerDetails; 
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo; 
	
	
	@PostMapping("/address")
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
	
	
	
	@GetMapping("/address/{id}")
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
	
	
	@PutMapping("/address/{id}")
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
	
	
	
	@DeleteMapping("/address/{id}")
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
	
	
	@PostMapping("/contact")
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
	
	
	
	@GetMapping("/contact/{id}")
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
	
	
	
	
	@PutMapping("/contact/{id}")
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
	
	@PostMapping("/profile")
	public CustomerProfile addCustomerProfile(@RequestBody CustomerProfile customerProfile, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.addCustomerProfile()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			LOGGER.info("customerIdFromToken >>> " + customerIdFromToken);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			} 
			else {
				String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
				LOGGER.info("Role is >>> "+roleByUserId);
				if (roleByUserId.equals("SADMIN")) {
					
					CustomerProfile customerProfile2 = new CustomerProfile();
					
					if (fieldValidation.isEmpty(customerProfile.getCustomerName()) && fieldValidation.isEmpty(customerProfile.getCustomerContact1()) && fieldValidation.isEmpty(customerProfile.getRegistrationType()) && fieldValidation.isEmpty(customerProfile.getRegistrationNo()) ) {
						customerProfile2.setCustomerName(customerProfile.getCustomerName());
						customerProfile2.setCustomerContact1(customerProfile.getCustomerContact1());
						customerProfile2.setRegistrationType(customerProfile.getRegistrationType());
						customerProfile2.setRegistrationNo(customerProfile.getRegistrationNo());
						customerProfile2.setStatus("COMPLEATE");
						customerProfile2.setcId(customerProfile.getcId());
						try {
							if (fieldValidation.isEmpty(customerProfile.getCustomerContact2())) {
								customerProfile2.setCustomerContact2(customerProfile.getCustomerContact2());
							} 
						} catch (Exception e) {
							// TODO: handle exception
						}
						LOGGER.info(customerProfile2.toString());
						return customerProfileRepo.save(customerProfile2);
					} else {
						throw new VendorNotFoundException("Validation error");
					}
				} else {
					throw new VendorNotFoundException("You don't have permission to add profile");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@PutMapping("/profile/{profileId}")
	public CustomerProfile updateCustomerProfile(@PathVariable long profileId, @RequestBody CustomerProfile customerProfile, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.updateCustomerProfile()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
				if (roleByUserId.equals("SADMIN")) {
					Optional<CustomerRegister> findById = customerRegisterRepo.findById(customerIdFromToken);
					if (findById.isPresent()) {
						if (findById.get().getcId() == profileId) {
							
							CustomerProfile customerProfile2 = new CustomerProfile();
							
							if (fieldValidation.isEmpty(customerProfile.getCustomerName()) && fieldValidation.isEmpty(customerProfile.getCustomerContact1()) && fieldValidation.isEmpty(customerProfile.getRegistrationType()) && fieldValidation.isEmpty(customerProfile.getRegistrationNo()) ) {
								customerProfile2.setCustomerName(customerProfile.getCustomerName());
								customerProfile2.setCustomerContact1(customerProfile.getCustomerContact1());
								customerProfile2.setRegistrationType(customerProfile.getRegistrationType());
								customerProfile2.setRegistrationNo(customerProfile.getRegistrationNo());
								customerProfile2.setStatus("COMPLEATE");
								customerProfile2.setcId(profileId);
								try {
									if (fieldValidation.isEmpty(customerProfile.getCustomerContact2())) {
										customerProfile2.setCustomerContact2(customerProfile.getCustomerContact2());
									} 
								} catch (Exception e) {
									// TODO: handle exception
								}
								return customerProfileRepo.save(customerProfile2);
							} else {
								throw new VendorNotFoundException("Validation error");
							}
							
						} else {
							throw new VendorNotFoundException("You don't have permission to update profile");
						}
					} else {
						throw new VendorNotFoundException("Your Details is not present in register table");
					}
				} else {
					throw new VendorNotFoundException("You don't have permission to update profile");
				}
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/profile")
	public CustomerProfile getCustomerProfile(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.getCustomerProfile()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
				if (roleByUserId.equals("SADMIN")) {
					Optional<CustomerRegister> findById = customerRegisterRepo.findById(customerIdFromToken);
					if (findById.isPresent()) {
						Optional<CustomerProfile> findById2 = customerProfileRepo.findById(findById.get().getcId());
						if (findById2.isPresent()) {
							return findById2.get();
						} else {
							throw new VendorNotFoundException("Customer profile not found");
							
						}
					} else {
						throw new VendorNotFoundException("Your Details is not present in register table");
					}
				} else {
					throw new VendorNotFoundException("You don't have permission to get profile");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
}
