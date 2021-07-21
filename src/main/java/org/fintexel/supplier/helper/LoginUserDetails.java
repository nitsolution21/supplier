package org.fintexel.supplier.helper;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetails {
	
	@Autowired
	private VendorRegisterRepo registerRepo;
	
	@Autowired
	private SupDetailsRepo detailsRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private String jwtToken;
	
	public String getLoginSupplierCode(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);
			String userName = jwtUtil.extractUsername(jwtToken);
			Optional<VendorRegister> findByUsername = registerRepo.findByUsername(userName);
			if (!findByUsername.isPresent()) {
				throw new VendorNotFoundException("Vendor not found");
			}
			else {
				List<SupDetails> findByRegisterId = detailsRepo.findByRegisterId(findByUsername.get().getRegisterId());
				if (findByRegisterId.size() > 0) {
					return findByRegisterId.get(0).getSupplierCode();
				} else {
					throw new VendorNotFoundException("Vendor Details Not found");
				}
			}
		}
		else {
			throw new VendorNotFoundException("Token Not Valid");
		}
	}
}
