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
@Table(name = "TBL_INVOICES")
public class SupplierInvoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INV_ID") private Long invId;
	@Column(name = "PO_ID") private Long POId;
	@JsonFormat(pattern = "YY-DD-DD")
	@Column(name = "INV_DATE") private Date invDate;
	@Column(name = "INV_DESC") private String invDesc;
	@Column(name = "INV_REGNUM") private String invRegNum;
//	@Column(name = "INV_TAXID") private String invTaxid;
	@Column(name = "INV_REMIT_TO") private String remitTo;
	@Column(name = "INV_BILL_TO") private String billTo;
//	@Column(name = "SHIP_CHARGES") private float shipCharges;
//	@Column(name = "HANDLING_CHARGES") private float handlingCharges;
//	@Column(name = "TOTAL_GROSS") private float totalGross;
//	@Column(name = "TOTAL_TAX") private float totalTax;
//	@Column(name = "SUBTOTAL") private float subtotal;
//	@Column(name = "TOTAL_AMOUNT") private float totalAmount;
	@Column(name = "INV_STATUS") private String status;
	@Column(name = "INV_AMOUNT") private String invAmount;
	@Column(name = "CREATED_BY") private String createdBy;
//	@JsonFormat(pattern = "YY-DD-DD")
//	@Column(name = "CREATED_ON") private Date createdOn;
	public Long getInvId() {
		return invId;
	}
	public void setInvId(Long invId) {
		this.invId = invId;
	}
	public Long getPOId() {
		return POId;
	}
	public void setPOId(Long pOId) {
		POId = pOId;
	}
	public Date getInvDate() {
		return invDate;
	}
	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}
	public String getInvDesc() {
		return invDesc;
	}
	public void setInvDesc(String invDesc) {
		this.invDesc = invDesc;
	}
	public String getInvRegNum() {
		return invRegNum;
	}
	public void setInvRegNum(String invRegNum) {
		this.invRegNum = invRegNum;
	}
	public String getRemitTo() {
		return remitTo;
	}
	public void setRemitTo(String remitTo) {
		this.remitTo = remitTo;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvAmount() {
		return invAmount;
	}
	public void setInvAmount(String invAmount) {
		this.invAmount = invAmount;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "SupplierInvoice [invId=" + invId + ", POId=" + POId + ", invDate=" + invDate + ", invDesc=" + invDesc
				+ ", invRegNum=" + invRegNum + ", remitTo=" + remitTo + ", billTo=" + billTo + ", status=" + status
				+ ", invAmount=" + invAmount + ", createdBy=" + createdBy + "]";
	}
	public SupplierInvoice(Long invId, Long pOId, Date invDate, String invDesc, String invRegNum, String remitTo,
			String billTo, String status, String invAmount, String createdBy) {
		super();
		this.invId = invId;
		POId = pOId;
		this.invDate = invDate;
		this.invDesc = invDesc;
		this.invRegNum = invRegNum;
		this.remitTo = remitTo;
		this.billTo = billTo;
		this.status = status;
		this.invAmount = invAmount;
		this.createdBy = createdBy;
	}
	public SupplierInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
