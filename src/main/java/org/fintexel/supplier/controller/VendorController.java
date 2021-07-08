package org.fintexel.supplier.controller;

import java.util.List;

import org.springframework.validation.*;
import java.util.Optional;

import javax.validation.Valid;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.UserRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.validation.FieldValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@RestController
public class VendorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private VendorRegisterRepo vendorRepo;
	
	@Autowired
	private SupAddressRepo supAddRepo;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@PostMapping("/vendor")
	public VendorRegister postRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		try{
			if(fieldValidation.isEmail(vendorReg.getEmail()) ) {
				VendorRegister save = this.vendorRepo.save(vendorReg);
				return save;
			}else {
				throw new VendorNotFoundException("Validation error");
			}
		}catch(Exception e){
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/vendor")
	public List<VendorRegister> getRegisterVendors() { 
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		try{
			List<VendorRegister> vendorList = this.vendorRepo.findAll();
			if(vendorList.isEmpty())
			{
				throw new VendorNotFoundException();
			}
			return vendorList;
		}catch(Exception e){
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@GetMapping("/vendor/{vendorId}")
	public Optional<VendorRegister> getRegisterVendor(@PathVariable() int vendorId) {
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		try{
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			LOGGER.info("Inside - VendorController.getRegisterVendor() - " +findById);
			if(!(findById.isPresent()))
			{
				throw new VendorNotFoundException("Vendor Not Found");
			}
			return findById;
		}catch(Exception e){
			throw new VendorNotFoundException(e.getMessage());
		}		
	}
	
	
	@PutMapping("/vendor/{vendorId}")
	public VendorRegister putRegisterVendor(@PathVariable("vendorId") int vendorId,@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.putRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if(!findById.isPresent()) {
				throw new VendorNotFoundException("Vendor Not Available");
			}else {
				if(fieldValidation.isEmail(vendorReg.getEmail()) ) {
					VendorRegister vr = findById.get();
					vr.setEmail(vendorReg.getEmail());
					vr.setSupplierCompName(vendorReg.getSupplierCompName());
					vr.setStatus(vendorReg.getStatus());
					return this.vendorRepo.save(vr);
				}else {
					throw new VendorNotFoundException("Validation error"); 
				}
				
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage()); 
		}
	}
	
	@DeleteMapping("/vendor/{vendorId}")
	public Object deleteRegisterVendor(@PathVariable() int vendorId) {
		LOGGER.info("Inside - VendorController.deleteRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if(!(findById.isPresent())) {
				throw new VendorNotFoundException("Vendor Does not exist");
			}else {
				this.vendorRepo.deleteById(vendorId);
				return "vendor deleted - "+vendorId;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	@GetMapping("/")
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
