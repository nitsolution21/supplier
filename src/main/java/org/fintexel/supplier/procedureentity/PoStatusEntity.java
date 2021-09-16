package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PO_status",procedureName = "PO_status")})
public class PoStatusEntity {
	
	
	@Id
	@Column(name="Open PO")
	private long openPo;
	
	@Column(name="Closed PO")
	private long closedPo;
	
	@Column(name="Orders_Shipped_And_Delivered")
	private long ordersShippedAndDelivered;
	
	@Column(name="Orders_In_Shipment")
	private long OrdersInShipment;
	
	@Column(name="Pending PO Approval")
	private long PendingPoApproval;
	
	@Column(name="Orders_Part_Delivered")
	private long OrdersPartDelivered;
	
	@Column(name="Total_Orders_Generated")
	private long TotalOrdersGenerated;

	public PoStatusEntity(long openPo, long closedPo, long ordersShippedAndDelivered, long ordersInShipment,
			long pendingPoApproval, long ordersPartDelivered, long totalOrdersGenerated) {
		super();
		this.openPo = openPo;
		this.closedPo = closedPo;
		this.ordersShippedAndDelivered = ordersShippedAndDelivered;
		OrdersInShipment = ordersInShipment;
		PendingPoApproval = pendingPoApproval;
		OrdersPartDelivered = ordersPartDelivered;
		TotalOrdersGenerated = totalOrdersGenerated;
	}

	public PoStatusEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getOpenPo() {
		return openPo;
	}

	public void setOpenPo(long openPo) {
		this.openPo = openPo;
	}

	public long getClosedPo() {
		return closedPo;
	}

	public void setClosedPo(long closedPo) {
		this.closedPo = closedPo;
	}

	public long getOrdersShippedAndDelivered() {
		return ordersShippedAndDelivered;
	}

	public void setOrdersShippedAndDelivered(long ordersShippedAndDelivered) {
		this.ordersShippedAndDelivered = ordersShippedAndDelivered;
	}

	public long getOrdersInShipment() {
		return OrdersInShipment;
	}

	public void setOrdersInShipment(long ordersInShipment) {
		OrdersInShipment = ordersInShipment;
	}

	public long getPendingPoApproval() {
		return PendingPoApproval;
	}

	public void setPendingPoApproval(long pendingPoApproval) {
		PendingPoApproval = pendingPoApproval;
	}

	public long getOrdersPartDelivered() {
		return OrdersPartDelivered;
	}

	public void setOrdersPartDelivered(long ordersPartDelivered) {
		OrdersPartDelivered = ordersPartDelivered;
	}

	public long getTotalOrdersGenerated() {
		return TotalOrdersGenerated;
	}

	public void setTotalOrdersGenerated(long totalOrdersGenerated) {
		TotalOrdersGenerated = totalOrdersGenerated;
	}

	@Override
	public String toString() {
		return "PoStatusEntity [openPo=" + openPo + ", closedPo=" + closedPo + ", ordersShippedAndDelivered="
				+ ordersShippedAndDelivered + ", OrdersInShipment=" + OrdersInShipment + ", PendingPoApproval="
				+ PendingPoApproval + ", OrdersPartDelivered=" + OrdersPartDelivered + ", TotalOrdersGenerated="
				+ TotalOrdersGenerated + ", getOpenPo()=" + getOpenPo() + ", getClosedPo()=" + getClosedPo()
				+ ", getOrdersShippedAndDelivered()=" + getOrdersShippedAndDelivered() + ", getOrdersInShipment()="
				+ getOrdersInShipment() + ", getPendingPoApproval()=" + getPendingPoApproval()
				+ ", getOrdersPartDelivered()=" + getOrdersPartDelivered() + ", getTotalOrdersGenerated()="
				+ getTotalOrdersGenerated() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
