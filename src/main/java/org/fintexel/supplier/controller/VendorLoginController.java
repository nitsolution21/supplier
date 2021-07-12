package org.fintexel.supplier.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.fintexel.supplier.entity.LoginResponce;
import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.services.VendorDetailsService;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class VendorLoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private VendorRegisterRepo vendorRegisterRepo;
	
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
	
	@GetMapping("/vendorLogin")
	public List<VendorLogin> getLoginVendor() {
		try {
			List<VendorLogin> vendorLogin = new ArrayList<VendorLogin>();
			List<VendorRegister> allVendor = vendorRegisterRepo.findAll();
			allVendor.forEach(vendor -> {
				if (vendor.getUsername() != null && vendor.getPassword() != null) {
					VendorLogin loginDetails = new VendorLogin();
					loginDetails.setUsername(vendor.getUsername());
					loginDetails.setPassword(vendor.getPassword());
					vendorLogin.add(loginDetails);
				}
			});
			return vendorLogin;
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException("Nothing to display");
		}
	}
}
