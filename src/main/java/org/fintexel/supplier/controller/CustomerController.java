package org.fintexel.supplier.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.fintexel.supplier.customerentity.CustomerContact;
import org.fintexel.supplier.customerentity.CustomerDepartment;
import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerProfile;
import org.fintexel.supplier.customerentity.CustomerProfileResponce;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerProfileRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.CustomerUserRolesRepo;
import org.fintexel.supplier.entity.RegType;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupContract;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.SupRequest;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.repository.RegTypeRepo;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.SupBankRepo;
import org.fintexel.supplier.repository.SupContractRepo;
import org.fintexel.supplier.repository.SupDepartmentRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.SupRequestRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableRegistrationRepo;
import org.fintexel.supplier.validation.FieldValidation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	
	@Autowired
	private SupAddressRepo supAddRepo;
	
	@Autowired
	private SupDetailsRepo supDetailsRepo;
	
	@Autowired
	private VendorRegisterRepo vendorRepo;
	
	@Autowired
	FlowableRegistrationRepo flowableRegistrationRepo;
	
	@Autowired
	CustomerAddressRepo customerAddressRepo;

	@Autowired
	private FieldValidation fieldValidation;

	@Autowired
	private CustomerContactRepo customerContactRepo;

	@Autowired
	private SupDepartmentRepo supDepartmentRepo;
	
	@Autowired
	private CustomerProfileRepo customerProfileRepo;

	@Autowired
	private GetCustomerDetails getCustomerDetails;

	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;

	@Autowired
	private CustomerDepartmentsRepo customerDepartmentsRepo;

	@Autowired
	private CustomerDepartmentRepo customerDepartmentRepo;

	@Autowired
	RegTypeRepo regTypeRepo;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private SupContractRepo supContractRepo;
	
	@Autowired
	private SupBankRepo supBankRepo;

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
				CustomerDepartment userCustomerDepartment = new CustomerDepartment();
				
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
							
							userCustomerDepartment.setcId(saveCustomerDepartments.getcId());
							userCustomerDepartment.setDepartmentId(saveCustomerDepartments.getDepartmentId());
							
							CustomerDepartment saveCustomerDepartment = customerDepartmentRepo.save(userCustomerDepartment);
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
									userCustomerDepartment.setcId(saveCustomerDepartments.getcId());
									userCustomerDepartment.setDepartmentId(saveCustomerDepartments.getDepartmentId());
									customerDepartmentRepo.save(userCustomerDepartment);
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
	
	
	
	
	
	@PostMapping("/vendor/registration")
	public VendorRegister postRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		String taskID1_ = "", taskID2_ = "", processInstID_ = "";
		try {
			if ((fieldValidation.isEmail(vendorReg.getEmail())
					& (fieldValidation.isEmpty(vendorReg.getSupplierCompName())))) {
				VendorRegister filterVendorReg = new VendorRegister();
				filterVendorReg.setEmail(vendorReg.getEmail());
				filterVendorReg.setSupplierCompName(vendorReg.getSupplierCompName());
				filterVendorReg.setStatus("APPROVED");
				List<VendorRegister> findAll = vendorRepo.findAll();
				for (VendorRegister find : findAll) {
					if (find.getEmail().equals(vendorReg.getEmail())) {
						throw new VendorNotFoundException("Email already exist");
					}
				}

				filterVendorReg.setUsername(vendorReg.getEmail());
				String rowPassword = java.util.UUID.randomUUID().toString();
				filterVendorReg.setPassword(passwordEncoder.encode(rowPassword));

				VendorRegister save = this.vendorRepo.save(filterVendorReg);
				filterVendorReg.setPassword(passwordEncoder.encode(rowPassword));
				filterVendorReg.setEmail(save.getEmail());
				filterVendorReg.setRegisterId(save.getRegisterId());
				filterVendorReg.setCreatedBy(save.getCreatedBy());
				filterVendorReg.setCreatedOn(save.getCreatedOn());
				filterVendorReg.setStatus(save.getStatus());
				filterVendorReg.setSupplierCompName(save.getSupplierCompName());
				filterVendorReg.setUsername(save.getUsername());
				filterVendorReg.setUpdatedBy(save.getUpdatedBy());
				filterVendorReg.setUpdatedOn(save.getUpdatedOn());

				/*
				 * ----------- REQUEST PROCESS ID with PROCESS DEFINITION KEY
				 * -------------------------------------------------------
				 */

				Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo
						.findByAuthorAndTitle("supplier_reg");
				RestTemplate restTemplate = new RestTemplate();

				HttpHeaders BaseAuthHeader = new HttpHeaders();
				BaseAuthHeader.setContentType(MediaType.APPLICATION_JSON);
				BaseAuthHeader.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				BaseAuthHeader.setBasicAuth("admin", "test");

				/*
				 * ============================== ProcessInstance Request
				 * ================================================
				 */
				Map<String, Object> pDMap = new HashMap<>();
				pDMap.put("processDefinitionId", findByAuthorAndTitle.get().getId());
				HttpEntity<Map<String, Object>> pDEntity = new HttpEntity<>(pDMap, BaseAuthHeader);
				ResponseEntity<String> response = restTemplate.postForEntity(
						"http://65.2.162.230:8080/flowable-rest/service/runtime/process-instances", pDEntity,
						String.class);

				/*
				 * ============================== Query Task 1
				 * ================================================
				 */

				Map<String, Object> queryMap = new HashMap<>();
				JSONObject jsonObject = new JSONObject(response.getBody());
				processInstID_ = (String) jsonObject.get("id");
				queryMap.put("processInstanceId", processInstID_);

				filterVendorReg.setProcessId(processInstID_);
				LOGGER.info("ProcessInstanceID : " + processInstID_);

				HttpEntity<Map<String, Object>> baseAuthEntity = new HttpEntity<>(queryMap, BaseAuthHeader);
				ResponseEntity<String> queryRequest_1 = restTemplate.exchange(
						"http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST, baseAuthEntity,
						String.class, 1);

				/*
				 * ----------- POST FORM VARIABLES
				 * -------------------------------------------------------
				 */

				JSONArray taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());
				JSONArray formReqBody = new JSONArray();

				taskID1_ = (String) taskJA.getJSONObject(0).get("id");

				LOGGER.info("Registration TaskID_1 : " + taskID1_);

				JSONObject suppliername = new JSONObject();
				suppliername.put("name", "suppliername");
				suppliername.put("scope", "local");
				suppliername.put("type", "string");
				suppliername.put("value", vendorReg.getSupplierCompName());
				formReqBody.put(suppliername);

				JSONObject supplieremail = new JSONObject();
				supplieremail.put("name", "supplieremail");
				supplieremail.put("scope", "local");
				supplieremail.put("type", "string");
				supplieremail.put("value", vendorReg.getEmail());
				formReqBody.put(supplieremail);

				JSONObject username = new JSONObject();
				username.put("name", "username");
				username.put("scope", "local");
				username.put("type", "string");
				username.put("value", vendorReg.getEmail());
				formReqBody.put(username);

				JSONObject password = new JSONObject();
				password.put("name", "password");
				password.put("scope", "local");
				password.put("type", "string");
				password.put("value", rowPassword);
				formReqBody.put(password);

				JSONObject registrationid = new JSONObject();
				registrationid.put("name", "registrationid");
				registrationid.put("scope", "local");
				registrationid.put("type", "string");
				registrationid.put("value", "SR " + filterVendorReg.getRegisterId());
				formReqBody.put(registrationid);

				HttpEntity<String> formReqEntity = new HttpEntity<String>(formReqBody.toString(), BaseAuthHeader);

				filterVendorReg.setTaskId(taskID1_);

				ResponseEntity<String> formResponse = restTemplate.exchange(
						"http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/" + taskID1_ + "/variables",
						HttpMethod.POST, formReqEntity, String.class, 1);

				/*
				 * ----------- GET COOKIE FROM DB-idm
				 * -------------------------------------------------------
				 */

				HttpHeaders loginHeader = new HttpHeaders();
				loginHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				MultiValueMap<String, String> loginMap = new LinkedMultiValueMap<String, String>();

				loginMap.add("j_username", "indexer");
				loginMap.add("j_password", "123");
				loginMap.add("submit", "Login");
				loginMap.add("_spring_security_remember_me", "true");

				HttpEntity<MultiValueMap<String, String>> loginReq = new HttpEntity<MultiValueMap<String, String>>(
						loginMap, loginHeader);
				ResponseEntity<String> loginResponse = restTemplate
						.postForEntity("http://65.2.162.230:8080/DB-idm/app/authentication", loginReq, String.class);
				JSONObject cookieJO = new JSONObject(loginResponse.getHeaders());
				String coockie_ = cookieJO.get("Set-Cookie").toString().replace("[", "").replace("]", "").replace("\"",
						"");

				LOGGER.info("Coockie : " + coockie_);

				/*
				 * ----------- AUTO CLAIMING Registration
				 * -------------------------------------------------------
				 */

				HttpHeaders autoCliamHeader = new HttpHeaders();
				autoCliamHeader.add("Cookie", coockie_);
				HttpEntity autoClaimEntity = new HttpEntity(null, autoCliamHeader);
				ResponseEntity autoClaimResponse = restTemplate.exchange(
						"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID1_ + "/action/claim", HttpMethod.PUT,
						autoClaimEntity, String.class);
				System.out.println("auto complite froom shantanu:    " + autoClaimResponse);

				/*
				 * ----------- AUTO COMPLETE Registration
				 * -------------------------------------------------------
				 */

				HttpHeaders autoCompleteHeader = new HttpHeaders();
				autoCompleteHeader.add("Cookie", coockie_);
				autoCompleteHeader.setContentType(MediaType.APPLICATION_JSON);

				JSONObject autoCompleate = new JSONObject();
				autoCompleate.put("taskIdActual", taskID1_);
				autoCompleate.put("suppliername", filterVendorReg.getSupplierCompName());
				autoCompleate.put("supplieremail", filterVendorReg.getEmail());
				autoCompleate.put("username", filterVendorReg.getUsername());
				autoCompleate.put("password", rowPassword);
				autoCompleate.put("registrationid", "SR " + filterVendorReg.getRegisterId());

				JSONObject autoCompleate_ = new JSONObject();
				autoCompleate_.put("formId", "56d9e9ee-ed45-11eb-ba6c-0a5bf303a9fe");
				autoCompleate_.put("values", autoCompleate);

				LOGGER.info("Body  " + autoCompleate_);
				LOGGER.info("headers  " + autoCompleteHeader);

				HttpEntity<String> autoCompeleteEntity = new HttpEntity<String>(autoCompleate_.toString(),
						autoCompleteHeader);
				ResponseEntity autoCompleteResponse = restTemplate.exchange(
						"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID1_, HttpMethod.POST,
						autoCompeleteEntity, String.class);
				LOGGER.info("Result  " + autoCompleteResponse.getHeaders());

				/*
				 * ----------- QUERY TO FETCH TASKID_2
				 * -------------------------------------------------------
				 */

				queryRequest_1 = restTemplate.exchange("http://65.2.162.230:8080/flowable-rest/service/query/tasks",
						HttpMethod.POST, baseAuthEntity, String.class, 1);
				taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());

				taskID2_ = (String) taskJA.getJSONObject(0).get("id");
				LOGGER.info("Registration TaskID_2 : " + taskID2_);

				// ----------- AUTO CLAIMING REGISTRATION APPROVAL
				// -------------------------------------------------------
				autoClaimResponse = restTemplate.exchange(
						"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID2_ + "/action/claim", HttpMethod.PUT,
						autoClaimEntity, String.class);

				// ----------- AUTO COMPLETE REGISTRATION APPROVAL
				// --------------------------------------------------------

				JSONObject autoComp2 = new JSONObject();
				autoComp2.put("taskIdActual", taskID2_);
				autoComp2.put("suppliername", filterVendorReg.getSupplierCompName());
				autoComp2.put("supplieremail", filterVendorReg.getEmail());
				autoComp2.put("approvesupplier", "Yes");
				autoComp2.put("approverremarkssupregistration", "");
				
				
				autoCompleate_ = new JSONObject();
				autoCompleate_.put("formId", "56d9e9ef-ed45-11eb-ba6c-0a5bf303a9fe");
				autoCompleate_.put("values", autoComp2);
				
				LOGGER.info("autoCompleate_  "+autoCompleate_);
				LOGGER.info("autoCompleteHeader  "+autoCompleteHeader);
				
				
				HttpEntity<String> autoCompeleteEntity2 = new HttpEntity<String>(autoCompleate_.toString(), autoCompleteHeader);			
				autoCompleteResponse = restTemplate.exchange( "http://65.2.162.230:8080/DB-task/app/rest/task-forms/"+taskID2_, HttpMethod.POST, autoCompeleteEntity2, String.class);
				LOGGER.info("Result  "+autoCompleteResponse.getHeaders());
				
				
				VendorRegister save1 = this.vendorRepo.save(filterVendorReg);
				save1.setPassword(rowPassword);
//				save1.setRegisterId("SR "+save1.getRegisterId());
				return save1;

			} else {
				throw new VendorNotFoundException("Validation error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor")
	public SupDetails postSupplierDetails(@RequestBody SupDetails supDetails) {
		LOGGER.info("Inside - VendorController.postSupplierDetails()");

		try {

			if ((fieldValidation.isEmpty(supDetails.getSupplierCompName()))
					& (fieldValidation.isEmpty(supDetails.getRegistrationType()))
					& (fieldValidation.isEmpty(supDetails.getRegisterId()))
					& (fieldValidation.isEmpty(supDetails.getRegistrationNo()))) {

				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(supDetails.getRegisterId());
				List<SupDetails> findAll = supDetailsRepo.findAll();

				if (findByRegisterId.size() < 1) {

					SupDetails filterSupDetails = new SupDetails();
					SupRequest supRequest = new SupRequest();
					DateTimeFormatter supCodeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime supCodeNow = LocalDateTime.now();

					filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
					filterSupDetails.setRegisterId(supDetails.getRegisterId());
					filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
					filterSupDetails.setRegistrationNo(supDetails.getRegistrationNo());

					DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime lastLoginNow = LocalDateTime.now();
					Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(lastLoginNow.format(lastLogingFormat));

					filterSupDetails.setLastlogin(lastLogin);

					try {

						if (fieldValidation.isEmpty(supDetails.getRemarks())) {
							filterSupDetails.setRemarks(supDetails.getRemarks());
						}

					} catch (Exception e) {

					}

					filterSupDetails.setLastlogin(supDetails.getLastlogin());
					filterSupDetails.setSupplierCode("SU:" + supCodeNow.format(supCodeFormat) + ":" + findAll.size());
					filterSupDetails.setStatus("APPROVED");
					SupDetails save = supDetailsRepo.save(filterSupDetails);
					return save;
				} else {

						throw new VendorNotFoundException("Vendor Already Exist");
				}

			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}
	
	
	@PostMapping("/vendor/address")
	public SupAddress postAddressVendor(@RequestBody SupAddress address) {

		try {
			String loginSupplierCode = address.getSupplierCode();
			if ((fieldValidation.isEmpty(address.getAddressType())) & (fieldValidation.isEmpty(address.getAddress1()))
					& (fieldValidation.isEmpty(address.getPostalCode())) & (fieldValidation.isEmpty(address.getCity()))
					& (fieldValidation.isEmpty(address.getCountry())) & (fieldValidation.isEmpty(address.getRegion()))
					& (fieldValidation.isEmpty(address.getAddressProof()))
					& (fieldValidation.isEmpty(address.getAddressProofPath()))) {

				if (!loginSupplierCode.equals(null)) {


					SupAddress filterAddressUp = new SupAddress();
					filterAddressUp.setSupplierCode(loginSupplierCode);
					filterAddressUp.setAddressType(address.getAddressType());
					filterAddressUp.setAddress1(address.getAddress1());
					try {
						if (fieldValidation.isEmpty(address.getAddress2())) {
							filterAddressUp.setAddress2(address.getAddress2());
						}
					} catch (Exception e) {

					}
					filterAddressUp.setPostalCode(address.getPostalCode());
					filterAddressUp.setCity(address.getCity());
					filterAddressUp.setCountry(address.getCountry());
					filterAddressUp.setRegion(address.getRegion());
					filterAddressUp.setStatus("APPROVED");
					filterAddressUp.setAddressProof(address.getAddressProof());
					filterAddressUp.setAddressProofPath(address.getAddressProofPath());
					SupAddress save = this.supAddRepo.save(filterAddressUp);
					return save;
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}
	
	
	
	@PostMapping("/vendor/contact")
	public SupContract postVendorContact(@RequestBody() SupContract contact) {
		LOGGER.info("Inside - VendorController.postVendorContact()");
		try {
			String loginSupplierCode = contact.getSupplierCode();
			if ((fieldValidation.isEmpty(contact.getContractType()))
					& (fieldValidation.isEmpty(contact.getContractTerms()))
					& (fieldValidation.isEmpty(contact.getContractProof()))
					& (fieldValidation.isEmpty(contact.getContractLocation()))) {
				if (!loginSupplierCode.equals(null)) {
					SupContract filterSupContract = new SupContract();
					List<SupBank> findBySupplierCode = supBankRepo.findBySupplierCode(loginSupplierCode);
					if (findBySupplierCode.size() > 0) {
						filterSupContract.setSupplierCode(loginSupplierCode);
						filterSupContract.setBankId((int) findBySupplierCode.get(0).getBankId());
						filterSupContract.setContractType(contact.getContractType());
						filterSupContract.setContractTerms(contact.getContractTerms());
						filterSupContract.setContractProof(contact.getContractProof());
						filterSupContract.setContractLocation(contact.getContractLocation());
						filterSupContract.setStatus("APPROVED");
						SupRequest supRequest = new SupRequest();
						SupContract save = supContractRepo.save(filterSupContract);

						return save;
					} else {
						throw new VendorNotFoundException("Please add bank first after that add contact");
					}

				} else {
					throw new VendorNotFoundException("Token Expir");
				}
			} else {
				throw new VendorNotFoundException("Valitation Error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}


	
	@PostMapping("/vendor/bank")
	public SupBank postBank(@RequestBody SupBank supBank) {
		LOGGER.info("Inside - VendorController.postBank()");
		try {
			String loginSupplierCode = supBank.getSupplierCode();
			if (fieldValidation.isEmpty(supBank.getAccountHolder())
					&& fieldValidation.isEmpty(supBank.getBankAccountNo())
//					&& fieldValidation.isEmpty(supBank.getBankBic())
					&& fieldValidation.isEmpty(supBank.getBankBranch())
					&& fieldValidation.isEmpty(supBank.getBankEvidence())
					&& fieldValidation.isEmpty(supBank.getBankName())
//					&& fieldValidation.isEmpty(supBank.getChequeNo())
					&& fieldValidation.isEmpty(supBank.getCountry()) && fieldValidation.isEmpty(supBank.getCurrency())
					&& fieldValidation.isEmpty(supBank.getEvidencePath())
					&& fieldValidation.isEmpty(supBank.getIfscCode())
//					&& fieldValidation.isEmpty(supBank.getSwiftCode())
//					&& fieldValidation.isEmpty(supBank.getTransilRoutingNo())
			) {
				if (!loginSupplierCode.equals(null)) {

					SupBank bank = new SupBank();
					bank.setAccountHolder(supBank.getAccountHolder());
					bank.setBankAccountNo(supBank.getBankAccountNo());
					bank.setBankBic(supBank.getBankBic());
					bank.setBankBranch(supBank.getBankBranch());
					bank.setBankEvidence(supBank.getBankEvidence());
					bank.setBankName(supBank.getBankName());
					bank.setChequeNo(supBank.getChequeNo());
					bank.setCountry(supBank.getCountry());
					bank.setCurrency(supBank.getCurrency());
					bank.setEvidencePath(supBank.getEvidencePath());
					bank.setIfscCode(supBank.getIfscCode());
					bank.setSupplierCode(loginSupplierCode);
					bank.setTransilRoutingNo(supBank.getTransilRoutingNo());
					bank.setSwiftCode(supBank.getSwiftCode());
					bank.setStatus("APPROVED");
					Optional<SupBank> findBySwiftCode = supBankRepo.findBySwiftCode(supBank.getSwiftCode());
					if (!findBySwiftCode.isPresent()) {
						SupBank postData = this.supBankRepo.save(bank);
						return postData;
					} else {
						throw new VendorNotFoundException("The Swift code all ready present");
					}
				} else {
					throw new VendorNotFoundException("Token Expir");
				}
			} else {
				throw new VendorNotFoundException("Some field are messing");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	
	@PostMapping("/vendor/department")
	public SupDepartment addSupDepartment(@RequestBody SupDepartment supDepartment) {
		try {
			String loginSupplierCode = supDepartment.getSupplierCode();
			if (fieldValidation.isEmpty(supDepartment.getDepartmentName())
					&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
					&& fieldValidation.isEmpty(supDepartment.getEmail())
					&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {

				if (fieldValidation.isEmail(supDepartment.getEmail())) {
					if (!loginSupplierCode.equals(null)) {

						SupDepartment department = new SupDepartment();
						department.setDepartmentName(supDepartment.getDepartmentName());
						department.setSupplierCode(loginSupplierCode);
						department.setSupplierContact1(supDepartment.getSupplierContact1());
						department.setEmail(supDepartment.getEmail());
						department.setStatus("APPROVED");
						try {
							if (fieldValidation.isEmpty(supDepartment.getSupplierContact2())
									&& fieldValidation.isEmpty(supDepartment.getAlternatePhoneno())) {
								department.setSupplierContact2(supDepartment.getSupplierContact2());
								department.setAlternatePhoneno(supDepartment.getAlternatePhoneno());
							}
						} catch (Exception e) {

						}
						department.setPhoneno(supDepartment.getPhoneno());
						SupDepartment save = supDepartmentRepo.save(department);
						return save;
					} else {
						throw new VendorNotFoundException("Token Expir");
					}
				} else {
					throw new VendorNotFoundException("Email Format Not Match");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	
	

	
}
