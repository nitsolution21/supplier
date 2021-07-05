package org.fintexel.supplier.controller;

import java.util.List;

import org.fintexel.supplier.SupplierApplication;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.Vendor;
import org.fintexel.supplier.repository.UserRepo;
import org.fintexel.supplier.repository.VendorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private VendorRepo vendorRepo;
	@PostMapping("/registerVendor")
	public String registerVendor() {
		LOGGER.info("Inside - VendorController.registerVendor()");
		return "Hello";
	}
	@GetMapping("/getRegisterVendor")
	@ResponseBody
	public Iterable<Vendor> getRegisterVendor() {
		return this.vendorRepo.findAll();
	}
	@PutMapping("/putRegisterVendor")
	@ResponseBody
	public Vendor putRegisterVendor(@RequestBody Vendor vendorReg) {
		return this.vendorRepo.save(vendorReg);
	}
	@GetMapping("/users")
	public String getUser() {
		LOGGER.info("Inside - VendorController.getUser()");
		return "Hello";
	}
	
	@GetMapping("/user")
		List<User> all(){
		LOGGER.info("Inside - VendorController.all()");
		return userRepo.findAll();
	}
	
	@PostMapping("/user")
	  User newVendor(@RequestBody User newUser) {
	    return userRepo.save(newUser);
	  }
	
	
}
