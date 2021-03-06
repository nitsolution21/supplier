package org.fintexel.supplier;

import javax.servlet.http.HttpServletRequest;

import org.fintexel.supplier.config.YMLConfig;
import org.fintexel.supplier.validation.FieldValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootApplication
@ComponentScan(basePackages= {"org.fintexel.supplier.*"})
public class SupplierApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierApplication.class);
	
	@Autowired
	private YMLConfig myConfig;
	

	@Bean
	public FieldValidation fieldValidation() {
		return new FieldValidation();
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	
	@Bean
	public BCryptPasswordEncoder bcryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
	@Bean
	public WebClient.Builder getWebClint(){
		return WebClient.builder();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(SupplierApplication.class, args);
		LOGGER.info("In Main Method");

	}

	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : "+myConfig.getName());
		LOGGER.info("environment : "+myConfig.getEnvironment());
		LOGGER.info("contextpath : "+myConfig.getContextpath());
		LOGGER.info("servers: "+myConfig.getServers());
		LOGGER.info("-------------------------------------");
	}
	

}
