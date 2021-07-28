package org.fintexel.supplier.service;

import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.UploadEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface UploadService {

	boolean upload(MultipartFile uploadFile);

	void bulkRegister(String email, String companyname);

	void validateEachVendor(UploadEntity uploadEntity);

	void bulkUploadSupplierDetails(SupDetails supDetails);

}
