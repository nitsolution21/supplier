package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PO_PENDINGS")
public class PurchesOrderPending {
	@Id
	@Column(name = "PO_ID") private long poId;
	@Column(name = "POITEM_ID") private long poitemId;
	@Column(name = "PENDING_QTY") private int pendingQty;
	public PurchesOrderPending() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PurchesOrderPending(long poId, long poitemId, int pendingQty) {
		super();
		this.poId = poId;
		this.poitemId = poitemId;
		this.pendingQty = pendingQty;
	}
	@Override
	public String toString() {
		return "PurchesOrderPending [poId=" + poId + ", poitemId=" + poitemId + ", pendingQty=" + pendingQty + "]";
	}
	public long getPoId() {
		return poId;
	}
	public void setPoId(long poId) {
		this.poId = poId;
	}
	public long getPoitemId() {
		return poitemId;
	}
	public void setPoitemId(long poitemId) {
		this.poitemId = poitemId;
	}
	public int getPendingQty() {
		return pendingQty;
	}
	public void setPendingQty(int pendingQty) {
		this.pendingQty = pendingQty;
	}
	
}
