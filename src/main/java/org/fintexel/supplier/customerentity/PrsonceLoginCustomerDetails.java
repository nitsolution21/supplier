package org.fintexel.supplier.customerentity;

import java.util.ArrayList;
import java.util.List;

public class PrsonceLoginCustomerDetails {
	
	private CustomerAddress customerAddress;
	
	private List<CustomerDepartments> customerDepartments = new ArrayList<CustomerDepartments>();
	
	private String poNumber;

	public PrsonceLoginCustomerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrsonceLoginCustomerDetails(CustomerAddress customerAddress, List<CustomerDepartments> customerDepartments,
			String poNumber) {
		super();
		this.customerAddress = customerAddress;
		this.customerDepartments = customerDepartments;
		this.poNumber = poNumber;
	}

	@Override
	public String toString() {
		return "PrsonceLoginCustomerDetails [customerAddress=" + customerAddress + ", customerDepartments="
				+ customerDepartments + ", poNumber=" + poNumber + "]";
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

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	
	
	
}
