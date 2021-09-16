package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "Invoice_Amount",procedureName = "Invoice_Amount")})
public class Invoice_Amount_Entity {
	
	@Id
	@Column(name="Total")
	private float total;
	
	@Column(name="Paid")
	private float paid;
	
	@Column(name="Unpaid")
	private float unpaid;
	
	

	public Invoice_Amount_Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Invoice_Amount_Entity(float total, float paid, float unpaid) {
		super();
		this.total = total;
		this.paid = paid;
		this.unpaid = unpaid;
	}

	
	
	@Override
	public String toString() {
		return "Invoice_Amount [total=" + total + ", paid=" + paid + ", unpaid=" + unpaid + ", getTotal()=" + getTotal()
				+ ", getPaid()=" + getPaid() + ", getUnpaid()=" + getUnpaid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getPaid() {
		return paid;
	}

	public void setPaid(float paid) {
		this.paid = paid;
	}

	public float getUnpaid() {
		return unpaid;
	}

	public void setUnpaid(float unpaid) {
		this.unpaid = unpaid;
	}
	
	
}
