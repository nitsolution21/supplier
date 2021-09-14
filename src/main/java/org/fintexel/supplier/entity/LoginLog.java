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
@Table(name = "SUP_LOGIN_LOG")
public class LoginLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID") private Long id;
	@Column(name = "REGISTER_ID") private Long registerId;
	@Column(name = "SUPPLIER_CODE") private String supplierCode;
	@Column(name = "LOGIN_ATTEMPT") private String loginAttempt;
	@Column(name = "IP_ADDRESS") private String ipAddress;
	@Column(name = "LOGIN_TIME")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date loginTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRegisterId() {
		return registerId;
	}
	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getLoginAttempt() {
		return loginAttempt;
	}
	public void setLoginAttempt(String loginAttempt) {
		this.loginAttempt = loginAttempt;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	@Override
	public String toString() {
		return "LoginLog [id=" + id + ", registerId=" + registerId + ", supplierCode=" + supplierCode
				+ ", loginAttempt=" + loginAttempt + ", ipAddress=" + ipAddress + ", loginTime=" + loginTime + "]";
	}
	public LoginLog(Long id, Long registerId, String supplierCode, String loginAttempt, String ipAddress,
			Date loginTime) {
		super();
		this.id = id;
		this.registerId = registerId;
		this.supplierCode = supplierCode;
		this.loginAttempt = loginAttempt;
		this.ipAddress = ipAddress;
		this.loginTime = loginTime;
	}
	public LoginLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
