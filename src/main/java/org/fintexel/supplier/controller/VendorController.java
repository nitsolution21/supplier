package org.fintexel.supplier.controller;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.SupplierApplication;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupStagingTable;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorErrorResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.SupAddRepo;
import org.fintexel.supplier.repository.UserRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private VendorRegisterRepo vendorRepo;
	
	@Autowired
	private SupAddRepo supAddRepo;
	
	@PostMapping("/registerVendor")
	public VendorRegister postRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		try{
			VendorRegister save = this.vendorRepo.save(vendorReg);
			return save;
		}catch(Exception e){
			throw new VendorErrorResponse(e);
		}
	}
	
	@GetMapping("/registerVendors")
	public List<VendorRegister> getRegisterVendors() { 
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		try{
			List<VendorRegister> vendorList = this.vendorRepo.findAll();
			if(vendorList.isEmpty())
			{
				throw new VendorNotFoundException("Vendor Not Available");
			}
			return vendorList;
		}catch(Exception e){
			throw new VendorNotFoundException(e);
		}
		
	}
	
	
	@GetMapping("/registerVendor")
	public Optional<VendorRegister> getRegisterVendor(@RequestBody int vendorId) {
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		try{
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if(findById.isEmpty())
			{
				throw new VendorNotFoundException("Vendor Not Available");
			}
			return findById;
		}catch(Exception e){
			throw new VendorNotFoundException(e);
		}
		
	}
	
	
	@PutMapping("/vendorDetails")
	public VendorRegister putRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.putRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById((int) vendorReg.getRegisterId());
			return this.vendorRepo.save(vendorReg);
		}catch(Exception e) {
			throw new VendorErrorResponse("Can't Update Vendor"); 
		}
	}
	
	@DeleteMapping("/vendor")
	public VendorRegister deleteRegisterVendor(@RequestBody int vendorId) {
		LOGGER.info("Inside - VendorController.deleteRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if(findById.isEmpty()) {
				throw new VendorNotFoundException("Vendor Not Available");
			}else {
				VendorRegister fromOptional=findById.get();
				fromOptional.setStatus("1");
				return this.vendorRepo.save(fromOptional);
			}
		}catch(Exception e) {
			throw new VendorErrorResponse(e);
		}
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
