package org.fintexel.supplier.helper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fintexel.supplier.entity.ForgotPasswordRequestEntity;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class LoginUserDetails {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetails.class);
	
	@Autowired
	private VendorRegisterRepo registerRepo;
	
	@Autowired
	private SupDetailsRepo detailsRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private String jwtToken;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	FlowableRegistrationRepo flowableRegistrationRepo;
	
	private String taskId;
	
	public String getLoginSupplierCode(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);
			String userName = jwtUtil.extractUsername(jwtToken);
			Optional<VendorRegister> findByUsername = registerRepo.findByUsername(userName);
			if (!findByUsername.isPresent()) {
				throw new VendorNotFoundException("Vendor not found");
			}
			else {
				List<SupDetails> findByRegisterId = detailsRepo.findByRegisterId(findByUsername.get().getRegisterId());
				if (findByRegisterId.size() > 0) {
					return findByRegisterId.get(0).getSupplierCode();
				} else {
					throw new VendorNotFoundException("Vendor Details Not found");
				}
			}
		}
		else {
			throw new VendorNotFoundException("Token Not Valid");
		}
	}
	
	/*public String forgotPassword(ForgotPasswordRequestEntity forgotPasswordRequestEntity) {
		String token = java.util.UUID.randomUUID().toString();
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//		LocalDateTime now = LocalDateTime.now();
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
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

//		AUTO CLAIM  START

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

//		AUTO CLAIM  END

//		AUTO COMPLEATE START

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
//		System.out.println("Body  " + mapp);
//		System.out.println("headers  " + header);
		HttpEntity<Map<String, Object>> entity = new HttpEntity(mapp.toString(), header);
		ResponseEntity rssResponsee = restTemplate.exchange(
				"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskId, HttpMethod.POST, entity,
				String.class);
//		System.out.print("Result  "+rssResponsee.getHeaders());
//		System.out.print("Result  " + rssResponsee.getHeaders());

//		AUTO COMPLEATE END

		ForgotPassword forgotPassword = new ForgotPassword();
		forgotPassword.setEmail(forgotPasswordRequestEntity.getEmail());
		forgotPassword.setToken(token);
		forgotPassword.setCreatedOn(date);
		forgotPassword.setStatus("MAILSEND");
		ForgotPassword save = forgotPasswordRepo.save(forgotPassword);
		LOGGER.info("after add data "+save);
		if (save.equals(null)) {
			return "Not save in database";
		}
		else {
			return "Please check your mail";
		}
	}*/
}
