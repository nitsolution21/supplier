package org.fintexel.supplier.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	@Autowired
	private CustomerUserDepartmentsRepo customerUserDepartmentsRepo;

	@Autowired
	private CustomerUserFunctionalitiRepo customerUserFunctionalitiRepo;

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
			Optional<CustomerUserRoles> findByUserId = customerUserRolesRepo
					.findByUserId(findByUsername.get().getUserId());
			if (findByUserId.isPresent()) {
				Optional<RolesMaster> findById = rolesMasterRepo.findById(findByUserId.get().getRoleId());
				if (findById.isPresent()) {
					if (findById.get().getRole().equals("SADMIN")) {

						List<CustomerDepartments> departmentForSAdmin = getCustomerDetails.getDepartmentForSAdmin();

						List<CustomerFunctionalitiesMaster> functionalitieForSAdmin = getCustomerDetails
								.getFunctionalitieForSAdmin();

						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token,
								findById.get().getRole(), departmentForSAdmin, functionalitieForSAdmin, "Both",
								findByUsername.get().getcId(), findByUsername.get().getUsername()));
//						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), "", findById.get().getRole(), departmentForSAdmin, functionalitieForSAdmin, "Both", findByUsername.get().getcId(), findByUsername.get().getUsername()));
					} else if (findById.get().getRole().equals("ADMIN")) {

						List<CustomerDepartments> departments = getCustomerDetails
								.getDepartments(findByUsername.get().getUserId());

						List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails
								.getFunctionaliti(findByUsername.get().getUserId());

						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token,
								findById.get().getRole(), departments, functionaliti, "Both",
								findByUsername.get().getcId(), findByUsername.get().getUsername()));
//						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), "", findById.get().getRole(), departments, functionaliti, "Both", findByUsername.get().getcId(), findByUsername.get().getUsername()));
					} else if (findById.get().getRole().equals("USER")) {
						List<CustomerDepartments> departments = getCustomerDetails
								.getDepartments(findByUsername.get().getUserId());

						List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails
								.getFunctionaliti(findByUsername.get().getUserId());

						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token,
								findById.get().getRole(), departments, functionaliti, "Read",
								findByUsername.get().getcId(), findByUsername.get().getUsername()));
//						return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), "", findById.get().getRole(), departments, functionaliti, "Read", findByUsername.get().getcId(), findByUsername.get().getUsername()));
					} else {
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
	private CustomerRegisterResponse customerRegistration(
			@RequestBody() CustomerRegisterRequest customerRegisterRequest) {
		LOGGER.info("Inside - CustomerLoginController.customerRegistration()");
		String taskID1_ = "", processInstID_ = "";
		try {
			if (fieldValidation.isEmpty(customerRegisterRequest.getName())
					&& fieldValidation.isEmpty(customerRegisterRequest.getEmail())
					&& fieldValidation.isEmpty(customerRegisterRequest.getRole())
					&& fieldValidation.isEmpty(customerRegisterRequest.getcId())
					&& fieldValidation.isEmpty(customerRegisterRequest.getDepartment())
					&& fieldValidation.isEmpty(customerRegisterRequest.getFuncationality())) {
				if (fieldValidation.isEmail(customerRegisterRequest.getEmail())) {
					CustomerRegister customerRegister = new CustomerRegister();
					CustomerUserRoles userRoles = new CustomerUserRoles();
					CustomerUserFunctionaliti functionality = new CustomerUserFunctionaliti();
					CustomerUserDepartments department = new CustomerUserDepartments();
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
					LOGGER.info("After save data in customer register table >>>>> " + registerCustomer);
					userRoles.setRoleId(customerRegisterRequest.getRole());
					userRoles.setUserId(registerCustomer.getUserId());
					Optional<CustomerUserRoles> findRoleByUserId = customerUserRolesRepo
							.findByUserId(registerCustomer.getUserId());
					if (!findRoleByUserId.isPresent()) {
						CustomerUserRoles saveUserRoles = customerUserRolesRepo.save(userRoles);
						LOGGER.info("After Save data in customer role table >>>>  " + saveUserRoles);
						if (!saveUserRoles.equals(null)) {
							Optional<RolesMaster> findByMasterTable = rolesMasterRepo
									.findById(saveUserRoles.getRoleId());
							LOGGER.info("After gating data from master table >>>>>>  " + findByMasterTable.get());
							if (findByMasterTable.isPresent()) {
								List<CustomerUserDepartments> findUserDepartmentsByUserId = customerUserDepartmentsRepo
										.findByUserId(registerCustomer.getUserId());
								if (findUserDepartmentsByUserId.size() < 1) {
									department.setDepartmentId(customerRegisterRequest.getDepartment());
									department.setUserId(registerCustomer.getUserId());
									CustomerUserDepartments customerUserDepartments = customerUserDepartmentsRepo
											.save(department);
									LOGGER.info(
											"After save customer Departments if user is not present in customer department table >>>>>>> "
													+ customerUserDepartments);
								} else {
									findUserDepartmentsByUserId.forEach(presentDepartment -> {
										if (presentDepartment.getDepartmentId() == customerRegisterRequest
												.getDepartment()) {
											throw new VendorNotFoundException(
													"The department all ready present for the particular user");
										}
									});
									department.setDepartmentId(customerRegisterRequest.getDepartment());
									department.setUserId(registerCustomer.getUserId());
									CustomerUserDepartments customerUserDepartments = customerUserDepartmentsRepo
											.save(department);
									LOGGER.info(
											"After save customer Departments if the department is not present for the particular user in customer department table >>>>>>>  "
													+ customerUserDepartments);
								}
								List<CustomerUserFunctionaliti> findFunctionalityByUserId = customerUserFunctionalitiRepo
										.findByUserId(registerCustomer.getUserId());
								/* start code from here */
								if (findFunctionalityByUserId.size() < 1) {
									if (customerRegisterRequest.getFuncationality() == 8) {
										List<CustomerUserFunctionaliti> customerUserFunctionalitis = new ArrayList<CustomerUserFunctionaliti>();
										CustomerUserFunctionaliti customerUserFunctionaliti = new CustomerUserFunctionaliti();
										functionality.setUserId(registerCustomer.getUserId());
										functionality.setfId(customerRegisterRequest.getFuncationality());
										
										customerUserFunctionaliti.setfId(customerRegisterRequest.getFuncationality());
										customerUserFunctionaliti.setUserId(registerCustomer.getUserId());
										
										customerUserFunctionalitis.add(functionality);
										customerUserFunctionalitis.add(customerUserFunctionaliti);
										
										customerUserFunctionalitiRepo.saveAll(customerUserFunctionalitis);
										
									} else {
										functionality.setUserId(registerCustomer.getUserId());
										functionality.setfId(customerRegisterRequest.getFuncationality());
										CustomerUserFunctionaliti save = customerUserFunctionalitiRepo.save(functionality);
										LOGGER.info(
												"After save customer Departments if user is not present in customer functionality table >>>>>>> "
														+ save);
									}

								} else {
									findFunctionalityByUserId.forEach(presentFunctionality -> {
										if (presentFunctionality.getfId() == registerCustomer.getUserId()) {
											throw new VendorNotFoundException(
													"The functionality all ready present for the particular user");
										}
									});
									functionality.setUserId(registerCustomer.getUserId());
									functionality.setfId(customerRegisterRequest.getFuncationality());
									CustomerUserFunctionaliti save = customerUserFunctionalitiRepo.save(functionality);
									LOGGER.info(
											"After save customer funcationality if the funcationality is not present for the particular user in customer funcationality table >>>>>>>  "
													+ save);
								}
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

									List<CustomerDepartments> departmentForSAdmin = getCustomerDetails
											.getDepartmentForSAdmin();

									customerRegisterResponse.setCustomerDepartments(departmentForSAdmin);

									List<CustomerFunctionalitiesMaster> functionalitieForSAdmin = getCustomerDetails
											.getFunctionalitieForSAdmin();

									customerRegisterResponse.setFunctionality(functionalitieForSAdmin);
								}

								if (findByMasterTable.get().getRole().equals("ADMIN")) {

									List<CustomerDepartments> departments = getCustomerDetails
											.getDepartments(registerCustomer.getUserId());

									customerRegisterResponse.setCustomerDepartments(departments);

									customerRegisterResponse.setAccess("Both");

									List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails
											.getFunctionaliti(registerCustomer.getUserId());

									customerRegisterResponse.setFunctionality(functionaliti);

								}

								if (findByMasterTable.get().getRole().equals("USER")) {

									List<CustomerDepartments> departments = getCustomerDetails
											.getDepartments(registerCustomer.getUserId());

									customerRegisterResponse.setCustomerDepartments(departments);

									customerRegisterResponse.setAccess("Read");

									List<CustomerFunctionalitiesMaster> functionaliti = getCustomerDetails
											.getFunctionaliti(registerCustomer.getUserId());

									customerRegisterResponse.setFunctionality(functionaliti);
								}

								/*
								 * ----------- REQUEST PROCESS ID with PROCESS DEFINITION KEY
								 * -------------------------------------------------------
								 */

								Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo
										.findByAuthorAndTitle("customer_registration");
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
										"http://65.2.162.230:8080/flowable-rest/service/runtime/process-instances",
										pDEntity, String.class);

								/*
								 * ============================== Query Task 1
								 * ================================================
								 */

								Map<String, Object> queryMap = new HashMap<>();
								JSONObject jsonObject = new JSONObject(response.getBody());
								processInstID_ = (String) jsonObject.get("id");
								queryMap.put("processInstanceId", processInstID_);
								LOGGER.info("ProcessInstanceID : " + processInstID_);
								HttpEntity<Map<String, Object>> baseAuthEntity = new HttpEntity<>(queryMap,
										BaseAuthHeader);
								ResponseEntity<String> queryRequest_1 = restTemplate.exchange(
										"http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST,
										baseAuthEntity, String.class, 1);

								/*-------------------------------------------POST From value ----------------------------------------------*/
								JSONObject jsonObject1 = new JSONObject(queryRequest_1.getBody());

								JSONArray array = new JSONArray(jsonObject1.get("data").toString());
//								System.out.println("queryRequest_1  2nd internal api"+array.getJSONObject(0).get("id"));
								JSONArray formReqBody = new JSONArray();

								JSONObject registrationId = new JSONObject();
								registrationId.put("name", "registrationid");
								registrationId.put("scope", "local");
								registrationId.put("type", "string");
								registrationId.put("value", registerCustomer.getUserId() + "");
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

								HttpEntity<String> formReqEntity = new HttpEntity<String>(formReqBody.toString(),
										BaseAuthHeader);

								ResponseEntity<String> formResponse = restTemplate.exchange(
										"http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/"
												+ array.getJSONObject(0).get("id") + "/variables",
										HttpMethod.POST, formReqEntity, String.class, 1);

								taskID1_ = (String) array.getJSONObject(0).get("id");

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
								ResponseEntity<String> loginResponse = restTemplate.postForEntity(
										"http://65.2.162.230:8080/DB-idm/app/authentication", loginReq, String.class);
								JSONObject cookieJO = new JSONObject(loginResponse.getHeaders());
								String coockie_ = cookieJO.get("Set-Cookie").toString().replace("[", "")
										.replace("]", "").replace("\"", "");

								LOGGER.info("Coockie : " + coockie_);

								/*
								 * ----------- AUTO CLAIMING Registration
								 * -------------------------------------------------------
								 */

								HttpHeaders autoCliamHeader = new HttpHeaders();
								autoCliamHeader.add("Cookie", coockie_);
								HttpEntity autoClaimEntity = new HttpEntity(null, autoCliamHeader);
								ResponseEntity autoClaimResponse = restTemplate.exchange(
										"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID1_ + "/action/claim",
										HttpMethod.PUT, autoClaimEntity, String.class);
								LOGGER.info("auto complite froom shantanu:    " + autoClaimResponse);

//								AUTO COMPLEATE START

								HttpHeaders header = new HttpHeaders();
								header.add("Cookie", coockie_);
								header.setContentType(MediaType.APPLICATION_JSON);
								header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
								JSONObject autoCompleate = new JSONObject();
								autoCompleate.put("taskIdActual", taskID1_);
								autoCompleate.put("registrationid", registerCustomer.getUserId() + "");
								autoCompleate.put("customername", registerCustomer.getName());
								autoCompleate.put("customeremail", registerCustomer.getEmail());
								autoCompleate.put("customerusername", registerCustomer.getUsername());
								autoCompleate.put("customerpassword", rowPassword);
								JSONObject mapp = new JSONObject();
								Optional<FlowableForm> findByFromId = flowableFormRepo.findByFromId("customerReg");
								mapp.put("formId", findByFromId.get().getId());
//								mapp.put("formId", "f11a2725-f6ad-11eb-ba6c-0a5bf303a9fe");
								mapp.put("values", autoCompleate);
								System.out.println("Body  " + mapp);
								System.out.println("headers  " + header);
								HttpEntity<Map<String, Object>> entity = new HttpEntity(mapp.toString(), header);
								ResponseEntity rssResponsee = restTemplate.exchange(
										"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID1_,
										HttpMethod.POST, entity, String.class);
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
				throw new VendorNotFoundException("All field are required");
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/registration")
	public List<CustomerRegisterResponse> getRegistration(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerLoginController.getRegistration()");
		try {
			if (getCustomerDetails.getCustomerIdFromToken(token) != (-1)) {
				long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
				long companyProfileIdByCustomerId = getCustomerDetails
						.getCompanyProfileIdByCustomerId(customerIdFromToken);

				LOGGER.info("Profile id is:  >>>" + companyProfileIdByCustomerId);
				List<CustomerRegister> finAllCustomerdBycId = customerRegisterRepo
						.findBycId(companyProfileIdByCustomerId);
				List<CustomerRegisterResponse> customerRegisterResponseList = new ArrayList<>();

				LOGGER.info("finAllCustomerdBycId>>>>>> " + finAllCustomerdBycId.toString());

				finAllCustomerdBycId.forEach(customer -> {
					try {
						Optional<CustomerRegister> findById = customerRegisterRepo.findById(customer.getUserId());
						LOGGER.info(findById.get().toString());
						List<CustomerDepartments> customerDepartments = new ArrayList<>();
						List<CustomerFunctionalitiesMaster> customerFunctionalitiesMaster = new ArrayList<>();
						CustomerRegisterResponse customerRegisterResponse = new CustomerRegisterResponse();
						customerRegisterResponse.setEmail(findById.get().getEmail());
						customerRegisterResponse.setUserId(findById.get().getUserId());
						customerRegisterResponse.setcId(findById.get().getcId());
						customerRegisterResponse.setName(findById.get().getName());
						customerRegisterResponse.setUsername(findById.get().getUsername());
						Optional<CustomerUserRoles> findByUserIdRole = customerUserRolesRepo
								.findByUserId(findById.get().getUserId());
						LOGGER.info(findByUserIdRole.get().toString());
						CustomerUserRoles customerUserRoles = findByUserIdRole.get();
						RolesMaster rolesMaster = rolesMasterRepo.findById(customerUserRoles.getRoleId()).get();
						LOGGER.info("oo  " + rolesMaster.toString());
						customerRegisterResponse.setRole(rolesMaster.getRole());
						List<CustomerUserDepartments> findByUserIdDept = customerUserDepartmentsRepo
								.findByUserId(customerUserRoles.getUserId());
						String deptTemp = "";
						for (CustomerUserDepartments dept : findByUserIdDept) {
							CustomerDepartments tempCustomerDepartments = customerDepartmentsRepo
									.findById(dept.getDepartmentId()).get();
							customerDepartments.add(tempCustomerDepartments);
							deptTemp = deptTemp + tempCustomerDepartments.getDepartmentName() + ",";
						}
						deptTemp = deptTemp.substring(0, deptTemp.lastIndexOf(","));
						customerRegisterResponse.setDepartment(deptTemp);
						customerRegisterResponse.setCustomerDepartments(customerDepartments);
						List<CustomerUserFunctionaliti> findByUserIdFunctionality = customerUserFunctionalitiRepo
								.findByUserId(customerUserRoles.getUserId());
						String funTemp = "";
						for (CustomerUserFunctionaliti func : findByUserIdFunctionality) {
							CustomerFunctionalitiesMaster tempCustomerFunctionalitiesMaster = customerFunctionalitiesMasterRepo
									.findById(func.getfId()).get();
							customerFunctionalitiesMaster.add(tempCustomerFunctionalitiesMaster);
							funTemp = funTemp + tempCustomerFunctionalitiesMaster.getfName() + ",";
						}
						funTemp = funTemp.substring(0, funTemp.lastIndexOf(","));
						customerRegisterResponse.setFunctionality(customerFunctionalitiesMaster);
						customerRegisterResponse.setCustomerFunctionality(funTemp);

						LOGGER.info("oo p " + customerRegisterResponse.toString());

						customerRegisterResponseList.add(customerRegisterResponse);
					} catch (Exception e) {
						LOGGER.info("error  " + customer.getUserId());
					}

				});
				LOGGER.info("final " + customerRegisterResponseList.toString());
				return customerRegisterResponseList;

			} else {
				throw new VendorNotFoundException("No Data Found");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PutMapping("/registration/{regId}")
	public CustomerRegisterResponse updateRegistration(@PathVariable long regId,
			@RequestBody() CustomerRegisterRequest customerRegisterRequest,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - CustomerLoginController.updateRegistration()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			String roleByUserId = getCustomerDetails.getRoleByUserId(customerIdFromToken);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			CustomerRegister customerRegister = new CustomerRegister();
			CustomerUserRoles userRoles = new CustomerUserRoles();
			CustomerUserFunctionaliti functionality = new CustomerUserFunctionaliti();
			CustomerUserDepartments department = new CustomerUserDepartments();
			CustomerRegisterResponse customerRegisterResponse = new CustomerRegisterResponse();
			Optional<CustomerRegister> findCustomerRegistrationById = customerRegisterRepo.findById(regId);
			
			switch (roleByUserId) {
			case "SADMIN":
				if (findCustomerRegistrationById.isPresent()) {
					if (findCustomerRegistrationById.get().getcId() == companyProfileIdByCustomerId) {
						if (fieldValidation.isEmpty(customerRegisterRequest.getName())
								&& fieldValidation.isEmpty(customerRegisterRequest.getEmail())
								&& fieldValidation.isEmpty(customerRegisterRequest.getRole())
								&& fieldValidation.isEmpty(customerRegisterRequest.getDepartment())
								&& fieldValidation.isEmpty(customerRegisterRequest.getFuncationality())) {
							if (fieldValidation.isEmail(customerRegisterRequest.getEmail())) {
								
								LOGGER.info("Id is <1> "+regId);
								
								customerRegister.setcId(companyProfileIdByCustomerId);
								customerRegister.setCreatedBy(findCustomerRegistrationById.get().getCreatedBy());
								customerRegister.setCreatedOn(findCustomerRegistrationById.get().getCreatedOn());
								customerRegister.setEmail(customerRegisterRequest.getEmail());
								customerRegister.setName(customerRegisterRequest.getName());
								customerRegister.setPassword(findCustomerRegistrationById.get().getPassword());
								customerRegister.setStatus(findCustomerRegistrationById.get().getStatus());
								customerRegister.setUserId(regId);
								customerRegister.setUsername(findCustomerRegistrationById.get().getUsername());

								CustomerRegister updateCustomer = customerRegisterRepo.save(customerRegister);
								
								LOGGER.info("After update customer register table"+updateCustomer);
								
								customerRegisterResponse.setcId(updateCustomer.getcId());
								customerRegisterResponse.setCreatedBy(updateCustomer.getCreatedBy());
								customerRegisterResponse.setCreatedOn(updateCustomer.getCreatedOn());
								customerRegisterResponse.setEmail(updateCustomer.getEmail());
								customerRegisterResponse.setName(updateCustomer.getName());
								customerRegisterResponse.setStatus(updateCustomer.getStatus());
								customerRegisterResponse.setUpdatedBy(updateCustomer.getUpdatedBy());
								customerRegisterResponse.setUpdatedOn(updateCustomer.getUpdatedOn());
								customerRegisterResponse.setUserId(updateCustomer.getUserId());
								customerRegisterResponse.setUsername(updateCustomer.getUsername());
								

								Optional<CustomerUserRoles> findUserroleByUserId = customerUserRolesRepo
										.findByUserId(regId);
								LOGGER.info("Find user role by id  |_____<<<>>>>_____|  "+findUserroleByUserId.get());
								if (findUserroleByUserId.isPresent()) {
									
									LOGGER.info("Id is from delete<2> "+regId);
									
									customerUserRolesRepo.deleteById(regId);
									
									userRoles.setRoleId(customerRegisterRequest.getRole());
									userRoles.setUserId(regId);
									
									CustomerUserRoles updateCustomerUserRoles = customerUserRolesRepo.save(userRoles);
									
									Optional<RolesMaster> findRoleFromMasterTableById = rolesMasterRepo.findById(updateCustomerUserRoles.getRoleId());
									
									customerRegisterResponse.setRole(findRoleFromMasterTableById.get().getRole());

								} else {
//									throw new VendorNotFoundException("Your role is not asign");
									customerRegisterResponse.setRole("Your role is not asign");
								}

								List<CustomerUserFunctionaliti> findFunctionalityByUserId = customerUserFunctionalitiRepo.findByUserId(regId);
								
								LOGGER.info("findFunctionalityByUserId |________<<>>>_____|",findFunctionalityByUserId.toString());
								LOGGER.info("findFunctionalityByUserId |________<<>>>_____| SIZE   ",findFunctionalityByUserId.size());
								
								if (findFunctionalityByUserId.size() > 0) {
									
//									findFunctionalityByUserId.forEach(userFunctionality -> {
//										if (userFunctionality.getfId() == customerRegisterRequest.getFuncationality()) {
//											
//											customerUserFunctionalitiRepo.deleteById(regId);
//											
//											LOGGER.info("in Functionality user id <3>"+regId);
//											
//											functionality.setfId(customerRegisterRequest.getFuncationality());
//											functionality.setUserId(regId);
//											
//											CustomerUserFunctionaliti customerUserFunctionaliti = customerUserFunctionalitiRepo.save(functionality);
//											LOGGER.info("Functionality final $$$ "+customerUserFunctionaliti);
//										}
//									});
									for(CustomerUserFunctionaliti userFunctionality : findFunctionalityByUserId) {
										if (userFunctionality.getfId() == customerRegisterRequest.getFuncationality()) {

											customerUserFunctionalitiRepo.deleteById(userFunctionality.getUserId());

											LOGGER.info("in Functionality user id <3>" + regId);

											functionality.setfId(customerRegisterRequest.getFuncationality());
											functionality.setUserId(regId);

											CustomerUserFunctionaliti customerUserFunctionaliti = customerUserFunctionalitiRepo
													.save(functionality);
											LOGGER.info("Functionality final $$$ " + customerUserFunctionaliti);
										}
										else {
											customerUserFunctionalitiRepo.deleteById(userFunctionality.getUserId());
											
											functionality.setfId(customerRegisterRequest.getFuncationality());
											functionality.setUserId(regId);

											CustomerUserFunctionaliti customerUserFunctionaliti = customerUserFunctionalitiRepo
													.save(functionality);
										}
									}
									
									List<CustomerFunctionalitiesMaster> listOfFunctionality = getCustomerDetails.getFunctionaliti(regId);
									customerRegisterResponse.setFunctionality(listOfFunctionality);
									
								} else {
									throw new VendorNotFoundException("Functionality is not assign");
								}
								
								List<CustomerUserDepartments> findDepartmentByUserId = customerUserDepartmentsRepo.findByUserId(regId);
								
								LOGGER.info("Department is >>> "+findDepartmentByUserId.size());
								if (findDepartmentByUserId.size() > 0) {
									LOGGER.info("After find The Department Details:  |________<<>>>________|"+findDepartmentByUserId);
									findDepartmentByUserId.forEach(userDepartment -> {
										if (userDepartment.getUserId() == customerRegisterRequest.getDepartment()) {
											
											LOGGER.info("in Functionality user id <4>"+regId);
											
											customerUserDepartmentsRepo.deleteById(userDepartment.getUserId());
											
											department.setDepartmentId(customerRegisterRequest.getDepartment());
											department.setUserId(regId);
											
											CustomerUserDepartments saveCustomerUserDepartments = customerUserDepartmentsRepo.save(department);
											LOGGER.info("Department final >>> "+saveCustomerUserDepartments);
										}
										else {
											customerUserDepartmentsRepo.deleteById(userDepartment.getUserId());
											
											department.setDepartmentId(customerRegisterRequest.getDepartment());
											department.setUserId(regId);
											
											CustomerUserDepartments saveCustomerUserDepartments = customerUserDepartmentsRepo.save(department);
											LOGGER.info("Department final >>> "+saveCustomerUserDepartments);
										}
									});
									
									List<CustomerDepartments> listOfDepartments = getCustomerDetails.getDepartments(regId);
									
									customerRegisterResponse.setCustomerDepartments(listOfDepartments);
									
								} else {
									throw new VendorNotFoundException("Department is not assign");
								}
																
								return customerRegisterResponse;
								
							} else {
								throw new VendorNotFoundException("Email not valid");
							}

						} else {
							throw new VendorNotFoundException("All field required");
						}
					} else {
						throw new VendorNotFoundException("You don't update another company user Details");
					}
				} else {
					throw new VendorNotFoundException("Your Requested id is not present!!");
				}
			case "ADMIN":
				
				if (findCustomerRegistrationById.isPresent()) {
					if (findCustomerRegistrationById.get().getcId() == companyProfileIdByCustomerId) {
						if (fieldValidation.isEmpty(customerRegisterRequest.getName())
								&& fieldValidation.isEmpty(customerRegisterRequest.getEmail())
								&& fieldValidation.isEmpty(customerRegisterRequest.getRole())
								&& fieldValidation.isEmpty(customerRegisterRequest.getDepartment())
								&& fieldValidation.isEmpty(customerRegisterRequest.getFuncationality())) {
							if (fieldValidation.isEmail(customerRegisterRequest.getEmail())) {
								customerRegister.setcId(companyProfileIdByCustomerId);
								customerRegister.setCreatedBy(findCustomerRegistrationById.get().getCreatedBy());
								customerRegister.setCreatedOn(findCustomerRegistrationById.get().getCreatedOn());
								customerRegister.setEmail(customerRegisterRequest.getEmail());
								customerRegister.setName(customerRegisterRequest.getName());
								customerRegister.setPassword(findCustomerRegistrationById.get().getPassword());
								customerRegister.setStatus(findCustomerRegistrationById.get().getStatus());
								customerRegister.setUserId(regId);
								customerRegister.setUsername(findCustomerRegistrationById.get().getUsername());

								CustomerRegister updateCustomer = customerRegisterRepo.save(customerRegister);
								
								customerRegisterResponse.setcId(updateCustomer.getcId());
								customerRegisterResponse.setCreatedBy(updateCustomer.getCreatedBy());
								customerRegisterResponse.setCreatedOn(updateCustomer.getCreatedOn());
								customerRegisterResponse.setEmail(updateCustomer.getEmail());
								customerRegisterResponse.setName(updateCustomer.getName());
								customerRegisterResponse.setStatus(updateCustomer.getStatus());
								customerRegisterResponse.setUpdatedBy(updateCustomer.getUpdatedBy());
								customerRegisterResponse.setUpdatedOn(updateCustomer.getUpdatedOn());

								Optional<CustomerUserRoles> findUserroleByUserId = customerUserRolesRepo
										.findByUserId(regId);
								if (findUserroleByUserId.isPresent()) {
									customerUserDepartmentsRepo.deleteById(regId);
									userRoles.setRoleId(customerRegisterRequest.getRole());
									userRoles.setUserId(regId);
									CustomerUserRoles updateCustomerUserRoles = customerUserRolesRepo.save(userRoles);
									
									Optional<RolesMaster> findRoleFromMasterTableById = rolesMasterRepo.findById(updateCustomerUserRoles.getRoleId());
									
									customerRegisterResponse.setRole(findRoleFromMasterTableById.get().getRole());

								} else {
//									throw new VendorNotFoundException("Your role is not asign");
									customerRegisterResponse.setRole("Your role is not asign");
								}

								List<CustomerUserFunctionaliti> findFunctionalityByUserId = customerUserFunctionalitiRepo.findByUserId(regId);
								if (findFunctionalityByUserId.size() > 0) {
									
									findFunctionalityByUserId.forEach(userFunctionality -> {
										if (userFunctionality.getfId() == customerRegisterRequest.getFuncationality()) {
											customerUserFunctionalitiRepo.deleteById(regId);
											functionality.setfId(customerRegisterRequest.getFuncationality());
											functionality.setUserId(regId);
											customerUserFunctionalitiRepo.save(functionality);
										}
									});
									
									List<CustomerFunctionalitiesMaster> listOfFunctionality = getCustomerDetails.getFunctionaliti(updateCustomer.getUserId());
									customerRegisterResponse.setFunctionality(listOfFunctionality);
									
								} else {
									throw new VendorNotFoundException("Functionality is not assign");
								}
								
								List<CustomerUserDepartments> findDepartmentByUserId = customerUserDepartmentsRepo.findByUserId(regId);
								if (findDepartmentByUserId.size() > 0) {
									findDepartmentByUserId.forEach(userDepartment -> {
										if (userDepartment.getUserId() == customerRegisterRequest.getDepartment()) {
											
											customerUserDepartmentsRepo.deleteById(customerRegisterRequest.getDepartment());
											
											department.setDepartmentId(customerRegisterRequest.getDepartment());
											department.setUserId(regId);
											
											CustomerUserDepartments saveCustomerUserDepartments = customerUserDepartmentsRepo.save(department);
										}
									});
									
									List<CustomerDepartments> listOfDepartments = getCustomerDetails.getDepartments(regId);
									
									customerRegisterResponse.setCustomerDepartments(listOfDepartments);
									
								} else {
									throw new VendorNotFoundException("Department is not assign");
								}
																
								return customerRegisterResponse;
								
							} else {
								throw new VendorNotFoundException("Email not valid");
							}

						} else {
							throw new VendorNotFoundException("All field required");
						}
					} else {
						throw new VendorNotFoundException("You don't update another company user Details");
					}
				} else {
					throw new VendorNotFoundException("Your Requested id is not present!!");
				}

			case "USER":
				throw new VendorNotFoundException("You don't have access to update user");
			default:
				throw new VendorNotFoundException("The role is not present");
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
