package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_LOGO")
public class Logo {
	@Id
	@Column(name = "CID") private int cId;
	@Column(name = "LOGO") private String logo;
//	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "CREATED_BY") private String createdBy;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "Logo [cId=" + cId + ", logo=" + logo + ", createdBy=" + createdBy + "]";
	}
	public Logo(int cId, String logo, String createdBy) {
		super();
		this.cId = cId;
		this.logo = logo;
		this.createdBy = createdBy;
	}
	public Logo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
