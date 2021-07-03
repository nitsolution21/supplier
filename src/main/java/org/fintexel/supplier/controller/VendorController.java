package org.fintexel.supplier.controller;

import org.fintexel.supplier.SupplierApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);

	@PostMapping("/registerVendor")
	public String registerVendor() {
		LOGGER.info("Inside - VendorController.registerVendor()");
		return "Hello";
	}
	
	@GetMapping("/vendor")
	public String getVendor() {
		LOGGER.info("Inside - VendorController.getVendor()");
		return "Hello";
	}
	
}
