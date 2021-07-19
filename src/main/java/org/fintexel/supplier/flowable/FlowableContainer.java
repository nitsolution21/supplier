package org.fintexel.supplier.flowable;

public class FlowableContainer {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FlowableContainer [id=" + id + ", getId()=" + getId() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public FlowableContainer(String id) {
		super();
		this.id = id;
	}

	public FlowableContainer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
