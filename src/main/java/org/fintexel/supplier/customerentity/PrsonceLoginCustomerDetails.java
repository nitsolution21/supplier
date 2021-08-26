package org.fintexel.supplier.customerentity;

import java.util.ArrayList;
import java.util.List;

public class PrsonceLoginCustomerDetails {
	
	private CustomerAddress customerAddress;
	
	private List<CustomerDepartments> customerDepartments = new ArrayList<CustomerDepartments>();

	public PrsonceLoginCustomerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrsonceLoginCustomerDetails(CustomerAddress customerAddress, List<CustomerDepartments> customerDepartments) {
		super();
		this.customerAddress = customerAddress;
		this.customerDepartments = customerDepartments;
	}

	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}

	public List<CustomerDepartments> getCustomerDepartments() {
		return customerDepartments;
	}

	public void setCustomerDepartments(List<CustomerDepartments> customerDepartments) {
		this.customerDepartments = customerDepartments;
	}

	@Override
	public String toString() {
		return "PrsonceLoginCustomerDetails [customerAddress=" + customerAddress + ", customerDepartments="
				+ customerDepartments + "]";
	}
	
	
}
