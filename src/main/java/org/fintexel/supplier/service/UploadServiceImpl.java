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
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.SupRequest;
import org.fintexel.supplier.entity.UploadEntity;
import org.fintexel.supplier.entity.UploadErrorEntity;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.SupAddressRepo;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Supplier;

@Service
public class UploadServiceImpl implements UploadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

	@Autowired
	FlowableRegistrationRepo flowableRegistrationRepo;

	@Autowired
	private SupDetails supDetails;

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
	UploadErrorEntity UploadErrorEntity;

	@Autowired
	UploadService uploadService;

	@Autowired
	UploadEntity uploadEntity;

	@Autowired
	private FieldValidation fieldValidation;

	@Override
	public boolean upload(MultipartFile uploadFile) {
		LOGGER.info("Inside  - UploadServiceImpl.upload()");

		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;

		File fileName = new File(uploadFile.getOriginalFilename());
		LOGGER.info("FileName 2 - " + uploadFile.getOriginalFilename());

		Sheet sheet = loadTemplate(fileName, sheetName);
		LOGGER.info("Sheet Name - 2 - " + sheet);

		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;
		LOGGER.info("*************");
		LOGGER.info("minRow - " + minRow);
		LOGGER.info("MaxRow - " + maxRow);
		LOGGER.info("MinCell - " + minCell);
		LOGGER.info("MaxCell - " + maxCell);

		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);

			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				String cellValue = dataFormatter.formatCellValue(cells);
				LOGGER.info("Cell Value - " + cellValue);

				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					UploadErrorEntity.setRowNumber(i);
					UploadErrorEntity.setCellNumber(c);
					UploadErrorEntity.setErrorDescription("Blank Value");

					LOGGER.info("UploadErrorEntity Class -  " + UploadErrorEntity.toString());
				}

				uploadEntity.setEmail(rows.getCell(0).toString());
				uploadEntity.setSupplierCompName(rows.getCell(1).toString());
				uploadEntity.setRegistrationType(rows.getCell(2).toString());
				uploadEntity.setCostCenter(rows.getCell(3).toString());
				uploadEntity.setRemarks(rows.getCell(4).toString());
				uploadEntity.setRegistrationNo(rows.getCell(5).toString());
				try {
					uploadEntity.setLastlogin(
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rows.getCell(6).toString()));
				} catch (Exception e) {
					// TODO: handle exception
				}

//				uploadEntity.setSlno(rows.getCell(7).toString());
//				uploadEntity.setSlno(rows.getCell(8).toString());
//				uploadEntity.setSlno(rows.getCell(9).toString());

			}
			System.out.println("#####################uploadEntity********************" + uploadEntity.toString());
			uploadService.validateEachVendor(uploadEntity);
			uploadService.validateSupplierDetails(uploadEntity);
		}

		return returnFlag;
	}

	private Sheet loadTemplate(File fileName, String sheetName) {

		XSSFWorkbook workbook = null;
		OPCPackage pkg;
		Sheet sheet = null;
		int count = 0;
		File file = null;
		LOGGER.info("1");
		try {
			if (fileName != null) {
				LOGGER.info("FileName 2 - " + fileName);
				pkg = OPCPackage.open("/home/soumen/Downloads/dbox.xlsx");
				LOGGER.info("21");
				workbook = new XSSFWorkbook(pkg);
				LOGGER.info("22");
				count = workbook.getNumberOfSheets();
				LOGGER.info("23");
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
			}

			filterVendorReg.setUsername(email);
			String rowPassword = java.util.UUID.randomUUID().toString();
			filterVendorReg.setPassword(passwordEncoder.encode(rowPassword));

			VendorRegister save = this.vendorRepo.save(filterVendorReg);

			filterVendorReg.setRegisterId(save.getRegisterId());

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
			System.out.println(
					"###auto complite for registration approval froom shantanu:    " + autoCliamRegApprovalEntity);
			System.out.println("###auto complite for registration approval id:    " + taskID2_);

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

			System.out.println("autoCompleteHeader" + autoCompleteRegApprovalHeader);
			System.out.println("autoCompleteBody" + autoCompleteRegApprovalBody);

			HttpEntity<String> autoCompleteRegApprovalEntity = new HttpEntity<String>(
					autoCompleteRegApprovalBody.toString(), autoCompleteRegApprovalHeader);
			System.out.println("##httpEntity autocompleate" + autoCompleteRegApprovalEntity);
			ResponseEntity autoCompleteRegResponse = restTemplate.postForEntity(
					"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID2_, autoCompleteRegApprovalEntity,
					String.class);

			System.out.println("autoCompleteRegResponse" + autoCompleteRegResponse);

			VendorRegister save1 = this.vendorRepo.save(filterVendorReg);
			uploadEntity.setRegisterId(save1.getRegisterId());
//			uploadEntity.setRegistrationNo(save1.getRegistrationNo());
			save1.setPassword(rowPassword);
//				save1.setRegisterId("SR "+save1.getRegisterId());

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
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
						return false;
					}
				}
				if (fieldValidation.isEmpty(uploadEntity.getSupplierCompName())) {
					uploadService.bulkRegister(uploadEntity.getEmail(), uploadEntity.getSupplierCompName());
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@Override
	public boolean validateSupplierDetails(UploadEntity uploadEntity) {

		LOGGER.info("Inside validateSupplierDetails()");
		try {

			if ((fieldValidation.isEmpty(uploadEntity.getSupplierCompName()))
					& (fieldValidation.isEmpty(uploadEntity.getRegistrationType()))
					& (fieldValidation.isEmpty(uploadEntity.getRegisterId()))
					& (fieldValidation.isEmpty(uploadEntity.getRegistrationNo()))
					& (fieldValidation.isEmpty(uploadEntity.getCostCenter()))
					& (fieldValidation.isEmpty(uploadEntity.getRemarks()))) {

				Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(findByEmail.get().getRegisterId());
				if (findByRegisterId.size() < 1) {
					return false;
				} else {
					uploadService.bulkUploadSupplierDetails(uploadEntity);
					return true;
				}

			} else {
				return false;
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@Override
	public boolean validateSupplierAddress(UploadEntity uploadEntity) {
		LOGGER.info("Inside validateSupplierAddress()");

		try {
			if ((fieldValidation.isEmpty(uploadEntity.getAddressType()))
					& (fieldValidation.isEmpty(uploadEntity.getAddress1())
							&& fieldValidation.isEmpty(uploadEntity.getAddress2()))
					& (fieldValidation.isEmpty(uploadEntity.getPostalCode()))
					& (fieldValidation.isEmpty(uploadEntity.getCity()))
					& (fieldValidation.isEmpty(uploadEntity.getCountry()))
					& (fieldValidation.isEmpty(uploadEntity.getRegion()))
					& (fieldValidation.isEmpty(uploadEntity.getAddressProof()))
					& (fieldValidation.isEmpty(uploadEntity.getAddressProofPath()))) {

				Optional<VendorRegister> findByEmail = vendorRepo.findByEmail(uploadEntity.getEmail());
				List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(findByEmail.get().getRegisterId());
				if (findByRegisterId.size() < 1) {
					return false;
				} else {
					uploadService.bulkUploadSupplierAddress(uploadEntity);
					return true;
				}
			} else {
				throw new VendorNotFoundException("Validation error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}


	}

	@Override
	public void bulkUploadSupplierDetails(UploadEntity supDetails) {
		LOGGER.info("Inside bulkUploadSupplierDetails()");
		try {

			LOGGER.info("Inside If()");

			List<SupDetails> findByRegisterId = supDetailsRepo.findByRegisterId(supDetails.getRegisterId());
			List<SupDetails> findAll = supDetailsRepo.findAll();
			if (findByRegisterId.size() < 1) {
				SupDetails filterSupDetails = new SupDetails();
				SupRequest supRequest = new SupRequest();
				Random rd = new Random();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
				Date date = new Date();
				filterSupDetails.setSupplierCompName(supDetails.getSupplierCompName());
				filterSupDetails.setRegisterId(supDetails.getRegisterId());
				filterSupDetails.setRegistrationType(supDetails.getRegistrationType());
				filterSupDetails.setRegistrationNo(supDetails.getRegistrationNo());
				filterSupDetails.setCostCenter(supDetails.getCostCenter());
				filterSupDetails.setRemarks(supDetails.getRemarks());
				System.out.println("$$$$$$$$$$$        t");
				filterSupDetails.setLastlogin(new Date());
				System.out.println("$$$$$$$$$$$  ");
				filterSupDetails.setSupplierCode("SU:" + formatter.format(date) + ":" + findAll.size());
				filterSupDetails.setStatus("PENDING");

				SupDetails save = supDetailsRepo.save(filterSupDetails);
				System.out.println("$$$$$$$$$$$  " + save.toString());
			} else {
				throw new VendorNotFoundException("Vendor Already Exist");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@Override
	public void bulkUploadSupplierAddress(UploadEntity address) {
		LOGGER.info("Inside bulkUploadSupplierAddress()");
		try {

			String loginSupplierCode = "";
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
					filterAddressUp.setStatus("PENDING");
					filterAddressUp.setAddressProof(address.getAddressProof());
					filterAddressUp.setAddressProofPath(address.getAddressProofPath());
					SupAddress save = this.supAddressRepo.save(filterAddressUp);
				}
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

}
