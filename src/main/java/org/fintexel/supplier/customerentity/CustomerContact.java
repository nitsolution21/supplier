package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUS_CONTRACT")
public class CustomerContact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONTRACT_ID") private Long contractId;
	@Column(name = "CONTRACT_TYPE") private String contractType;
	@Column(name = "CONTRACT_TERMS") private Long contractTerms;
	@Column(name = "CONTRACT_PROOF") private String contractProof;
	@Column(name = "CONTRACT_LOCATION") private String contractLocation;
	@Column(name = "CREATED_BY") private String createdBy;
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "UPDATED_ON") private Date updatedOn;
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Long getContractTerms() {
		return contractTerms;
	}
	public void setContractTerms(Long contractTerms) {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
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
		return "CustomerContact [contractId=" + contractId + ", contractType=" + contractType + ", contractTerms="
				+ contractTerms + ", contractProof=" + contractProof + ", contractLocation=" + contractLocation
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + "]";
	}
	public CustomerContact(Long contractId, String contractType, Long contractTerms, String contractProof,
			String contractLocation, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.contractId = contractId;
		this.contractType = contractType;
		this.contractTerms = contractTerms;
		this.contractProof = contractProof;
		this.contractLocation = contractLocation;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	public CustomerContact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
