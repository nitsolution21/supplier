package org.fintexel.supplier.controller;

import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.CustomerUserRoles;
import org.fintexel.supplier.customerentity.LoginResponceForCustomer;
import org.fintexel.supplier.customerentity.RolesMaster;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.CustomerUserRolesRepo;
import org.fintexel.supplier.customerrepository.RolesMasterRepo;
import org.fintexel.supplier.entity.VendorLogin;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.services.CustomerDetailsServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	@Autowired
	private CustomerDetailsServices customerDetailsServices;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;
	
	@Autowired
	private CustomerUserRolesRepo customerUserRolesRepo;
	
	@Autowired
	private RolesMasterRepo rolesMasterRepo;
	
	@PostMapping("/login")
	private ResponseEntity<?> customerLogin(@RequestBody VendorLogin vendorLogin) {
		LOGGER.info("Inside - CustomerLoginController.customerLogin()");
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(vendorLogin.getUsername(), vendorLogin.getPassword()));
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		UserDetails customer = this.customerDetailsServices.loadUserByUsername(vendorLogin.getUsername());
		String token = jwtUtil.generateToken(customer);
		
		Optional<CustomerRegister> findByUsername = customerRegisterRepo.findByUsername(vendorLogin.getUsername());
		if (findByUsername.isPresent()) {
			Optional<CustomerUserRoles> findByUserId = customerUserRolesRepo.findByUserId(findByUsername.get().getUserId());
			if (findByUserId.isPresent()) {
				Optional<RolesMaster> findById = rolesMasterRepo.findById(findByUserId.get().getRoleId());
				if (findById.isPresent()) {
					return ResponseEntity.ok(new LoginResponceForCustomer(findByUsername.get().getUserId(), token, findById.get().getRole()));
				} else {
					throw new VendorNotFoundException("Roll is not presen");
				}
			} else {
				throw new VendorNotFoundException("Customer roll not define");
			}
		} else {
			throw new VendorNotFoundException("Customer not found");
		}
	}
//	
//	@PostMapping("/registration")
//	private void customerRegistration() {
//		LOGGER.info("Inside - CustomerLoginController.customerRegistration()");
//	}
}
