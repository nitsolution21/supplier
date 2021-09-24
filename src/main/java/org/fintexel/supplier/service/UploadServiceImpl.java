package org.fintexel.supplier.service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fintexel.supplier.customerentity.ContractAndAddressType;
import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerFunctionalitiesMaster;
import org.fintexel.supplier.customerentity.GeoEntity;
import org.fintexel.supplier.customerentity.RolesMaster;
import org.fintexel.supplier.customerrepository.ContractAndAddressTypeRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerFunctionalitiesMasterRepo;
import org.fintexel.supplier.customerrepository.GeoRepo;
import org.fintexel.supplier.customerrepository.RolesMasterRepo;
import org.fintexel.supplier.entity.BulkUploadSuccessError;
import org.fintexel.supplier.entity.MasterCurrencyType;
import org.fintexel.supplier.entity.MasterRegType;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupContract;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.SupRequest;
import org.fintexel.supplier.entity.UploadEntity;
import org.fintexel.supplier.entity.UploadErrorEntity;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.repository.MasterCurrencyTypeRepo;
import org.fintexel.supplier.repository.MasterRegTypeRepo;
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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Supplier;

@Service
public class UploadServiceImpl implements UploadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

	@Autowired
	private FlowableRegistrationRepo flowableRegistrationRepo;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	private SupContractRepo supContractRepo;

	@Autowired
	private SupBankRepo supBankRepo;

	@Autowired
	private SupDepartmentRepo supDepartmentRepo;

	@Autowired
	private SupDetails supDetails;

	@Autowired
	private GetCustomerDetails getCustomerDetails;

	@Autowired
	private SupAddress supAddress;

	@Autowired
	private SupAddressRepo supAddressRepo;

	@Autowired
	private SupDetailsRepo supDetailsRepo;

	@Autowired
	private SupRequestRepo supRequestRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private VendorRegisterRepo vendorRepo;

	@Autowired
	private UploadErrorEntity UploadErrorEntity;

	@Autowired
	UploadService uploadService;

	@Autowired
	private UploadEntity uploadEntity;

	@Autowired
	private FieldValidation fieldValidation;

	@Autowired
	private MasterCurrencyTypeRepo masterCurrencyTypeRepo;

	@Autowired
	private MasterRegTypeRepo masterRegTypeRepo;

	@Autowired
	private CustomerDepartmentsRepo customerDepartmentsRepo;

	@Autowired
	private RolesMasterRepo rolesMasterRepo;

	@Autowired
	private CustomerFunctionalitiesMasterRepo customerFunctionalitiesMasterRepo;

	@Autowired
	private GeoRepo geoRepo;

	@Autowired
	private ContractAndAddressTypeRepo contractAndAddressTypeRepo;
	

//	Map<String, String> errorMap = new HashMap<>();
	List<BulkUploadSuccessError> errorMap = new ArrayList<BulkUploadSuccessError>();

	@Override
	public List<BulkUploadSuccessError> upload(MultipartFile uploadFile) {

		LOGGER.info("Inside  - UploadServiceImpl.upload()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				uploadEntity.setEmail(rows.getCell(0).toString());
				/*
				 * * ------------------- BULK REGISTRATION STAR *
				 * -----------------------------------
				 */
				try {

					uploadEntity.setSupplierCompName(rows.getCell(1).toString());
					uploadService.validateEachVendor(uploadEntity);

				} catch (Exception e) {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
//					errorMap.put(uploadEntity.getEmail(), "In Upload(reg)  " + e.getMessage());

				}

				/*
				 * ------------------- BULK REGISTRATION END -----------------------------------
				 */

				/* ------------------- BULK DETAILS START ----------------------------------- */

				try {

					uploadEntity.setRegistrationType(rows.getCell(2).toString());
//					uploadEntity.setCostCenter(rows.getCell(3).toString());
//					uploadEntity.setRemarks(rows.getCell(4).toString());
					uploadEntity.setRegistrationNo(rows.getCell(5).toString());

					try {

						uploadEntity.setLastlogin(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rows.getCell(6).toString()));

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
//						errorMap.put(uploadEntity.getEmail(), "In Upload(details)  " + e.getMessage());

					} 

					uploadService.validateSupplierDetails(uploadEntity, "UPLOAD");

				} catch (Exception e) {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));

//					errorMap.put(uploadEntity.getEmail(), "In Upload(details)  " + e.getMessage());

				}

				/* ------------------- BULK DETAILS END ----------------------------------- */

				/* ------------------- BULK ADDRESS START ----------------------------------- */

				try {

					uploadEntity.setAddressType(rows.getCell(7).toString());
					uploadEntity.setAddress1(rows.getCell(8).toString());
					uploadEntity.setPostalCode((int) Float.parseFloat(rows.getCell(10).toString()));
					uploadEntity.setCity(rows.getCell(11).toString());
					uploadEntity.setCountry(rows.getCell(12).toString());
					uploadEntity.setRegion(rows.getCell(13).toString());
					uploadEntity.setAddressProof(rows.getCell(14).toString());
					uploadEntity.setAddressProofPath(rows.getCell(15).toString());

					uploadService.validateSupplierAddress(uploadEntity, "UPLOAD");

				} catch (Exception e) {

					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));

				}

				/* ------------------- BULK ADDRESS END ----------------------------------- */

				/*
				 * ------------------- BULK BANK DETAILS START
				 * -----------------------------------
				 */
				try {

					uploadEntity.setAccountHolder(rows.getCell(16).toString());
					uploadEntity.setBankAccountNo(rows.getCell(17).toString());
					try {
						uploadEntity.setBankBic(rows.getCell(18).toString());
					} catch (Exception e) {

					}
					uploadEntity.setBankBranch(rows.getCell(19).toString());
					uploadEntity.setBankEvidence(rows.getCell(20).toString());
					uploadEntity.setBankName(rows.getCell(21).toString());
					try {
						uploadEntity.setChequeNo(rows.getCell(22).toString());
					} catch (Exception e) {

					}

					uploadEntity.setCountry(rows.getCell(23).toString());
					uploadEntity.setCurrency(rows.getCell(24).toString());
					uploadEntity.setEvidencePath(rows.getCell(25).toString());
					uploadEntity.setIfscCode(rows.getCell(26).toString());
					try {
						uploadEntity.setSwiftCode(rows.getCell(27).toString());
					} catch (Exception e) {

					}
					try {
						uploadEntity.setTransilRoutingNo(rows.getCell(28).toString());
					} catch (Exception e) {

					}

					uploadService.validateSupplierBank(uploadEntity, "UPLOAD");

				} catch (Exception e) {

					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));

				}

				/*
				 * ------------------- BULK BANK DETAILS END -----------------------------------
				 */

				/*
				 * ------------------- BULK BANK DEPT START -----------------------------------
				 */

				try {

					uploadEntity.setDepartmentName(rows.getCell(29).toString());
					uploadEntity.setSupplierContact1(rows.getCell(30).toString());

					uploadEntity.setSupEmail(rows.getCell(32).toString());
					uploadEntity.setPhoneno(rows.getCell(33).toString());

					uploadService.validateSupplierDepartment(uploadEntity, "UPLOAD");

				} catch (Exception e) {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
				}

				/* ------------------- BULK BANK DEPT END ----------------------------------- */

				/* ------------------- BULK CONTACT START ----------------------------------- */

				try {

					uploadEntity.setContractType(rows.getCell(35).toString());

					uploadEntity.setContractTerms((int) Float.parseFloat(rows.getCell(36).toString()));
					uploadEntity.setContractProof(rows.getCell(37).toString());
					uploadEntity.setContractLocation(rows.getCell(38).toString());

					uploadService.validateSupplierContact(uploadEntity, "UPLOAD");

					/* ------------------- BULK CONTACT END ----------------------------------- */

				} catch (Exception e) {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
				}
			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Email is null"));
			}

		}

		return errorMap;
	}

	@Override
	public void bulkRegister(String email, String companyname) {
		String taskID1_ = "", taskID2_ = "", processInstID_ = "";
		try {

			VendorRegister filterVendorReg = new VendorRegister();
			filterVendorReg.setEmail(email);
			filterVendorReg.setSupplierCompName(companyname);
			filterVendorReg.setStatus("1");
			List<VendorRegister> findAll = vendorRepo.findAll();
			for (VendorRegister find : findAll) {
				if (find.getEmail().equals(email)) {
					throw new VendorNotFoundException("Email already exist - " + email);
				}
				if (find.getSupplierCompName().toLowerCase()
						.equals(filterVendorReg.getSupplierCompName().toLowerCase())) {
					throw new VendorNotFoundException("Company already exist");
				}

			}

			filterVendorReg.setUsername(email);
			String rowPassword = java.util.UUID.randomUUID().toString();
			filterVendorReg.setPassword(passwordEncoder.encode(rowPassword));

			VendorRegister saveRegisterObj = this.vendorRepo.save(filterVendorReg);
			System.out.println("Save Object  " + saveRegisterObj.toString());

			filterVendorReg.setRegisterId(saveRegisterObj.getRegisterId());

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
					"http://65.2.162.230:8080/flowable-rest/service/runtime/process-instances", pDEntity, String.class);

			/*
			 * ============================== Query Task 1
			 * ================================================
			 */

			Map<String, Object> queryMap = new HashMap<>();
			JSONObject jsonObject = new JSONObject(response.getBody());
			processInstID_ = (String) jsonObject.get("id");
			queryMap.put("processInstanceId", processInstID_);

			filterVendorReg.setProcessId(processInstID_);
//			LOGGER.info("ProcessInstanceID : " + processInstID_);

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

			JSONObject suppliername = new JSONObject();
			suppliername.put("name", "suppliername");
			suppliername.put("scope", "local");
			suppliername.put("type", "string");
			suppliername.put("value", filterVendorReg.getSupplierCompName());
			formReqBody.put(suppliername);

			JSONObject supplieremail = new JSONObject();
			supplieremail.put("name", "supplieremail");
			supplieremail.put("scope", "local");
			supplieremail.put("type", "string");
			supplieremail.put("value", filterVendorReg.getEmail());
			formReqBody.put(supplieremail);

			JSONObject username = new JSONObject();
			username.put("name", "username");
			username.put("scope", "local");
			username.put("type", "string");
			username.put("value", filterVendorReg.getEmail());
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

			HttpEntity<MultiValueMap<String, String>> loginReq = new HttpEntity<MultiValueMap<String, String>>(loginMap,
					loginHeader);
			ResponseEntity<String> loginResponse = restTemplate
					.postForEntity("http://65.2.162.230:8080/DB-idm/app/authentication", loginReq, String.class);
			JSONObject cookieJO = new JSONObject(loginResponse.getHeaders());
			String coockie_ = cookieJO.get("Set-Cookie").toString().replace("[", "").replace("]", "").replace("\"", "");

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

			HttpEntity<String> autoCompeleteEntity = new HttpEntity<String>(autoCompleate_.toString(),
					autoCompleteHeader);
			ResponseEntity autoCompleteResponse = restTemplate.exchange(
					"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID1_, HttpMethod.POST,
					autoCompeleteEntity, String.class);

			/*
			 * ----------- QUERY TO FETCH TASKID_2
			 * -------------------------------------------------------
			 */

			queryRequest_1 = restTemplate.exchange("http://65.2.162.230:8080/flowable-rest/service/query/tasks",
					HttpMethod.POST, baseAuthEntity, String.class, 1);
			taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());

			taskID2_ = (String) taskJA.getJSONObject(0).get("id");

			/*
			 * ----------- AUTO CLAIM FOR REGISTRATION APPROVAL
			 * ----------------------------------------
			 */

			HttpHeaders autoCliamRegApprovalHeader = new HttpHeaders();
			autoCliamRegApprovalHeader.add("Cookie", coockie_);
			HttpEntity autoCliamRegApprovalEntity = new HttpEntity(null, autoCliamRegApprovalHeader);
			ResponseEntity autoCliamRegApprovalResponse = restTemplate.exchange(
					"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID2_ + "/action/claim", HttpMethod.PUT,
					autoCliamRegApprovalEntity, String.class);

			/*
			 * ----------- AUTO COMPLEATE FOR REGISTRATION APPROVAL
			 * ----------------------------------------
			 */

			HttpHeaders autoCompleteRegApprovalHeader = new HttpHeaders();
			autoCompleteRegApprovalHeader.add("Cookie", coockie_);
			autoCompleteRegApprovalHeader.setContentType(MediaType.APPLICATION_JSON);
			autoCompleteRegApprovalHeader.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			String approveSupplier = "Yes";
			String approverRemarksSupRegistration = "";
			JSONObject autoCompleteRegApproval = new JSONObject();
			autoCompleteRegApproval.put("taskIdActual", taskID2_);
			autoCompleteRegApproval.put("suppliername", filterVendorReg.getSupplierCompName());
			autoCompleteRegApproval.put("supplieremail", filterVendorReg.getEmail());
			autoCompleteRegApproval.put("approvesupplier", approveSupplier);
			autoCompleteRegApproval.put("approverremarkssupregistration", approverRemarksSupRegistration);

			JSONObject autoCompleteRegApprovalBody = new JSONObject();
			autoCompleteRegApprovalBody.put("formId", "56d9e9ef-ed45-11eb-ba6c-0a5bf303a9fe");
			autoCompleteRegApprovalBody.put("values", autoCompleteRegApproval);

			HttpEntity<String> autoCompleteRegApprovalEntity = new HttpEntity<String>(
					autoCompleteRegApprovalBody.toString(), autoCompleteRegApprovalHeader);
			ResponseEntity autoCompleteRegResponse = restTemplate.postForEntity(
					"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID2_, autoCompleteRegApprovalEntity,
					String.class);

//			VendorRegister save1 = this.vendorRepo.save(filterVendorReg);
//			uploadEntity.setRegisterId(save1.getRegisterId());
//			uploadEntity.setRegistrationNo(save1.getRegistrationNo());
//			save1.setPassword(rowPassword);

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
		}

	}

	@Override
	public boolean validateEachVendor(UploadEntity uploadEntity) {

		LOGGER.info("Inside validateEachVendor()");

		try {

			if (fieldValidation.isEmpty(uploadEntity.getEmail())) {
				List<VendorRegister> findAll = vendorRepo.findAll();
				for (VendorRegister find : findAll) {
					if (find.getEmail().equals(uploadEntity.getEmail())) {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Email Id is Existl"));
						return false;
					}
				}
				if (fieldValidation.isEmpty(uploadEntity.getSupplierCompName())) {
					uploadService.bulkRegister(uploadEntity.getEmail(), uploadEntity.getSupplierCompName());
					return true;
				} else {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Company Name is Null"));
					return false;
				}

			} else {
				errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Email is null"));
				return false;
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
			return false;
		}

	}

	@Override
	public boolean validateSupplierDetails(UploadEntity uploadEntity, String type) {

		LOGGER.info("Inside validateSupplierDetails()");
		try {

			if ((fieldValidation.isEmpty(uploadEntity.getSupplierCompName()))
					& (fieldValidation.isEmpty(uploadEntity.getRegistrationType()))
					& (fieldValidation.isEmpty(uploadEntity.getRegistrationNo()))
//					& (fieldValidation.isEmpty(uploadEntity.getCostCenter()))
//					& (fieldValidation.isEmpty(uploadEntity.getRemarks()))
			) {

				Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(findByEmail.get().getRegisterId());

				if (type.equals("UPLOAD")) {
					if (findByRegisterId.size() < 1) {
						uploadEntity.setRegisterId(findByEmail.get().getRegisterId());
						uploadService.bulkUploadSupplierDetails(uploadEntity, "UPLOAD");
						return true;
					} else {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Vendor Details Already Exist"));
						return false;
					}

				} else if (type.equals("UPDATE")) {
					if (findByRegisterId.size() < 1) {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Vendor Details Not Exist"));
						return false;
					} else {
						uploadEntity.setRegisterId(findByEmail.get().getRegisterId());
						uploadEntity.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
						;
						uploadService.bulkUploadSupplierDetails(uploadEntity, "UPDATE");
						return true;
					}
				} else {
					return false;
				}

			} else {
				errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Details Field is Null"));
				return false;
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
			return false;
		}

	}

	@Override
	public boolean validateSupplierAddress(UploadEntity uploadEntity, String type) {
		LOGGER.info("Inside validateSupplierAddress()");

		try {
			if ((fieldValidation.isEmpty(uploadEntity.getAddressType()))
					& fieldValidation.isEmpty(uploadEntity.getAddress1())
					& (fieldValidation.isEmpty(uploadEntity.getPostalCode()))
					& (fieldValidation.isEmpty(uploadEntity.getCity()))
					& (fieldValidation.isEmpty(uploadEntity.getCountry()))
					& (fieldValidation.isEmpty(uploadEntity.getRegion()))
					& (fieldValidation.isEmpty(uploadEntity.getAddressProof()))
					& (fieldValidation.isEmpty(uploadEntity.getAddressProofPath()))) {

				if (type.equals("UPLOAD")) {

					Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
					List<SupDetails> findByRegisterId = supDetailsRepo
							.findByRegisterId(findByEmail.get().getRegisterId());
					if (findByRegisterId.size() < 1) {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Details is Not Created"));
						return false;
					} else {
						uploadEntity.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
						uploadService.bulkUploadSupplierAddress(uploadEntity, "UPLOAD");
						return true;
					}

				} else if (type.equals("UPDATE")) {
					if (fieldValidation.isEmpty(uploadEntity.getAddressId())) {
						Optional<SupAddress> findById = supAddressRepo.findById(uploadEntity.getAddressId());
						if (findById.isPresent()) {
							if (findById.get().getStatus().equals("DELETE")) {
								errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Address is Deleted"));
								return false;

							} else {
								Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
								List<SupDetails> findByRegisterId = supDetailsRepo
										.findByRegisterId(findByEmail.get().getRegisterId());
								if (findByRegisterId.size() < 1) {
									errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Details is Not Created"));
									return false;
								} else {
									uploadEntity.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
									uploadService.bulkUploadSupplierAddress(uploadEntity, "UPDATE");
									return true;
								}

							}

						} else {
							errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Address is Not Present"));
							return false;
						}

					} else {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Address Field is Null"));
						return false;
					}
				} else {
					return false;
				}
			} else {
				errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Address Field is Null"));
				return false;
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
			return false;
		}

	}

	@Override
	public boolean validateSupplierBank(UploadEntity supBank, String type) {

		LOGGER.info("Inside validateSupplierBank()--");
		try {

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

				if (type.equals("UPLOAD")) {

					Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
					List<SupDetails> findByRegisterId = supDetailsRepo
							.findByRegisterId(findByEmail.get().getRegisterId());
					if (findByRegisterId.size() < 1) {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Details is Not Created"));
						return false;
					} else {
						uploadEntity.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
						Optional<SupBank> findBySwiftCode = supBankRepo.findBySwiftCode(supBank.getSwiftCode());
						if (!findBySwiftCode.isPresent()) {
							uploadService.bulkUploadSupplierBank(supBank, "UPLOAD");
							return true;
						} else {
							errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail()," In Supplier bank Swift Code is Present"));
//							errorMap.put(uploadEntity.getEmail(), "In Supplier bank Swift Code is Present");
							errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier bank Swift Code is Present"));
							return false;
						}
					}

				} else if (type.equals("UPDATE")) {

					if (fieldValidation.isEmpty(supBank.getBankId())) {
						Optional<SupBank> findById = supBankRepo.findById(supBank.getBankId());
						LOGGER.info("Inside validateSupplierBank()-- if");
						if (findById.isPresent()) {

							if (!findById.get().getStatus().equals("DELETE")) {
								Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
								List<SupDetails> findByRegisterId = supDetailsRepo
										.findByRegisterId(findByEmail.get().getRegisterId());
								if (findByRegisterId.size() < 1) {
									errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail()," Supplier Details is Not Created"));
									return false;
								} else {

									uploadEntity.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
									Optional<SupBank> findBySwiftCode = supBankRepo
											.findBySwiftCode(supBank.getSwiftCode());
									LOGGER.info(
											"Inside validateSupplierBank()-- present " + findBySwiftCode.toString());
									if (findBySwiftCode.isPresent()) {

										uploadService.bulkUploadSupplierBank(supBank, "UPDATE");
										return true;
									} else {
										
//										errorMap.put(uploadEntity.getEmail(), "In Supplier bank Swift Code is Present");
										errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier bank Swift Code is Present"));
										return false;
									}
								}
							} else {
//								errorMap.put(uploadEntity.getEmail(), "In Supplier Bank is Deleted");
								errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Bank is Deleted"));
								return false;
							}

						} else {
//							errorMap.put(uploadEntity.getEmail(), "In Supplier Bank id is Not Present in DB");
							errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Bank id is Not Present in DB"));
							return false;
						}
					} else {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Bank Field is Null"));
						return false;
					}

				} else {
					return false;
				}

			} else {
//				errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail()," In Supplier Bank Field is Null"));
				return false;
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
			return false;
		}
	}

	@Override
	public boolean validateSupplierDepartment(UploadEntity supDepartment, String type) {
		LOGGER.info("Inside validateSupplierDepartment()");
		try {

			if (type.equals("UPLOAD")) {
				if (fieldValidation.isEmpty(supDepartment.getDepartmentName())
						&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
						&& fieldValidation.isEmpty(supDepartment.getSupEmail())
						&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {
					Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
					List<SupDetails> findByRegisterId = supDetailsRepo
							.findByRegisterId(findByEmail.get().getRegisterId());
					if (findByRegisterId.size() < 1) {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail()," Supplier Details is Not Created"));
						return false;
					} else {
						supDepartment.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
						uploadService.bulkUploadSupplierDepartment(supDepartment, "UPLOAD");
						return true;
					}

				} else {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"In Supplier Department Field is Null"));
					return false;
				}
			} else if (type.equals("UPDATE")) {

				Optional<SupDepartment> findById = supDepartmentRepo.findById(supDepartment.getDepartmentId());
				if (findById.isPresent()) {
					if (findById.get().getStatus().equals("DELETE")) {
						
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"In Supplier Department is Deleted"));
						return false;
					} else {
						if (fieldValidation.isEmpty(supDepartment.getDepartmentName())
								&& fieldValidation.isEmpty(supDepartment.getSupplierContact1())
								&& fieldValidation.isEmpty(supDepartment.getSupEmail())
								&& fieldValidation.isEmpty(supDepartment.getPhoneno())) {
							Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
							List<SupDetails> findByRegisterId = supDetailsRepo
									.findByRegisterId(findByEmail.get().getRegisterId());
							if (findByRegisterId.size() < 1) {
								errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Details is Not Created"));
								return false;
							} else {
								supDepartment.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
								supDepartment.setDepartmentId(findById.get().getDepartmentId());
								uploadService.bulkUploadSupplierDepartment(supDepartment, "UPDATE");
								return true;
							}
						} else {
							return false;
						}
					}
				} else {
					return false;
				}

			} else {
				return false;
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
			return false;
		}
	}

	@Override
	public boolean validateSupplierContact(UploadEntity contact, String type) {
		LOGGER.info("Inside - validateSupplierContact()");
		try {
			if ((fieldValidation.isEmpty(contact.getContractType()))
					& (fieldValidation.isEmpty(contact.getContractTerms()))
					& (fieldValidation.isEmpty(contact.getContractProof()))
					& (fieldValidation.isEmpty(contact.getContractLocation()))) {

				Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(findByEmail.get().getRegisterId());
				if (findByRegisterId.size() < 1) {
					errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Supplier Details is Not Created"));
					return false;
				} else {
					List<SupBank> findBySupplierCode = supBankRepo
							.findBySupplierCode(findByRegisterId.get(0).getSupplierCode());
					if (findBySupplierCode.size() > 0) {
						uploadEntity.setSupplierCode(findByRegisterId.get(0).getSupplierCode());
						uploadEntity.setBankId(findBySupplierCode.get(0).getBankId());

						if (type.equals("UPLOAD")) {
							uploadService.bulkUploadSupplierContact(contact, "UPLOAD");
							return true;
						} else if (type.equals("UPDATE")) {

							Optional<SupContract> findById = supContractRepo.findById(contact.getContractId());
							LOGGER.info("Inside - validateSupplierContact() --");
							if (findById.isPresent()) {

								if (findById.get().getStatus().equals("DELETE")) {
									return false;
								} else {

									contact.setContractId(findById.get().getContractId());
									uploadService.bulkUploadSupplierContact(contact, "UPDATE");
									return true;
								}

							} else {
								return false;
							}
						} else {
							return false;
						}

					} else {
						errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),"Bank is Not Created"));
						return false;
					}

				}

			} else {
//				errorMap.put(uploadEntity.getEmail(), "In Supplier Contact Field is Null");
				return false;
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
			return false;
		}

	}

	@Override
	public void bulkUploadSupplierDetails(UploadEntity supDetails, String type) {
		LOGGER.info("Inside bulkUploadSupplierDetails()");
		try {

			if (type.equals("UPLOAD")) {

				List<SupDetails> findAll = supDetailsRepo.findAll();
				SupDetails filterSupDetails = new SupDetails();
				Random rd = new Random();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
				Date date = new Date();
				filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
				filterSupDetails.setRegisterId(supDetails.getRegisterId());
				filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
				filterSupDetails.setRegistrationNo(supDetails.getRegistrationNo());
				filterSupDetails.setCostCenter(supDetails.getCostCenter());
				filterSupDetails.setRemarks(supDetails.getRemarks());
				filterSupDetails.setLastlogin(new Date());
				filterSupDetails.setSupplierCode("SU:" + formatter.format(date) + ":" + findAll.size());
				filterSupDetails.setStatus("APPROVED");

				SupDetails save = supDetailsRepo.save(filterSupDetails);

			} else if (type.equals("UPDATE")) {
				SupDetails filterSupDetails = new SupDetails();

				filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
				filterSupDetails.setRegisterId(supDetails.getRegisterId());
				filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
				filterSupDetails.setRegistrationNo(supDetails.getRegistrationNo());
				filterSupDetails.setCostCenter(supDetails.getCostCenter());
				filterSupDetails.setRemarks(supDetails.getRemarks());
				filterSupDetails.setLastlogin(new Date());
				filterSupDetails.setSupplierCode(supDetails.getSupplierCode());
				filterSupDetails.setStatus("APPROVED");

				SupDetails save = supDetailsRepo.save(filterSupDetails);

			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
		}

	}

	@Override
	public void bulkUploadSupplierAddress(UploadEntity address, String type) {
		LOGGER.info("Inside bulkUploadSupplierAddress()");
		try {

			SupAddress filterAddressUp = new SupAddress();
			filterAddressUp.setSupplierCode(address.getSupplierCode());
			filterAddressUp.setAddressType(address.getAddressType());
			filterAddressUp.setAddress1(address.getAddress1());
			try {
				if (fieldValidation.isEmpty(address.getAddress2())) {
					filterAddressUp.setAddress2(address.getAddress2());
				}
			} catch (Exception e) {
//				errorMap.put(uploadEntity.getEmail(), e.getMessage());
			}
			filterAddressUp.setPostalCode(address.getPostalCode());
			filterAddressUp.setCity(address.getCity());
			filterAddressUp.setCountry(address.getCountry());
			filterAddressUp.setRegion(address.getRegion());
			filterAddressUp.setStatus("APPROVED");
			filterAddressUp.setAddressProof(address.getAddressProof());
			filterAddressUp.setAddressProofPath(address.getAddressProofPath());

			if (type.equals("UPLOAD")) {
				supAddressRepo.save(filterAddressUp);
			} else if (type.equals("UPLOAD")) {
				filterAddressUp.setAddressId(address.getAddressId());
				supAddressRepo.save(filterAddressUp);
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
		}

	}

	@Override
	public void bulkUploadSupplierBank(UploadEntity supBank, String type) {

		LOGGER.info("Inside bulkUploadSupplierBank()--");
		try {
			SupBank bank = new SupBank();
			bank.setSupplierCode(supBank.getSupplierCode());
			bank.setAccountHolder(supBank.getAccountHolder());
			bank.setBankAccountNo(supBank.getBankAccountNo());
			try {
				bank.setBankBic(supBank.getBankBic());
			} catch (Exception e) {

			}
			bank.setBankBranch(supBank.getBankBranch());
			bank.setBankEvidence(supBank.getBankEvidence());
			bank.setBankName(supBank.getBankName());

			try {
				bank.setChequeNo(supBank.getChequeNo());
			} catch (Exception e) {

			}
			bank.setCountry(supBank.getCountry());
			bank.setCurrency(supBank.getCurrency());
			bank.setEvidencePath(supBank.getEvidencePath());
			bank.setIfscCode(supBank.getIfscCode());
			try {
				bank.setTransilRoutingNo(supBank.getTransilRoutingNo());
			} catch (Exception e) {

			}
			try {
				bank.setSwiftCode(supBank.getSwiftCode());
			} catch (Exception e) {

			}

			bank.setStatus("APPROVED");
			LOGGER.info("Inside bulkUploadSupplierBank()-- ststus");
			if (type.equals("UPLOAD")) {
				SupBank postData = this.supBankRepo.save(bank);
			} else if (type.equals("UPDATE")) {
				bank.setBankId(supBank.getBankId());
				SupBank postData = this.supBankRepo.save(bank);
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
		}

	}

	@Override
	public void bulkUploadSupplierDepartment(UploadEntity supDepartment, String type) {

		LOGGER.info("Inside bulkUploadSupplierDepartment()");
		try {
			SupDepartment department = new SupDepartment();
			department.setDepartmentName(supDepartment.getDepartmentName());
			department.setSupplierContact1(supDepartment.getSupplierContact1());
			department.setEmail(supDepartment.getSupEmail());
			department.setSupplierCode(supDepartment.getSupplierCode());
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
			LOGGER.info("Inside bulkUploadSupplierDepartment() -- UPDATE" + type);
			if (type.equals("UPLOAD")) {
				supDepartmentRepo.save(department);
			} else if (type.equals("UPDATE")) {

				department.setDepartmentId(supDepartment.getDepartmentId());
				supDepartmentRepo.save(department);
			}

		} catch (Exception e) {
			errorMap.add(new BulkUploadSuccessError(uploadEntity.getEmail(),e.getMessage()));
		}

	}

	@Override
	public void bulkUploadSupplierContact(UploadEntity contact, String type) {

		LOGGER.info("Inside bulkUploadSupplierContact()");
		try {
			SupContract filterSupContract = new SupContract();

			filterSupContract.setSupplierCode(contact.getSupplierCode());
			filterSupContract.setBankId((int) contact.getBankId());
			filterSupContract.setContractType(contact.getContractType());
			filterSupContract.setContractTerms(contact.getContractTerms());
			filterSupContract.setContractProof(contact.getContractProof());
			filterSupContract.setContractLocation(contact.getContractLocation());
			filterSupContract.setStatus("APPROVED");

			if (type.equals("UPLOAD")) {
				supContractRepo.save(filterSupContract);
			} else if (type.equals("UPDATE")) {
				filterSupContract.setContractId(contact.getContractId());
				supContractRepo.save(filterSupContract);
			}

		} catch (Exception e) {
//			errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
		}

	}

	private Sheet loadTemplate(MultipartFile uploadFile, String sheetName) {

		File fileName = new File(uploadFile.getOriginalFilename());
		XSSFWorkbook workbook = null;
		OPCPackage pkg;
		Sheet sheet = null;
		int count = 0;
		File file = null;
		LOGGER.info("1");
		try {
			if (fileName != null) {

//				pkg = OPCPackage.open("/home/soumen/Downloads/dboxupdate.xlsx");
//				workbook = new XSSFWorkbook(pkg);
				workbook = new XSSFWorkbook(uploadFile.getInputStream());
				count = workbook.getNumberOfSheets();
				if (count < 0) {

					LOGGER.error("Invalid File");

				} else {

					LOGGER.info("3");
					sheet = workbook.getSheet(sheetName);

				}
			}
		} catch (Exception e) {
			LOGGER.error("Catch Block - " + e);
		}
		return sheet;
	}

	@Override
	public List<BulkUploadSuccessError> update(MultipartFile uploadFile) {
		LOGGER.info("Inside  - UploadServiceImpl.update()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());
		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Cell Value " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				uploadEntity.setEmail(rows.getCell(0).toString());

				/* ------------------- BULK DETAILS START ----------------------------------- */

				try {
					uploadEntity.setSupplierCompName(rows.getCell(1).toString());
					uploadEntity.setRegistrationType(rows.getCell(2).toString());
					uploadEntity.setRegistrationNo(rows.getCell(3).toString());

					try {

						uploadEntity.setLastlogin(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rows.getCell(4).toString()));

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));

					}

					uploadService.validateSupplierDetails(uploadEntity, "UPDATE");

				} catch (Exception e) {

					errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));

				}

				/* ------------------- BULK DETAILS END ----------------------------------- */

				/* ------------------- BULK ADDRESS START ----------------------------------- */

				try {

					uploadEntity.setAddressId((long) Float.parseFloat(rows.getCell(5).toString()));
					uploadEntity.setAddressType(rows.getCell(6).toString());
					uploadEntity.setAddress1(rows.getCell(7).toString());

					uploadEntity.setPostalCode((int) Float.parseFloat(rows.getCell(9).toString()));
					uploadEntity.setCity(rows.getCell(10).toString());
					uploadEntity.setCountry(rows.getCell(11).toString());
					uploadEntity.setRegion(rows.getCell(12).toString());
					uploadEntity.setAddressProof(rows.getCell(13).toString());
					uploadEntity.setAddressProofPath(rows.getCell(14).toString());

					uploadService.validateSupplierAddress(uploadEntity, "UPDATE");

				} catch (Exception e) {

					errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));

				}

				/* ------------------- BULK ADDRESS END ----------------------------------- */

				/*
				 * ------------------- BULK BANK DETAILS START
				 * -----------------------------------
				 */
				try {
					uploadEntity.setBankId((long) Float.parseFloat(rows.getCell(15).toString()));
					uploadEntity.setAccountHolder(rows.getCell(16).toString());
					uploadEntity.setBankAccountNo(rows.getCell(17).toString());
					try {
						uploadEntity.setBankBic(rows.getCell(18).toString());
					} catch (Exception e) {

					}
					uploadEntity.setBankBranch(rows.getCell(19).toString());
					uploadEntity.setBankEvidence(rows.getCell(20).toString());
					uploadEntity.setBankName(rows.getCell(21).toString());
					try {
						uploadEntity.setChequeNo(rows.getCell(22).toString());
					} catch (Exception e) {

					}

					uploadEntity.setCountry(rows.getCell(23).toString());
					uploadEntity.setCurrency(rows.getCell(24).toString());
					uploadEntity.setEvidencePath(rows.getCell(25).toString());
					uploadEntity.setIfscCode(rows.getCell(26).toString());
					try {
						uploadEntity.setSwiftCode(rows.getCell(27).toString());
					} catch (Exception e) {

					}
					try {
						uploadEntity.setTransilRoutingNo(rows.getCell(28).toString());
					} catch (Exception e) {

					}

					uploadService.validateSupplierBank(uploadEntity, "UPDATE");

				} catch (Exception e) {

					errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));

				}

				/*
				 * ------------------- BULK BANK DETAILS END -----------------------------------
				 */

				/*
				 * ------------------- BULK BANK DEPT START -----------------------------------
				 */

				try {

					uploadEntity.setDepartmentId((long) Float.parseFloat(rows.getCell(29).toString()));
					uploadEntity.setDepartmentName(rows.getCell(30).toString());
					uploadEntity.setSupplierContact1(rows.getCell(31).toString());

					uploadEntity.setSupEmail(rows.getCell(33).toString());
					uploadEntity.setPhoneno(rows.getCell(34).toString());

					uploadService.validateSupplierDepartment(uploadEntity, "UPDATE");

				} catch (Exception e) {
					errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
				}

				/* ------------------- BULK BANK DEPT END ----------------------------------- */

				/* ------------------- BULK CONTACT START ----------------------------------- */

				try {

					uploadEntity.setContractId((long) Float.parseFloat(rows.getCell(36).toString()));
					System.out.println("try " + uploadEntity.getContractId());
					uploadEntity.setContractType(rows.getCell(37).toString());
					System.out.println("try " + uploadEntity.getContractType());
					uploadEntity.setContractTerms((int) Float.parseFloat(rows.getCell(38).toString()));
					System.out.println("try " + uploadEntity.getContractTerms());
					uploadEntity.setContractProof(rows.getCell(39).toString());
					System.out.println("try " + uploadEntity.getContractProof());
					uploadEntity.setContractLocation(rows.getCell(40).toString());
					System.out.println("try " + uploadEntity.getContractLocation());
					uploadService.validateSupplierContact(uploadEntity, "UPDATE");

					/* ------------------- BULK CONTACT END ----------------------------------- */

				} catch (Exception e) {
					System.out.println("catch  " + e.getMessage());
					errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
				}
			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"Email is Empty"));
			}

		}

		return errorMap;
	}

	@Override
	public List<BulkUploadSuccessError> uploadCurrencyType(MultipartFile uploadFile) {

		LOGGER.info("Inside  - UploadServiceImpl.upload()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())
						&& fieldValidation.isEmpty(rows.getCell(1).toString())
						&& fieldValidation.isEmpty(rows.getCell(2).toString())) {

					try {

						MasterCurrencyType masterCurrencyType = new MasterCurrencyType();
						masterCurrencyType.setCode(rows.getCell(0).toString());
						masterCurrencyType.setCurrency(rows.getCell(1).toString());
						masterCurrencyType.setCountry(rows.getCell(2).toString());
						masterCurrencyTypeRepo.save(masterCurrencyType);

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}

				}
			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));
			}

		}

		return errorMap;
	}

	@Override
	public List<BulkUploadSuccessError> uploadRegType(MultipartFile uploadFile) {

		LOGGER.info("Inside  - UploadServiceImpl.upload()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())) {
					try {

						MasterRegType masterRegType = new MasterRegType();
						masterRegType.setName(rows.getCell(0).toString());
						masterRegTypeRepo.save(masterRegType);

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}

				}

			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));
			}

		}

		return errorMap;
	}

	@Override
	public List<BulkUploadSuccessError> uploadDept(MultipartFile uploadFile) {

		LOGGER.info("Inside  - UploadServiceImpl.upload()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())
						&& fieldValidation.isEmpty(rows.getCell(1).toString())
						&& fieldValidation.isEmpty(rows.getCell(2).toString())
						&& fieldValidation.isEmpty(rows.getCell(3).toString())
						&& fieldValidation.isEmpty(rows.getCell(4).toString())) {

					try {
						CustomerDepartments customerDepartments = new CustomerDepartments();
						customerDepartments.setcId((long) Math.round(Float.parseFloat((rows.getCell(0).toString()))));
						customerDepartments.setDepartmentName(rows.getCell(1).toString());
						customerDepartments.setEmail(rows.getCell(2).toString());
						customerDepartments.setPhoneNo(rows.getCell(3).toString());
						customerDepartments.setCostCode(rows.getCell(4).toString());
						customerDepartments.setStatus("APPROVED");
//							try {
//								customerDepartments.setAlternatePhoneNo(rows.getCell(5).toString());
//							}catch(Exception e) {
//								throw new VendorNotFoundException(e.getMessage());
//							}
						CustomerDepartments save = customerDepartmentsRepo.save(customerDepartments);
						System.out.println("save     %%%%%%  " + save);

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}

				}

			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));
			}

		}
		System.out.println("returnFlag " + returnFlag);
		return errorMap;
	}

	@Override
	public List<BulkUploadSuccessError> uploadRole(MultipartFile uploadFile) {

		LOGGER.info("Inside  - UploadServiceImpl.upload()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())
						&& fieldValidation.isEmpty(rows.getCell(1).toString())) {
					try {

						RolesMaster rolesMaster = new RolesMaster();
						rolesMaster.setRoleId((long) Math.round(Float.parseFloat(rows.getCell(0).toString())));
						rolesMaster.setRole(rows.getCell(1).toString());
						RolesMaster save = rolesMasterRepo.save(rolesMaster);

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}
				}

			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));
			}

		}

		return errorMap;
	}

	@Override
	public List<BulkUploadSuccessError> uploadFunc(MultipartFile uploadFile) {

		LOGGER.info("Inside  - UploadServiceImpl.uploadFunc()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())
						&& fieldValidation.isEmpty(rows.getCell(1).toString())) {

					try {

						CustomerFunctionalitiesMaster customerFunctionalitiesMaster = new CustomerFunctionalitiesMaster();
						customerFunctionalitiesMaster.setfName(rows.getCell(0).toString());
						customerFunctionalitiesMaster.setfDesc(rows.getCell(1).toString());
						CustomerFunctionalitiesMaster save = customerFunctionalitiesMasterRepo
								.save(customerFunctionalitiesMaster);
						System.out.print("save " + save.toString());

					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}

				}
			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));
			}

		}

		return errorMap;
	}

	@Override
	public List<BulkUploadSuccessError> bulkUploadRegionCountry(MultipartFile uploadFile) {
		// TODO Auto-generated method stub

		LOGGER.info("Inside  - UploadServiceImpl.bulkUploadRegionCountry()");
//		try {

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())) {
					try {
						uploadEntity.setRegionName(rows.getCell(0).toString());

						try {
							if (fieldValidation.isEmpty(rows.getCell(1).toString())) {
								uploadEntity.setCountryName(rows.getCell(1).toString());
							}
						} catch (Exception e) {
						}

						GeoEntity geoEntity = new GeoEntity();
						List<GeoEntity> findByNameReg = geoRepo
								.findByNameWithType(uploadEntity.getRegionName().toUpperCase(), "REGION");

						if (findByNameReg.size() < 1) {
							geoEntity.setName(uploadEntity.getRegionName());
							geoEntity.setParentId(0);
							geoEntity.setType("REGION");
							GeoEntity save = geoRepo.save(geoEntity);
							GeoEntity geoEntityCountry = new GeoEntity();
							geoEntityCountry.setName(uploadEntity.getCountryName());
							geoEntityCountry.setParentId(save.getGeoId());
							geoEntityCountry.setType("COUNTRY");
							geoRepo.save(geoEntityCountry);
						} else {
							List<GeoEntity> findByNameCoun = geoRepo
									.findByNameWithType(uploadEntity.getRegionName().toUpperCase(), "COUNTRY");

							if (findByNameCoun.size() < 1) {
								geoEntity.setName(uploadEntity.getCountryName());
								geoEntity.setParentId(findByNameReg.get(0).getGeoId());
								geoEntity.setType("COUNTRY");
								GeoEntity save = geoRepo.save(geoEntity);
							}
						}
					} catch (Exception e) {
						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}

				}
			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));

			}

		}

		return errorMap;

//		}catch(Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//		
	}

	@Override
	public List<BulkUploadSuccessError> bulkUploadContractAndAddressType(MultipartFile uploadFile, String token) {
		// TODO Auto-generated method stub

		LOGGER.info("Inside  - UploadServiceImpl.bulkUploadRegionCountry()");
//		try {

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(uploadFile, sheetName);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);

				System.out.println("Value is ==========  " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

				}

			}

			try {
				if (fieldValidation.isEmpty(rows.getCell(0).toString())
						&& fieldValidation.isEmpty(rows.getCell(1).toString())
						&& fieldValidation.isEmpty(rows.getCell(2).toString())) {
					try {

						long cIdFromToken = getCustomerDetails.getCIdFromToken(token);
						uploadEntity.setcId(Integer.parseInt(cIdFromToken + ""));
						uploadEntity.setName(rows.getCell(0).toString());
						uploadEntity.setType(rows.getCell(1).toString());
//								uploadEntity.setCreatedOn(rows.getCell(4).toString());
//								uploadEntity.setCreatedBy();

						try {
							if (fieldValidation.isEmpty(rows.getCell(2).toString())) {
//										uploadEntity.setOrder(Integer.parseInt(rows.getCell(3).toString()));
							}
						} catch (Exception e) {
						}
						List<ContractAndAddressType> findByNameWithCId = contractAndAddressTypeRepo.findByNameWithCId(
								uploadEntity.getName().toUpperCase(), Integer.parseInt(cIdFromToken + ""), "ADDRESS");
						if (findByNameWithCId.size() < 1) {
							ContractAndAddressType contractAndAddressType = new ContractAndAddressType();
							contractAndAddressType.setcId((long) Integer.parseInt(cIdFromToken + ""));
							contractAndAddressType.setName(uploadEntity.getName());
							if (uploadEntity.getType().equals("ADDRESS")) {
								contractAndAddressType.setType("ADDRESS");
							} else if (uploadEntity.getType().equals("CONTRACT")) {
								contractAndAddressType.setType("CONTRACT");
							}
							contractAndAddressTypeRepo.save(contractAndAddressType);
						}

					} catch (Exception e) {
//						errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,e.getMessage()));
					}

				}
			} catch (Exception e) {
				errorMap.add(new BulkUploadSuccessError("Row-- " + i+1,"some fields are missing"));
//				errorMap.put("Row-- " + i+1, "some fields are missing");

			}

		}

		return errorMap;

//		}catch(Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//		
	}

}
