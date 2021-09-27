package org.fintexel.supplier.customerentity;

import java.util.Date;
import java.util.List;

import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.entity.SupDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SupplierAllDetailsForPO {
	
//	private String supplierCode;
//	private Long registerId;
//	private String supplierCompName;
//	private String registrationType;
//	private String registrationNo; 
//	private String status;
//	private String costCenter;
//	private String remarks;
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date lastlogin;
//	private int createdBy;
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date createdOn;
//	private int updatedBy;
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")private Date updatedOn;
//	private long departmentId;
//	private String departmentName;
//	private String supplierContact1;
//	private String supplierContact2;
//	private String email;
//	private String phoneno;
//	private String alternatePhoneno;
//	private long bankId;
//	private String bankName;
//	private String bankBranch;
//	private String bankBic;
//	private String bankAccountNo;
//	private String currency;
//	private String transilRoutingNo;
//	private String chequeNo;
//	private String accountHolder;
//	private String swiftCode;
//	private String ifscCode;
//	private String country;
//	private String bankEvidence;
//	private String evidencePath;
//	private Long addressId;
//	private String addressType;
//	private String address1;
//	private String address2;
//	private String city;
//	private int postalCode;
//	private String region;
//	private String addressProof;
//	private String addressProofPath;
	
	private SupDetails supDetails;
	private List<SupDepartment> findBySupplierCodeDepertment;
	private SupAddress supAddress;
	private SupBank supBank;
	private List<InventoryDetails> findBySupplierCodeInventory;
	private List<ItemCategory> findBySupplierCodeCategory;
	private String logo;
	public SupDetails getSupDetails() {
		return supDetails;
	}
	public void setSupDetails(SupDetails supDetails) {
		this.supDetails = supDetails;
	}
	public List<SupDepartment> getFindBySupplierCodeDepertment() {
		return findBySupplierCodeDepertment;
	}
	public void setFindBySupplierCodeDepertment(List<SupDepartment> findBySupplierCodeDepertment) {
		this.findBySupplierCodeDepertment = findBySupplierCodeDepertment;
	}
	public SupAddress getSupAddress() {
		return supAddress;
	}
	public void setSupAddress(SupAddress supAddress) {
		this.supAddress = supAddress;
	}
	public SupBank getSupBank() {
		return supBank;
	}
	public void setSupBank(SupBank supBank) {
		this.supBank = supBank;
	}
	public List<InventoryDetails> getFindBySupplierCodeInventory() {
		return findBySupplierCodeInventory;
	}
	public void setFindBySupplierCodeInventory(List<InventoryDetails> findBySupplierCodeInventory) {
		this.findBySupplierCodeInventory = findBySupplierCodeInventory;
	}
	public List<ItemCategory> getFindBySupplierCodeCategory() {
		return findBySupplierCodeCategory;
	}
	public void setFindBySupplierCodeCategory(List<ItemCategory> findBySupplierCodeCategory) {
		this.findBySupplierCodeCategory = findBySupplierCodeCategory;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Override
	public String toString() {
		return "SupplierAllDetailsForPO [supDetails=" + supDetails + ", findBySupplierCodeDepertment="
				+ findBySupplierCodeDepertment + ", supAddress=" + supAddress + ", supBank=" + supBank
				+ ", findBySupplierCodeInventory=" + findBySupplierCodeInventory + ", findBySupplierCodeCategory="
				+ findBySupplierCodeCategory + ", logo=" + logo + "]";
	}
	public SupplierAllDetailsForPO(SupDetails supDetails, List<SupDepartment> findBySupplierCodeDepertment,
			SupAddress supAddress, SupBank supBank, List<InventoryDetails> findBySupplierCodeInventory,
			List<ItemCategory> findBySupplierCodeCategory, String logo) {
		super();
		this.supDetails = supDetails;
		this.findBySupplierCodeDepertment = findBySupplierCodeDepertment;
		this.supAddress = supAddress;
		this.supBank = supBank;
		this.findBySupplierCodeInventory = findBySupplierCodeInventory;
		this.findBySupplierCodeCategory = findBySupplierCodeCategory;
		this.logo = logo;
	}
	public SupplierAllDetailsForPO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	

	
}
