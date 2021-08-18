package org.fintexel.supplier.controller;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fintexel.supplier.entity.FileUploadResponse;
import org.fintexel.supplier.exceptions.VendorErrorResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.FileUploadHelper;
import org.fintexel.supplier.helper.LoginUserDetails;
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
	
	
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile uploadFile) {
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
			
			boolean flag = uploadService.upload(uploadFile);
			LOGGER.info("return Flag  - "+flag);
			
			
			return "";
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException(e.getMessage());
		}
		
		

	}
	
	
	@PostMapping("/update")
	public String update(@RequestPart("file") final MultipartFile file) {
		LOGGER.info("Inside - UploadController.update()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			boolean flag = uploadService.update(file);
			LOGGER.info("return Flag  - "+flag);
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		return null;
	}
	
	@PostMapping("/uploadSupplierProof")
	public FileUploadResponse uploadSupplierProof(@RequestParam("file") MultipartFile file,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - UploadController.uploadSupplierProof()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
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
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new VendorNotFoundException(e.getMessage());
		}

	}
	
	
	
	
	@PostMapping("/uploadCurrencyType")
	public String uploadCurrencyType(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			boolean flag = uploadService.uploadCurrencyType(file);
			LOGGER.info("return Flag  - "+flag);
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		return null;
	}
	
	@PostMapping("/uploadRegType")
	public String uploadRegType(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			boolean flag = uploadService.uploadRegType(file);
			LOGGER.info("return Flag  - "+flag);
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		return null;
	}
	
	
	@PostMapping("/uploadDept")
	public String uploadDept(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			boolean flag = uploadService.uploadDept(file);
			LOGGER.info("return Flag  - "+flag);
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		return null;
	}

	
	@PostMapping("/uploadRole")
	public String uploadRole(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			boolean flag = uploadService.uploadRole(file);
			LOGGER.info("return Flag  - "+flag);
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		return null;
	}
	
	@PostMapping("/uploadFunc")
	public String uploadFunc(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			boolean flag = uploadService.uploadFunc(file);
			LOGGER.info("return Flag  - "+flag);
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		return null;
	}


}
