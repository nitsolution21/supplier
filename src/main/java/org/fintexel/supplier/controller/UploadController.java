package org.fintexel.supplier.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fintexel.supplier.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestPart("file") final MultipartFile uploadFile) throws ParseException {
		LOGGER.info("Inside - UploadController.upload()");
		
		byte[] bytes;
		if(uploadFile.isEmpty()) {
			LOGGER.info("File is Empty");
		}else {
			LOGGER.info("File name is - "+uploadFile.getName());
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
		

	}
	

}
