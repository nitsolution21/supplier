package org.fintexel.supplier.customerentity;

import java.util.List;

public class POFlowableItem {
	
	private PurchesOrder purchesOrder;
	private List<PurchesOrderItems> purchesOrderItems;
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
	@Override
	public String toString() {
		return "POFlowableItem [purchesOrder=" + purchesOrder + ", purchesOrderItems=" + purchesOrderItems + "]";
	}
	public POFlowableItem(PurchesOrder purchesOrder, List<PurchesOrderItems> purchesOrderItems) {
		super();
		this.purchesOrder = purchesOrder;
		this.purchesOrderItems = purchesOrderItems;
	}
	public POFlowableItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
