package org.fintexel.supplier.customerentity;

import java.util.List;

public class RequestPurchesOrder {
	private PurchesOrder purchesOrder;
	private List<PurchesOrderItems> purchesOrderItems;
	private PurchesOrderStatus purchesOrderStatus;
	private PurchesOrderAttachment purchesOrderAttachment;
	
	public RequestPurchesOrder() {
		super();
	}

	public RequestPurchesOrder(PurchesOrder purchesOrder, List<PurchesOrderItems> purchesOrderItems,
			PurchesOrderStatus purchesOrderStatus, PurchesOrderAttachment purchesOrderAttachment) {
		super();
		this.purchesOrder = purchesOrder;
		this.purchesOrderItems = purchesOrderItems;
		this.purchesOrderStatus = purchesOrderStatus;
		this.purchesOrderAttachment = purchesOrderAttachment;
	}

	@Override
	public String toString() {
		return "RequestPurchesOrder [purchesOrder=" + purchesOrder + ", purchesOrderItems=" + purchesOrderItems
				+ ", purchesOrderStatus=" + purchesOrderStatus + ", purchesOrderAttachment=" + purchesOrderAttachment
				+ "]";
	}

	public PurchesOrder getPurchesOrder() {
		return purchesOrder;
	}

	public void setPurchesOrder(PurchesOrder purchesOrder) {
		this.purchesOrder = purchesOrder;
	}

	public List<PurchesOrderItems> getPurchesOrderItems() {
		return purchesOrderItems;
	}

	public void setPurchesOrderItems(List<PurchesOrderItems> purchesOrderItems) {
		this.purchesOrderItems = purchesOrderItems;
	}

	public PurchesOrderStatus getPurchesOrderStatus() {
		return purchesOrderStatus;
	}

	public void setPurchesOrderStatus(PurchesOrderStatus purchesOrderStatus) {
		this.purchesOrderStatus = purchesOrderStatus;
	}

	public PurchesOrderAttachment getPurchesOrderAttachment() {
		return purchesOrderAttachment;
	}

	public void setPurchesOrderAttachment(PurchesOrderAttachment purchesOrderAttachment) {
		this.purchesOrderAttachment = purchesOrderAttachment;
	}
	
	
}
