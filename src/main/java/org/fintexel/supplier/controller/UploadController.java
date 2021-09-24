package org.fintexel.supplier.controller;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.entity.BulkUploadSuccessError;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.FileUploadResponse;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorErrorResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.FileUploadHelper;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@Autowired
	private VendorRegisterRepo registerRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;

	
	
	@PostMapping("/upload")
	@ResponseBody
	public List<BulkUploadSuccessError> upload(@RequestParam("file") MultipartFile uploadFile) {
		LOGGER.info(uploadFile.getContentType());
		LOGGER.info(uploadFile.getName());
		LOGGER.info(uploadFile.getOriginalFilename());
		
		LOGGER.info("Inside - UploadController.upload()");
		try {
			byte[] bytes;
			if(uploadFile.isEmpty()) {
				LOGGER.info("File is Empty");
				throw new VendorNotFoundException("File is Empty");
				
			}else {
				String originalFilename = uploadFile.getOriginalFilename();
				String substring = originalFilename.substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1, uploadFile.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				
				
//				LOGGER.info("File name is - "+uploadFile.getName());
			}
			
			
			Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date fetchNueva = format.parse(currentTimeStamp.toString());
			format = new SimpleDateFormat("dd MMM YYY HH:mm");
			
			LOGGER.info("Time Stamp - "+fetchNueva);
			
			String uploadRefID = uploadFile.getOriginalFilename()+"_"+format.format(fetchNueva);
			LOGGER.info("Ref ID  - "+uploadRefID);
			
			List<BulkUploadSuccessError> flag = uploadService.upload(uploadFile);
			LOGGER.info("return Flag  - "+flag);
			
			
			return flag;
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException(e.getMessage());
		}
		
		

	}
	
	
	@PostMapping("/update")
	public List<BulkUploadSuccessError> update(@RequestPart("file") final MultipartFile file) {
		LOGGER.info("Inside - UploadController.update()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.update(file);
			LOGGER.info("return Flag  - "+flag);
			return flag;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@PostMapping("/uploadSupplierProof")
	public FileUploadResponse uploadSupplierProof(@RequestParam("file") MultipartFile file,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - UploadController.uploadSupplierProof()");
		try {
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7);
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = registerRepo.findByUsername(userName);
				if (findByUsername.isPresent()) {
					if (file.getSize() < 1) {
						throw new VendorNotFoundException("File is Empty");
					}

					FileUploadResponse uploadFile = fileUploadHelper.uploadFile(file);
					if (!uploadFile.equals(null)) {
						return uploadFile;
					} else {
						throw new VendorNotFoundException("Something went wrong !! Please try again");
					}
				} else {
					Optional<CustomerRegister> findByCustomerUsername = customerRegisterRepo.findByUsername(userName);
					if (findByCustomerUsername.isPresent()) {
						if (file.getSize() < 1) {
							throw new VendorNotFoundException("File is Empty");
						}

						FileUploadResponse uploadFile = fileUploadHelper.uploadFile(file);
						if (!uploadFile.equals(null)) {
							return uploadFile;
						} else {
							throw new VendorNotFoundException("Something went wrong !! Please try again");
						}
					} else {
						throw new VendorNotFoundException("We can't find your details");
					}
				}
			}
			else {
				throw new VendorNotFoundException("Token note found");
			}
//			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
//			if (!loginSupplierCode.equals(null)) {
//				if (file.getSize() < 1) {
//					throw new VendorNotFoundException("File is Empty");
//				}
//
//				FileUploadResponse uploadFile = fileUploadHelper.uploadFile(file);
//				if (!uploadFile.equals(null)) {
//					return uploadFile;
//				} else {
//					throw new VendorNotFoundException("Something went wrong !! Please try again");
//				}
//			} else {
//				throw new VendorNotFoundException("Token not valid");
//			}
			
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new VendorNotFoundException(e.getMessage());
		}

	}
	
	
	
	
	@PostMapping("/uploadCurrencyType")
	public List<BulkUploadSuccessError> uploadCurrencyType(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadCurrencyType(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}
	
	@PostMapping("/uploadRegType")
	public List<BulkUploadSuccessError> uploadRegType(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadRegType(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		
	}
	
	
	@PostMapping("/uploadDept")
	public List<BulkUploadSuccessError> uploadDept(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadDept(file);
			LOGGER.info("return Flag  - "+flag);
			return flag;
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		
	}

	
	@PostMapping("/uploadRole")
	public List<BulkUploadSuccessError> uploadRole(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadRole(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		
	}
	
	@PostMapping("/uploadFunc")
	public List<BulkUploadSuccessError> uploadFunc(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					System.out.println("file type  _____---------  " + substring);
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadFunc(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	
	}
	
	@PostMapping("/uploadRegionCountry")
	public List<BulkUploadSuccessError> uploadRegionCountry(@RequestParam("file") MultipartFile file){
		
		LOGGER.info("Inside - UploadController.uploadRegionCountry()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					System.out.println("file type  _____---------  " + substring);
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.bulkUploadRegionCountry(file);
//			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@PostMapping("/uploadContractAndAddressType")
	public List<BulkUploadSuccessError> uploadContractAndAddressType(@RequestParam("file") MultipartFile file , @RequestHeader(name = "Authorization") String token) {
		
		LOGGER.info("Inside - UploadController.uploadContractAndAddress()");
		
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					System.out.println("file type  _____---------  " + substring);
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.bulkUploadContractAndAddressType(file,token);
//			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	


}
