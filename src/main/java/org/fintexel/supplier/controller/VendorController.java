package org.fintexel.supplier.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {

	@PostMapping("/registerVendor")
	public String registerVendor() {
		return "Hello";
	}
	
	@GetMapping("/vendor")
	public String getVendor() {
		return "Hello";
	}
	
}
