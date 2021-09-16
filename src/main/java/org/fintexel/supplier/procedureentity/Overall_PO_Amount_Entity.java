package org.fintexel.supplier.procedureentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "Overall_PO_Amount",procedureName = "Overall_PO_Amount")})
public class Overall_PO_Amount_Entity {

	@Id
	@Column(name="Overall PO Amount")
	private float overallPoAmount;

	
	
	@Override
	public String toString() {
		return "Overall_PO_Amount_Entity [overallPoAmount=" + overallPoAmount + ", getOverallPoAmount()="
				+ getOverallPoAmount() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public Overall_PO_Amount_Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Overall_PO_Amount_Entity(float overallPoAmount) {
		super();
		this.overallPoAmount = overallPoAmount;
	}

	public float getOverallPoAmount() {
		return overallPoAmount;
	}

	public void setOverallPoAmount(float overallPoAmount) {
		this.overallPoAmount = overallPoAmount;
	}
	
	
}
