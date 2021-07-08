package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUP_CONTRACT")
public class SupContract {
	
	@Id
	@Column(name = "CONTRACT_ID")
	private int contractId;
	
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
	
	@Column(name = "CREATED_BY")
	private int createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "UPDATED_BY")
	private int updatedBy;
	
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

}
