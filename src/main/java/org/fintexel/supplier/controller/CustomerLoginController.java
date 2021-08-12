package org.fintexel.supplier.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerDepartment;
import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerFunctionalitiesMaster;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.CustomerRegisterRequest;
import org.fintexel.supplier.customerentity.CustomerRegisterResponse;
import org.fintexel.supplier.customerentity.CustomerUserDepartments;
import org.fintexel.supplier.customerentity.CustomerUserFunctionaliti;
import org.fintexel.supplier.customerentity.CustomerUserRoles;
import org.fintexel.supplier.customerentity.LoginResponceForCustomer;
import org.fintexel.supplier.customerentity.RolesMaster;
import org.fintexel.supplier.customerrepository.CustomerDepartmentRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerFunctionalitiesMasterRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.CustomerUserDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerUserFunctionalitiRepo;
import org.fintexel.supplier.customerrepository.CustomerUserRolesRepo;
import org.fintexel.supplier.customerrepository.RolesMasterRepo;
import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.entity.flowableentity.FlowableForm;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.flowablerepo.FlowableFormRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableRegistrationRepo;
import org.fintexel.supplier.services.CustomerDetailsServices;
import org.fintexel.supplier.services.VendorDetailsService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerLoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLoginController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerDetailsServices customerDetailsServices;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;
	
	@Autowired
	private CustomerUserRolesRepo customerUserRolesRepo;
	
	@Autowired
	private RolesMasterRepo rolesMasterRepo;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	FlowableRegistrationRepo flowableRegistrationRepo;
	
	@Autowired
	private CustomerFunctionalitiesMasterRepo customerFunctionalitiesMasterRepo;
	
	@Autowired
	private CustomerDepartmentsRepo customerDepartmentsRepo;
	
	@Autowired
	private FlowableFormRepo flowableFormRepo;
	
	@Autowired
	private GetCustomerDetails getCustomerDetails;
	
	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@PostMapping("/login")
	private ResponseEntity<?> customerLogin(@RequestBody VendorLogin vendorLogin) {
		LOGGER.info("Inside - CustomerLoginController.customerLogin()");
		try {
			Authentication authenticate = this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(vendorLogin.getUsername(), vendorLogin.getPassword()));
			LOGGER.info(authenticate.toString());
		} catch (Exception e) {
			LOGGER.info("in catch");
			throw new VendorNotFoundException(e.getMessage());
		}
		
		LOGGER.info("After try catch");
		
		UserDetails customer = this.vendorDetailsService.loadUserByUsername(vendorLogin.getUsername());
		String token = jwtUtil.generateToken(customer);
		
		Optional<CustomerRegister> findByUsername = customerRegisterRepo.findByUsername(vendorLogin.getUsername());
		if (findByUsername.isPresent()) {
			Optional<CustomerUserRoles> findByUserId = customerUserRolesRepo.findByUserId(findByUsername.get().getUserId());
			if (findByUserId.isPresent()) {
				Optional<RolesMaster> findById = rolesMasterRepo.findById(findByUserId.get().getRoleId());
				if (findById.isPresent()) {
					if (findById.get().getRole().equals("SADMIN")) {
						
						List<CustomerDepartments> departmentForSAdmin = getCustomerDetails.getDepartmentForSAdmin();
						
						List<CustomerFunctionalitiesMaster> functionalitieForSAdmin = getCustomerDetails.getFunctionalitieForSAdmin();
						
						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token, findById.get().getRole(), departmentForSAdmin, functionalitieForSAdmin, "Both", findByUsername.get().getcId(), findByUsername.get().getUsername()));
//						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), "", findById.get().getRole(), departmentForSAdmin, functionalitieForSAdmin, "Both", findByUsername.get().getcId(), findByUsername.get().getUsername()));
					}
					else if(findById.get().getRole().equals("ADMIN")) {
						
						List<CustomerDepartments> departments = getCustomerDetails.getDepartments(findByUsername.get().getUserId());
						
						List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails.getFunctionaliti(findByUsername.get().getUserId());
						
						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token, findById.get().getRole(), departments, functionaliti, "Both", findByUsername.get().getcId(), findByUsername.get().getUsername()));
//						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), "", findById.get().getRole(), departments, functionaliti, "Both", findByUsername.get().getcId(), findByUsername.get().getUsername()));
					}
					else if(findById.get().getRole().equals("USER")) {
						List<CustomerDepartments> departments = getCustomerDetails.getDepartments(findByUsername.get().getUserId());
						
						List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails.getFunctionaliti(findByUsername.get().getUserId());
						
						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token, findById.get().getRole(), departments, functionaliti, "Read", findByUsername.get().getcId(), findByUsername.get().getUsername()));
//						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), "", findById.get().getRole(), departments, functionaliti, "Read", findByUsername.get().getcId(), findByUsername.get().getUsername()));
					}
					else {
						throw new VendorNotFoundException("Role is incorrect!!");
					}
					
				} else {
					throw new VendorNotFoundException("Role is not presen");
				}
			} else {
				throw new VendorNotFoundException("Customer role not define");
			}
		} else {
			throw new VendorNotFoundException("Customer not found");
		}
	}
	
	@PostMapping("/registration")
	private CustomerRegisterResponse customerRegistration(@RequestBody() CustomerRegisterRequest customerRegisterRequest) {
		LOGGER.info("Inside - CustomerLoginController.customerRegistration()");
		String taskID1_="", processInstID_=""; 
		try {
			if (fieldValidation.isEmpty(customerRegisterRequest.getName())
				&& fieldValidation.isEmpty(customerRegisterRequest.getEmail())
				&& fieldValidation.isEmpty(customerRegisterRequest.getRole())
				&& fieldValidation.isEmpty(customerRegisterRequest.getcId())
				) {
				if (fieldValidation.isEmail(customerRegisterRequest.getEmail())) {
					CustomerRegister customerRegister = new CustomerRegister();
					CustomerUserRoles userRoles = new CustomerUserRoles();
					customerRegister.setEmail(customerRegisterRequest.getEmail());
					customerRegister.setName(customerRegisterRequest.getName());
					customerRegister.setUsername(customerRegisterRequest.getEmail());
					customerRegister.setcId(customerRegisterRequest.getcId());
					customerRegister.setStatus("COMPLITE");
					List<CustomerRegister> findAllCustomer = customerRegisterRepo.findAll();
					findAllCustomer.forEach(customer -> {
						if (customer.getEmail().equals(customerRegisterRequest.getEmail())) {
							throw new VendorNotFoundException("Email already exist");
						}
					});
					String rowPassword = java.util.UUID.randomUUID().toString();
					customerRegister.setPassword(passwordEncoder.encode(rowPassword));
					CustomerRegister registerCustomer = customerRegisterRepo.save(customerRegister);
					userRoles.setRoleId(customerRegisterRequest.getRole());
					userRoles.setUserId(registerCustomer.getUserId());
					Optional<CustomerUserRoles> findRoleByUserId = customerUserRolesRepo.findByUserId(registerCustomer.getUserId());
					if (!findRoleByUserId.isPresent()) {
						CustomerUserRoles saveUserRoles = customerUserRolesRepo.save(userRoles);
						if (!saveUserRoles.equals(null)) {
							Optional<RolesMaster> findByMasterTable = rolesMasterRepo.findById(saveUserRoles.getRoleId());
							if (findByMasterTable.isPresent()) {
								CustomerRegisterResponse customerRegisterResponse = new CustomerRegisterResponse();
								customerRegisterResponse.setcId(registerCustomer.getcId());
								customerRegisterResponse.setCreatedBy(registerCustomer.getCreatedBy());
								customerRegisterResponse.setCreatedOn(registerCustomer.getCreatedOn());
								customerRegisterResponse.setEmail(registerCustomer.getEmail());
								customerRegisterResponse.setName(registerCustomer.getName());
								customerRegisterResponse.setPassword(rowPassword);
								customerRegisterResponse.setRole(findByMasterTable.get().getRole());
								customerRegisterResponse.setStatus(registerCustomer.getStatus());
								customerRegisterResponse.setUpdatedBy(registerCustomer.getUpdatedBy());
								customerRegisterResponse.setUpdatedOn(registerCustomer.getUpdatedOn());
								
								if (findByMasterTable.get().getRole().equals("SADMIN")) {
									customerRegisterResponse.setAccess("Both");
									
									List<CustomerDepartments> departmentForSAdmin = getCustomerDetails.getDepartmentForSAdmin();
									
									customerRegisterResponse.setCustomerDepartments(departmentForSAdmin);
									
									List<CustomerFunctionalitiesMaster> functionalitieForSAdmin = getCustomerDetails.getFunctionalitieForSAdmin();
									
									customerRegisterResponse.setFunctionality(functionalitieForSAdmin);
								}
								
								if (findByMasterTable.get().getRole().equals("ADMIN")) {
									
									List<CustomerDepartments> departments = getCustomerDetails.getDepartments(registerCustomer.getUserId());
									
									customerRegisterResponse.setCustomerDepartments(departments);
									
									customerRegisterResponse.setAccess("Both");
									
									List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails.getFunctionaliti(registerCustomer.getUserId());
									
									customerRegisterResponse.setFunctionality(functionaliti);
									
								}
								
								if(findByMasterTable.get().getRole().equals("USER")) {
									
									List<CustomerDepartments> departments = getCustomerDetails.getDepartments(registerCustomer.getUserId());
									
									customerRegisterResponse.setCustomerDepartments(departments);
									
									customerRegisterResponse.setAccess("Read");
									
									List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails.getFunctionaliti(registerCustomer.getUserId());
									
									customerRegisterResponse.setFunctionality(functionaliti);
								}
								
								
								/*  ----------- REQUEST PROCESS ID with PROCESS DEFINITION KEY ------------------------------------------------------- */
								
								
								Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo.findByAuthorAndTitle("customer_registration");
								RestTemplate restTemplate = new RestTemplate();
								
								HttpHeaders BaseAuthHeader = new HttpHeaders();
								BaseAuthHeader.setContentType(MediaType.APPLICATION_JSON);
								BaseAuthHeader.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
								BaseAuthHeader.setBasicAuth("admin", "test");
								
								/* ==============================	 ProcessInstance Request		================================================*/
								Map<String, Object> pDMap = new HashMap<>();
								pDMap.put("processDefinitionId", findByAuthorAndTitle.get().getId());
								HttpEntity<Map<String, Object>> pDEntity = new HttpEntity<>(pDMap, BaseAuthHeader);
								ResponseEntity<String> response = restTemplate.postForEntity("http://65.2.162.230:8080/flowable-rest/service/runtime/process-instances", pDEntity, String.class);
								
								
								/* ==============================	 Query Task 1		================================================*/
								
								
								Map<String, Object> queryMap = new HashMap<>();
								JSONObject jsonObject = new JSONObject(response.getBody());
								processInstID_= (String) jsonObject.get("id"); 
								queryMap.put("processInstanceId", processInstID_);
								LOGGER.info("ProcessInstanceID : "+processInstID_);
								HttpEntity<Map<String, Object>> baseAuthEntity = new HttpEntity<>(queryMap, BaseAuthHeader);
								ResponseEntity<String> queryRequest_1 = restTemplate.exchange( "http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST, baseAuthEntity, String.class, 1);
								
								/*-------------------------------------------POST From value ----------------------------------------------*/
								JSONObject jsonObject1 = new JSONObject(queryRequest_1.getBody());

								JSONArray array = new JSONArray(jsonObject1.get("data").toString());
								JSONArray formReqBody = new JSONArray();
								
								JSONObject registrationId = new JSONObject();
								registrationId.put("name", "registrationid");
								registrationId.put("scope", "local");
								registrationId.put("type", "string");
								registrationId.put("value", registerCustomer.getUserId());
								formReqBody.put(registrationId);
								
								JSONObject customerName = new JSONObject();
								customerName.put("name", "customername");
								customerName.put("scope", "local");
								customerName.put("type", "string");
								customerName.put("value", registerCustomer.getName());
								formReqBody.put(customerName);
								
								JSONObject customerEmail = new JSONObject();
								customerEmail.put("name", "customeremail");
								customerEmail.put("scope", "local");
								customerEmail.put("type", "string");
								customerEmail.put("value", registerCustomer.getEmail());
								formReqBody.put(customerEmail);
								
								JSONObject customerUsername = new JSONObject();
								customerUsername.put("name", "customerusername");
								customerUsername.put("scope", "local");
								customerUsername.put("type", "string");
								customerUsername.put("value", registerCustomer.getUsername());
								formReqBody.put(customerUsername);
								
								JSONObject customerPassword = new JSONObject();
								customerPassword.put("name", "customerpassword");
								customerPassword.put("scope", "local");
								customerPassword.put("type", "string");
								customerPassword.put("value", rowPassword);
								formReqBody.put(customerPassword);

								HttpEntity<String> formReqEntity = new HttpEntity<String>(formReqBody.toString(), BaseAuthHeader);
								
								ResponseEntity<String> formResponse = restTemplate.exchange( "http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/" + array.getJSONObject(0).get("id") + "/variables", HttpMethod.POST, formReqEntity, String.class, 1);
								taskID1_ = (String)array.getJSONObject(0).get("id");
								
								/*  ----------- 	GET COOKIE FROM DB-idm	 ------------------------------------------------------- */
								
								HttpHeaders loginHeader = new HttpHeaders();
								loginHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
								MultiValueMap<String, String> loginMap= new LinkedMultiValueMap<String, String>();
								
								loginMap.add("j_username", "indexer");
								loginMap.add("j_password", "123");
								loginMap.add("submit", "Login");
								loginMap.add("_spring_security_remember_me", "true");
								
								HttpEntity<MultiValueMap<String, String>> loginReq = new HttpEntity<MultiValueMap<String, String>>(loginMap, loginHeader);
								ResponseEntity<String> loginResponse = restTemplate.postForEntity( "http://65.2.162.230:8080/DB-idm/app/authentication", loginReq , String.class );
								JSONObject cookieJO = new JSONObject(loginResponse.getHeaders());
								String coockie_ = cookieJO.get("Set-Cookie").toString().replace("[", "").replace("]", "").replace("\"", "");	
								
								LOGGER.info("Coockie : " +coockie_);
								
								/*  ----------- 	AUTO CLAIMING Registration 	 ------------------------------------------------------- */
								
								HttpHeaders autoCliamHeader = new HttpHeaders();
								autoCliamHeader.add("Cookie", coockie_);
								HttpEntity autoClaimEntity = new HttpEntity(null, autoCliamHeader);
								ResponseEntity autoClaimResponse = restTemplate.exchange( "http://65.2.162.230:8080/DB-task/app/rest/tasks/"+ taskID1_ + "/action/claim", HttpMethod.PUT, autoClaimEntity, String.class);
								LOGGER.info("auto complite froom shantanu:    "+autoClaimResponse);
								
//								AUTO COMPLEATE START

								HttpHeaders header = new HttpHeaders();
								header.add("Cookie", coockie_);
								header.setContentType(MediaType.APPLICATION_JSON);
								header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
								JSONObject autoCompleate = new JSONObject();
								autoCompleate.put("taskIdActual", taskID1_);
								autoCompleate.put("registrationid", registerCustomer.getUserId());
								autoCompleate.put("customername", registerCustomer.getName());
								autoCompleate.put("customeremail", registerCustomer.getEmail());
								autoCompleate.put("customerusername", registerCustomer.getUsername());
								autoCompleate.put("customerpassword", rowPassword);
								JSONObject mapp = new JSONObject();
								Optional<FlowableForm> findByFromId = flowableFormRepo.findByFromId("'customerReg' ");
								mapp.put("formId", findByFromId.get().getId());
								mapp.put("values", autoCompleate);
								System.out.println("Body  " + mapp);
								System.out.println("headers  " + header);
								HttpEntity<Map<String, Object>> entity = new HttpEntity(mapp.toString(), header);
								ResponseEntity rssResponsee = restTemplate.exchange(
										"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID1_, HttpMethod.POST, entity,
										String.class);
//								System.out.print("Result  "+rssResponsee.getHeaders());
								System.out.print("Result  " + rssResponsee.getHeaders());

//								AUTO COMPLEATE END
								
								return customerRegisterResponse;
							} else {
								throw new VendorNotFoundException("The roll is not present");
							}
							
						} else {
							throw new VendorNotFoundException("User role is not created");
						}
					} else {
						throw new VendorNotFoundException("For this uaer all ready role is present");
					}
				} else {
					throw new VendorNotFoundException("Email is not Valid");
				}
				
			} else {
				throw new VendorNotFoundException("All field are requard");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getRoles")
	public List<RolesMaster> getRoles() {
		List<RolesMaster> findAll = rolesMasterRepo.findAll();
		
		if (findAll.size() > 0) {
			return findAll;
		} else {
			throw new VendorNotFoundException("");
		}
	}
	
	@GetMapping("/getAllDepartments")
	public List<CustomerDepartments> getAllDepartments() {
		try {
			List<CustomerDepartments> findAll = customerDepartmentsRepo.findAll();
			
			if (findAll.size() > 0) {
				return findAll;
			} else {
				throw new VendorNotFoundException("Department not found");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/getAllFunctionality")
	public List<CustomerFunctionalitiesMaster> getAllFunctionality() {
		try {
			List<CustomerFunctionalitiesMaster> findAll = customerFunctionalitiesMasterRepo.findAll();
			if (findAll.size() > 0) {
				return findAll;
			} else {
				throw new VendorNotFoundException("Functionality not found");
			}
		} catch (Exception e) {
			LOGGER.info("in Catch " + e.getMessage());
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
