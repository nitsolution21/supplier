package org.fintexel.supplier.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	@Value("${file.upload-path}")
	private String UPLOAD_DIR;

	public boolean uploadFile(MultipartFile file, String folderName, String purpose, String SupCode) {

		try {
			String[] parts = file.getContentType().split("/");
			String extension = parts[1];
			Files.copy(file.getInputStream(), Paths.get(
					UPLOAD_DIR + File.separator + folderName + File.separator + SupCode + purpose + "." + extension),
					StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
