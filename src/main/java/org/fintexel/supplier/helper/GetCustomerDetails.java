package org.fintexel.supplier.helper;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCustomerDetails {

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
	
	String departmentName = null, UserFunctionalitiName = null;
	
	public long getCustomerIdFromToken(String token) {
		String username = jwtUtil.extractUsername(token);
		Optional<CustomerRegister> findByUsername = customerRegisterRepo.findByUsername(username);
		if (findByUsername.isPresent()) {
			return findByUsername.get().getUserId();
		}
		else {
			// return -1 for customer not found
			return -1;
		}
	}
	
	public String getDepartments(long userId) {
		
		List<CustomerUserDepartments> findDepartmentIdByUserId = customerUserDepartmentsRepo.findByUserId(userId);
		if (findDepartmentIdByUserId.size() > 0)  {
			for(CustomerUserDepartments department : findDepartmentIdByUserId ) {
				Optional<CustomerDepartments> findDepartmentById = customerDepartmentsRepo.findById(department.getDepartmentId());
				this.departmentName = this.departmentName + findDepartmentById.get().getDepartmentName() +",";
			}
			return this.departmentName; 
		}
		else {
			return this.departmentName = "Department not found for this user";
		}
	}
	
	public String getFunctionaliti(long userId) {
		
		List<CustomerUserFunctionaliti> findUserFunctionalitiIdByUserId = customerUserFunctionalitiRepo.findByUserId(userId);
		
		if (findUserFunctionalitiIdByUserId.size() > 0) {
			
			for(CustomerUserFunctionaliti customerUserFunctionaliti : findUserFunctionalitiIdByUserId) {
				Optional<CustomerFunctionalitiesMaster> findFunctionalitiById = customerFunctionalitiesMasterRepo.findById(customerUserFunctionaliti.getfId());
				
				this.UserFunctionalitiName = this.UserFunctionalitiName + findFunctionalitiById.get().getfName() + ",";
			}
			return this.UserFunctionalitiName;
		}
		else {
			return this.UserFunctionalitiName= "Functionaliti not found for this user";
		}
	}
	
	public String getRoleByUserId(long userId) {
		Optional<CustomerUserRoles> findByUserId = customerUserRolesRepo.findByUserId(userId);
		if (findByUserId.isPresent()) {
			Optional<RolesMaster> findById = rolesMasterRepo.findById(findByUserId.get().getRoleId());
			if (findById.isPresent()) {
				return findById.get().getRole();
			} else {
				return "Role is not presen";
			}
		} else {
			return "Customer role not define";		}
	}
}
