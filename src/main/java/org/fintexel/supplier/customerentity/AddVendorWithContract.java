package org.fintexel.supplier.customerentity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AddVendorWithContract {
	
		private long registerId;
		private String email;
		private String supplierCompName;
		private String username;
		private String password;
		private String status;
		private String processId;
		private String taskId;
		private int createdBy;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date createdOn;
		private int updatedBy;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date updatedOn;
		private String supplierCode;
		private String registrationType;
		private String registrationNo; 
		private String costCenter;
		private String remarks;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") private Date lastlogin;
		private Long contractId;
		private Long cId;
		private String contractType;
		private Long contractTerms;
		private String contractProof;
		private String contractLocation;
		public long getRegisterId() {
			return registerId;
		}
		public void setRegisterId(long registerId) {
			this.registerId = registerId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSupplierCompName() {
			return supplierCompName;
		}
		public void setSupplierCompName(String supplierCompName) {
			this.supplierCompName = supplierCompName;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getProcessId() {
			return processId;
		}
		public void setProcessId(String processId) {
			this.processId = processId;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
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
		public String getSupplierCode() {
			return supplierCode;
		}
		public void setSupplierCode(String supplierCode) {
			this.supplierCode = supplierCode;
		}
		public String getRegistrationType() {
			return registrationType;
		}
		public void setRegistrationType(String registrationType) {
			this.registrationType = registrationType;
		}
		public String getRegistrationNo() {
			return registrationNo;
		}
		public void setRegistrationNo(String registrationNo) {
			this.registrationNo = registrationNo;
		}
		public String getCostCenter() {
			return costCenter;
		}
		public void setCostCenter(String costCenter) {
			this.costCenter = costCenter;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public Date getLastlogin() {
			return lastlogin;
		}
		public void setLastlogin(Date lastlogin) {
			this.lastlogin = lastlogin;
		}
		public Long getContractId() {
			return contractId;
		}
		public void setContractId(Long contractId) {
			this.contractId = contractId;
		}
		public Long getcId() {
			return cId;
		}
		public void setcId(Long cId) {
			this.cId = cId;
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
		@Override
		public String toString() {
			return "AddVendorWithContract [registerId=" + registerId + ", email=" + email + ", supplierCompName="
					+ supplierCompName + ", username=" + username + ", password=" + password + ", status=" + status
					+ ", processId=" + processId + ", taskId=" + taskId + ", createdBy=" + createdBy + ", createdOn="
					+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", supplierCode="
					+ supplierCode + ", registrationType=" + registrationType + ", registrationNo=" + registrationNo
					+ ", costCenter=" + costCenter + ", remarks=" + remarks + ", lastlogin=" + lastlogin
					+ ", contractId=" + contractId + ", cId=" + cId + ", contractType=" + contractType
					+ ", contractTerms=" + contractTerms + ", contractProof=" + contractProof + ", contractLocation="
					+ contractLocation + "]";
		}
		public AddVendorWithContract(long registerId, String email, String supplierCompName, String username,
				String password, String status, String processId, String taskId, int createdBy, Date createdOn,
				int updatedBy, Date updatedOn, String supplierCode, String registrationType, String registrationNo,
				String costCenter, String remarks, Date lastlogin, Long contractId, Long cId, String contractType,
				Long contractTerms, String contractProof, String contractLocation) {
			super();
			this.registerId = registerId;
			this.email = email;
			this.supplierCompName = supplierCompName;
			this.username = username;
			this.password = password;
			this.status = status;
			this.processId = processId;
			this.taskId = taskId;
			this.createdBy = createdBy;
			this.createdOn = createdOn;
			this.updatedBy = updatedBy;
			this.updatedOn = updatedOn;
			this.supplierCode = supplierCode;
			this.registrationType = registrationType;
			this.registrationNo = registrationNo;
			this.costCenter = costCenter;
			this.remarks = remarks;
			this.lastlogin = lastlogin;
			this.contractId = contractId;
			this.cId = cId;
			this.contractType = contractType;
			this.contractTerms = contractTerms;
			this.contractProof = contractProof;
			this.contractLocation = contractLocation;
		}
		public AddVendorWithContract() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
	

}
