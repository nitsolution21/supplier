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
@Table(name = "SUP_M_CURRENCY")
public class MasterCurrencyType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "ID") private Long id;
	@Column(name = "CODE") private String code;
	@Column(name = "CURRENCY") private String currency;
	@Column(name = "COUNTRY") private String country;
	@Column(name = "STATUS") private String status;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
		return "MasterRegType [id=" + id + ", code=" + code + ", currency=" + currency + ", country=" + country
				+ ", status=" + status + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	public MasterCurrencyType(Long id, String code, String currency, String country, String status, String createdBy,
			Date createdOn, String updatedBy, Date updatedOn) {
		super();
		this.id = id;
		this.code = code;
		this.currency = currency;
		this.country = country;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	public MasterCurrencyType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
