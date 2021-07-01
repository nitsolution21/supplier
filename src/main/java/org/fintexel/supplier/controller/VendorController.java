package org.fintexel.supplier.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@PostMapping("/registerVendor")
	public String registerVendor() {
		return "Hello";
	}
}
