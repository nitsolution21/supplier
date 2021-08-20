package org.fintexel.supplier.controller;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.fintexel.supplier.customerentity.CustomerContact;

import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerProfile;
import org.fintexel.supplier.customerentity.CustomerProfileResponce;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.CustomerUserDepartments;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerProfileRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.CustomerUserDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerUserRolesRepo;
import org.fintexel.supplier.entity.RegType;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.repository.RegTypeRepo;
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

	@Autowired
	private CustomerDepartmentsRepo customerDepartmentsRepo;
	
	@Autowired
	CustomerUserDepartmentsRepo customerUserDepartmentsRepo;

	@Autowired
	RegTypeRepo regTypeRepo;

	@PostMapping("/address")
	public CustomerAddress createCustomerAddress(@RequestBody CustomerAddress customerAddress, @RequestHeader(name = "Authorization") String token) {

		LOGGER.info("Inside - CustomerController.createCustomerAddress()");

		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
				CustomerAddress filterCustomerAddress = new CustomerAddress();
				switch (roleByUserId) {
				case "SADMIN":
					if ((fieldValidation.isEmpty(customerAddress.getAddressType()))
							&& (fieldValidation.isEmpty(customerAddress.getAddress1()))
							&& (fieldValidation.isEmpty(customerAddress.getCity()))
							&& (fieldValidation.isEmpty(customerAddress.getPostalCode()))
							&& (fieldValidation.isEmpty(customerAddress.getCountry()))
							&& (fieldValidation.isEmpty(customerAddress.getRegion()))
							&& (fieldValidation.isEmpty(customerAddress.getAddressProof()))
							&& (fieldValidation.isEmpty(customerAddress.getAddressProofPath()))) {
						
						filterCustomerAddress.setcId(companyProfileIdByCustomerId);
						filterCustomerAddress.setAddressType(customerAddress.getAddressType());
						filterCustomerAddress.setAddress1(customerAddress.getAddress1());
						filterCustomerAddress.setCity(customerAddress.getCity());
						filterCustomerAddress.setPostalCode(customerAddress.getPostalCode());
						filterCustomerAddress.setCountry(customerAddress.getCountry());
						filterCustomerAddress.setRegion(customerAddress.getRegion());
						filterCustomerAddress.setAddressProof(customerAddress.getAddressProof());
						filterCustomerAddress.setAddressProofPath(customerAddress.getAddressProofPath());
						filterCustomerAddress.setStatus("COMPLEATE");
						try {
							if (fieldValidation.isEmpty(customerAddress.getAddress2())) {
								filterCustomerAddress.setAddress2(customerAddress.getAddress2());
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

						 CustomerAddress saveCustomerAddress = customerAddressRepo.save(filterCustomerAddress);
						 

						return saveCustomerAddress;

					} else {
						throw new VendorNotFoundException("Validation error");
					}
					
				case "ADMIN":
					if ((fieldValidation.isEmpty(customerAddress.getAddressType()))
							&& (fieldValidation.isEmpty(customerAddress.getAddress1()))
							&& (fieldValidation.isEmpty(customerAddress.getCity()))
							&& (fieldValidation.isEmpty(customerAddress.getPostalCode()))
							&& (fieldValidation.isEmpty(customerAddress.getCountry()))
							&& (fieldValidation.isEmpty(customerAddress.getRegion()))
							&& (fieldValidation.isEmpty(customerAddress.getAddressProof()))
							&& (fieldValidation.isEmpty(customerAddress.getAddressProofPath()))
							&& (fieldValidation.isEmpty(customerAddress.getStatus()))) {
						
						filterCustomerAddress.setcId(companyProfileIdByCustomerId);
						filterCustomerAddress.setAddressType(customerAddress.getAddressType());
						filterCustomerAddress.setAddress1(customerAddress.getAddress1());
						filterCustomerAddress.setCity(customerAddress.getCity());
						filterCustomerAddress.setPostalCode(customerAddress.getPostalCode());
						filterCustomerAddress.setCountry(customerAddress.getCountry());
						filterCustomerAddress.setRegion(customerAddress.getRegion());
						filterCustomerAddress.setAddressProof(customerAddress.getAddressProof());
						filterCustomerAddress.setAddressProofPath(customerAddress.getAddressProofPath());
						filterCustomerAddress.setStatus("COMPLEATE");
						try {
							if (fieldValidation.isEmpty(customerAddress.getAddress2())) {
								filterCustomerAddress.setAddress2(customerAddress.getAddress2());
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

						 CustomerAddress saveCustomerAddress = customerAddressRepo.save(filterCustomerAddress);
						 

						return saveCustomerAddress;

					} else {
						throw new VendorNotFoundException("Validation error");
					}
				case "USER":
					throw new VendorNotFoundException("You don't have access to add address");

				default:
					throw new VendorNotFoundException("The role is not present");
				}
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}
	
	@GetMapping("/address")
	public List<CustomerAddress> getAddress(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.getAddress()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			List<CustomerAddress> findByCId = customerAddressRepo.findActiveAddress(companyProfileIdByCustomerId);
			if (findByCId.size() > 0) {
				return findByCId;
			} else {
				throw new VendorNotFoundException("No address found for this company!! Please add first");
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

//	@GetMapping("/address/{id}")
//	public CustomerAddress getCustomerAddress(@PathVariable Long id) {
//
//		LOGGER.info("Inside - CustomerController.getCustomerAddress()");
//
//		try {
//
//			Optional<CustomerAddress> findById = customerAddressRepo.findById(id);
//			if (findById.isPresent()) {
//
//				return findById.get();
//
//			} else {
//
//				throw new VendorNotFoundException("No Data Found");
//
//			}
//
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}

	@PutMapping("/address/{id}")
	public CustomerAddress putCustomerAddress(@PathVariable Long id, @RequestBody CustomerAddress customerAddress, @RequestHeader(name = "Authorization") String token) {

		LOGGER.info("Inside - CustomerController.putCustomerAddress()");

		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			Optional<CustomerAddress> findCustomerAddressById = customerAddressRepo.findById(id);
			if (findCustomerAddressById.isPresent()) {
				if (findCustomerAddressById.get().getcId() == companyProfileIdByCustomerId) {
					CustomerAddress filterCustomerAddress = new CustomerAddress();
					switch (roleByUserId) {
					case "SADMIN":
						if ((fieldValidation.isEmpty(customerAddress.getAddressType()))
								&& (fieldValidation.isEmpty(customerAddress.getAddress1()))
								&& (fieldValidation.isEmpty(customerAddress.getCity()))
								&& (fieldValidation.isEmpty(customerAddress.getPostalCode()))
								&& (fieldValidation.isEmpty(customerAddress.getCountry()))
								&& (fieldValidation.isEmpty(customerAddress.getRegion()))
								&& (fieldValidation.isEmpty(customerAddress.getAddressProof()))
								&& (fieldValidation.isEmpty(customerAddress.getAddressProofPath()))) {
							
							filterCustomerAddress.setcId(companyProfileIdByCustomerId);
							filterCustomerAddress.setAddressType(customerAddress.getAddressType());
							filterCustomerAddress.setAddress1(customerAddress.getAddress1());
							filterCustomerAddress.setCity(customerAddress.getCity());
							filterCustomerAddress.setPostalCode(customerAddress.getPostalCode());
							filterCustomerAddress.setCountry(customerAddress.getCountry());
							filterCustomerAddress.setRegion(customerAddress.getRegion());
							filterCustomerAddress.setAddressProof(customerAddress.getAddressProof());
							filterCustomerAddress.setAddressProofPath(customerAddress.getAddressProofPath());
							filterCustomerAddress.setStatus("COMPLEATE");
							filterCustomerAddress.setAddressId(id);
							try {
								if (fieldValidation.isEmpty(customerAddress.getAddress2())) {
									filterCustomerAddress.setAddress2(customerAddress.getAddress2());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							 CustomerAddress saveCustomerAddress = customerAddressRepo.save(filterCustomerAddress);
							 

							return saveCustomerAddress;

						} else {
							throw new VendorNotFoundException("Validation error");
						}
					case "ADMIN":
						if ((fieldValidation.isEmpty(customerAddress.getAddressType()))
								&& (fieldValidation.isEmpty(customerAddress.getAddress1()))
								&& (fieldValidation.isEmpty(customerAddress.getCity()))
								&& (fieldValidation.isEmpty(customerAddress.getPostalCode()))
								&& (fieldValidation.isEmpty(customerAddress.getCountry()))
								&& (fieldValidation.isEmpty(customerAddress.getRegion()))
								&& (fieldValidation.isEmpty(customerAddress.getAddressProof()))
								&& (fieldValidation.isEmpty(customerAddress.getAddressProofPath()))
								&& (fieldValidation.isEmpty(customerAddress.getStatus()))) {
							
							filterCustomerAddress.setcId(companyProfileIdByCustomerId);
							filterCustomerAddress.setAddressType(customerAddress.getAddressType());
							filterCustomerAddress.setAddress1(customerAddress.getAddress1());
							filterCustomerAddress.setCity(customerAddress.getCity());
							filterCustomerAddress.setPostalCode(customerAddress.getPostalCode());
							filterCustomerAddress.setCountry(customerAddress.getCountry());
							filterCustomerAddress.setRegion(customerAddress.getRegion());
							filterCustomerAddress.setAddressProof(customerAddress.getAddressProof());
							filterCustomerAddress.setAddressProofPath(customerAddress.getAddressProofPath());
							filterCustomerAddress.setStatus("COMPLEATE");
							filterCustomerAddress.setAddressId(id);
							try {
								if (fieldValidation.isEmpty(customerAddress.getAddress2())) {
									filterCustomerAddress.setAddress2(customerAddress.getAddress2());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							 CustomerAddress saveCustomerAddress = customerAddressRepo.save(filterCustomerAddress);
							 

							return saveCustomerAddress;

						} else {
							throw new VendorNotFoundException("Validation error");
						}
					case "USER":
						throw new VendorNotFoundException("You don't have access to update address");
						
					default:
						throw new VendorNotFoundException("The role is not present");
					}
				} else {
					throw new VendorNotFoundException("You don't have permission to change another company address");
				}
			} else {
				throw new VendorNotFoundException("Address not found");
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@DeleteMapping("/address/{id}")
	public CustomerAddress deleteCustomerAddress(@PathVariable Long id) {
		LOGGER.info("Inside - CustomerController.deleteCustomerAddress()");

		try {

			Optional<CustomerAddress> findById = customerAddressRepo.findById(id);
			if (findById.isPresent()) {

				CustomerAddress customerAddress = findById.get();
				if (customerAddress.getStatus().equals("deleted")) {
					throw new VendorNotFoundException("Data Already Deleted");
				} else {
					customerAddress.setStatus("DELETED");
					return customerAddressRepo.save(customerAddress);
				}
			} else {

				throw new VendorNotFoundException("No Data Found");

			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	@PostMapping("/contact")
	public CustomerContact createCustomerContact(@RequestBody CustomerContact customerContact) {
		LOGGER.info("Inside - CustomerController.createCustomerContact()");
		try {
			if ((fieldValidation.isEmpty(customerContact.getcId()))
					&& (fieldValidation.isEmpty(customerContact.getSupplierCode()))
					&& (fieldValidation.isEmpty(customerContact.getContractType()))
					&& (fieldValidation.isEmpty(customerContact.getContractTerms()))
					&& (fieldValidation.isEmpty(customerContact.getContractProof()))
					&& (fieldValidation.isEmpty(customerContact.getContractLocation()))) {
				CustomerContact filterCustomerContact = new CustomerContact();
				filterCustomerContact.setcId(customerContact.getcId());
				filterCustomerContact.setSupplierCode(customerContact.getSupplierCode());
				filterCustomerContact.setContractType(customerContact.getContractType());
				filterCustomerContact.setContractTerms(customerContact.getContractTerms());
				filterCustomerContact.setContractProof(customerContact.getContractProof());
				filterCustomerContact.setContractLocation(customerContact.getContractLocation());

				return customerContactRepo.save(filterCustomerContact);

			} else {
				throw new VendorNotFoundException("Validation error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	@GetMapping("/contact/{id}")
	public CustomerContact getCustomerContact(@PathVariable Long id) {
		LOGGER.info("Inside - CustomerController.getCustomerContact()");
		try {

			Optional<CustomerContact> findById = customerContactRepo.findById(id);
			if (findById.isPresent()) {
				return findById.get();
			} else {
				throw new VendorNotFoundException("Data not exist");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PutMapping("/contact/{id}")
	public CustomerContact putCustomerContact(@PathVariable Long id, @RequestBody CustomerContact customerContact) {
		LOGGER.info("Inside - CustomerController.putCustomerContact()");

		try {

			Optional<CustomerContact> findById = customerContactRepo.findById(id);
			if (findById.isPresent()) {
				if ((fieldValidation.isEmpty(customerContact.getcId()))
						&& (fieldValidation.isEmpty(customerContact.getSupplierCode()))
						&& (fieldValidation.isEmpty(customerContact.getContractType()))
						&& (fieldValidation.isEmpty(customerContact.getContractTerms()))
						&& (fieldValidation.isEmpty(customerContact.getContractProof()))
						&& (fieldValidation.isEmpty(customerContact.getContractLocation()))) {
					CustomerContact filterCustomerContact = new CustomerContact();
					filterCustomerContact.setContractId(findById.get().getContractId());
					filterCustomerContact.setcId(customerContact.getcId());
					filterCustomerContact.setSupplierCode(customerContact.getSupplierCode());
					filterCustomerContact.setContractType(customerContact.getContractType());
					filterCustomerContact.setContractTerms(customerContact.getContractTerms());
					filterCustomerContact.setContractProof(customerContact.getContractProof());
					filterCustomerContact.setContractLocation(customerContact.getContractLocation());

					return customerContactRepo.save(filterCustomerContact);

				} else {
					throw new VendorNotFoundException("Validation error");
				}
			} else {
				throw new VendorNotFoundException("Data not exist");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PostMapping("/profile")
	public CustomerProfileResponce addCustomerProfile(@RequestBody CustomerProfile customerProfile,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.addCustomerProfile()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			LOGGER.info("customerIdFromToken >>> " + customerIdFromToken);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
				LOGGER.info("Role is >>> " + roleByUserId);
				if (roleByUserId.equals("SADMIN")) {

					CustomerProfile customerProfile2 = new CustomerProfile();

					if (fieldValidation.isEmpty(customerProfile.getCustomerName())
							&& fieldValidation.isEmpty(customerProfile.getCustomerContact1())
							&& fieldValidation.isEmpty(customerProfile.getRegistrationType())
							&& fieldValidation.isEmpty(customerProfile.getRegistrationNo())) {
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
						CustomerProfileResponce customerProfileResponce = new CustomerProfileResponce();
						CustomerProfile save = customerProfileRepo.save(customerProfile2);
						Optional<RegType> findRegistrationNameById = regTypeRepo
								.findById((long) customerProfile2.getRegistrationType());
						customerProfileResponce.setcId(save.getcId());
						customerProfileResponce.setCreatedBy(save.getCreatedBy());
						customerProfileResponce.setCreatedOn(save.getCreatedOn());
						customerProfileResponce.setCustomerContact1(save.getCustomerContact1());
						customerProfileResponce.setCustomerContact2(save.getCustomerContact2());
						customerProfileResponce.setCustomerName(save.getCustomerName());
						customerProfileResponce.setRegistrationNo(save.getRegistrationNo());
						customerProfileResponce.setRegistrationType(save.getRegistrationType());
						customerProfileResponce.setRegistrationTypeName(findRegistrationNameById.get().getName());
						customerProfileResponce.setStatus(save.getStatus());
						customerProfileResponce.setUpdatedBy(save.getUpdatedBy());
						customerProfileResponce.setUpdatedOn(save.getUpdatedOn());

						return customerProfileResponce;

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
	public CustomerProfile updateCustomerProfile(@PathVariable long profileId,
			@RequestBody CustomerProfile customerProfile, @RequestHeader(name = "Authorization") String token) {
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

							if (fieldValidation.isEmpty(customerProfile.getCustomerName())
									&& fieldValidation.isEmpty(customerProfile.getCustomerContact1())
									&& fieldValidation.isEmpty(customerProfile.getRegistrationType())
									&& fieldValidation.isEmpty(customerProfile.getRegistrationNo())) {
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
	public CustomerProfileResponce getCustomerProfile(@RequestHeader(name = "Authorization") String token) {
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
							CustomerProfileResponce customerProfileResponce = new CustomerProfileResponce();
							
							Optional<RegType> findRegistrationTypeById = regTypeRepo.findById((long) findById2.get().getRegistrationType());
							
							customerProfileResponce.setcId(findById2.get().getcId());
							customerProfileResponce.setCreatedBy(findById2.get().getCreatedBy());
							customerProfileResponce.setCreatedOn(findById2.get().getCreatedOn());
							customerProfileResponce.setCustomerContact1(findById2.get().getCustomerContact1());
							customerProfileResponce.setCustomerContact2(findById2.get().getCustomerContact2());
							customerProfileResponce.setCustomerName(findById2.get().getCustomerName());
							customerProfileResponce.setRegistrationNo(findById2.get().getRegistrationNo());
							customerProfileResponce.setRegistrationType(findById2.get().getRegistrationType());
							customerProfileResponce.setRegistrationTypeName(findRegistrationTypeById.get().getName());
							customerProfileResponce.setStatus(findById2.get().getStatus());
							customerProfileResponce.setUpdatedBy(findById2.get().getUpdatedBy());
							customerProfileResponce.setUpdatedOn(findById2.get().getUpdatedOn());
							return customerProfileResponce;
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
		
		//Test push
		
	}

	@PostMapping("/department")
	public CustomerDepartments addCustomerDepartment(@RequestBody CustomerDepartments customerDepartments, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.addCustomerDepartment()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
				String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
				long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
				CustomerDepartments departments = new CustomerDepartments();
				CustomerUserDepartments userCustomerDepartment = new CustomerUserDepartments();
				
				switch (roleByUserId) {
				case "SADMIN":
					if (fieldValidation.isEmpty(customerDepartments.getDepartmentName()) && fieldValidation.isEmpty(customerDepartments.getEmail()) && fieldValidation.isEmpty(customerDepartments.getPhoneNo()) && fieldValidation.isEmpty(customerDepartments.getCostCode())) {
						if (fieldValidation.isEmail(customerDepartments.getEmail())) {
							departments.setDepartmentName(customerDepartments.getDepartmentName());
							departments.setEmail(customerDepartments.getEmail());
							departments.setPhoneNo(customerDepartments.getPhoneNo());
							departments.setCostCode(customerDepartments.getCostCode());
							departments.setcId(companyProfileIdByCustomerId);
							try {
								if (fieldValidation.isEmpty(customerDepartments.getAlternatePhoneNo())) {
									departments.setAlternatePhoneNo(customerDepartments.getAlternatePhoneNo());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
							departments.setStatus("COMPLEATE");
							CustomerDepartments saveCustomerDepartments = customerDepartmentsRepo.save(departments);
							
							LOGGER.info("Save department is:   "+departments);
							
							userCustomerDepartment.setUserId(customerIdFromToken);
							userCustomerDepartment.setDepartmentId(saveCustomerDepartments.getDepartmentId());
							
							LOGGER.info("Save department user is:   "+userCustomerDepartment);
							
							CustomerUserDepartments customerUserDepartments = customerUserDepartmentsRepo.save(userCustomerDepartment);
							
							LOGGER.info("After Save department user is:   "+customerUserDepartments);
							
							return saveCustomerDepartments;
						} else {
							throw new VendorNotFoundException("Email id not valid");
						}
					} else {
						throw new VendorNotFoundException("All field required");
					}
				case "ADMIN":
					if (fieldValidation.isEmpty(customerDepartments.getDepartmentName()) && fieldValidation.isEmpty(customerDepartments.getEmail()) && fieldValidation.isEmpty(customerDepartments.getPhoneNo()) && fieldValidation.isEmpty(customerDepartments.getCostCode())) {
						if (fieldValidation.isEmail(customerDepartments.getEmail())) {
							departments.setDepartmentName(customerDepartments.getDepartmentName());
							departments.setEmail(customerDepartments.getEmail());
							departments.setPhoneNo(customerDepartments.getPhoneNo());
							departments.setCostCode(customerDepartments.getCostCode());
							departments.setcId(companyProfileIdByCustomerId);
							try {
								if (fieldValidation.isEmpty(customerDepartments.getAlternatePhoneNo())) {
									departments.setAlternatePhoneNo(customerDepartments.getAlternatePhoneNo());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
							departments.setStatus("COMPLEATE");
							CustomerDepartments saveCustomerDepartments = customerDepartmentsRepo.save(departments);
							
							List<CustomerRegister> findByCId = customerRegisterRepo.findBycId(companyProfileIdByCustomerId);
							
							for(CustomerRegister customer : findByCId) {
								String getRoleByUserId = getCustomerDetails.getRoleByUserId(customer.getUserId());
								if (getRoleByUserId.equals("SADMIN")) {
									userCustomerDepartment.setUserId(customer.getUserId());
									userCustomerDepartment.setDepartmentId(saveCustomerDepartments.getDepartmentId());
									customerUserDepartmentsRepo.save(userCustomerDepartment);
								}
							}
							
							return saveCustomerDepartments;
						} else {
							throw new VendorNotFoundException("Email not valid");
						}
					} else {
						throw new VendorNotFoundException("All field required");
					}
				case "USER":
					throw new VendorNotFoundException("You don't have access to add department");
				default:
					throw new VendorNotFoundException("The role is not present");
				}
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/department")
	public List<CustomerDepartments> getDepartments(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.getDepartments()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
				return getCustomerDetails.getDepartments(customerIdFromToken);
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@PutMapping("/department/{departmentId}")
	public CustomerDepartments updateDepartments(@PathVariable long departmentId, @RequestBody CustomerDepartments customerDepartments, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerController.updateDepartments()");
		try {
			Optional<CustomerDepartments> findDepartmentsById = customerDepartmentsRepo.findById(departmentId);
			if (findDepartmentsById.isPresent()) {
				long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
				String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
				if (customerIdFromToken == -1) {
					throw new VendorNotFoundException("Customer not found");
				}
				else {
					CustomerDepartments departments = new CustomerDepartments();
					switch (roleByUserId) {
					case "SADMIN":
						if (fieldValidation.isEmpty(customerDepartments.getDepartmentName()) && fieldValidation.isEmpty(customerDepartments.getEmail()) && fieldValidation.isEmpty(customerDepartments.getPhoneNo()) && fieldValidation.isEmpty(customerDepartments.getCostCode())) {
							if (fieldValidation.isEmail(customerDepartments.getEmail())) {
								departments.setDepartmentName(customerDepartments.getDepartmentName());
								departments.setEmail(customerDepartments.getEmail());
								departments.setPhoneNo(customerDepartments.getPhoneNo());
								departments.setCostCode(customerDepartments.getCostCode());
								departments.setDepartmentId(departmentId);
								departments.setcId(customerIdFromToken);
								try {
									if (fieldValidation.isEmpty(customerDepartments.getAlternatePhoneNo())) {
										departments.setAlternatePhoneNo(customerDepartments.getAlternatePhoneNo());
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
								departments.setStatus("COMPLEATE");
								CustomerDepartments saveCustomerDepartments = customerDepartmentsRepo.save(departments);
								
								return saveCustomerDepartments;
							} else {
								throw new VendorNotFoundException("Email id not valid");
							}
						} else {
							throw new VendorNotFoundException("All field required");
						}
					case "ADMIN":
						if (fieldValidation.isEmpty(customerDepartments.getDepartmentName()) && fieldValidation.isEmpty(customerDepartments.getEmail()) && fieldValidation.isEmpty(customerDepartments.getPhoneNo()) && fieldValidation.isEmpty(customerDepartments.getCostCode())) {
							if (fieldValidation.isEmail(customerDepartments.getEmail())) {
								departments.setDepartmentName(customerDepartments.getDepartmentName());
								departments.setEmail(customerDepartments.getEmail());
								departments.setPhoneNo(customerDepartments.getPhoneNo());
								departments.setCostCode(customerDepartments.getCostCode());
								departments.setDepartmentId(departmentId);
								departments.setcId(customerIdFromToken);
								try {
									if (fieldValidation.isEmpty(customerDepartments.getAlternatePhoneNo())) {
										departments.setAlternatePhoneNo(customerDepartments.getAlternatePhoneNo());
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
								departments.setStatus("COMPLEATE");
								CustomerDepartments saveCustomerDepartments = customerDepartmentsRepo.save(departments);
								
								return saveCustomerDepartments;
							} else {
								throw new VendorNotFoundException("Email id not valid");
							}
						} else {
							throw new VendorNotFoundException("All field required");
						}
					case "USER": 
						throw new VendorNotFoundException("You don't have access to add department");
					default:
						throw new VendorNotFoundException("The role is not present");
					}
				}
			} else {
				throw new VendorNotFoundException("Department not found");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
}
