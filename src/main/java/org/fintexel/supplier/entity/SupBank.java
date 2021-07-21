package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "SUP_BANK")
public class SupBank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANK_ID")
	private long bankId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	
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
	
	@Column(name = "STATUS")
	private String status;

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SupBank [bankId=" + bankId + ", supplierCode=" + supplierCode + ", bankName=" + bankName
				+ ", bankBranch=" + bankBranch + ", bankBic=" + bankBic + ", bankAccountNo=" + bankAccountNo
				+ ", currency=" + currency + ", transilRoutingNo=" + transilRoutingNo + ", chequeNo=" + chequeNo
				+ ", accountHolder=" + accountHolder + ", swiftCode=" + swiftCode + ", ifscCode=" + ifscCode
				+ ", country=" + country + ", bankEvidence=" + bankEvidence + ", evidencePath=" + evidencePath
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", status=" + status + "]";
	}

	public SupBank(long bankId, String supplierCode, String bankName, String bankBranch, String bankBic,
			String bankAccountNo, String currency, String transilRoutingNo, String chequeNo, String accountHolder,
			String swiftCode, String ifscCode, String country, String bankEvidence, String evidencePath, int createdBy,
			Date createdOn, int updatedBy, Date updatedOn, String status) {
		super();
		this.bankId = bankId;
		this.supplierCode = supplierCode;
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
		this.status = status;
	}

	public SupBank() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public SupBank(long bankId, String supplierCode, String bankName, String bankBranch, String bankBic,
			String bankAccountNo, String currency, String transilRoutingNo, String chequeNo, String accountHolder,
			String swiftCode, String ifscCode, String country, String bankEvidence, String evidencePath, String status) {
		super();
		this.bankId = bankId;
		this.supplierCode = supplierCode;
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
		this.status = status;
	}

	
	
	
	public static SupBank fromJson(String value) {
		JSONObject obj = new JSONObject(value);
		    return new SupBank (Long.parseLong((String) obj.get("bankId")) , (String)obj.get("supplierCode") ,
		    		(String) obj.get("bankName") ,(String) obj.get("bankBranch") ,(String) obj.get("bankBic") ,
		    		(String) obj.get("bankAccountNo") , (String) obj.get("currency") , (String) obj.get("transilRoutingNo") ,
		    		(String) obj.get("chequeNo") , (String) obj.get("accountHolder") , (String) obj.get("swiftCode") ,
		    		(String) obj.get("ifscCode") , (String) obj.get("country") , (String) obj.get("bankEvidence") ,
		    		(String) obj.get("evidencePath") ,(String) obj.get("status") );
	}
	
	
	
	
	
}
