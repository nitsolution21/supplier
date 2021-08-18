package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUP_M_REG_TYPE")
public class MasterRegType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "ID") private Long id;
	@Column(name = "NAME") private String name;
	@Column(name = "CREATED_BY") private String createdBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") @Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") @Column(name = "UPDATED_ON") private Date updatedOn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "MasterRegType [id=" + id + ", name=" + name + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	public MasterRegType(Long id, String name, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	public MasterRegType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
