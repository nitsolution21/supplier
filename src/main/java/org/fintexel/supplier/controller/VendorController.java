package org.fintexel.supplier.controller;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.validation.*;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.fintexel.supplier.entity.ApproveMap;
import org.fintexel.supplier.entity.CurrencyMaster;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.RegType;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupContract;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.SupRequest;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableForm;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.flowable.FlowableContainer;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.CurrencyMasterRepo;
import org.fintexel.supplier.repository.RegTypeRepo;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.SupContractRepo;
import org.fintexel.supplier.repository.SupBankRepo;
import org.fintexel.supplier.repository.SupDepartmentRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.SupRequestRepo;
import org.fintexel.supplier.repository.UserRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableFormRepo;
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
	private FlowableFormRepo flowableFormRepo;

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
	private SupDepartmentRepo supDepartmentRepo;

	@Autowired
	WebClient.Builder builder;

	@Autowired
	FlowableRegistrationRepo flowableRegistrationRepo;

	@Autowired
	RegTypeRepo regTypeRepo;

	@Autowired
	CurrencyMasterRepo currencyMasterRepo;

	@PostMapping("/registration")
	public VendorRegister postRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		String taskID1_ = "", taskID2_ = "", processInstID_ = "";
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
					if(find.getSupplierCompName().toLowerCase().equals(vendorReg.getSupplierCompName().toLowerCase())) {
						throw new VendorNotFoundException("Company already exist");
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

				FlowableForm flowableForm = flowableFormRepo.findByFromId("supplierReg").get();
				String id = flowableForm.getId();
				
				System.out.println("id" + id);
				
				JSONObject autoCompleate_ = new JSONObject();
				autoCompleate_.put("formId", id);
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

//				JSONObject autoComp2 = new JSONObject();
//				autoComp2.put("taskIdActual", taskID2_);
//				autoComp2.put("suppliername", filterVendorReg.getSupplierCompName());
//				autoComp2.put("supplieremail", filterVendorReg.getEmail());
//				autoComp2.put("approvesupplier", "Yes");
//				autoComp2.put("approverremarkssupregistration", "");
//				
//				
//				autoCompleate_ = new JSONObject();
//				autoCompleate_.put("formId", "56d9e9ef-ed45-11eb-ba6c-0a5bf303a9fe");
//				autoCompleate_.put("values", autoComp2);
//				
//				LOGGER.info("autoCompleate_  "+autoCompleate_);
//				LOGGER.info("autoCompleteHeader  "+autoCompleteHeader);
//				
//				
//				HttpEntity<String> autoCompeleteEntity2 = new HttpEntity<String>(autoCompleate_.toString(), autoCompleteHeader);			
//				autoCompleteResponse = restTemplate.exchange( "http://65.2.162.230:8080/DB-task/app/rest/task-forms/"+taskID2_, HttpMethod.POST, autoCompeleteEntity2, String.class);
//				LOGGER.info("Result  "+autoCompleteResponse.getHeaders());
//				
//				
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

//	@GetMapping("/")
//	public String getUser() {
//		LOGGER.info("Inside - VendorController.getUser()");
//		return "Hello";
//	}
//
//	@GetMapping("/user")
//	List<User> all() {
//		LOGGER.info("Inside - VendorController.all()");
//		return userRepo.findAll();
//	}
//
//	@PostMapping("/user")
//	User newVendor(@RequestBody User newUser) {
//		return userRepo.save(newUser);
//	}

//	@GetMapping("/supplierAddress")
//	public List<SupAddress> allAdd() {
//		LOGGER.info("Inside - VendorController.allAdd()");
//		List<SupAddress> findAll = supAddRepo.findAll();
//		return findAll;
//	}

	// ********** Write By Soumen **********//
	// Start

	@GetMapping("/regtype")
	public List<RegType> getRegType(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.getRegType()");
		try {

			List<RegType> findAll = regTypeRepo.findAll();
			if (findAll.size() < 1) {
				throw new VendorNotFoundException("No Data Present");
			} else {
				return findAll;
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
					
					
					try {
						filterSupDetails.setRemarks(supDetails.getRemarks());
					}catch(Exception e) {
						
					}
					
					DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime lastLoginNow = LocalDateTime.now();
					Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(lastLoginNow.format(lastLogingFormat));

//					filterSupDetails.setLastlogin(lastLogin);

					try {

						if (fieldValidation.isEmpty(supDetails.getRemarks())) {
							filterSupDetails.setRemarks(supDetails.getRemarks());
						}

					} catch (Exception e) {

					}

//					filterSupDetails.setLastlogin(supDetails.getLastlogin());
					filterSupDetails.setSupplierCode("SU:" + supCodeNow.format(supCodeFormat) + ":" + findAll.size());
					filterSupDetails.setCreatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
					filterSupDetails.setCreatedOn(lastLogin);
					filterSupDetails.setStatus("PENDING");
					SupDetails save = supDetailsRepo.save(filterSupDetails);
					JSONObject suppliernamenew = new JSONObject(filterSupDetails);
					supRequest.setSupplierCode(filterSupDetails.getSupplierCode());
					supRequest.setTableName("SUP_DETAILS");
					supRequest.setId(save.getRegisterId());
					supRequest.setNewValue(suppliernamenew.toString());
					supRequest.setStatus("PENDING");
					supRequest.setReqType("CREATE");

					supRequestRepo.save(supRequest);
					return save;
				} else {

					if (findByRegisterId.get(0).getStatus().equals("PENDING")) {
						throw new VendorNotFoundException("Already Previous Request is Pending");
					} else {
						throw new VendorNotFoundException("Vendor Already Exist");
					}
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
					& (fieldValidation.isEmpty(supDetails.getRegistrationNo()))
//					& (fieldValidation.isEmpty(supDetails.getCostCenter()))
					& (fieldValidation.isEmpty(supDetails.getRemarks()))
					) {

				Optional<SupDetails> findById = supDetailsRepo.findById(code);

				if (!loginSupplierCode.equals(null)) {

					if (findById.isPresent()) {

						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
							if (findById.get().getStatus().equals("PENDING")) {
								throw new VendorNotFoundException("Already Previous Request is Pending");
							} else {
								SupDetails filterSupDetails = supDetailsRepo.findById(loginSupplierCode).get();
//								filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
								filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
								filterSupDetails.setRegistrationNo(supDetails.getRegistrationNo());
//								filterSupDetails.setRegisterId(supDetails.getRegisterId());
//								filterSupDetails.setCostCenter(supDetails.getCostCenter());
								try {
									filterSupDetails.setRemarks(supDetails.getRemarks());
								}catch(Exception e) {
									
								}
								DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
								LocalDateTime lastLoginNow = LocalDateTime.now();
								Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(lastLoginNow.format(lastLogingFormat));
								filterSupDetails.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
								filterSupDetails.setUpdatedOn(lastLogin);
//								filterSupDetails.setSupplierCode(findById.get().getSupplierCode());
								filterSupDetails.setStatus("PENDING");

								List<SupRequest> findAllWithStatus = supRequestRepo.findAllWithStatus("PENDING");
								for (SupRequest obj : findAllWithStatus) {
									if (obj.getId() != null) {
										if (obj.getId().equals(filterSupDetails.getRegisterId())) {
											throw new VendorNotFoundException("This Request is Already Pending");
										}
									}
								}
								JSONObject suppliernamenew = new JSONObject(filterSupDetails);
								JSONObject suppliernameold = new JSONObject(findById.get());
								SupRequest supRequest = new SupRequest();
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_DETAILS");
								supRequest.setId(filterSupDetails.getRegisterId());
								supRequest.setNewValue(suppliernamenew.toString());
								supRequest.setOldValue(suppliernameold.toString());
								supRequest.setStatus("PENDING");
								supRequest.setReqType("UPDATE");
								SupDetails save = supDetailsRepo.save(filterSupDetails);
								supRequestRepo.save(supRequest);
								return save;
							}

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

	@DeleteMapping("/vendor/{code}")
	public String deleteSupDetails(@PathVariable() String code, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - VendorController.deleteSupDetails()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupDetails> findById = supDetailsRepo.findById(code);
			if (findById.isPresent()) {
				if (findById.get().getSupplierCode() == loginSupplierCode) {
					if (findById.get().getStatus().equals("PENDING")) {
						throw new VendorNotFoundException("Already Previous Request is Pending");
					} else {
						if (!findById.get().getStatus().equals("DELETE")) {
							SupDetails supDetails = findById.get();
							supDetails.setStatus("PENDING");
							SupRequest supRequest = new SupRequest();
							JSONObject suppliernamenew = new JSONObject(findById.get());
							supRequest.setSupplierCode(findById.get().getSupplierCode());
							supRequest.setTableName("SUP_DETAILS");
							supRequest.setId(findById.get().getRegisterId());
							supRequest.setNewValue(suppliernamenew.toString());
							supRequest.setStatus("PENDING");
							supRequest.setReqType("DELETE");
							supRequestRepo.save(supRequest);
							supDetailsRepo.save(supDetails);
							return "Request Sent to Admin";
						} else {
							throw new VendorNotFoundException("Already Deleted");
						}
					}

				} else {
					throw new VendorNotFoundException("You don't have permission to delete this vendor");
				}
			} else {
				throw new VendorNotFoundException("Vendor Not Exist");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor/address")
	public SupAddress postAddressVendor(@RequestBody SupAddress address,
			@RequestHeader(name = "Authorization") String token) {

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(address.getAddressType())) & (fieldValidation.isEmpty(address.getAddress1()))
					& (fieldValidation.isEmpty(address.getPostalCode())) & (fieldValidation.isEmpty(address.getCity()))
					& (fieldValidation.isEmpty(address.getCountry())) & (fieldValidation.isEmpty(address.getRegion()))
					& (fieldValidation.isEmpty(address.getAddressProof()))
					& (fieldValidation.isEmpty(address.getAddressProofPath()))) {

				if (!loginSupplierCode.equals(null)) {

					List<SupAddress> findBySupplierCode = supAddRepo.findBySupplierCode(loginSupplierCode);
					for (SupAddress obj : findBySupplierCode) {
						if (obj.getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						}
					}
					
					

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
					
					DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime lastLoginNow = LocalDateTime.now();
					Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(lastLoginNow.format(lastLogingFormat));
					SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
					filterAddressUp.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
					filterAddressUp.setUpdatedOn(lastLogin);
					filterAddressUp.setPostalCode(address.getPostalCode());
					filterAddressUp.setCity(address.getCity());
					filterAddressUp.setCountry(address.getCountry());
					filterAddressUp.setRegion(address.getRegion());
					filterAddressUp.setStatus("PENDING");
					filterAddressUp.setAddressProof(address.getAddressProof());
					filterAddressUp.setAddressProofPath(address.getAddressProofPath());
					SupAddress save = this.supAddRepo.save(filterAddressUp);

					SupRequest supRequest = new SupRequest();
					JSONObject suppliername = new JSONObject(filterAddressUp);
					supRequest.setSupplierCode(loginSupplierCode);
					supRequest.setTableName("SUP_ADDRESS");
					supRequest.setId(save.getAddressId());
					supRequest.setNewValue(suppliername.toString());
					supRequest.setStatus("PENDING");
					supRequest.setReqType("CREATE");
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
						if (findById.get().getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						} else {
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
								
								DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
								LocalDateTime lastLoginNow = LocalDateTime.now();
								Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(lastLoginNow.format(lastLogingFormat));
								SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
								filterAddressUp.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
								filterAddressUp.setUpdatedOn(lastLogin);
								filterAddressUp.setPostalCode(address.getPostalCode());
								filterAddressUp.setCity(address.getCity());
								filterAddressUp.setCountry(address.getCountry());
								filterAddressUp.setRegion(address.getRegion());
								filterAddressUp.setStatus("PENDING");
								filterAddressUp.setAddressProof(address.getAddressProof());
								filterAddressUp.setAddressProofPath(address.getAddressProofPath());

								filterAddressUp.setAddressId(addressId);

								List<SupRequest> findAllWithStatus = supRequestRepo.findAllWithStatus("PENDING");
								for (SupRequest obj : findAllWithStatus) {
									if (obj.getId() != null) {
										if (obj.getId().equals(filterAddressUp.getAddressId())) {
											throw new VendorNotFoundException("This Request is Already Pending");
										}
									}
								}
								SupRequest supRequest = new SupRequest();
								JSONObject suppliernameold = new JSONObject(findById.get());
								JSONObject suppliernamenew = new JSONObject(filterAddressUp);
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_ADDRESS");
								supRequest.setId(address.getAddressId());
								supRequest.setNewValue(suppliernamenew.toString());
								supRequest.setOldValue(suppliernameold.toString());
								supRequest.setStatus("PENDING");
								supRequest.setReqType("UPDATE");
								supRequestRepo.save(supRequest);
								return this.supAddRepo.save(filterAddressUp);
							} else {
								throw new VendorNotFoundException("Validation error");
							}
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

						if (findById.get().getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						} else {

							if (findById.get().getStatus().equals("DELETE")) {
								throw new VendorNotFoundException("Already Deleted");
							} else {
								SupAddress supAddress = findById.get();
								supAddress.setStatus("PENDING");
								SupRequest supRequest = new SupRequest();
								JSONObject suppliername = new JSONObject(findById.get());
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_ADDRESS");
								supRequest.setId(findById.get().getAddressId());
								supRequest.setNewValue(suppliername.toString());
								supRequest.setStatus("PENDING");
								supRequest.setReqType("DELETE");
								supRequestRepo.save(supRequest);
								this.supAddRepo.save(supAddress);
								return "Request Sent to Admin";
							}

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
	public SupContract postVendorContact(@RequestBody() SupContract contact,
			@RequestHeader(name = "Authorization") String token) {
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
						filterSupContract.setStatus("PENDING");
						SupRequest supRequest = new SupRequest();
						SupContract save = supContractRepo.save(filterSupContract);
						JSONObject suppliernamenew = new JSONObject(filterSupContract);
						supRequest.setSupplierCode(loginSupplierCode);
						supRequest.setTableName("SUP_CONTRACT");
						supRequest.setId(save.getContractId());
						supRequest.setNewValue(suppliernamenew.toString());
						supRequest.setStatus("PENDING");
						supRequest.setReqType("CREATE");
						supRequestRepo.save(supRequest);

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
							filterSupContract.setStatus("PENDING");
							filterSupContract.setContractId(id);

							List<SupRequest> findAllWithStatus = supRequestRepo.findAllWithStatus("PENDING");
							for (SupRequest obj : findAllWithStatus) {
								if (obj.getId() != null) {
									if (obj.getId().equals(filterSupContract.getBankId())) {
										throw new VendorNotFoundException("This Request is Already Pending");
									}
								}
							}
							SupRequest supRequest = new SupRequest();
							JSONObject suppliernamenew = new JSONObject(filterSupContract);
							JSONObject suppliernameold = new JSONObject(findById.get());
							supRequest.setSupplierCode(loginSupplierCode);
							supRequest.setTableName("SUP_CONTRACT");
							supRequest.setId(contact.getContractId());
							supRequest.setNewValue(suppliernamenew.toString());
							supRequest.setOldValue(suppliernameold.toString());
							supRequest.setStatus("PENDING");
							supRequest.setReqType("UPDATE");
							supRequestRepo.save(supRequest);

							return supContractRepo.save(filterSupContract);
						} else {
							throw new VendorNotFoundException(
									"You don't have permission to update this vendor contact");
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
						SupContract supContract = findById.get();
						if (supContract.getStatus().equals("DELETE")) {
							throw new VendorNotFoundException("Already Deleted");
						} else {
							supContract.setStatus("PENDING");
							SupRequest supRequest = new SupRequest();
							SupContract save = supContractRepo.save(findById.get());
							JSONObject suppliernamenew = new JSONObject(findById.get());
							supRequest.setSupplierCode(loginSupplierCode);
							supRequest.setTableName("SUP_CONTRACT");
							supRequest.setId(findById.get().getContractId());
							supRequest.setNewValue(suppliernamenew.toString());
							supRequest.setStatus("PENDING");
							supRequest.setReqType("DELETE");
							supRequestRepo.save(supRequest);
							supContractRepo.save(supContract);
							return "Request Set to Admin";
						}
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

					List<SupBank> findBySupplierCode = supBankRepo.findBySupplierCode(loginSupplierCode);
					for (SupBank obj : findBySupplierCode) {
						if (obj.getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						}
					}
					SupBank bank = new SupBank();
					
					DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime lastLoginNow = LocalDateTime.now();
					Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(lastLoginNow.format(lastLogingFormat));
					SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
					bank.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
					bank.setUpdatedOn(lastLogin);
					
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
					bank.setStatus("PENDING");
					Optional<SupBank> findBySwiftCode = supBankRepo.findBySwiftCode(supBank.getSwiftCode());
					if (!findBySwiftCode.isPresent()) {
						SupBank postData = this.supBankRepo.save(bank);
						SupRequest supRequest = new SupRequest();
						JSONObject suppliernamenew = new JSONObject(bank);
						supRequest.setSupplierCode(loginSupplierCode);
						supRequest.setTableName("SUP_BANK");
						supRequest.setId(postData.getBankId());
						supRequest.setNewValue(suppliernamenew.toString());
						supRequest.setStatus("PENDING");
						supRequest.setReqType("CREATE");
						supRequestRepo.save(supRequest);
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
						if (findById.get().getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						} else {
							if (fieldValidation.isEmpty(supBank.getAccountHolder())
									&& fieldValidation.isEmpty(supBank.getBankAccountNo())
//									&& fieldValidation.isEmpty(supBank.getBankBic())
									&& fieldValidation.isEmpty(supBank.getBankBranch())
									&& fieldValidation.isEmpty(supBank.getBankEvidence())
									&& fieldValidation.isEmpty(supBank.getBankName())
//									&& fieldValidation.isEmpty(supBank.getChequeNo())
									&& fieldValidation.isEmpty(supBank.getCountry())
									&& fieldValidation.isEmpty(supBank.getCurrency())
									&& fieldValidation.isEmpty(supBank.getEvidencePath())
//									&& fieldValidation.isEmpty(supBank.getIfscCode())
//									&& fieldValidation.isEmpty(supBank.getTransilRoutingNo())
									) {
								SupBank bank = new SupBank();
								
								DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
								LocalDateTime lastLoginNow = LocalDateTime.now();
								Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.parse(lastLoginNow.format(lastLogingFormat));
								SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
								bank.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
								bank.setUpdatedOn(lastLogin);
								
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
								bank.setStatus("PENDING");

								SupBank sb = this.supBankRepo.save(bank);

								List<SupRequest> findAllWithStatus = supRequestRepo.findAllWithStatus("PENDING");
								for (SupRequest obj : findAllWithStatus) {
									if (obj.getId() != null) {
										if (obj.getId().equals(bank.getBankId())) {
											throw new VendorNotFoundException("This Request is Already Pending");
										}
									}
								}
								SupRequest supRequest = new SupRequest();
								JSONObject suppliernamenew = new JSONObject(bank);
								JSONObject suppliernameold = new JSONObject(findById.get());
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_BANK");
								supRequest.setId(supBank.getBankId());
								supRequest.setNewValue(suppliernamenew.toString());
								supRequest.setOldValue(suppliernameold.toString());
								supRequest.setStatus("PENDING");
								supRequest.setReqType("UPDATE");
								supRequestRepo.save(supRequest);
								return sb;
							} else {
								throw new VendorNotFoundException("Validation Error");
							}
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
						SupBank supBank = findById.get();
						if (findById.get().getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						} else {
							if (supBank.getStatus().equals("DELETE")) {
								throw new VendorNotFoundException("Already Deleted");
							} else {
								supBank.setStatus("PENDING");
								SupRequest supRequest = new SupRequest();
								JSONObject suppliernamenew = new JSONObject(findById.get());
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_BANK");
								supRequest.setId(findById.get().getBankId());
								supRequest.setNewValue(suppliernamenew.toString());
								supRequest.setStatus("PENDING");
								supRequest.setReqType("DELETE");
								supRequestRepo.save(supRequest);
								supBankRepo.save(supBank);
								return "Request Sent to Admin";

							}
						}

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
	public SupDepartment addSupDepartment(@RequestBody SupDepartment supDepartment,
			@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (fieldValidation.isEmpty(supDepartment.getDepartmentName())
					&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
					&& fieldValidation.isEmpty(supDepartment.getEmail())
					&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {

				if (fieldValidation.isEmail(supDepartment.getEmail())) {
					if (!loginSupplierCode.equals(null)) {

						List<SupDepartment> findBySupplierCode = supDepartmentRepo
								.findBySupplierCode(loginSupplierCode);
						for (SupDepartment obj : findBySupplierCode) {
							if (obj.getStatus().equals("PENDING")) {
								throw new VendorNotFoundException("Already Previous Request is Pending");
							}
						}

						SupDepartment department = new SupDepartment();
						
						DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						LocalDateTime lastLoginNow = LocalDateTime.now();
						Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(lastLoginNow.format(lastLogingFormat));
						SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
						department.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
						department.setUpdatedOn(lastLogin);
						
						department.setDepartmentName(supDepartment.getDepartmentName());
						department.setSupplierCode(loginSupplierCode);
						department.setSupplierContact1(supDepartment.getSupplierContact1());
						department.setEmail(supDepartment.getEmail());
						department.setStatus("PENDING");
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
						SupRequest supRequest = new SupRequest();
						JSONObject suppliernamenew = new JSONObject(department);
						supRequest.setSupplierCode(loginSupplierCode);
						supRequest.setTableName("SUP_DEPARTMENT");
						supRequest.setId(save.getDepartmentId());
						supRequest.setNewValue(suppliernamenew.toString());
						supRequest.setStatus("PENDING");
						supRequest.setReqType("CREATE");
						supRequestRepo.save(supRequest);
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

	@PutMapping("/vendor/department/{departmentId}")
	public SupDepartment updateSupDepartment(@PathVariable long departmentId, @RequestBody SupDepartment supDepartment,
			@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<SupDepartment> findById = supDepartmentRepo.findById(departmentId);
			if (!loginSupplierCode.equals(null)) {
				if (findById.isPresent()) {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						if (findById.get().getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						} else {
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
									department.setStatus("PENDING");
									try {
										if (fieldValidation.isEmpty(supDepartment.getSupplierContact2())
												&& fieldValidation.isEmpty(supDepartment.getAlternatePhoneno())) {
											department.setSupplierContact2(supDepartment.getSupplierContact2());
											department.setAlternatePhoneno(supDepartment.getAlternatePhoneno());
										}
									} catch (Exception e) {

									}
									DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
									LocalDateTime lastLoginNow = LocalDateTime.now();
									Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.parse(lastLoginNow.format(lastLogingFormat));
									SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
									department.setUpdatedBy(Integer.parseInt(supDetails.getRegisterId()+""));
									department.setUpdatedOn(lastLogin);
									
									department.setPhoneno(supDepartment.getPhoneno());
									department.setDepartmentId(departmentId);

									List<SupRequest> findAllWithStatus = supRequestRepo.findAllWithStatus("PENDING");
									for (SupRequest obj : findAllWithStatus) {
										if (obj.getId() != null) {
											if (obj.getId().equals(department.getDepartmentId())) {
												throw new VendorNotFoundException("This Request is Already Pending");
											}
										}
									}
									SupRequest supRequest = new SupRequest();
									JSONObject suppliernamenew = new JSONObject(department);
									JSONObject suppliernameold = new JSONObject(findById.get());
									supRequest.setSupplierCode(loginSupplierCode);
									supRequest.setTableName("SUP_DEPARTMENT");
									supRequest.setId(supDepartment.getDepartmentId());
									supRequest.setNewValue(suppliernamenew.toString());
									supRequest.setOldValue(suppliernameold.toString());
									supRequest.setStatus("PENDING");
									supRequest.setReqType("UPDATE");
									supRequestRepo.save(supRequest);
									return supDepartmentRepo.save(department);
								} else {
									throw new VendorNotFoundException("Email Format Not Valid");
								}
							} else {
								throw new VendorNotFoundException("Validation Error");
							}
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
						SupDepartment supDepartment = findById.get();
						if (findById.get().getStatus().equals("PENDING")) {
							throw new VendorNotFoundException("Already Previous Request is Pending");
						} else {
							if (supDepartment.getStatus().equals("DELETE")) {
								throw new VendorNotFoundException("Already Deleted");
							} else {
								supDepartment.setStatus("PENDING");
								supDepartmentRepo.save(supDepartment);
								SupRequest supRequest = new SupRequest();
								JSONObject suppliernamenew = new JSONObject(findById.get());
								supRequest.setSupplierCode(loginSupplierCode);
								supRequest.setTableName("SUP_DEPARTMENT");
								supRequest.setId(findById.get().getDepartmentId());
								supRequest.setNewValue(findById.get().toString());
								supRequest.setStatus("PENDING");
								supRequest.setReqType("DELETE");
								supRequestRepo.save(supRequest);
								return "Request Sent to Admin";
							}
						}

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

	@GetMapping("vendor/pending")
	public List<SupRequest> getPendingVendors() {
		LOGGER.info("Inside - VendorController.getPendingVendors()");
		try {
			List<SupRequest> findAllWithStatus = supRequestRepo.findAllWithStatus("PENDING");
			LOGGER.info("okk????????????  " + findAllWithStatus.size());
			if (findAllWithStatus.size() >= 1) {
				return findAllWithStatus;
			} else {
				throw new VendorNotFoundException("No Pending Data");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@GetMapping("vendor/pending/request/{code}")
	public List<SupRequest> getPendingRequest(@PathVariable() String code) {
		LOGGER.info("Inside - VendorController.getPendingRequest()");
		try {
			List<SupRequest> findWithStatus = supRequestRepo.findAllStatus("PENDING", code);
			return findWithStatus;
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PostMapping("vendor/approved")
	public CustomeResponseEntity vendorApproved(@RequestBody() ArrayList<ApproveMap> approveMap) {
		LOGGER.info("Inside - VendorController.vendorApproved()");
		try {
//			String tableName;
//			tableName=name.equals("address") ? "SUP_ADDRESS": name.equals("bank")?
//			"SUP_BANK":name.equals("contact")?"SUP_CONTRACT":name.equals("department")?
//			"SUP_DEPARTMENT":name.equals("details")?"SUP_DETAILS":"";

			for (ApproveMap obj : approveMap) {
				Optional<SupRequest> findById = supRequestRepo.findById(obj.getId());
				SupRequest supRequest2 = findById.get();
				String oldValue = "";
				if (supRequest2.getReqType().equals("UPDATE")) {
					oldValue = supRequest2.getOldValue();
				}
				String newValue = supRequest2.getNewValue();
				String tableName = supRequest2.getTableName();
				supRequest2.setStatus("APPROVED");
				if (tableName.equals("SUP_ADDRESS")) {
					if (supRequest2.getReqType().equals("UPDATE")) {
						SupAddress supAddressOld = SupAddress.fromJson(oldValue);

					}

					SupAddress supAddressNew = SupAddress.fromJson(newValue);

					// supAddressNew.setAddressId(supAddressNew.getAddressId());
					// supAddressNew.setStatus(findById.get().getStatus());
					supAddressNew.setStatus(obj.getStatus());
					System.out.println("save   -----   " + obj.getStatus());
					supAddRepo.save(supAddressNew);
					supRequestRepo.save(supRequest2);

				}
//			else if(tableName.equals("SUP_CONTRACT")) {
//				if(!supRequest2.getReqType().equals("CREATE")) {
//					SupContract supContactOld = SupContract.fromJson(oldValue);
//					
//				}
//				
//				SupContract supContactnew = SupContract.fromJson(newValue);
//				supContactnew.setContractId(supContactnew.getContractId());
//				supContactnew.setStatus(findById.get().getStatus());
//				supContractRepo.save(supContactnew);
//				supRequestRepo.save(supRequest2);
//			}
				else if (tableName.equals("SUP_DEPARTMENT")) {
					if (supRequest2.getReqType().equals("UPDATE")) {
						SupDepartment supDepartmentOld = SupDepartment.fromJson(oldValue);
					}

					SupDepartment supDepartmentnew = SupDepartment.fromJson(newValue);
					LOGGER.info("Inside - VendorController.vendorApproved() -dept" + supDepartmentnew);
//				supDepartmentnew.setDepartmentId(supDepartmentnew.getDepartmentId());
//				supDepartmentnew.setStatus(findById.get().getStatus());
					supDepartmentnew.setStatus(obj.getStatus());
					supDepartmentRepo.save(supDepartmentnew);
					supRequestRepo.save(supRequest2);
					LOGGER.info("before if3");
				} else if (tableName.equals("SUP_BANK")) {
					if (supRequest2.getReqType().equals("UPDATE")) {
						SupBank supBankOld = SupBank.fromJson(oldValue);
					}
					SupBank supBankNew = SupBank.fromJson(newValue);
//				supBankNew.setBankId(supBankNew.getBankId());
//				supBankNew.setStatus(findById.get().getStatus());
					supBankNew.setStatus(obj.getStatus());
					LOGGER.info("before if4" + supBankNew.toString() );
					supBankRepo.save(supBankNew);
					supRequestRepo.save(supRequest2);
					
				} else if (tableName.equals("SUP_DETAILS")) {
					if (supRequest2.getReqType().equals("UPDATE")) {
						SupDetails supDetailsOld = SupDetails.fromJson(oldValue);
					}

					SupDetails supDetailsNew = SupDetails.fromJson(newValue);
					LOGGER.info("______________" + supRequest2);
//				supDetailsNew.setRegisterId(supDetailsNew.getRegisterId());
					supDetailsNew.setStatus(obj.getStatus());
//				List<RegType> findAll = regTypeRepo.findAll();
//				for (RegType find : findAll) {
//					if((!find.getName().equals(supDetailsNew.getRegistrationType())) && (obj.getStatus().equals("APPROVED"))) {
//						
//					}else {
//						RegType regType=new RegType();
//						regType.setName(supDetailsNew.getRegistrationType());
//						regTypeRepo.save(regType);
//					}
//				}
					LOGGER.info("before if5");
					supDetailsRepo.save(supDetailsNew);
					supRequestRepo.save(supRequest2);
				}

			}
			
			return new CustomeResponseEntity("SUCCESS", "DATA IS UPDATE");

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/currency")
	public List<CurrencyMaster> getcurrency() {
		try {
			List<CurrencyMaster> findAll = currencyMasterRepo.findAll();
			if (findAll.size() > 0) {
				return findAll;
			} else {
				throw new VendorNotFoundException("Currency not Found");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

}
