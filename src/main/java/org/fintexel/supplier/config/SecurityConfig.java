package org.fintexel.supplier.config;

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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private VendorDetailsService vendorDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
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
				.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
				.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and()...
//    }
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

}
