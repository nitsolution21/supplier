package org.fintexel.supplier.customerentity;

import java.util.ArrayList;
import java.util.List;

public class PrsonceLoginCustomerDetails {
	
	private CustomerAddress customerAddress;
	
	private List<CustomerDepartments> customerDepartments = new ArrayList<CustomerDepartments>();
	
	private String poNumber;
	
	private long contractTerms;
	
	private long contractId;

	public PrsonceLoginCustomerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrsonceLoginCustomerDetails(CustomerAddress customerAddress, List<CustomerDepartments> customerDepartments,
			String poNumber, long contractTerms, long contractId) {
		super();
		this.customerAddress = customerAddress;
		this.customerDepartments = customerDepartments;
		this.poNumber = poNumber;
		this.contractTerms = contractTerms;
		this.contractId = contractId;
	}

	@Override
	public String toString() {
		return "PrsonceLoginCustomerDetails [customerAddress=" + customerAddress + ", customerDepartments="
				+ customerDepartments + ", poNumber=" + poNumber + ", contractTerms=" + contractTerms + ", contractId="
				+ contractId + "]";
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

	public long getContractTerms() {
		return contractTerms;
	}

	public void setContractTerms(long contractTerms) {
		this.contractTerms = contractTerms;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	
	
	
}
