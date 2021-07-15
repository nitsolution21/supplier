package org.fintexel.supplier.controller;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.*;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupContract;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.SupContractRepo;
import org.fintexel.supplier.repository.SupBankRepo;
import org.fintexel.supplier.repository.SupDepartmentRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.UserRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.validation.FieldValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@RestController
@CrossOrigin(origins = "*")
public class VendorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private SupDetailsRepo supDetailsRepo;

	@Autowired
	private VendorRegisterRepo vendorRepo;

	@Autowired
	private SupAddressRepo supAddRepo;

	@Autowired
	private FieldValidation fieldValidation;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SupContractRepo supContractRepo;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private SupBankRepo supBankRepo;

	private String jwtToken;

	@Autowired
	SupDepartmentRepo supDepartmentRepo;

	@PostMapping("/vendor")
	public VendorRegister postRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		try {
			if ((fieldValidation.isEmail(vendorReg.getEmail())
					& (fieldValidation.isEmpty(vendorReg.getSupplierCompName())))) {
				VendorRegister filterVendorReg = new VendorRegister();
				filterVendorReg.setEmail(vendorReg.getEmail());
				filterVendorReg.setSupplierCompName(vendorReg.getSupplierCompName());
				filterVendorReg.setStatus("1");
				List<VendorRegister> findAll = vendorRepo.findAll();
				for (VendorRegister find : findAll) {
					if (find.getEmail().equals(vendorReg.getEmail())) {
						throw new VendorNotFoundException("Email already exist");
					}
				}
//				try {
//					RestTemplate restTemplate=new RestTemplate();
//						List getObject=restTemplate.getForObject("url", ArrayList.class, filterVendorReg);
//						if(getObject.status==1){
//							filterVendorReg.setUsername(getObject.get(0).username);
//							filterVendorReg.setPassword(getObject.get(0).password);
//						}
//				}catch(Exception e) {
//					
//				}
				System.out.println(java.util.UUID.randomUUID().toString());
				filterVendorReg.setUsername(vendorReg.getEmail());
				String rowPassword = java.util.UUID.randomUUID().toString();
				filterVendorReg.setPassword(passwordEncoder.encode(rowPassword));
				VendorRegister save = this.vendorRepo.save(filterVendorReg);
				save.setPassword(rowPassword);
				return save;
			}
			else {
				throw new VendorNotFoundException("Validation error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor")
	public VendorRegister getRegisterVendors(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);

			try {
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
				if (!findByUsername.isPresent()) {
					throw new VendorNotFoundException("Vendor not found");
				}
				return findByUsername.get();
			} catch (Exception e) {
				throw new VendorNotFoundException(e.getMessage());
			}
		}

		else {
			throw new VendorNotFoundException("Token is not valid");
		}
	}

	@GetMapping("/vendor/{vendorId}")
	public Optional<VendorRegister> getRegisterVendor(@PathVariable() long vendorId) {
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			LOGGER.info("Inside - VendorController.getRegisterVendor() - " + findById);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Vendor Not Found");
			}
			return findById;
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PutMapping("/vendor/{vendorId}")
	public VendorRegister putRegisterVendor(@PathVariable("vendorId") long vendorId,
			@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.putRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if (!findById.isPresent()) {
				throw new VendorNotFoundException("Vendor Not Available");
			} else {
				if ((fieldValidation.isEmail(vendorReg.getEmail()))
						& (fieldValidation.isEmpty(vendorReg.getSupplierCompName()))
						& (vendorReg.getRegisterId() == vendorId)) {
					VendorRegister vr = findById.get();
					vr.setEmail(vendorReg.getEmail());
					vr.setSupplierCompName(vendorReg.getSupplierCompName());
					vr.setStatus(vendorReg.getStatus());
					return this.vendorRepo.save(vr);
				} else {
					throw new VendorNotFoundException("Validation error");
				}

			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/{vendorId}")
	public Object deleteRegisterVendor(@PathVariable() long vendorId) {
		LOGGER.info("Inside - VendorController.deleteRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Vendor Does not exist");
			} else {
				VendorRegister vendorRegister = findById.get();
				vendorRegister.setStatus("0");
				this.vendorRepo.save(vendorRegister);
				return "vendor deleted - " + vendorId;
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/")
	public String getUser() {
		LOGGER.info("Inside - VendorController.getUser()");
		return "Hello";
	}

	@GetMapping("/user")
	List<User> all() {
		LOGGER.info("Inside - VendorController.all()");
		return userRepo.findAll();
	}

	@PostMapping("/user")
	User newVendor(@RequestBody User newUser) {
		return userRepo.save(newUser);
	}

	@GetMapping("/supplierAddress")
	public List<SupAddress> allAdd() {
		LOGGER.info("Inside - VendorController.allAdd()");
		List<SupAddress> findAll = supAddRepo.findAll();
		return findAll;
	}

	// ********** Write By Soumen **********//
	// Start
	@PostMapping("/vendor/details")
	public SupDetails postSupplierDetails(@RequestBody SupDetails supDetails) {
		LOGGER.info("Inside - VendorController.postSupplierDetails()");
		try {
			if ((fieldValidation.isEmpty(supDetails.getSupplierCompName()))
					& (fieldValidation.isEmpty(supDetails.getRegistrationType()))
					& (fieldValidation.isEmpty(supDetails.getRegristrationNo()))
					& (fieldValidation.isEmpty(supDetails.getCostCenter()))
					& (fieldValidation.isEmpty(supDetails.getRemarks()))
					& (fieldValidation.isEmpty(supDetails.getLastlogin()))) {
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(supDetails.getRegisterId());
				if (findByRegisterId.size() < 1) {
					SupDetails filterSupDetails = new SupDetails();
					Random rd = new Random();
					filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
					filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
					filterSupDetails.setRegristrationNo(supDetails.getRegristrationNo());
					filterSupDetails.setCostCenter(supDetails.getCostCenter());
					filterSupDetails.setRemarks(supDetails.getRemarks());
					filterSupDetails.setLastlogin(supDetails.getLastlogin());
					filterSupDetails.setSupplierCode("SU-" + java.time.LocalDate.now() + rd.nextInt(10));
					filterSupDetails.setStatus("1");
					return supDetailsRepo.save(filterSupDetails);
				} else {
					throw new VendorNotFoundException("Vendor Not Exist");
				}

			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@GetMapping("/vendor/details")
	public List<SupDetails> getSupplierDetails() {
		List<SupDetails> findAll = supDetailsRepo.findAll();
		return findAll;
	}
	@GetMapping("/vendor/details")
	public SupDetails getSupplierDetails(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getSupplierDetails()");
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);
			try {
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
				if (!findByUsername.isPresent()) {
					throw new VendorNotFoundException("Vendor not found");
				} else {
					List<SupDetails> findByRegisterId = supDetailsRepo
							.findByRegisterId(findByUsername.get().getRegisterId());
					if (findByRegisterId.size() > 1) {
						return findByRegisterId.get(0);
					} else {
						throw new VendorNotFoundException("Vendor Details Not Found");
					}
				}
				// return findByUsername.get();
			} catch (Exception e) {
				throw new VendorNotFoundException(e.getMessage());
			}
		} else {
			throw new VendorNotFoundException("Token is not valid");
		}
	}

//	@GetMapping("/vendor/{code}")
//	public SupDetails getSupplierDetails(@PathVariable() String code) {
//		LOGGER.info("Inside - VendorController.getSupplierDetails()");
//		try {
//			Optional<SupDetails> findById = supDetailsRepo.findById(code);
//			if (findById.isPresent()) {
//				System.out.println(findById.get());
//				return findById.get();
//			} else {
//				throw new VendorNotFoundException("Vendor Not Exist");
//			}
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//
//	}

	@PutMapping("/vendor/details/{code}")
	public SupDetails putSupDetails(@RequestBody() SupDetails supDetails, @PathVariable() String code) {
		LOGGER.info("Inside - VendorController.putSupDetails()");
		try {
			if ((fieldValidation.isEmpty(supDetails.getSupplierCompName()))
					& (fieldValidation.isEmpty(supDetails.getRegistrationType()))
					& (fieldValidation.isEmpty(supDetails.getRegristrationNo()))
					& (fieldValidation.isEmpty(supDetails.getCostCenter()))
					& (fieldValidation.isEmpty(supDetails.getRemarks()))
					& (fieldValidation.isEmpty(supDetails.getLastlogin()))
					& (fieldValidation.isEmpty(supDetails.getCreatedBy()))
					& (fieldValidation.isEmpty(supDetails.getCreatedOn()))
					& (fieldValidation.isEmpty(supDetails.getUpdatedBy()))
					& (fieldValidation.isEmpty(supDetails.getUpdatedOn()))) {
				Optional<SupDetails> findById = supDetailsRepo.findById(code);
				if (findById.isPresent()) {
					SupDetails filterSupDetails = new SupDetails();
					filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
					filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
					filterSupDetails.setRegristrationNo(supDetails.getRegristrationNo());
					filterSupDetails.setCostCenter(supDetails.getCostCenter());
					filterSupDetails.setRemarks(supDetails.getRemarks());
					filterSupDetails.setLastlogin(supDetails.getLastlogin());
					filterSupDetails.setSupplierCode(findById.get().getSupplierCode());
					filterSupDetails.setStatus("1");
					return supDetailsRepo.save(filterSupDetails);
				} else {
					throw new VendorNotFoundException("Vendor Not Exist");
				}

			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/details/{code}")
	public String deleteSupDetails(@PathVariable() String code) {
		LOGGER.info("Inside - VendorController.deleteSupDetails()");
		try {
			Optional<SupDetails> findById = supDetailsRepo.findById(code);
			if (findById.isPresent()) {
				if (!findById.get().getStatus().equals("0")) {
					SupDetails supDetails = findById.get();
					supDetails.setStatus("0");
					supDetailsRepo.save(supDetails);
					return "Successfully Deleted";
				} else {
					throw new VendorNotFoundException("Already Deleted");
				}
			} else {
				throw new VendorNotFoundException("Vendor Not Exist");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor/address")
	public SupAddress postAddressVendor(@RequestBody SupAddress address) {

		try {
			if ((fieldValidation.isEmpty(address.getSupplierCode()))
					& (fieldValidation.isEmpty(address.getAddressType()))
					& (fieldValidation.isEmpty(address.getAddress2()))
					& (fieldValidation.isEmpty(address.getPostalCode())) & (fieldValidation.isEmpty(address.getCity()))
					& (fieldValidation.isEmpty(address.getCountry())) & (fieldValidation.isEmpty(address.getRegion()))
					& (fieldValidation.isEmpty(address.getStatus()))
					& (fieldValidation.isEmpty(address.getAddressProof()))
					& (fieldValidation.isEmpty(address.getAddressProofPath()))) {
				SupAddress filterAddressUp = new SupAddress();
				filterAddressUp.setSupplierCode(address.getSupplierCode());
				filterAddressUp.setAddressType(address.getAddressType());
				filterAddressUp.setAddress1(address.getAddress1());
				filterAddressUp.setAddress2(address.getAddress2());
				filterAddressUp.setPostalCode(address.getPostalCode());
				filterAddressUp.setCity(address.getCity());
				filterAddressUp.setCountry(address.getCountry());
				filterAddressUp.setRegion(address.getRegion());
				filterAddressUp.setStatus(address.getStatus());
				filterAddressUp.setAddressProof(address.getAddressProof());
				filterAddressUp.setAddressProofPath(address.getAddressProofPath());
				SupAddress save = this.supAddRepo.save(filterAddressUp);
				return save;
			} else {
				throw new VendorNotFoundException("Validation error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@GetMapping("/vendor/address")
	public List<SupAddress> getVendorAddress(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getVendorAddress()");

		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);

			try {
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
				if (!findByUsername.isPresent()) {
					throw new VendorNotFoundException("Vendor not found");
				} else {
					List<SupDetails> registerDetails = supDetailsRepo
							.findByRegisterId(findByUsername.get().getRegisterId());
					if (registerDetails.size() > 1) {
						List<SupAddress> vendorAddress = this.supAddRepo
								.findBySupplierCode(registerDetails.get(0).getSupplierCode());

						if (vendorAddress.size() < 1) {
							throw new VendorNotFoundException("Vendor Not Exist");
						} else {
							return vendorAddress;
						}
					} else {
						throw new VendorNotFoundException("Vendor Details Not Found");
					}
				}
			} catch (Exception e) {
				throw new VendorNotFoundException(e.getMessage());
			}
		}

		else {
			throw new VendorNotFoundException("Token is not valid");
		}

	}

//	@GetMapping("/vendor/address/{addressId}")
//	public Optional<SupAddress> getVendorsAddress(@PathVariable() long addressId) {
//
//		LOGGER.info("Inside - VendorController.getVendorsAddress()");
//
//		try {
//			Optional<SupAddress> findById = this.supAddRepo.findById(addressId);
//			LOGGER.info("Inside - VendorController.getVendorsAddress() - " + findById);
//
//			if (!findById.isPresent()) {
//				throw new VendorNotFoundException("Vendor Address Not Found");
//			} else {
//				return findById;
//			}
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//
//	}

	@PutMapping("/vendor/address/{addressId}")
	public SupAddress putVendorAddress(@PathVariable("addressId") long addressId, @RequestBody SupAddress address) {
		LOGGER.info("Inside - VendorController.putVendorAddress()");

		try {
			Optional<SupAddress> findById = this.supAddRepo.findById(addressId);
			if (!findById.isPresent()) {
				throw new VendorNotFoundException("Vendor Address Not Available");
			} else {
				if ((fieldValidation.isEmpty(address.getSupplierCode()))
						& (fieldValidation.isEmpty(address.getAddressType()))
						& (fieldValidation.isEmpty(address.getAddress2()))
						& (fieldValidation.isEmpty(address.getPostalCode()))
						& (fieldValidation.isEmpty(address.getCity())) & (fieldValidation.isEmpty(address.getCountry()))
						& (fieldValidation.isEmpty(address.getRegion()))
						& (fieldValidation.isEmpty(address.getStatus()))
						& (fieldValidation.isEmpty(address.getAddressProof()))
						& (fieldValidation.isEmpty(address.getAddressProofPath()))) {
					SupAddress filterAddressUp = findById.get();

					filterAddressUp.setSupplierCode(address.getSupplierCode());
					filterAddressUp.setAddressType(address.getAddressType());
					filterAddressUp.setAddress1(address.getAddress1());
					filterAddressUp.setAddress2(address.getAddress2());
					filterAddressUp.setPostalCode(address.getPostalCode());
					filterAddressUp.setCity(address.getCity());
					filterAddressUp.setCountry(address.getCountry());
					filterAddressUp.setRegion(address.getRegion());
					filterAddressUp.setStatus(address.getStatus());
					filterAddressUp.setAddressProof(address.getAddressProof());
					filterAddressUp.setAddressProofPath(address.getAddressProofPath());

					filterAddressUp.setAddressId(addressId);
					return this.supAddRepo.save(filterAddressUp);
				} else {
					throw new VendorNotFoundException("Validation error");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/address/{addressId}")
	public Object deleteVendorAddress(@PathVariable("addressId") long addressId) {
		LOGGER.info("Inside - VendorController.deleteVendorAddress()");
		try {
			Optional<SupAddress> findById = this.supAddRepo.findById(addressId);
			if (!findById.isPresent()) {
				throw new VendorNotFoundException("Vendor Address Does not exist");
			} else {
				if (findById.get().getStatus().equals("0")) {
					throw new VendorNotFoundException("Already Deleted");
				} else {
					SupAddress supAddress = findById.get();
					supAddress.setStatus("0");
					this.supAddRepo.save(supAddress);
					return "Successfully Deleted";
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor/contact")
	public SupContract postVendorContact(@RequestBody() SupContract contact) {
		LOGGER.info("Inside - VendorController.postVendorContact()");
		try {
			if ((fieldValidation.isEmpty(contact.getSupplierCode())) & (fieldValidation.isEmpty(contact.getBankId()))
					& (fieldValidation.isEmpty(contact.getContractType()))
					& (fieldValidation.isEmpty(contact.getContractTerms()))
					& (fieldValidation.isEmpty(contact.getContractProof()))
					& (fieldValidation.isEmpty(contact.getContractLocation()))) {
				SupContract filterSupContract = new SupContract();
				filterSupContract.setSupplierCode(contact.getSupplierCode());
				filterSupContract.setBankId(contact.getBankId());
				filterSupContract.setContractType(contact.getContractType());
				filterSupContract.setContractTerms(contact.getContractTerms());
				filterSupContract.setContractProof(contact.getContractProof());
				filterSupContract.setContractLocation(contact.getContractLocation());
				return supContractRepo.save(filterSupContract);
			} else {
				throw new VendorNotFoundException("Valitation Error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor/contact")
	public List<SupContract> getSupContracts(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getSupContracts()");

		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);

			try {
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
				if (!findByUsername.isPresent()) {
					throw new VendorNotFoundException("Vendor not found");
				} else {
					List<SupDetails> registerDetails = supDetailsRepo
							.findByRegisterId(findByUsername.get().getRegisterId());
					if (registerDetails.size() > 1) {
						List<SupContract> findBySupplierCode = supContractRepo
								.findBySupplierCode(registerDetails.get(0).getSupplierCode());
						if (findBySupplierCode.size() > 1) {
							return findBySupplierCode;
						} else {
							throw new VendorNotFoundException("Vendor Contact Does not exist");
						}
					} else {
						throw new VendorNotFoundException("Vendor Details Not Found");
					}
				}
			} catch (Exception e) {
				throw new VendorNotFoundException(e.getMessage());
			}
		}

		else {
			throw new VendorNotFoundException("Token is not valid");
		}

	}

//	@GetMapping("/vendor/contact/{id}")
//	public SupContract getSupContracts(@PathVariable() Long id) {
//		LOGGER.info("Inside - VendorController.getSupContracts()" + id);
//		try {
//			Optional<SupContract> findById = supContractRepo.findById(id);
//			if (findById.isPresent()) {
//				return findById.get();
//			} else {
//				throw new VendorNotFoundException("Vendor Contact Does not exist");
//			}
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}

	@PutMapping("/vendor/contact/{id}")
	public SupContract putSupContract(@PathVariable() Long id, @RequestBody SupContract contact) {
		LOGGER.info("Inside - VendorController.putSupContract()" + id);
		try {
			if ((fieldValidation.isEmpty(contact.getSupplierCode())) & (fieldValidation.isEmpty(contact.getBankId()))
					& (fieldValidation.isEmpty(contact.getContractType()))
					& (fieldValidation.isEmpty(contact.getContractTerms()))
					& (fieldValidation.isEmpty(contact.getContractProof()))
					& (fieldValidation.isEmpty(contact.getContractLocation()))) {

				Optional<SupContract> findById = supContractRepo.findById(id);
				if (findById.isPresent()) {
					SupContract filterSupContract = new SupContract();
					filterSupContract.setSupplierCode(contact.getSupplierCode());
					filterSupContract.setBankId(contact.getBankId());
					filterSupContract.setContractType(contact.getContractType());
					filterSupContract.setContractTerms(contact.getContractTerms());
					filterSupContract.setContractProof(contact.getContractProof());
					filterSupContract.setContractLocation(contact.getContractLocation());
					filterSupContract.setContractId(id);
					return supContractRepo.save(filterSupContract);
				} else {
					throw new VendorNotFoundException("Vendor Contact Does not exist");
				}
			} else {
				throw new VendorNotFoundException("Valitation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

//		@DeleteMapping("/contact/{id}")
//		public SupContract deleteSupContract(@PathVariable() Long id) {
//			LOGGER.info("Inside - VendorController.deleteSupContract()");
//			try {
//				Optional<SupContract> findById = supContractRepo.findById(id);
//				if(findById.isPresent()) {
//					SupContract supContract = findById.get();
//					supContract.getS
//				}else {
//					throw new VendorNotFoundException("Vendor Contact Does not exist");
//				}
//			}catch (Exception e) {
//				throw new VendorNotFoundException(e.getMessage());
//			}
//		}

	// ********** Write By Soumen **********//
	// End

	@PostMapping("/vendor/bank")
	public SupBank postBank(@RequestBody SupBank supBank) {
		try {
			if (fieldValidation.isEmpty(supBank.getAccountHolder())
					&& fieldValidation.isEmpty(supBank.getBankAccountNo())
					&& fieldValidation.isEmpty(supBank.getBankBic()) && fieldValidation.isEmpty(supBank.getBankBranch())
					&& fieldValidation.isEmpty(supBank.getBankEvidence())
					&& fieldValidation.isEmpty(supBank.getBankName()) && fieldValidation.isEmpty(supBank.getChequeNo())
					&& fieldValidation.isEmpty(supBank.getCountry()) && fieldValidation.isEmpty(supBank.getCurrency())
					&& fieldValidation.isEmpty(supBank.getEvidencePath())
					&& fieldValidation.isEmpty(supBank.getIfscCode())
					&& fieldValidation.isEmpty(supBank.getSupplierCode())
					&& fieldValidation.isEmpty(supBank.getSwiftCode())
					&& fieldValidation.isEmpty(supBank.getTransilRoutingNo())) {
				SupBank postData = this.supBankRepo.save(supBank);
				return postData;
			} else {
				throw new VendorNotFoundException("Some field are messing");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@GetMapping("/vendor/bank")
	public List<SupBank> getBank(@RequestHeader(name = "Authorization") String token) {

		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);

			try {
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);

				if (!findByUsername.isPresent()) {
					throw new VendorNotFoundException("Vendor not found");
				} else {
					List<SupDetails> findByRegisterId = supDetailsRepo
							.findByRegisterId(findByUsername.get().getRegisterId());
					if (findByRegisterId.size() > 1) {
						SupDetails supDetails = findByRegisterId.get(0);
						if (!supDetails.equals(null)) {
							List<SupBank> supBankDetails = supBankRepo.findBySupplierCode(supDetails.getSupplierCode());
							if (!supBankDetails.isEmpty()) {
								throw new VendorNotFoundException("Bank details not found");
							} else {
								return supBankDetails;
							}
						} else {
							throw new VendorNotFoundException("Vendor Detail not found");
						}
					} else {
						throw new VendorNotFoundException("Vendor Detail not found");
					}
				}

			} catch (Exception e) {
				throw new VendorNotFoundException(e.getMessage());
			}
		}

		else {
			throw new VendorNotFoundException("Token is not valid");
		}

	}

	@PutMapping("/vendor/bank/{bankId}")
	public SupBank putBank(@PathVariable("bankId") long bankId, @RequestBody SupBank supBank) {

		Optional<SupBank> findById = this.supBankRepo.findById(bankId);
		try {
			if (!findById.isPresent()) {
				throw new VendorNotFoundException("This Bank id not found");
			} else {
				SupBank sb = this.supBankRepo.save(supBank);
				return sb;
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/bank/{bankId}")
	public Object deleteBank(@PathVariable("bankId") long bankId) {

		Optional<SupBank> findById = this.supBankRepo.findById(bankId);
		try {
			if (!findById.isPresent()) {
				throw new VendorNotFoundException("This Bank id not found");
			} else {
				this.supBankRepo.deleteById(bankId);
				return "Successfully Deleted ";
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor/department")
	public List<SupDepartment> getSupDepartment(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getSupDepartment()");
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);

			try {
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
				if (!findByUsername.isPresent()) {
					throw new VendorNotFoundException("Vendor not found");
				} else {
					List<SupDetails> findByRegisterId = supDetailsRepo
							.findByRegisterId(findByUsername.get().getRegisterId());
					if (findByRegisterId.size() > 1) {
						List<SupDepartment> supDepartment = supDepartmentRepo
								.findBySupplierCode(findByRegisterId.get(0).getSupplierCode());
						if (!supDepartment.equals(null)) {
							return supDepartment;
						} else {
							throw new VendorNotFoundException("Vendor department not present");
						}
					} else {
						throw new VendorNotFoundException("Suplieer details not present");
					}
				}
			} catch (Exception e) {
				throw new VendorNotFoundException(e.getMessage());
			}
		}

		else {
			throw new VendorNotFoundException("Token is not valid");
		}
	}

	@PostMapping("/vendor/department")
	public SupDepartment addSupDepartment(@RequestBody SupDepartment supDepartment) {
		try {
			if (fieldValidation.isEmpty(supDepartment.getSupplierCode())
					&& fieldValidation.isEmpty(supDepartment.getDepartmentName())
					&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
					&& fieldValidation.isEmpty(supDepartment.getEmail())
					&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {

				if (fieldValidation.isEmail(supDepartment.getEmail())) {
					return supDepartmentRepo.save(supDepartment);
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

	@PutMapping("/vendor/department/{departmentId}")
	public SupDepartment updateSupDepartment(@PathVariable long departmentId,
			@RequestBody SupDepartment supDepartment) {
		Optional<SupDepartment> findById = supDepartmentRepo.findById(departmentId);
		try {
			if (!findById.isPresent()) {
				if (fieldValidation.isEmpty(supDepartment.getSupplierCode())
						&& fieldValidation.isEmpty(supDepartment.getDepartmentName())
						&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
						&& fieldValidation.isEmpty(supDepartment.getEmail())
						&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {
					if (fieldValidation.isEmail(supDepartment.getEmail())) {
						return supDepartmentRepo.save(supDepartment);
					} else {
						throw new VendorNotFoundException("Email Format Not Valid");
					}
				} else {
					throw new VendorNotFoundException("Validation Error");
				}
			} else {
				throw new VendorNotFoundException("Department Not Found");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@DeleteMapping("/vendor/department/{departmentId}")
	public Object deleteSupDepartment(@PathVariable long departmentId) {
		Optional<SupDepartment> findById = supDepartmentRepo.findById(departmentId);
		try {
			if (!findById.isPresent()) {
				supDepartmentRepo.deleteById(departmentId);
				return "Successfully Deleted";
			} else {
				throw new VendorNotFoundException("Department Not Found");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

}
