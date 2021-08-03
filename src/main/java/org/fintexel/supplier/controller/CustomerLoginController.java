package org.fintexel.supplier.controller;

import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerLoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLoginController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@PostMapping("/login")
//	private ResponseEntity<?> customerLogin(@RequestBody VendorLogin vendorLogin) {
//		LOGGER.info("Inside - CustomerLoginController.customerLogin()");
//		try {
//			this.authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(vendorLogin.getUsername(), vendorLogin.getPassword()));
//		} catch (Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}
//	
//	@PostMapping("/registration")
//	private void customerRegistration() {
//		LOGGER.info("Inside - CustomerLoginController.customerRegistration()");
//	}
}
