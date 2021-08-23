package org.fintexel.supplier.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerFunctionalitiesMaster;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.CustomerUserDepartments;
import org.fintexel.supplier.customerentity.CustomerUserFunctionaliti;
import org.fintexel.supplier.customerentity.CustomerUserRoles;
import org.fintexel.supplier.customerentity.RolesMaster;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerFunctionalitiesMasterRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.CustomerUserDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerUserFunctionalitiRepo;
import org.fintexel.supplier.customerrepository.CustomerUserRolesRepo;
import org.fintexel.supplier.customerrepository.RolesMasterRepo;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCustomerDetails {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerDetails.class);

	@Autowired
	private CustomerUserDepartmentsRepo customerUserDepartmentsRepo;
	
	@Autowired
	private CustomerDepartmentsRepo customerDepartmentsRepo;
	
	@Autowired
	private CustomerUserFunctionalitiRepo customerUserFunctionalitiRepo;
	
	@Autowired
	private CustomerFunctionalitiesMasterRepo customerFunctionalitiesMasterRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;
	
	@Autowired
	private CustomerUserRolesRepo customerUserRolesRepo;
	
	@Autowired
	private RolesMasterRepo rolesMasterRepo;
	
	private String jwtToken;
	
//	String departmentName = null, UserFunctionalitiName = null;
	
	private List<CustomerDepartments> customerDepartments = new  ArrayList<>();
	
	private List<CustomerFunctionalitiesMaster> customerFunctionalitiesMasters  = new  ArrayList<>();
	
	public long getCustomerIdFromToken(String token) {
		LOGGER.info("Token is >>>> "+token);
		try {
			if (token != null && token.startsWith("Bearer ")) {
				jwtToken = token.substring(7);
				LOGGER.info("actual token is >>>> "+jwtToken);
				String username = jwtUtil.extractUsername(jwtToken);
				LOGGER.info("User name from token >>>>> "+username);
				Optional<CustomerRegister> findByUsername = customerRegisterRepo.findByUsername(username);
				if (findByUsername.isPresent()) {
					return findByUsername.get().getUserId();
				}
				else {
					// return -1 for customer not found
					return -1;
				}
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	public long getCIdFromToken(String token) {
		LOGGER.info("Token is >>>> "+token);
		try {
			if (token != null && token.startsWith("Bearer ")) {
				jwtToken = token.substring(7);
				LOGGER.info("actual token is >>>> "+jwtToken);
				String username = jwtUtil.extractUsername(jwtToken);
				LOGGER.info("User name from token >>>>> "+username);
				Optional<CustomerRegister> findByUsername = customerRegisterRepo.findByUsername(username);
				if (findByUsername.isPresent()) {
					return findByUsername.get().getcId();
				}
				else {
					// return -1 for customer not found
					return -1;
				}
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	public List<CustomerDepartments> getDepartments(long userId) {
		try {
			List<CustomerUserDepartments> findDepartmentIdByUserId = customerUserDepartmentsRepo.findByUserId(userId);
			if (findDepartmentIdByUserId.size() > 0)  {
				for(CustomerUserDepartments department : findDepartmentIdByUserId ) {
					Optional<CustomerDepartments> findDepartmentById = customerDepartmentsRepo.findById(department.getDepartmentId());
					//this.departmentName = this.departmentName + findDepartmentById.get().getDepartmentName() +",";
					this.customerDepartments.add(findDepartmentById.get());
				}
				return this.customerDepartments; 
			}
			else {
//				return this.departmentName = "Department not found for this user";
				throw new VendorNotFoundException("Department not found for this user");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	public List<CustomerFunctionalitiesMaster> getFunctionaliti(long userId) {
		
		try {
			
			List<CustomerUserFunctionaliti> findUserFunctionalitiIdByUserId = customerUserFunctionalitiRepo.findByUserId(userId);
			
			if (findUserFunctionalitiIdByUserId.size() > 0) {
				
				for(CustomerUserFunctionaliti customerUserFunctionaliti : findUserFunctionalitiIdByUserId) {
					Optional<CustomerFunctionalitiesMaster> findFunctionalitiById = customerFunctionalitiesMasterRepo.findById(customerUserFunctionaliti.getfId());
					
					//his.UserFunctionalitiName = this.UserFunctionalitiName + findFunctionalitiById.get().getfName() + ",";
					this.customerFunctionalitiesMasters.add(findFunctionalitiById.get());
				}
				return this.customerFunctionalitiesMasters;
			}
			else {
//				return this.UserFunctionalitiName= "Functionaliti not found for this user";
				throw new VendorNotFoundException("Functionality not found for this user");
				
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}
	
	public String getRoleByUserId(long userId) {
		try {
			
			Optional<CustomerUserRoles> findByUserId = customerUserRolesRepo.findByUserId(userId);
			if (findByUserId.isPresent()) {
				Optional<RolesMaster> findById = rolesMasterRepo.findById(findByUserId.get().getRoleId());
				if (findById.isPresent()) {
					return findById.get().getRole();
				} else {
					return "Role is not presen";
				}
			} else {
				return "Customer role not define";	
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	public List<CustomerDepartments> getDepartmentForSAdmin() {
		try {
			List<CustomerDepartments> findAll = customerDepartmentsRepo.findAll();
			
			if (findAll.size() > 0) {
				return findAll;
			} else {
				throw new VendorNotFoundException("Department not found");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	public List<CustomerFunctionalitiesMaster> getFunctionalitieForSAdmin() {
		try {
			List<CustomerFunctionalitiesMaster> findAll = customerFunctionalitiesMasterRepo.findAll();
			if (findAll.size() > 0) {
				return findAll;
			} else {
				throw new VendorNotFoundException("Functionality not found");
			}
		} catch (Exception e) {
			LOGGER.info("in Catch " + e.getMessage());
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	public long getCompanyProfileIdByCustomerId(long userId) {
		try {
			Optional<CustomerRegister> findById = customerRegisterRepo.findById(userId);
			if (findById.isPresent()) {
				return findById.get().getcId();
			} else {
				throw new VendorNotFoundException("Can't find any company profile whith the login user");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
