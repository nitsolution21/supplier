package org.fintexel.supplier.service;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.UploadEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface UploadService {

	boolean upload(MultipartFile uploadFile);

	boolean validateEachVendor(UploadEntity uploadEntity);
	
	void bulkRegister(String email, String companyname);
	
	boolean validateSupplierDetails(UploadEntity uploadEntity);
	
	void bulkUploadSupplierDetails(UploadEntity supDetails);
	
	boolean validateSupplierAddress(UploadEntity uploadEntity);
	
	void bulkUploadSupplierAddress(UploadEntity supAddress);

}
