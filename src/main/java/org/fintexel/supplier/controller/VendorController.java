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
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.SupAddressRepo;
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
	private JwtUtil jwtUtil;

	@Autowired
	private SupBankRepo supBankRepo;

	private String jwtToken;
	
	@Autowired
	SupDepartmentRepo supDepartmentRepo; 

	@PostMapping("/vendor")
	public String postRegisterVendor(@RequestBody VendorRegister vendorReg) {
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
				return "Successfully Register";
			} else {
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
	@PostMapping("/supplier")
	public SupDetails postSupplierDetails(@RequestBody SupDetails supDetails) {
		LOGGER.info("Inside - VendorController.postSupplierDetails()");
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
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(supDetails.getRegisterId());
				if (findByRegisterId.size() < 1) {
					Random rd = new Random();
					supDetails.setSupplierCode("SU-" + java.time.LocalDate.now() + rd.nextInt(10));// Have to Varify
					supDetails.setStatus("1");
					return supDetailsRepo.save(supDetails);
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

	@GetMapping("/supplier")
	public List<SupDetails> getSupplierDetails() {
		List<SupDetails> findAll = supDetailsRepo.findAll();
		return findAll;
	}

	@GetMapping("/supplier/{code}")
	public SupDetails getSupplierDetails(@PathVariable() String code) {
		LOGGER.info("Inside - VendorController.getSupplierDetails()");
		try {
			Optional<SupDetails> findById = supDetailsRepo.findById(code);
			if (findById.isPresent()) {
				System.out.println(findById.get());
				return findById.get();
			} else {
				throw new VendorNotFoundException("Vendor Not Exist");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PutMapping("/supplier/{code}")
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
					supDetails.setRegisterId(findById.get().getRegisterId());
					supDetails.setStatus("1");
					return supDetailsRepo.save(supDetails);
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

	@DeleteMapping("/supplier/{code}")
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

	// ********** Write By Soumen **********//
	// End

	@PostMapping("vendor/bank")
	public SupBank postBank(@RequestBody SupBank supBank) {

		if (fieldValidation.isEmpty(supBank.getAccountHolder()) && fieldValidation.isEmpty(supBank.getBankAccountNo())
				&& fieldValidation.isEmpty(supBank.getBankBic()) && fieldValidation.isEmpty(supBank.getBankBranch())
				&& fieldValidation.isEmpty(supBank.getBankEvidence()) && fieldValidation.isEmpty(supBank.getBankName())
				&& fieldValidation.isEmpty(supBank.getChequeNo()) && fieldValidation.isEmpty(supBank.getCountry())
				&& fieldValidation.isEmpty(supBank.getCurrency()) && fieldValidation.isEmpty(supBank.getEvidencePath())
				&& fieldValidation.isEmpty(supBank.getIfscCode()) && fieldValidation.isEmpty(supBank.getSupplierCode())
				&& fieldValidation.isEmpty(supBank.getSwiftCode())
				&& fieldValidation.isEmpty(supBank.getTransilRoutingNo())) {
			SupBank postData = this.supBankRepo.save(supBank);
			return postData;
		} else {
			throw new VendorNotFoundException("Some field are messing");
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
				}
				else {
					List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(findByUsername.get().getRegisterId());
					if (findByRegisterId.size() > 1) {
						List<SupDepartment> supDepartment = supDepartmentRepo.findBySupplierCode(findByRegisterId.get(0).getSupplierCode());
						if (!supDepartment.isEmpty()) {
							return supDepartment;
						} 
						else {
							throw new VendorNotFoundException("Vendor department not present");
						}
					} 
					else {
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
			if (fieldValidation.isEmpty(supDepartment.getSupplierCode()) && fieldValidation.isEmpty(supDepartment.getDepartmentName())
					&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())  && fieldValidation.isEmpty(supDepartment.getEmail())
					&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {
					
					if (fieldValidation.isEmail(supDepartment.getEmail())) {
						return supDepartmentRepo.save(supDepartment);
					} 
					else {
						throw new VendorNotFoundException("Email Format Not Match");
					}
				} 
				else {
					throw new VendorNotFoundException("Validation Error");
				}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@PutMapping("/vendor/department/{departmentId}")
	public SupDepartment updateSupDepartment(@PathVariable long departmentId, @RequestBody SupDepartment supDepartment) {
		Optional<SupDepartment> findById = supDepartmentRepo.findById(departmentId);
		try {
			if (!findById.isEmpty()) {
				if (fieldValidation.isEmpty(supDepartment.getSupplierCode()) && fieldValidation.isEmpty(supDepartment.getDepartmentName())
						&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())  && fieldValidation.isEmpty(supDepartment.getEmail())
						&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {
					if (fieldValidation.isEmail(supDepartment.getEmail())) {
						return supDepartmentRepo.save(supDepartment);
					} 
					else {
						throw new VendorNotFoundException("Email Format Not Valid");
					}
				}
				else {
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
			if (!findById.isEmpty()) {
				supDepartmentRepo.deleteById(departmentId);
				return "Successfully Deleted";
			}
			else {
				throw new VendorNotFoundException("Department Not Found");
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	
	

}
