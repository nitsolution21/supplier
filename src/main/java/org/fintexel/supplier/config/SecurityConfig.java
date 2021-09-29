package org.fintexel.supplier.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.CorsFilter;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.services.CustomerDetailsServices;
import org.fintexel.supplier.services.VendorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private CustomerDetailsServices customerDetailsServices;

	
	@Autowired
	private YMLConfig myConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		

			
//				auth.userDetailsService(customerDetailsServices);
			
				auth.userDetailsService(vendorDetailsService);
				

			
	}
	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
				csrf()
				.disable()
				.cors().and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/login").permitAll()
				.antMatchers(HttpMethod.POST,"/registration").permitAll()
				.antMatchers(HttpMethod.GET,"/vendorLogin").permitAll()
				.antMatchers(HttpMethod.GET,"/vendorLogin/{id}").permitAll()
				.antMatchers(HttpMethod.PUT,"/vendorLogin/{id}").permitAll()
				.antMatchers(HttpMethod.POST,"/supplier").permitAll()
				.antMatchers(HttpMethod.POST,"/forgotPassword").permitAll()
				.antMatchers(HttpMethod.POST,"/recoverPassword").permitAll()
				.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
				.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
				.antMatchers(HttpMethod.GET,"/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
				//.antMatchers(HttpMethod.POST,"/upload").permitAll()
				.antMatchers(HttpMethod.POST,"/customer/login").permitAll()
//				.antMatchers(HttpMethod.POST,"/customer/registration").permitAll()
				.antMatchers(HttpMethod.POST,"/customer/login").permitAll()
				.antMatchers(HttpMethod.GET,"/vendor/pending/request/{code}").permitAll()
				.antMatchers(HttpMethod.POST,"/vendor/approved").permitAll()
				.antMatchers(HttpMethod.GET,"/vendor/pending").permitAll()
				.antMatchers(HttpMethod.POST,"/customer/po/pendingCustomerStatus").permitAll()
				.antMatchers(HttpMethod.GET,"/customer/po/pendingCustomer/details/{id}").permitAll()
				.antMatchers(HttpMethod.GET,"/customer/po/pendingCustomer").permitAll()
//				.antMatchers(HttpMethod.GET,"/download/{file}").permitAll()
//				.antMatchers(HttpMethod.GET,"/getOpenPo").permitAll()
//				.antMatchers(HttpMethod.POST,"/uploadRegionCountry").permitAll()
//				.antMatchers(HttpMethod.POST,"/update").permitAll()
//				.antMatchers(HttpMethod.POST,"/uploadRegType").permitAll()
//				.antMatchers(HttpMethod.POST,"/uploadDept").permitAll()
//				.antMatchers(HttpMethod.POST,"/uploadRole").permitAll()
//				.antMatchers(HttpMethod.POST,"/uploadCurrencyType").permitAll()
//				.antMatchers(HttpMethod.POST,"/uploadFunc").permitAll()
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

}
