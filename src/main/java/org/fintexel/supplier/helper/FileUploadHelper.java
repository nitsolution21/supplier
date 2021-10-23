package org.fintexel.supplier.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.fintexel.supplier.entity.FileUploadResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadHelper.class);
	@Value("${file.upload-path}")
	private String UPLOAD_DIR;

	public FileUploadResponse uploadFile(MultipartFile file) {
		
		LOGGER.info("in FileUploadHelper:  "+ UPLOAD_DIR);

		try {
			String[] parts = file.getContentType().split("/");
			String extension = parts[1];
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String[] parts1 = dtf.format(now).split(" ");
			String[] date = parts1[0].split("/");
			String[] time = parts1[1].split(":");
			Files.copy(
					file.getInputStream(), Paths.get(UPLOAD_DIR + file.getOriginalFilename().split("\\.")[0] + date[0]
							+ date[1] + date[2] + time[0] + time[1] + time[2] + "." + extension),
					StandardCopyOption.REPLACE_EXISTING);
			Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename().split("\\.")[0] + date[0]
					+ date[1] + date[2] + time[0] + time[1] + time[2] + "." + extension);
			LOGGER.info("path "+path);
			FileUploadResponse fileUploadResponse = new FileUploadResponse();
			
//			file.transferTo(new File(UPLOAD_DIR+file.getOriginalFilename().split("\\.")[0] + date[0]
//					+ date[1] + date[2] + time[0] + time[1] + time[2] + "." + extension));
			
			fileUploadResponse.setFileName(file.getOriginalFilename().split("\\.")[0] + date[0]
					+ date[1] + date[2] + time[0] + time[1] + time[2] + "." + extension);
	
			
			fileUploadResponse.setPath(UPLOAD_DIR);
			
			return fileUploadResponse;
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
