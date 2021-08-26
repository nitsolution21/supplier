package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_PO")
public class PurchesOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PO_ID") private Long POId;
	@Column(name = "CID") private int cId;
	@Column(name = "PO_NUMBER") private String poNumber;
	@Column(name = "USER_ID") private Long userId;
	@Column(name = "SUPPLIER_CODE") private String supplierCode;
	@Column(name = "DEPARTMENT_ID") private Long departmentId;
	@Column(name = "CUS_ADDR_ID") private Long cusAddrId;
	@Column(name = "CUS_ADDR_TEXT") private String cusAddrText;
	@Column(name = "SUP_ADDR_ID") private Long supAddrId;
	@Column(name = "SUP_ADDR_TEXT") private String supAddrText;
	@Column(name = "CONTRACT_ID") private Long contractId;
	@Column(name = "CONTRACT_TERMS") private int contractTerms;
	@Column(name = "COMMENT") private String comment;
	@Column(name = "SHIP_TO_ID") private Long shipToId;
	@Column(name = "SHIP_TO_TEXT") private String shipToText;
	@Column(name = "BILL_TO_ID") private Long billToId;
	@Column(name = "BILL_TO_TEXT") private String billToText;
	@Column(name = "DELIVERY_TO_ID") private Long deliveryToId;
	@Column(name = "DELIVERY_TO_TEXT") private String deliveryToText;
	@Column(name = "CUR_TYPE") private String curType;
	@Column(name = "AMOUNT") private float amount;
	@Column(name = "STATUS") private String status;
	@Column(name = "STATUS_COMMENT") private String statusComment;
	@Column(name = "CREATED_BY") private String createdBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "IS_DELETED") private int isDeleted;
	
	
	public PurchesOrder() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PurchesOrder(Long pOId, int cId, String poNumber, Long userId, String supplierCode, Long departmentId,
			Long cusAddrId, String cusAddrText, Long supAddrId, String supAddrText, Long contractId, int contractTerms,
			String comment, Long shipToId, String shipToText, Long billToId, String billToText, Long deliveryToId,
			String deliveryToText, String curType, float amount, String status, String statusComment, String createdBy,
			Date createdOn, int isDeleted) {
		super();
		POId = pOId;
		this.cId = cId;
		this.poNumber = poNumber;
		this.userId = userId;
		this.supplierCode = supplierCode;
		this.departmentId = departmentId;
		this.cusAddrId = cusAddrId;
		this.cusAddrText = cusAddrText;
		this.supAddrId = supAddrId;
		this.supAddrText = supAddrText;
		this.contractId = contractId;
		this.contractTerms = contractTerms;
		this.comment = comment;
		this.shipToId = shipToId;
		this.shipToText = shipToText;
		this.billToId = billToId;
		this.billToText = billToText;
		this.deliveryToId = deliveryToId;
		this.deliveryToText = deliveryToText;
		this.curType = curType;
		this.amount = amount;
		this.status = status;
		this.statusComment = statusComment;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.isDeleted = isDeleted;
	}


	@Override
	public String toString() {
		return "PurchesOrder [POId=" + POId + ", cId=" + cId + ", poNumber=" + poNumber + ", userId=" + userId
				+ ", supplierCode=" + supplierCode + ", departmentId=" + departmentId + ", cusAddrId=" + cusAddrId
				+ ", cusAddrText=" + cusAddrText + ", supAddrId=" + supAddrId + ", supAddrText=" + supAddrText
				+ ", contractId=" + contractId + ", contractTerms=" + contractTerms + ", comment=" + comment
				+ ", shipToId=" + shipToId + ", shipToText=" + shipToText + ", billToId=" + billToId + ", billToText="
				+ billToText + ", deliveryToId=" + deliveryToId + ", deliveryToText=" + deliveryToText + ", curType="
				+ curType + ", amount=" + amount + ", status=" + status + ", statusComment=" + statusComment
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", isDeleted=" + isDeleted + "]";
	}


	public Long getPOId() {
		return POId;
	}


	public void setPOId(Long pOId) {
		POId = pOId;
	}


	public int getcId() {
		return cId;
	}


	public void setcId(int cId) {
		this.cId = cId;
	}


	public String getPoNumber() {
		return poNumber;
	}


	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getSupplierCode() {
		return supplierCode;
	}


	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}


	public Long getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}


	public Long getCusAddrId() {
		return cusAddrId;
	}


	public void setCusAddrId(Long cusAddrId) {
		this.cusAddrId = cusAddrId;
	}


	public String getCusAddrText() {
		return cusAddrText;
	}


	public void setCusAddrText(String cusAddrText) {
		this.cusAddrText = cusAddrText;
	}


	public Long getSupAddrId() {
		return supAddrId;
	}


	public void setSupAddrId(Long supAddrId) {
		this.supAddrId = supAddrId;
	}


	public String getSupAddrText() {
		return supAddrText;
	}


	public void setSupAddrText(String supAddrText) {
		this.supAddrText = supAddrText;
	}


	public Long getContractId() {
		return contractId;
	}


	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}


	public int getContractTerms() {
		return contractTerms;
	}


	public void setContractTerms(int contractTerms) {
		this.contractTerms = contractTerms;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Long getShipToId() {
		return shipToId;
	}


	public void setShipToId(Long shipToId) {
		this.shipToId = shipToId;
	}


	public String getShipToText() {
		return shipToText;
	}


	public void setShipToText(String shipToText) {
		this.shipToText = shipToText;
	}


	public Long getBillToId() {
		return billToId;
	}


	public void setBillToId(Long billToId) {
		this.billToId = billToId;
	}


	public String getBillToText() {
		return billToText;
	}


	public void setBillToText(String billToText) {
		this.billToText = billToText;
	}


	public Long getDeliveryToId() {
		return deliveryToId;
	}


	public void setDeliveryToId(Long deliveryToId) {
		this.deliveryToId = deliveryToId;
	}


	public String getDeliveryToText() {
		return deliveryToText;
	}


	public void setDeliveryToText(String deliveryToText) {
		this.deliveryToText = deliveryToText;
	}


	public String getCurType() {
		return curType;
	}


	public void setCurType(String curType) {
		this.curType = curType;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusComment() {
		return statusComment;
	}


	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
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


	public int getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	

	
	
}