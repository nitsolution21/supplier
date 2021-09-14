package org.fintexel.supplier.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.entity.ChangePassword;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.ForgotPassword;
import org.fintexel.supplier.entity.ForgotPasswordRequestEntity;
import org.fintexel.supplier.entity.LoginLog;
import org.fintexel.supplier.entity.LoginResponce;
import org.fintexel.supplier.entity.RecoverPassword;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableForm;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.flowable.FlowableContainer;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.ForgotPasswordRepo;
import org.fintexel.supplier.repository.LoginLogRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableFormRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableRegistrationRepo;
import org.fintexel.supplier.services.CustomerDetailsServices;
import org.fintexel.supplier.services.VendorDetailsService;
import org.fintexel.supplier.validation.FieldValidation;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
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
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendorLoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VendorLoginController.class);

	@Autowired
	private SupDetailsRepo supDetailsRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;

	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private CustomerDetailsServices customerDetailsServices;

	@Autowired
	private VendorRegisterRepo vendorRegisterRepo;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private FieldValidation fieldValidation;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private VendorRegisterRepo registerRepo;

	private VendorLogin loginDetails;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private FlowableRegistrationRepo flowableRegistrationRepo;

	@Autowired
	private ForgotPasswordRepo forgotPasswordRepo;
	
	@Autowired
	private LoginLogRepo loginLogRepo;

	@Autowired
	private RestTemplate restTemplate;

	private String taskId;

	@Autowired
	private FlowableFormRepo flowableFormRepo;

	@PostMapping("/login")
	public ResponseEntity<?> venderLogin(@RequestBody VendorLogin vendorLogin) {
		LOGGER.info("Inside - VendorLoginController.venderLogin()");
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(vendorLogin.getUsername(), vendorLogin.getPassword()));
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

		UserDetails vendor = this.vendorDetailsService.loadUserByUsername(vendorLogin.getUsername());

		String token = jwtUtil.generateToken(vendor);

	Optional<VendorRegister> findByUsername = vendorRegisterRepo.findByUsername(vendor.getUsername());
		
		DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime lastLoginNow = LocalDateTime.now();
		boolean lastLogin = false;
		SupDetails supDetails = new SupDetails();
		Date lastLoginTime = new Date();
		try {
			lastLoginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(lastLoginNow.format(lastLogingFormat));
			supDetails = supDetailsRepo.findByRegisterId(findByUsername.get().getRegisterId()).get(0);
			
			if(fieldValidation.isEmpty(supDetails.getLastlogin())) {
				lastLogin =true;
			}else {
				supDetails.setLastlogin(lastLoginTime);
				supDetailsRepo.save(supDetails);
			}
		}catch(Exception e) {
			
		}
		
		Optional<LoginLog> findByRegisterId = loginLogRepo.findByRegisterId(findByUsername.get().getRegisterId());
		if(findByRegisterId.isPresent()) {
			LoginLog loginLog = findByRegisterId.get();
			loginLog.setLoginTime(lastLoginTime);
			int parseInt = Integer.parseInt(loginLog.getLoginAttempt())+1;
			loginLog.setLoginAttempt(parseInt+"");
			loginLogRepo.save(loginLog);
			lastLogin=true;
		}else {
			LoginLog loginLog = new LoginLog();
			loginLog.setRegisterId(findByUsername.get().getRegisterId());
			loginLog.setLoginTime(lastLoginTime);
			loginLog.setLoginAttempt("1");
			loginLogRepo.save(loginLog);
			lastLogin=false;
		}
		
		
		return ResponseEntity.ok(new LoginResponce(vendor.getUsername(), token, findByUsername.get().getRegisterId(),
				findByUsername.get().getSupplierCompName(), lastLogin));
	}

	@GetMapping("/vendorLogin")
	public List<VendorLogin> getLoginVendor() {
		try {
			List<VendorLogin> vendorLogin = new ArrayList<VendorLogin>();
			List<VendorRegister> allVendor = vendorRegisterRepo.findAll();
			allVendor.forEach(vendor -> {
				if (vendor.getUsername() != null && vendor.getPassword() != null) {
					loginDetails = new VendorLogin();
					loginDetails.setUsername(vendor.getUsername());
					loginDetails.setPassword(vendor.getPassword());
					vendorLogin.add(loginDetails);
				}
			});
			return vendorLogin;
		} catch (Exception e) {
			throw new VendorNotFoundException("Nothing to display");
		}
	}

	@GetMapping("/vendorLogin/{id}")
	public VendorLogin getSingleUsernamePassword(@PathVariable long id) {

		loginDetails = new VendorLogin();
		Optional<VendorRegister> findById = vendorRegisterRepo.findById(id);

		if (findById.get().getUsername() != null && findById.get().getPassword() != null) {
			loginDetails.setUsername(findById.get().getUsername());
			loginDetails.setPassword(findById.get().getPassword());
			return loginDetails;
		} else {
			throw new VendorNotFoundException("Username Password Not found!!");
		}
	}

	@PutMapping("/vendorLogin/{id}")
	public VendorLogin updateVendoUsernameAndPassword(@PathVariable long id, @RequestBody VendorLogin vendorLogin) {
		try {
			Optional<VendorRegister> findById = vendorRegisterRepo.findById(id);
			loginDetails = new VendorLogin();
			VendorRegister vendorRegister = findById.get();
			if (findById.isPresent()) {
				if (fieldValidation.isEmpty(vendorLogin.getUsername())
						&& fieldValidation.isEmpty(vendorLogin.getPassword())) {
					String encode = bCryptPasswordEncoder.encode(vendorLogin.getPassword());
					vendorRegister.setUsername(vendorLogin.getUsername());
					vendorRegister.setPassword(encode);
					vendorRegister.setRegisterId(id);

					VendorRegister saveVendor = vendorRegisterRepo.save(vendorRegister);
					loginDetails.setUsername(saveVendor.getUsername());
					loginDetails.setPassword(vendorLogin.getPassword());
					return loginDetails;
				} else {
					throw new VendorNotFoundException("User id password can't be null");
				}
			} else {
				throw new VendorNotFoundException("Vendor not avalable");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/changePassword")
	public CustomeResponseEntity changePassword(@RequestBody ChangePassword changePassword,
			@RequestHeader(name = "Authorization") String token) {
		try {
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7);
				String userName = jwtUtil.extractUsername(jwtToken);
				if (fieldValidation.isEmpty(changePassword.getNewPassword())
						&& fieldValidation.isEmpty(changePassword.getOldPassword())
						&& fieldValidation.isEmpty(changePassword.getUsername())) {
					if (userName.equals(changePassword.getUsername())) {
						Optional<VendorRegister> findByUsername = registerRepo.findByUsername(userName);
						if (!findByUsername.isPresent()) {
							Optional<CustomerRegister> findCustomerByUsername = customerRegisterRepo.findByUsername(userName);
							if (findCustomerByUsername.isPresent()) {
								if (passwordEncoder.matches(changePassword.getOldPassword(), findCustomerByUsername.get().getPassword())) {
									CustomerRegister customerRegister = new CustomerRegister();
									customerRegister.setcId(findCustomerByUsername.get().getcId());
									customerRegister.setCreatedBy(findCustomerByUsername.get().getCreatedBy());
									customerRegister.setCreatedOn(findCustomerByUsername.get().getCreatedOn());
									customerRegister.setEmail(findCustomerByUsername.get().getEmail());
									customerRegister.setName(findCustomerByUsername.get().getName());
									customerRegister.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
									customerRegister.setStatus(findCustomerByUsername.get().getStatus());
									customerRegister.setUserId(findCustomerByUsername.get().getUserId());
									customerRegister.setUsername(findCustomerByUsername.get().getUsername());
									
									customerRegisterRepo.save(customerRegister);
									
									return new CustomeResponseEntity("SUCCESS","Password changed!!!");
								}
								else {
									throw new VendorNotFoundException("Old password not match");
								}
							} else {
								throw new VendorNotFoundException("Vendor not found");
							}
							//throw new VendorNotFoundException("Vendor not found");
						} else {
							if (passwordEncoder.matches(changePassword.getOldPassword(),
									findByUsername.get().getPassword())) {
								VendorRegister register = new VendorRegister();
								register.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
								register.setRegisterId(findByUsername.get().getRegisterId());
								register.setEmail(findByUsername.get().getEmail());
								register.setStatus(findByUsername.get().getStatus());
								register.setSupplierCompName(findByUsername.get().getSupplierCompName());
								register.setUsername(changePassword.getUsername());
								register.setProcessId(findByUsername.get().getProcessId());
								register.setTaskId(findByUsername.get().getTaskId());
								VendorRegister save = registerRepo.save(register);
								save.setPassword(changePassword.getNewPassword());
								
								return new CustomeResponseEntity("SUCCESS","Password changed!!!");

							} else {
								throw new VendorNotFoundException("Old password not match");
							}
						}
					} else {
						throw new VendorNotFoundException("You don't have permission to change the password");
					}
				} else {
					throw new VendorNotFoundException("Username, Old password, New password all field required");
				}
			} else {
				throw new VendorNotFoundException("Token Not Valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PostMapping("/forgotPassword")
	public CustomeResponseEntity forgotPassword(@RequestBody ForgotPasswordRequestEntity forgotPasswordRequestEntity) {
		try {
			if (fieldValidation.isEmpty(forgotPasswordRequestEntity.getEmail())
					&& fieldValidation.isEmpty(forgotPasswordRequestEntity.getUrl())) {
				if (fieldValidation.isEmail(forgotPasswordRequestEntity.getEmail())) {
					Optional<VendorRegister> findByEmail = registerRepo
							.findByEmail(forgotPasswordRequestEntity.getEmail());
					if (!findByEmail.isPresent()) {
						Optional<CustomerRegister> findCustomerByEmail = customerRegisterRepo.findByEmail(forgotPasswordRequestEntity.getEmail());
						if (findCustomerByEmail.isPresent()) {
							
							/*--------------------------------------Start Customer------------------------------------------------*/
							
							
							
							
							String token = java.util.UUID.randomUUID().toString();
//							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//							LocalDateTime now = LocalDateTime.now();
//							
//							SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
							Date date = new Date();
							try {
								Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo
										.findByAuthorAndTitle("forgot_supplier_pwd");

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
								Map<String, Object> mapp = new HashMap<>();
								map.put("processInstanceId", (String) jsonObject.get("id"));

								// LOGGER.info("Task ID for forgot password - "+(String) jsonObject.get("id"));

								HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
								ResponseEntity<String> exchange = restTemplate.exchange(
										"http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST, request,
										String.class, 1);
								JSONObject jsonObject1 = new JSONObject(exchange.getBody());

								JSONArray array = new JSONArray(jsonObject1.get("data").toString());
								JSONArray arrayy = new JSONArray();

								JSONObject forgotPwdEmail = new JSONObject();
								forgotPwdEmail.put("name", "supplierforgotemailid");
								forgotPwdEmail.put("scope", "local");
								forgotPwdEmail.put("type", "string");
								forgotPwdEmail.put("value", forgotPasswordRequestEntity.getEmail());
								arrayy.put(forgotPwdEmail);

								JSONObject forgotPwdLink = new JSONObject();
								forgotPwdLink.put("name", "forgotpwdlink");
								forgotPwdLink.put("scope", "local");
								forgotPwdLink.put("type", "string");
								forgotPwdLink.put("value", forgotPasswordRequestEntity.getUrl() + "/" + token);
								arrayy.put(forgotPwdLink);

								HttpEntity<String> entityy = new HttpEntity<String>(arrayy.toString(), headers);
								ResponseEntity<String> response2 = restTemplate.exchange(
										"http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/"
												+ array.getJSONObject(0).get("id") + "/variables",
										HttpMethod.POST, entityy, String.class, 1);

								taskId = (String) array.getJSONObject(0).get("id");

								LOGGER.info("Taks ID for forgot password " + array.getJSONObject(0).get("id"));

							} catch (Exception e) {
							}

//							AUTO CLAIM  START

							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
							MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
							map.add("j_username", "indexer");
							map.add("j_password", "123");
							map.add("submit", "Login");
							map.add("_spring_security_remember_me", "true");
							HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
									map, headers);
							ResponseEntity<String> response = restTemplate
									.postForEntity("http://65.2.162.230:8080/DB-idm/app/authentication", request, String.class);
							JSONObject claimcookie = new JSONObject(response.getHeaders());
							String replace = claimcookie.get("Set-Cookie").toString().replace("[", "").replace("]", "")
									.replace("\"", "");
							HttpHeaders requestHeaders = new HttpHeaders();
							requestHeaders.add("Cookie", replace);
							HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
							ResponseEntity rssResponse = restTemplate.exchange(
									"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskId + "/action/claim",
									HttpMethod.PUT, requestEntity, String.class);

//							AUTO CLAIM  END

//							AUTO COMPLEATE START

							HttpHeaders header = new HttpHeaders();
							header.add("Cookie", replace);
							header.setContentType(MediaType.APPLICATION_JSON);
							header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
							JSONObject autoCompleate = new JSONObject();
							autoCompleate.put("taskIdActual", taskId);
							autoCompleate.put("supplierforgotemailid", forgotPasswordRequestEntity.getEmail());
							autoCompleate.put("forgotpwdlink", forgotPasswordRequestEntity.getUrl() + "/" + token);
							JSONObject mapp = new JSONObject();
							Optional<FlowableForm> findByFromId = flowableFormRepo.findByFromId("frmforgotpwdsupplier");
							mapp.put("formId", findByFromId.get().getId());
							mapp.put("values", autoCompleate);
//							System.out.println("Body  " + mapp);
//							System.out.println("headers  " + header);
							HttpEntity<Map<String, Object>> entity = new HttpEntity(mapp.toString(), header);
							ResponseEntity rssResponsee = restTemplate.exchange(
									"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskId, HttpMethod.POST, entity,
									String.class);
//							System.out.print("Result  "+rssResponsee.getHeaders());
//							System.out.print("Result  " + rssResponsee.getHeaders());

//							AUTO COMPLEATE END

							ForgotPassword forgotPassword = new ForgotPassword();
							forgotPassword.setEmail(forgotPasswordRequestEntity.getEmail());
							forgotPassword.setToken(token);
							forgotPassword.setCreatedOn(date);
							forgotPassword.setStatus("MAILSEND");
							ForgotPassword save = forgotPasswordRepo.save(forgotPassword);
							LOGGER.info("after add data "+save);
							if (save.equals(null)) {
								throw new VendorNotFoundException("Not save in database");
							}
							else {
								return new CustomeResponseEntity("SUCCESS","Please check your mail");
							}
							
							
							
							
							
							/*-----------------------------------------End CUStomer------------------------------------------------*/
							
							
							
							
							
						} else {
							throw new VendorNotFoundException("We could't found your email id");
						}
						
					}
					else {
						String token = java.util.UUID.randomUUID().toString();
//						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//						LocalDateTime now = LocalDateTime.now();
//						
//						SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
						Date date = new Date();
						try {
							Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo
									.findByAuthorAndTitle("forgot_supplier_pwd");

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
							Map<String, Object> mapp = new HashMap<>();
							map.put("processInstanceId", (String) jsonObject.get("id"));

							// LOGGER.info("Task ID for forgot password - "+(String) jsonObject.get("id"));

							HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
							ResponseEntity<String> exchange = restTemplate.exchange(
									"http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST, request,
									String.class, 1);
							JSONObject jsonObject1 = new JSONObject(exchange.getBody());

							JSONArray array = new JSONArray(jsonObject1.get("data").toString());
							JSONArray arrayy = new JSONArray();

							JSONObject forgotPwdEmail = new JSONObject();
							forgotPwdEmail.put("name", "supplierforgotemailid");
							forgotPwdEmail.put("scope", "local");
							forgotPwdEmail.put("type", "string");
							forgotPwdEmail.put("value", forgotPasswordRequestEntity.getEmail());
							arrayy.put(forgotPwdEmail);

							JSONObject forgotPwdLink = new JSONObject();
							forgotPwdLink.put("name", "forgotpwdlink");
							forgotPwdLink.put("scope", "local");
							forgotPwdLink.put("type", "string");
							forgotPwdLink.put("value", forgotPasswordRequestEntity.getUrl() + "/" + token);
							arrayy.put(forgotPwdLink);

							HttpEntity<String> entityy = new HttpEntity<String>(arrayy.toString(), headers);
							ResponseEntity<String> response2 = restTemplate.exchange(
									"http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/"
											+ array.getJSONObject(0).get("id") + "/variables",
									HttpMethod.POST, entityy, String.class, 1);

							taskId = (String) array.getJSONObject(0).get("id");

							LOGGER.info("Taks ID for forgot password " + array.getJSONObject(0).get("id"));

						} catch (Exception e) {
						}

//						AUTO CLAIM  START

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
						MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
						map.add("j_username", "indexer");
						map.add("j_password", "123");
						map.add("submit", "Login");
						map.add("_spring_security_remember_me", "true");
						HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
								map, headers);
						ResponseEntity<String> response = restTemplate
								.postForEntity("http://65.2.162.230:8080/DB-idm/app/authentication", request, String.class);
						JSONObject claimcookie = new JSONObject(response.getHeaders());
						String replace = claimcookie.get("Set-Cookie").toString().replace("[", "").replace("]", "")
								.replace("\"", "");
						HttpHeaders requestHeaders = new HttpHeaders();
						requestHeaders.add("Cookie", replace);
						HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
						ResponseEntity rssResponse = restTemplate.exchange(
								"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskId + "/action/claim",
								HttpMethod.PUT, requestEntity, String.class);

//						AUTO CLAIM  END

//						AUTO COMPLEATE START

						HttpHeaders header = new HttpHeaders();
						header.add("Cookie", replace);
						header.setContentType(MediaType.APPLICATION_JSON);
						header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
						JSONObject autoCompleate = new JSONObject();
						autoCompleate.put("taskIdActual", taskId);
						autoCompleate.put("supplierforgotemailid", forgotPasswordRequestEntity.getEmail());
						autoCompleate.put("forgotpwdlink", forgotPasswordRequestEntity.getUrl() + "/" + token);
						JSONObject mapp = new JSONObject();
						Optional<FlowableForm> findByFromId = flowableFormRepo.findByFromId("frmforgotpwdsupplier");
						mapp.put("formId", findByFromId.get().getId());
						mapp.put("values", autoCompleate);
//						System.out.println("Body  " + mapp);
//						System.out.println("headers  " + header);
						HttpEntity<Map<String, Object>> entity = new HttpEntity(mapp.toString(), header);
						ResponseEntity rssResponsee = restTemplate.exchange(
								"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskId, HttpMethod.POST, entity,
								String.class);
//						System.out.print("Result  "+rssResponsee.getHeaders());
//						System.out.print("Result  " + rssResponsee.getHeaders());

//						AUTO COMPLEATE END

						ForgotPassword forgotPassword = new ForgotPassword();
						forgotPassword.setEmail(forgotPasswordRequestEntity.getEmail());
						forgotPassword.setToken(token);
						forgotPassword.setCreatedOn(date);
						forgotPassword.setStatus("MAILSEND");
						ForgotPassword save = forgotPasswordRepo.save(forgotPassword);
						LOGGER.info("after add data "+save);
						if (save.equals(null)) {
							throw new VendorNotFoundException("Not save in database");
						}
						else {
//							return "Please check your mail";
							return new CustomeResponseEntity("SUCCESS","Please check your mail");
						}
					}
				} else {
					throw new VendorNotFoundException("Provide correct email");
				}
			} else {
				throw new VendorNotFoundException("Email id and URL required");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/recoverPassword")
	public CustomeResponseEntity recoverPassword(@RequestBody RecoverPassword recoverPassword) {
		try {
			if (fieldValidation.isEmpty(recoverPassword.getNewPasswoed())
					&& fieldValidation.isEmpty(recoverPassword.getToken())) {
				Optional<ForgotPassword> findByToken = forgotPasswordRepo.findByToken(recoverPassword.getToken());
				if (findByToken.isPresent()) {
					if (!findByToken.get().getStatus().equals("EXPIRE")) {
						Date date = new Date();
						if (date.getYear() == findByToken.get().getCreatedOn().getYear()
								&& date.getMonth() == findByToken.get().getCreatedOn().getMonth()
								&& date.getDate() == findByToken.get().getCreatedOn().getDate()
								&& date.getHours() == findByToken.get().getCreatedOn().getHours()) {
							int expireDate = date.getMinutes() - findByToken.get().getCreatedOn().getMinutes();
							LOGGER.info("cuttent date "+date);
							LOGGER.info("DB date "+findByToken.get().getCreatedOn());
							if (expireDate <= 30) {
								Optional<VendorRegister> findByEmail = registerRepo
										.findByEmail(findByToken.get().getEmail());
								if (findByEmail.isPresent()) {
									VendorRegister register = new VendorRegister();
									register.setPassword(passwordEncoder.encode(recoverPassword.getNewPasswoed()));
									register.setRegisterId(findByEmail.get().getRegisterId());
									register.setEmail(findByEmail.get().getEmail());
									register.setStatus(findByEmail.get().getStatus());
									register.setSupplierCompName(findByEmail.get().getSupplierCompName());
									register.setUsername(findByEmail.get().getUsername());
									register.setProcessId(findByEmail.get().getProcessId());
									register.setTaskId(findByEmail.get().getTaskId());
									VendorRegister save = registerRepo.save(register);
									// save.setPassword(recoverPassword.getNewPasswoed());
									ForgotPassword forgotPassword = new ForgotPassword();
									forgotPassword.setCreatedOn(findByToken.get().getCreatedOn());
									forgotPassword.setEmail(findByToken.get().getEmail());
									forgotPassword.setToken(recoverPassword.getToken());
									forgotPassword.setId(findByToken.get().getId());
									forgotPassword.setStatus("EXPIRE");
									forgotPasswordRepo.save(forgotPassword);
//									return "password has been changed!! please remember your password";
									return new CustomeResponseEntity("SUCCESS","password has been changed!! please remember your password");

								} else {
									Optional<CustomerRegister> findCustomerByEmail = customerRegisterRepo.findByEmail(findByToken.get().getEmail());
									if (findCustomerByEmail.isPresent()) {
										CustomerRegister customerRegister = new CustomerRegister();
										customerRegister.setcId(findCustomerByEmail.get().getcId());
										customerRegister.setEmail(findCustomerByEmail.get().getEmail());
										customerRegister.setName(findCustomerByEmail.get().getName());
										customerRegister.setPassword(passwordEncoder.encode(recoverPassword.getNewPasswoed()));
										customerRegister.setStatus(findCustomerByEmail.get().getStatus());
										customerRegister.setUserId(findCustomerByEmail.get().getUserId());
										customerRegister.setUsername(findCustomerByEmail.get().getUsername());
										
										customerRegisterRepo.save(customerRegister);
										
										ForgotPassword forgotPassword = new ForgotPassword();
										forgotPassword.setCreatedOn(findByToken.get().getCreatedOn());
										forgotPassword.setEmail(findByToken.get().getEmail());
										forgotPassword.setToken(recoverPassword.getToken());
										forgotPassword.setId(findByToken.get().getId());
										forgotPassword.setStatus("EXPIRE");
										forgotPasswordRepo.save(forgotPassword);
										
										return new CustomeResponseEntity("SUCCESS","password has been changed!! please remember your password");
										
									} else {
										throw new VendorNotFoundException("Can't Find your emai");
									}
									//throw new VendorNotFoundException("Can't Find your emai id");
								}
							} else {
								throw new VendorNotFoundException("Time expire");
							}
						} else {
							throw new VendorNotFoundException("Time expire");
						}
					} else {
						throw new VendorNotFoundException("Token expire");
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Password and token required");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

}
