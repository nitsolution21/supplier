package org.fintexel.supplier.services;

import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerDetails;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.entity.VendorDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VendorDetailsService implements UserDetailsService {
	
	@Autowired
	private VendorRegisterRepo vendorRegisterRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<VendorRegister> vendor = vendorRegisterRepo.findByUsername(username);
		vendor.orElseThrow(() -> new VendorNotFoundException("Not found "+username));
		return vendor.map(VendorDetails :: new).get();
	}
	


}
