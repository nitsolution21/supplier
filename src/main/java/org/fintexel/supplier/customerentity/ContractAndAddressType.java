package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUS_DEFAULTS")
public class ContractAndAddressType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID") private Long id;
	@Column(name = "CID") private int cId;
	@Column(name = "NAME") private int name;
	@Column(name = "TYPE") private int type;
	@Column(name = "ORDER") private int order;
	@Column(name = "CREATED_ON") private int createdOn;
	@Column(name = "CREATED_BY") private int createdBy;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(int createdOn) {
		this.createdOn = createdOn;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "ContractAndAddressType [id=" + id + ", cId=" + cId + ", name=" + name + ", type=" + type + ", order="
				+ order + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}
	public ContractAndAddressType(Long id, int cId, int name, int type, int order, int createdOn, int createdBy) {
		super();
		this.id = id;
		this.cId = cId;
		this.name = name;
		this.type = type;
		this.order = order;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}
	public ContractAndAddressType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
