package org.fintexel.supplier.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.fintexel.supplier.entity.LoginResponce;
import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.services.VendorDetailsService;
import org.fintexel.supplier.validation.FieldValidation;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	VendorLogin loginDetails;
	
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
					loginDetails = new VendorLogin();
					loginDetails.setUsername(vendor.getUsername());
					loginDetails.setPassword(vendor.getPassword());
					vendorLogin.add(loginDetails);
				}
			});
			return vendorLogin;
		} catch (Exception e) {
			throw new VendorNotFoundException("Nothing to display");
		}
	}
	
	@GetMapping("/vendorLogin/{id}")
	public VendorLogin getSingleUsernamePassword(@PathVariable long id) {
		
		loginDetails = new VendorLogin();
		Optional<VendorRegister> findById = vendorRegisterRepo.findById(id);
		
		if (findById.get().getUsername() != null && findById.get().getPassword() != null) {
			loginDetails.setUsername(findById.get().getUsername());
			loginDetails.setPassword(findById.get().getPassword());
			return loginDetails;
		} else {
			throw new VendorNotFoundException("Username Password Not found!!");
		}
	}
	
	@PutMapping("/vendorLogin/{id}")
	public VendorLogin updateVendoUsernameAndPassword(@PathVariable long id, @RequestBody VendorLogin vendorLogin) {
		try {
			Optional<VendorRegister> findById = vendorRegisterRepo.findById(id);
			loginDetails = new VendorLogin();
			VendorRegister vendorRegister = findById.get();
			if (findById.isPresent()) {
				System.out.println("in frist if");
				if (fieldValidation.isEmpty(vendorLogin.getUsername()) && fieldValidation.isEmpty(vendorLogin.getPassword())) {
					System.out.println("in second if");
					String encode = bCryptPasswordEncoder.encode(vendorLogin.getPassword());
					vendorRegister.setUsername(vendorLogin.getUsername());
					vendorRegister.setPassword(encode);
					vendorRegister.setRegisterId(id);
					
					VendorRegister saveVendor = vendorRegisterRepo.save(vendorRegister);
					loginDetails.setUsername(saveVendor.getUsername());
					loginDetails.setPassword(vendorLogin.getPassword());
					return loginDetails;
				}
				else {
					System.out.println("in second else");
					throw new VendorNotFoundException("User id password can't be null");
				}
			} 
			else {
				System.out.println();
				throw new VendorNotFoundException("Vendor not avalable");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
