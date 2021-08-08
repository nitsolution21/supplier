package org.fintexel.supplier.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fintexel.supplier.entity.VendorDetails;
import org.fintexel.supplier.exceptions.VendorErrorResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.exceptions.VendorRestExceptionHandler;
import org.fintexel.supplier.helper.JwtUtil;
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
				// TODO: handle exception
				throw new VendorNotFoundException("token is not validated");
			}
			
			
//			UserDetails userDetails = customerDetailsServices.loadUserByUsername(username);
			
//			UserDetails userDetails = vendorDetailsService.loadUserByUsername(username);
			
			if ((myConfig.getContextpath() +"/login").equals("/dev/login")) {
				System.out.println("in vendor login");
				this.userDetails = vendorDetailsService.loadUserByUsername(username);
				
			} 
			else if((myConfig.getContextpath() + "/customer/login").equals("/dev/customer/login")){
				System.out.println("in customer login");
				this.userDetails = customerDetailsServices.loadUserByUsername(username);
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
