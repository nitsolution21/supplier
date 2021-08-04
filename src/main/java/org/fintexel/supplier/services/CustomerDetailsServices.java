package org.fintexel.supplier.services;

import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerDetails;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServices implements UserDetailsService {

	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CustomerRegister> customer = customerRegisterRepo.findByUsername(username);
		customer.orElseThrow(() -> new UsernameNotFoundException("Not found"+username));
		return customer.map(CustomerDetails :: new).get();
	}
	
}
