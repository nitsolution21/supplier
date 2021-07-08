package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_BANK")
public class SupBank {
	
	@Id
	@Column(name = "BANK_ID")
	private int bankId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "SUPPLIER_COST_CODE")
	private String supplierCostCode;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "BANK_BRANCH")
	private String bankBranch;
	
	@Column(name = "BANK_BIC")
	private String bankBic;
	
	@Column(name = "BANK_ACCOUNT_NO")
	private String bankAccountNo;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "TRANSIT_ROUTING_NO")
	private String transilRoutingNo;
	
	@Column(name = "CHEQUE_NO")
	private String chequeNo;
	
	@Column(name = "ACCOUNT_HOLDER")
	private String accountHolder;
	
	@Column(name = "SWIFT_CODE")
	private String swiftCode;
	
	@Column(name = "IFSC_CODE")
	private String ifscCode;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "BANK_EVIDENCE")
	private String bankEvidence;
	
	@Column(name = "EVIDENCE_PATH")
	private String evidencePath;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	public SupBank(int bankId, String supplierCode, String supplierCostCode, String bankName, String bankBranch,
			String bankBic, String bankAccountNo, String currency, String transilRoutingNo, String chequeNo,
			String accountHolder, String swiftCode, String ifscCode, String country, String bankEvidence,
			String evidencePath, int createdBy, Date createdOn, int updatedBy, Date updatedOn) {
		super();
		this.bankId = bankId;
		this.supplierCode = supplierCode;
		this.supplierCostCode = supplierCostCode;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.bankBic = bankBic;
		this.bankAccountNo = bankAccountNo;
		this.currency = currency;
		this.transilRoutingNo = transilRoutingNo;
		this.chequeNo = chequeNo;
		this.accountHolder = accountHolder;
		this.swiftCode = swiftCode;
		this.ifscCode = ifscCode;
		this.country = country;
		this.bankEvidence = bankEvidence;
		this.evidencePath = evidencePath;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public SupBank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierCostCode() {
		return supplierCostCode;
	}

	public void setSupplierCostCode(String supplierCostCode) {
		this.supplierCostCode = supplierCostCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankBic() {
		return bankBic;
	}

	public void setBankBic(String bankBic) {
		this.bankBic = bankBic;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransilRoutingNo() {
		return transilRoutingNo;
	}

	public void setTransilRoutingNo(String transilRoutingNo) {
		this.transilRoutingNo = transilRoutingNo;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBankEvidence() {
		return bankEvidence;
	}

	public void setBankEvidence(String bankEvidence) {
		this.bankEvidence = bankEvidence;
	}

	public String getEvidencePath() {
		return evidencePath;
	}

	public void setEvidencePath(String evidencePath) {
		this.evidencePath = evidencePath;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
