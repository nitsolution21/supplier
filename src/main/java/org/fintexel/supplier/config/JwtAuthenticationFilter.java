package org.fintexel.supplier.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.entity.VendorDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorErrorResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.exceptions.VendorRestExceptionHandler;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.services.CustomerDetailsServices;
import org.fintexel.supplier.services.VendorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private CustomerDetailsServices customerDetailsServices; 
	
	@Autowired
	private YMLConfig myConfig;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;
	
	@Autowired
	private VendorRegisterRepo vendorRegisterRepo;
	
	UserDetails userDetails;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String headers = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		
		if (headers != null && headers.startsWith("Bearer ")) {
			jwtToken = headers.substring(7);
			
			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (Exception e) {
				throw new VendorNotFoundException("token is not validated");
			}
			
			
			 Optional<VendorRegister> findVendorByUsername = vendorRegisterRepo.findByUsername(username);
			if (findVendorByUsername.isPresent()) {
				this.userDetails = vendorDetailsService.loadUserByUsername(username);
			} else {
				Optional<CustomerRegister> findCustomerByUsername = customerRegisterRepo.findByUsername(username);
				if (findCustomerByUsername.isPresent()) {
					this.userDetails = customerDetailsServices.loadUserByUsername(username);
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
				
			}
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				throw new VendorNotFoundException("token is not validated");
			}
			
		}
		
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//	    response.setHeader("Access-Control-Allow-Credentials", "true");
//	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//	    response.setHeader("Access-Control-Max-Age", "3600");
//	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
//		
		
		filterChain.doFilter(request, response);
		
	}
	
	
	
}
