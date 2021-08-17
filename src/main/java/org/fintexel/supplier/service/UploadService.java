package org.fintexel.supplier.service;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.UploadEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface UploadService {
	
	
	/* ------------ BULK UPLOAD START ---------------------*/

	boolean upload(MultipartFile uploadFile);

	boolean validateEachVendor(UploadEntity uploadEntity);
	
	void bulkRegister(String email, String companyname);
	
	boolean validateSupplierDetails(UploadEntity uploadEntity , String type);
	
	void bulkUploadSupplierDetails(UploadEntity supDetails , String type);
	
	boolean validateSupplierAddress(UploadEntity uploadEntity , String type);
	
	void bulkUploadSupplierAddress(UploadEntity supAddress , String type);
	
	boolean validateSupplierBank(UploadEntity uploadEntity , String type);
	
	void bulkUploadSupplierBank(UploadEntity supAddress , String type);
	
	boolean validateSupplierDepartment(UploadEntity uploadEntity , String type);
	
	void bulkUploadSupplierDepartment(UploadEntity supAddress , String type);
	
	boolean validateSupplierContact(UploadEntity uploadEntity , String type);
	
	void bulkUploadSupplierContact(UploadEntity supAddress , String type);
	
	
	
	/* ------------ BULK UPLOAD END ---------------------*/
	
	
	/* ------------ BULK UPDATE START ---------------------*/
	
	boolean update(MultipartFile uploadFile);
	

}
