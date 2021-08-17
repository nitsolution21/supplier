package org.fintexel.supplier.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUP_FORGOT_PW")
public class ForgotPassword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
	private long id;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "CREATED_ON",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	
	@Column(name = "STATUS")
	private String status;

	public ForgotPassword(long id, String email, String token, Date createdOn, String status) {
		super();
		this.id = id;
		this.email = email;
		this.token = token;
		this.createdOn = createdOn;
		this.status = status;
	}

	public ForgotPassword() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ForgotPassword [id=" + id + ", email=" + email + ", token=" + token + ", createdOn=" + createdOn
				+ ", status=" + status + "]";
	}
	
	
	
	
}
