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
@Table(name = "TBL_CUS_DEFAULTS")
public class ContractAndAddressType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID") private Long id;
	@Column(name = "CID") private Long cId;
	@Column(name = "NAME") private String name;
	@Column(name = "TYPE") private String type;
	@Column(name = "`ORDER`") private Long order;
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "CREATED_BY") private String createdBy;
	@Override
	public String toString() {
		return "ContractAndAddressType [id=" + id + ", cId=" + cId + ", name=" + name + ", type=" + type + ", order="
				+ order + ", createdBy=" + createdBy + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public ContractAndAddressType(Long id, Long cId, String name, String type, Long order, String createdBy) {
		super();
		this.id = id;
		this.cId = cId;
		this.name = name;
		this.type = type;
		this.order = order;
		this.createdBy = createdBy;
	}
	public ContractAndAddressType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
