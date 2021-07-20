package org.fintexel.supplier.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.fintexel.supplier.entity.SupRequest;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.flowable.FlowableContainer;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.SupContractRepo;
import org.fintexel.supplier.repository.SupBankRepo;
import org.fintexel.supplier.repository.SupDepartmentRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.SupRequestRepo;
import org.fintexel.supplier.repository.UserRepo;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;

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

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private SupRequestRepo supRequestRepo;
	
	private String jwtToken;

	@Autowired
	SupDepartmentRepo supDepartmentRepo;

	@Autowired
	WebClient.Builder builder;

	@Autowired
	FlowableRegistrationRepo flowableRegistrationRepo;

	@PostMapping("/registration")
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
				filterVendorReg.setUsername(vendorReg.getEmail());
				String rowPassword = java.util.UUID.randomUUID().toString();
				filterVendorReg.setPassword(passwordEncoder.encode(rowPassword));
				VendorRegister save = this.vendorRepo.save(filterVendorReg);
				save.setPassword(rowPassword);
				try {

				

//			FLOWABLE POST API CALL WITH PROCESS DEFINITION KEY
//				START

					Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo
							.findByAuthorAndTitle("supplier_reg");
					FlowableContainer flowableContainer = new FlowableContainer();
					RestTemplate restTemplate = new RestTemplate();
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
					headers.setBasicAuth("admin", "test");
					Map<String, Object> map = new HashMap<>();
					map.put("processDefinitionId", findByAuthorAndTitle.get().getId());
					HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
					ResponseEntity<String> response = restTemplate.postForEntity(
							"http://65.2.162.230:8080/flowable-rest/service/runtime/process-instances", entity,
							String.class);
					JSONObject jsonObject = new JSONObject(response.getBody());


//				END 

//					headers.set("processInstanceId", (String) jsonObject.get("id"));
					Map<String, Object> mapp = new HashMap<>();
					map.put("processInstanceId", (String) jsonObject.get("id"));
					LOGGER.info("Task ID - "+(String) jsonObject.get("id"));
					
					HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
					ResponseEntity<String> exchange = restTemplate.exchange(
							"http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST, request,
							String.class, 1);
					JSONObject jsonObject1 = new JSONObject(exchange.getBody());
					
					
					JSONArray array = new JSONArray(jsonObject1.get("data").toString());
					JSONArray arrayy = new JSONArray();
					JSONObject suppliername = new JSONObject();
					suppliername.put("name", "suppliername");
					suppliername.put("scope", "local");
					suppliername.put("type", "string");
					suppliername.put("value", save.getSupplierCompName());
					arrayy.put(suppliername);
					JSONObject supplieremail = new JSONObject();
					supplieremail.put("name", "supplieremail");
					supplieremail.put("scope", "local");
					supplieremail.put("type", "string");
					supplieremail.put("value", save.getEmail());
					arrayy.put(supplieremail);
					JSONObject username = new JSONObject();
					username.put("name", "username");
					username.put("scope", "local");
					username.put("type", "string");
					username.put("value", save.getEmail());
					arrayy.put(username);
					JSONObject password = new JSONObject();
					password.put("name", "password");
					password.put("scope", "local");
					password.put("type", "string");
					password.put("value", save.getPassword());
					arrayy.put(password);
					HttpEntity<String> entityy = new HttpEntity<String>(arrayy.toString(), headers);
					ResponseEntity<String> response2 = restTemplate.exchange(
							"http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/"
									+ array.getJSONObject(0).get("id") + "/variables",
							HttpMethod.POST, entityy, String.class, 1);
					
					LOGGER.info("Taks ID 2" +array.getJSONObject(0).get("id"));
					
					System.out.println(response2);
				} catch (Exception e) {
					
					System.out.println("exception "+e);
				}
				
				
				
//				END FLOWABLE

				return save;
			} else {
				throw new VendorNotFoundException("Validation error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	/*
	 * @GetMapping("/vendor") public VendorRegister
	 * getRegisterVendors(@RequestHeader(name = "Authorization") String token) {
	 * LOGGER.info("Inside - VendorController.getRegisterVendor()"); if (token !=
	 * null && token.startsWith("Bearer ")) { jwtToken = token.substring(7);
	 * 
	 * try { String userName = jwtUtil.extractUsername(jwtToken);
	 * Optional<VendorRegister> findByUsername =
	 * vendorRepo.findByUsername(userName); if (!findByUsername.isPresent()) { throw
	 * new VendorNotFoundException("Vendor not found"); } return
	 * findByUsername.get(); } catch (Exception e) { throw new
	 * VendorNotFoundException(e.getMessage()); } }
	 * 
	 * else { throw new VendorNotFoundException("Token is not valid"); } }
	 */

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

//	@PutMapping("/vendor/{vendorId}")
//	public VendorRegister putRegisterVendor(@PathVariable("vendorId") long vendorId,
//			@RequestBody VendorRegister vendorReg) {
//		LOGGER.info("Inside - VendorController.putRegisterVendor()");
//		try {
//			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
//			if (!findById.isPresent()) {
//				throw new VendorNotFoundException("Vendor Not Available");
//			} else {
//				if ((fieldValidation.isEmail(vendorReg.getEmail()))
//						& (fieldValidation.isEmpty(vendorReg.getSupplierCompName()))) {
//					VendorRegister vr = findById.get();
//					vr.setEmail(vendorReg.getEmail());
//					vr.setSupplierCompName(vendorReg.getSupplierCompName());
//					vr.setStatus("1");
//					return this.vendorRepo.save(vr);
//				} else {
//					throw new VendorNotFoundException("Validation error");
//				}
//
//			} 
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}

//	@GetMapping("/vendor")
//	public VendorRegister getRegisterVendors(@RequestHeader(name = "Authorization") String token) {
//		LOGGER.info("Inside - VendorController.getRegisterVendor()");
//		if (token != null && token.startsWith("Bearer ")) {
//			jwtToken = token.substring(7);
//
//			try {
//				String userName = jwtUtil.extractUsername(jwtToken);
//				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
//				if (!findByUsername.isPresent()) {
//					throw new VendorNotFoundException("Vendor not found");
//				}
//				return findByUsername.get();
//			} catch (Exception e) {
//				throw new VendorNotFoundException(e.getMessage());
//			}
//		}
//
//		else {
//			throw new VendorNotFoundException("Token is not valid");
//		}
//	}
//
//	@GetMapping("/vendor/{vendorId}")
//	public Optional<VendorRegister> getRegisterVendor(@PathVariable() long vendorId) {
//		LOGGER.info("Inside - VendorController.getRegisterVendor()");
//		try {
//			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
//			LOGGER.info("Inside - VendorController.getRegisterVendor() - " + findById);
//			if (!(findById.isPresent())) {
//				throw new VendorNotFoundException("Vendor Not Found");
//			}
//			return findById;
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}
//
//	@PutMapping("/vendor/{vendorId}")
//	public VendorRegister putRegisterVendor(@PathVariable("vendorId") long vendorId,
//			@RequestBody VendorRegister vendorReg) {
//		LOGGER.info("Inside - VendorController.putRegisterVendor()");
//		try {
//			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
//			if (!findById.isPresent()) {
//				throw new VendorNotFoundException("Vendor Not Available");
//			} else {
//				if ((fieldValidation.isEmail(vendorReg.getEmail()))
//						& (fieldValidation.isEmpty(vendorReg.getSupplierCompName()))
//						) {
//					VendorRegister vr = findById.get();
//					vr.setEmail(vendorReg.getEmail());
//					vr.setSupplierCompName(vendorReg.getSupplierCompName());
//					vr.setStatus("1");
//					return this.vendorRepo.save(vr);
//				} else {
//					throw new VendorNotFoundException("Validation error");
//				}
//
//			}
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}
//
//	@DeleteMapping("/vendor/{vendorId}")
//	public Object deleteRegisterVendor(@PathVariable() long vendorId) {
//		LOGGER.info("Inside - VendorController.deleteRegisterVendor()");
//		try {
//			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
//			if (!(findById.isPresent())) {
//				throw new VendorNotFoundException("Vendor Does not exist");
//			} else {
//				VendorRegister vendorRegister = findById.get();
//				vendorRegister.setStatus("0");
//				this.vendorRepo.save(vendorRegister);
//				return "vendor deleted - " + vendorId;
//			}
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}

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
	@PostMapping("/vendor")
	public SupDetails postSupplierDetails(@RequestBody SupDetails supDetails) {
		LOGGER.info("Inside - VendorController.postSupplierDetails()");
		try {
			if ((fieldValidation.isEmpty(supDetails.getSupplierCompName()))
					& (fieldValidation.isEmpty(supDetails.getRegistrationType()))
					& (fieldValidation.isEmpty(supDetails.getRegisterId()))
					& (fieldValidation.isEmpty(supDetails.getRegristrationNo()))
					& (fieldValidation.isEmpty(supDetails.getCostCenter()))
					& (fieldValidation.isEmpty(supDetails.getRemarks()))
					& (fieldValidation.isEmpty(supDetails.getLastlogin()))) {
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(supDetails.getRegisterId());
				List<SupDetails> findAll = supDetailsRepo.findAll();
				if (findByRegisterId.size() < 1) {
					SupDetails filterSupDetails = new SupDetails();
					SupRequest supRequest=new SupRequest();
					Random rd = new Random();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");  
					 Date date = new Date();  
					filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
					filterSupDetails.setRegisterId(supDetails.getRegisterId());
					filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
					filterSupDetails.setRegristrationNo(supDetails.getRegristrationNo());
					filterSupDetails.setCostCenter(supDetails.getCostCenter());
					filterSupDetails.setRemarks(supDetails.getRemarks());
					filterSupDetails.setLastlogin(supDetails.getLastlogin());
					filterSupDetails.setSupplierCode("SU:" + formatter.format(date) +":"+ findAll.size());
					filterSupDetails.setStatus("2");
					
					
					
					supRequest.setSupplierCode(filterSupDetails.getSupplierCode());
					supRequest.setTableName("SUP_DETAILS");
					supRequest.setId(null);
					supRequest.setNewValue(filterSupDetails.toString());
					supRequest.setStatus("0");
					supRequestRepo.save(supRequest);
					return supDetailsRepo.save(filterSupDetails);
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

	@GetMapping("/vendor")
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
					if (findByRegisterId.size() > 0) {
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

	@PutMapping("/vendor/{code}")
	public SupDetails putSupDetails(@RequestBody() SupDetails supDetails, @PathVariable() String code,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.putSupDetails()");
		try {

			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);

			if ((fieldValidation.isEmpty(supDetails.getSupplierCompName()))
					& (fieldValidation.isEmpty(supDetails.getRegistrationType()))
					& (fieldValidation.isEmpty(supDetails.getRegisterId()))
					& (fieldValidation.isEmpty(supDetails.getRegristrationNo()))
					& (fieldValidation.isEmpty(supDetails.getCostCenter()))
					& (fieldValidation.isEmpty(supDetails.getRemarks()))
					& (fieldValidation.isEmpty(supDetails.getLastlogin()))) {
				Optional<SupDetails> findById = supDetailsRepo.findById(code);
				if (!loginSupplierCode.equals(null)) {
					if (findById.isPresent()) {

						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {

							SupDetails filterSupDetails = new SupDetails();
							filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
							filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
							filterSupDetails.setRegristrationNo(supDetails.getRegristrationNo());
							filterSupDetails.setRegisterId(supDetails.getRegisterId());
							filterSupDetails.setCostCenter(supDetails.getCostCenter());
							filterSupDetails.setRemarks(supDetails.getRemarks());
							filterSupDetails.setLastlogin(supDetails.getLastlogin());
							filterSupDetails.setSupplierCode(findById.get().getSupplierCode());
							filterSupDetails.setStatus("2");
							SupDetails save = supDetailsRepo.save(filterSupDetails);
							
							SupRequest supRequest=new SupRequest();
							supRequest.setSupplierCode(loginSupplierCode);
							supRequest.setTableName("SUP_DETAILS");
							supRequest.setId(filterSupDetails.getRegisterId());
							supRequest.setNewValue(filterSupDetails.toString());
							supRequest.setOldValue(findById.get().toString());
							supRequest.setStatus("0");
							supRequestRepo.save(supRequest);
							return save;
						} else {
							throw new VendorNotFoundException("You don't have permission to update this vendor");
						}
					} else {
						throw new VendorNotFoundException("Vendor Not Exist");
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}

			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

//	@DeleteMapping("/vendor/{code}")
//	public String deleteSupDetails(@PathVariable() String code, @RequestHeader(name = "Authorization") String token) {
//		LOGGER.info("Inside - VendorController.deleteSupDetails()");
//		try {
//			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
//			Optional<SupDetails> findById = supDetailsRepo.findById(code);
//			if (findById.isPresent()) {
//				if (findById.get().getSupplierCode() == loginSupplierCode) {
//					if (!findById.get().getStatus().equals("0")) {
//						SupDetails supDetails = findById.get();
//						supDetails.setStatus("0");
//						supDetailsRepo.save(supDetails);
//						return "Successfully Deleted";
//					} else {
//						throw new VendorNotFoundException("Already Deleted");
//					}
//				} else {
//					throw new VendorNotFoundException("You don't have permission to delete this vendor");
//				}
//			} else {
//				throw new VendorNotFoundException("Vendor Not Exist");
//			}
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}

	@PostMapping("/vendor/address")
	public SupAddress postAddressVendor(@RequestBody SupAddress address,
			@RequestHeader(name = "Authorization") String token) {

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(address.getAddressType()))
					& (fieldValidation.isEmpty(address.getAddress1()))
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
					filterAddressUp.setStatus("2");
					filterAddressUp.setAddressProof(address.getAddressProof());
					filterAddressUp.setAddressProofPath(address.getAddressProofPath());
					SupAddress save = this.supAddRepo.save(filterAddressUp);
					
					SupRequest supRequest=new SupRequest();
					
					supRequest.setSupplierCode(loginSupplierCode);
					supRequest.setTableName("SUP_ADDRESS");
					supRequest.setId(null);
					supRequest.setNewValue(filterAddressUp.toString());
					supRequest.setStatus("0");
					supRequestRepo.save(supRequest);
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

	@GetMapping("/vendor/address")
	public List<SupAddress> getVendorAddress(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getVendorAddress()");

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				
				List<SupAddress> vendorAddress = this.supAddRepo.findBySupplierCode(loginSupplierCode);

				if (vendorAddress.size() < 1) {
					throw new VendorNotFoundException("Vendor Not Exist");
				} else {
					return vendorAddress;
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
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
	public SupAddress putVendorAddress(@PathVariable("addressId") long addressId, @RequestBody SupAddress address,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.putVendorAddress()");

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupAddress> findById = this.supAddRepo.findById(addressId);
			if (!loginSupplierCode.equals(null)) {
				if (!findById.isPresent()) {	
					throw new VendorNotFoundException("Vendor Address Not Available");
				} else {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						if ((fieldValidation.isEmpty(address.getAddressType()))
								& (fieldValidation.isEmpty(address.getAddress1()))
								& (fieldValidation.isEmpty(address.getPostalCode()))
								& (fieldValidation.isEmpty(address.getCity()))
								& (fieldValidation.isEmpty(address.getCountry()))
								& (fieldValidation.isEmpty(address.getRegion()))
								& (fieldValidation.isEmpty(address.getAddressProof()))
								& (fieldValidation.isEmpty(address.getAddressProofPath()))) {
							SupAddress filterAddressUp = findById.get();

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
							filterAddressUp.setStatus("2");
							filterAddressUp.setAddressProof(address.getAddressProof());
							filterAddressUp.setAddressProofPath(address.getAddressProofPath());

							filterAddressUp.setAddressId(addressId);
							
							SupRequest supRequest=new SupRequest();
							
							supRequest.setSupplierCode(loginSupplierCode);
							supRequest.setTableName("SUP_ADDRESS");
							supRequest.setId(address.getAddressId());
							supRequest.setNewValue(filterAddressUp.toString());
							supRequest.setOldValue(filterAddressUp.toString());
							supRequest.setStatus("0");
							supRequestRepo.save(supRequest);
							
							
							return this.supAddRepo.save(filterAddressUp);
						} else {
							throw new VendorNotFoundException("Validation error");
						}
					} else {
						throw new VendorNotFoundException("You don't have permission to update this vendor address");
					}
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/address/{addressId}")
	public Object deleteVendorAddress(@PathVariable("addressId") long addressId,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.deleteVendorAddress()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupAddress> findById = this.supAddRepo.findById(addressId);
			if (!loginSupplierCode.equals(null)) {
				if (!findById.isPresent()) {
					throw new VendorNotFoundException("Vendor Address Does not exist");
				} else {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						if (findById.get().getStatus().equals("0")) {
							throw new VendorNotFoundException("Already Deleted");
						} else {
							SupAddress supAddress = findById.get();
							supAddress.setStatus("0");
							this.supAddRepo.save(supAddress);
							return "Successfully Deleted";
						}
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this vendor address");
					}
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor/contact")
	public SupContract postVendorContact(@RequestBody() SupContract contact, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.postVendorContact()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
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
						filterSupContract.setStatus("2");
						SupRequest supRequest=new SupRequest();
						
						supRequest.setSupplierCode(loginSupplierCode);
						supRequest.setTableName("SUP_CONTRACT");
						supRequest.setId(null);
						supRequest.setNewValue(filterSupContract.toString());
						supRequest.setStatus("0");
						supRequestRepo.save(supRequest);
						
						
						return supContractRepo.save(filterSupContract);
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

	@GetMapping("/vendor/contact")
	public List<SupContract> getSupContracts(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getSupContracts()");

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				List<SupContract> findBySupplierCode = supContractRepo.findBySupplierCode(loginSupplierCode);
				if (findBySupplierCode.size() > 0) {
					return findBySupplierCode;
				} else {
					throw new VendorNotFoundException("Vendor Contact Does not exist");
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
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
	public SupContract putSupContract(@PathVariable() Long id, @RequestBody SupContract contact,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.putSupContract()" + id);
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(contact.getContractType()))
					& (fieldValidation.isEmpty(contact.getContractTerms()))
					& (fieldValidation.isEmpty(contact.getContractProof()))
					& (fieldValidation.isEmpty(contact.getContractLocation()))) {

				Optional<SupContract> findById = supContractRepo.findById(id);
				if (!loginSupplierCode.equals(null)) {
					if (findById.isPresent()) {
						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
							SupContract filterSupContract = new SupContract();
							filterSupContract.setSupplierCode(loginSupplierCode);
							filterSupContract.setContractType(contact.getContractType());
							filterSupContract.setContractTerms(contact.getContractTerms());
							filterSupContract.setBankId(findById.get().getBankId());
							filterSupContract.setContractProof(contact.getContractProof());
							filterSupContract.setContractLocation(contact.getContractLocation());
							filterSupContract.setStatus("2");
							filterSupContract.setContractId(id);
							
							
							SupRequest supRequest=new SupRequest();
							
							supRequest.setSupplierCode(loginSupplierCode);
							supRequest.setTableName("SUP_CONTRACT");
							supRequest.setId(null);
							supRequest.setNewValue(filterSupContract.toString());
							supRequest.setOldValue(findById.get().toString());
							supRequest.setStatus("0");
							
							return supContractRepo.save(filterSupContract);
						} else {
							throw new VendorNotFoundException("You don't have permission to update this vendor contact");
						}
					} else {
						throw new VendorNotFoundException("Vendor Contact Does not exist");
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

	@DeleteMapping("/vendor/contact/{id}")
	public Object deleteSupContract(@PathVariable() Long id, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.deleteSupContract()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupContract> findById = supContractRepo.findById(id);
			if (!loginSupplierCode.equals(null)) {
				if (findById.isPresent()) {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						supContractRepo.deleteById(id);
						return "Deleted Successfuly";
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this vendor contact");
					}
				} else {
					throw new VendorNotFoundException("Vendor Contact Does not exist");
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	// ********** Write By Soumen **********//
	// End

	@PostMapping("/vendor/bank")
	public SupBank postBank(@RequestBody SupBank supBank, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.postBank()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (fieldValidation.isEmpty(supBank.getAccountHolder())
					&& fieldValidation.isEmpty(supBank.getBankAccountNo())
					&& fieldValidation.isEmpty(supBank.getBankBic()) && fieldValidation.isEmpty(supBank.getBankBranch())
					&& fieldValidation.isEmpty(supBank.getBankEvidence())
					&& fieldValidation.isEmpty(supBank.getBankName()) && fieldValidation.isEmpty(supBank.getChequeNo())
					&& fieldValidation.isEmpty(supBank.getCountry()) && fieldValidation.isEmpty(supBank.getCurrency())
					&& fieldValidation.isEmpty(supBank.getEvidencePath())
					&& fieldValidation.isEmpty(supBank.getIfscCode())
					&& fieldValidation.isEmpty(supBank.getSwiftCode())
					&& fieldValidation.isEmpty(supBank.getTransilRoutingNo())) {
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
					bank.setStatus("2");
					Optional<SupBank> findBySwiftCode = supBankRepo.findBySwiftCode(supBank.getSwiftCode());
					if (!findBySwiftCode.isPresent()) {
						SupRequest supRequest=new SupRequest();
						
						supRequest.setSupplierCode(loginSupplierCode);
						supRequest.setTableName("SUP_BANK");
						supRequest.setId(null);
						supRequest.setNewValue(bank.toString());
						supRequest.setStatus("0");
						
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

	@GetMapping("/vendor/bank")
	public List<SupBank> getBank(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getBank()");

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				List<SupBank> supBankDetails = supBankRepo.findBySupplierCode(loginSupplierCode);
				if (supBankDetails.size() < 1) {
					throw new VendorNotFoundException("Bank details not found");
				} else {
					return supBankDetails;
				}

			} else {
				throw new VendorNotFoundException("Token Expir");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PutMapping("/vendor/bank/{bankId}")
	public SupBank putBank(@PathVariable("bankId") long bankId, @RequestBody SupBank supBank,
			@RequestHeader(name = "Authorization") String token) {

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupBank> findById = this.supBankRepo.findById(bankId);
			if (!loginSupplierCode.equals(null)) {
				if (!findById.isPresent()) {
					throw new VendorNotFoundException("This Bank id not found");
				} else {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						if (fieldValidation.isEmpty(supBank.getAccountHolder())
								&& fieldValidation.isEmpty(supBank.getBankAccountNo())
								&& fieldValidation.isEmpty(supBank.getBankBic())
								&& fieldValidation.isEmpty(supBank.getBankBranch())
								&& fieldValidation.isEmpty(supBank.getBankEvidence())
								&& fieldValidation.isEmpty(supBank.getBankName())
								&& fieldValidation.isEmpty(supBank.getChequeNo())
								&& fieldValidation.isEmpty(supBank.getCountry())
								&& fieldValidation.isEmpty(supBank.getCurrency())
								&& fieldValidation.isEmpty(supBank.getEvidencePath())
								&& fieldValidation.isEmpty(supBank.getIfscCode())
								&& fieldValidation.isEmpty(supBank.getTransilRoutingNo())) {
							SupBank bank = new SupBank();
							bank.setBankId(bankId);
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
							bank.setStatus("2");
							
							SupBank sb = this.supBankRepo.save(bank);
							
							SupRequest supRequest=new SupRequest();
							
							supRequest.setSupplierCode(loginSupplierCode);
							supRequest.setTableName("SUP_BANK");
							supRequest.setId(null);
							supRequest.setNewValue(bank.toString());
							supRequest.setOldValue(findById.get().toString());
							supRequest.setStatus("0");
							
							return sb;
						} else {
							throw new VendorNotFoundException("Validation Error");
						}
					} else {
						throw new VendorNotFoundException("You don't have permission to update this vendor bank");
					}

				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/bank/{bankId}")
	public Object deleteBank(@PathVariable("bankId") long bankId, @RequestHeader(name = "Authorization") String token) {

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupBank> findById = this.supBankRepo.findById(bankId);
			if (!loginSupplierCode.equals(null)) {
				if (!findById.isPresent()) {
					throw new VendorNotFoundException("This Bank id not found");
				} else {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						this.supBankRepo.deleteById(bankId);
						return "Successfully Deleted ";
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this vendor bank");
					}
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor/department")
	public List<SupDepartment> getSupDepartment(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getSupDepartment()");

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				List<SupDepartment> supDepartment = supDepartmentRepo.findBySupplierCode(loginSupplierCode);
				if (supDepartment.size() > 0) {
					return supDepartment;
				} else {
					throw new VendorNotFoundException("Vendor department not present");
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor/department")
	public SupDepartment addSupDepartment(@RequestBody SupDepartment supDepartment, @RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
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
						department.setStatus("2");
						try {
							if (fieldValidation.isEmpty(supDepartment.getSupplierContact2()) && fieldValidation.isEmpty(supDepartment.getAlternatePhoneno())) {
								department.setSupplierContact2(supDepartment.getSupplierContact2());
								department.setAlternatePhoneno(supDepartment.getAlternatePhoneno());
							}
						} catch (Exception e) {

						}
						department.setPhoneno(supDepartment.getPhoneno());
						
						SupRequest supRequest=new SupRequest();
						
						supRequest.setSupplierCode(loginSupplierCode);
						supRequest.setTableName("SUP_DEPARTMENT");
						supRequest.setId(null);
						supRequest.setNewValue(department.toString());
						supRequest.setStatus("0");
						
						return supDepartmentRepo.save(department);
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

	@PutMapping("/vendor/department/{departmentId}")
	public SupDepartment updateSupDepartment(@PathVariable long departmentId, @RequestBody SupDepartment supDepartment,
			@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupDepartment> findById = supDepartmentRepo.findById(departmentId);
			if (!loginSupplierCode.equals(null)) {
				if (findById.isPresent()) {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						if (fieldValidation.isEmpty(supDepartment.getDepartmentName())
								&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
								&& fieldValidation.isEmpty(supDepartment.getEmail())
								&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {
							if (fieldValidation.isEmail(supDepartment.getEmail())) {
								SupDepartment department = new SupDepartment();
								department.setDepartmentName(supDepartment.getDepartmentName());
								department.setSupplierCode(loginSupplierCode);
								department.setSupplierContact1(supDepartment.getSupplierContact1());
								department.setEmail(supDepartment.getEmail());
								department.setStatus("0");
								try {
									if (fieldValidation.isEmpty(supDepartment.getSupplierContact2()) && fieldValidation.isEmpty(supDepartment.getAlternatePhoneno())) {
										department.setSupplierContact2(supDepartment.getSupplierContact2());
										department.setAlternatePhoneno(supDepartment.getAlternatePhoneno());
									}
								} catch (Exception e) {

								}
								department.setPhoneno(supDepartment.getPhoneno());
								department.setDepartmentId(departmentId);
								
								SupRequest supRequest=new SupRequest();
								
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_DEPARTMENT");
								supRequest.setId(null);
								supRequest.setNewValue(department.toString());
								supRequest.setOldValue(findById.get().toString());
								supRequest.setStatus("0");
								
								
								return supDepartmentRepo.save(department);
							} else {
								throw new VendorNotFoundException("Email Format Not Valid");
							}
						} else {
							throw new VendorNotFoundException("Validation Error");
						}
					} else {
						throw new VendorNotFoundException("You don't have permission to update this vendor department");
					}
				} else {
					throw new VendorNotFoundException("Department Not Found");
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@DeleteMapping("/vendor/department/{departmentId}")
	public Object deleteSupDepartment(@PathVariable long departmentId,
			@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupDepartment> findById = supDepartmentRepo.findById(departmentId);
			if (!loginSupplierCode.equals(null)) {
				if (findById.isPresent()) {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						supDepartmentRepo.deleteById(departmentId);
						return "Successfully Deleted";
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this vendor department");
					}
				} else {
					throw new VendorNotFoundException("Department Not Found");
				}
			} else {
				throw new VendorNotFoundException("Token Expir");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

}
