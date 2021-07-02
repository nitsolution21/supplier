package org.fintexel.supplier;

import org.fintexel.supplier.config.YMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupplierApplication implements CommandLineRunner {
	
	@Autowired
	private YMLConfig myConfig;

	public static void main(String[] args) {
		SpringApplication.run(SupplierApplication.class, args);
		System.out.println("In Main Method");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("-------------------------------------");
		System.out.println("name : "+myConfig.getName());
		System.out.println("environment : "+myConfig.getEnvironment());
		System.out.println("contextpath : "+myConfig.getContextpath());
		System.out.println("servers: "+myConfig.getServers());
		System.out.println("-------------------------------------");
	}
	

}
