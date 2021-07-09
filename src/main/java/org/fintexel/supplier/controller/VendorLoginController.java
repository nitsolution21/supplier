package org.fintexel.supplier.controller;

import org.fintexel.supplier.entity.LoginResponce;
import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.services.VendorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorLoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/vendorLogin")
	public ResponseEntity<?> venderLogin(@RequestBody VendorLogin vendorLogin) {
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(vendorLogin.getUsername(), vendorLogin.getPassword()));
		} 
		catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		UserDetails vendor = this.vendorDetailsService.loadUserByUsername(vendorLogin.getUsername());
		
		String token = jwtUtil.generateToken(vendor);
		
		return ResponseEntity.ok(new LoginResponce(vendor.getUsername(), token));
	}
}
