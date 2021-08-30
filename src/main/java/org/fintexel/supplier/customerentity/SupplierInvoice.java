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
	@Column(name = "INV_TAXID") private String invTaxid;
	@Column(name = "REMIT_TO") private String remitTo;
	@Column(name = "BILL_TO") private String billTo;
	@Column(name = "SHIP_CHARGES") private float shipCharges;
	@Column(name = "HANDLING_CHARGES") private float handlingCharges;
	@Column(name = "TOTAL_GROSS") private float totalGross;
	@Column(name = "TOTAL_TAX") private float totalTax;
	@Column(name = "SUBTOTAL") private float subtotal;
	@Column(name = "TOTAL_AMOUNT") private float totalAmount;
	@Column(name = "STATUS") private String status;
	@Column(name = "INV_ATTACHMENT") private String invAttachment;
	@Column(name = "CREATED_BY") private String createdBy;
	@JsonFormat(pattern = "YY-DD-DD")
	@Column(name = "CREATED_ON") private Date createdOn;
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
	public String getInvTaxid() {
		return invTaxid;
	}
	public void setInvTaxid(String invTaxid) {
		this.invTaxid = invTaxid;
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
	public float getShipCharges() {
		return shipCharges;
	}
	public void setShipCharges(float shipCharges) {
		this.shipCharges = shipCharges;
	}
	public float getHandlingCharges() {
		return handlingCharges;
	}
	public void setHandlingCharges(float handlingCharges) {
		this.handlingCharges = handlingCharges;
	}
	public float getTotalGross() {
		return totalGross;
	}
	public void setTotalGross(float totalGross) {
		this.totalGross = totalGross;
	}
	public float getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}
	public float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvAttachment() {
		return invAttachment;
	}
	public void setInvAttachment(String invAttachment) {
		this.invAttachment = invAttachment;
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
	@Override
	public String toString() {
		return "SupplierInvoice [invId=" + invId + ", POId=" + POId + ", invDate=" + invDate + ", invDesc=" + invDesc
				+ ", invTaxid=" + invTaxid + ", remitTo=" + remitTo + ", billTo=" + billTo + ", shipCharges="
				+ shipCharges + ", handlingCharges=" + handlingCharges + ", totalGross=" + totalGross + ", totalTax="
				+ totalTax + ", subtotal=" + subtotal + ", totalAmount=" + totalAmount + ", status=" + status
				+ ", invAttachment=" + invAttachment + ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	public SupplierInvoice(Long invId, Long pOId, Date invDate, String invDesc, String invTaxid, String remitTo,
			String billTo, float shipCharges, float handlingCharges, float totalGross, float totalTax, float subtotal,
			float totalAmount, String status, String invAttachment, String createdBy, Date createdOn) {
		super();
		this.invId = invId;
		POId = pOId;
		this.invDate = invDate;
		this.invDesc = invDesc;
		this.invTaxid = invTaxid;
		this.remitTo = remitTo;
		this.billTo = billTo;
		this.shipCharges = shipCharges;
		this.handlingCharges = handlingCharges;
		this.totalGross = totalGross;
		this.totalTax = totalTax;
		this.subtotal = subtotal;
		this.totalAmount = totalAmount;
		this.status = status;
		this.invAttachment = invAttachment;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}
	public SupplierInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
