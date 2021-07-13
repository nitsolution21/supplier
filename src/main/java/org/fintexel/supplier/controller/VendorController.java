package org.fintexel.supplier.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.*;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.User;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.UserRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.validation.FieldValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@RestController
@CrossOrigin(origins = "*")
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
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private String jwtToken;
	
	@PostMapping("/vendor")
	public VendorRegister postRegisterVendor(@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.registerVendor()");
		try{
			if((fieldValidation.isEmail(vendorReg.getEmail()) &  (fieldValidation.isEmpty(vendorReg.getSupplierCompName())) )) {
				VendorRegister filterVendorReg=new VendorRegister();
				filterVendorReg.setEmail(vendorReg.getEmail());
				filterVendorReg.setSupplierCompName(vendorReg.getSupplierCompName());
				filterVendorReg.setStatus(vendorReg.getStatus());
				List<VendorRegister> findAll = vendorRepo.findAll();
				for (VendorRegister find : findAll) {
					if(find.getEmail().equals(vendorReg.getEmail())) {
						throw new VendorNotFoundException("Vendor Email already exist");
					}
				}
//				try {
//					RestTemplate restTemplate=new RestTemplate();
//						List getObject=restTemplate.getForObject("url", ArrayList.class, filterVendorReg);
//						if(getObject.status==1){
//							filterVendorReg.setUsername(getObject.get(0).username);
//							filterVendorReg.setPassword(getObject.get(0).password);
//						}
//				}catch(Exception e) {
//					
//				}
				 System.out.println(java.util.UUID.randomUUID().toString());
				filterVendorReg.setUsername(vendorReg.getEmail());
				filterVendorReg.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));
				VendorRegister save = this.vendorRepo.save(filterVendorReg);
				return save;
			}else {
				throw new VendorNotFoundException("Validation error");
			}
		}catch(Exception e){
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/vendor")
	public VendorRegister getRegisterVendors(@RequestHeader (name="Authorization") String token) { 
		LOGGER.info("Inside - VendorController.getRegisterVendor()");
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);
			
			try{
				String userName = jwtUtil.extractUsername(jwtToken);
//				List<VendorRegister> vendorList = this.vendorRepo.findAll();
				Optional<VendorRegister> findByUsername = vendorRepo.findByUsername(userName);
				if(findByUsername.isEmpty())
				{
					throw new VendorNotFoundException();
				}
				return findByUsername.get();
			}catch(Exception e){
				throw new VendorNotFoundException(e.getMessage());
			}
		} 
		
		else {
			throw new VendorNotFoundException("Token is not valid");
		}
	}
	
	@GetMapping("/vendor/{vendorId}")
	public Optional<VendorRegister> getRegisterVendor(@PathVariable() long vendorId) {
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
	public VendorRegister putRegisterVendor(@PathVariable("vendorId") long vendorId,@RequestBody VendorRegister vendorReg) {
		LOGGER.info("Inside - VendorController.putRegisterVendor()");
		try {
			Optional<VendorRegister> findById = this.vendorRepo.findById(vendorId);
			if(!findById.isPresent()) {
				throw new VendorNotFoundException("Vendor Not Available");
			}else {
				if((fieldValidation.isEmail(vendorReg.getEmail())) &  (fieldValidation.isEmpty(vendorReg.getSupplierCompName())) & (vendorReg.getRegisterId()==vendorId)) {
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
	public Object deleteRegisterVendor(@PathVariable() long vendorId) {
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
