package org.fintexel.supplier.controller;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.SupplierApplication;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.Vendor;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.SupAddRepo;
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
	
	@Autowired
	private SupAddRepo supAddRepo;
	
	@PostMapping("/registerVendor")
	public Vendor registerVendor(@RequestBody Vendor vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		return this.vendorRepo.save(vendorReg);
	}
	
	@GetMapping("/getRegisterVendor")
	public Iterable<Vendor> getRegisterVendor() {
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		try{
			List<Vendor> vendorList = this.vendorRepo.findAll();
			if(vendorList.isEmpty())
			{
				throw new VendorNotFoundException("Vendor Not available");
			}
			return vendorList;
		}catch(Exception e){
			throw new VendorNotFoundException(e);
		}
		
	}
	@PutMapping("/updateExistingVendorDetails")
	public Vendor putRegisterVendor(@RequestBody Vendor vendorReg) {
		LOGGER.info("Inside - VendorController.putRegisterVendor()");
		Optional<Vendor> findById = this.vendorRepo.findById((long) vendorReg.getId());
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
	
	@GetMapping("/supplierAddress")
	public List<SupAddress> allAdd() {
		LOGGER.info("Inside - VendorController.allAdd()");
		List<SupAddress> findAll = supAddRepo.findAll();
		return findAll;
	}
	
}
