package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUP_CONTRACT")
public class SupContract {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONTRACT_ID")
	private Long contractId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "BANK_ID")
	private int bankId;
	
	@Column(name = "CONTRACT_TYPE")
	private String contractType;
	
	@Column(name = "CONTRACT_TERMS")
	private int contractTerms;
	
	@Column(name = "CONTRACT_PROOF")
	private String contractProof;
	
	@Column(name = "CONTRACT_LOCATION")
	private String contractLocation;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public int getContractTerms() {
		return contractTerms;
	}

	public void setContractTerms(int contractTerms) {
		this.contractTerms = contractTerms;
	}

	public String getContractProof() {
		return contractProof;
	}

	public void setContractProof(String contractProof) {
		this.contractProof = contractProof;
	}

	public String getContractLocation() {
		return contractLocation;
	}

	public void setContractLocation(String contractLocation) {
		this.contractLocation = contractLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "SupContract [contractId=" + contractId + ", supplierCode=" + supplierCode + ", bankId=" + bankId
				+ ", contractType=" + contractType + ", contractTerms=" + contractTerms + ", contractProof="
				+ contractProof + ", contractLocation=" + contractLocation + ", status=" + status + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ "]";
	}

	public SupContract(Long contractId, String supplierCode, int bankId, String contractType, int contractTerms,
			String contractProof, String contractLocation, String status, int createdBy, Date createdOn, int updatedBy,
			Date updatedOn) {
		super();
		this.contractId = contractId;
		this.supplierCode = supplierCode;
		this.bankId = bankId;
		this.contractType = contractType;
		this.contractTerms = contractTerms;
		this.contractProof = contractProof;
		this.contractLocation = contractLocation;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public SupContract() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}
