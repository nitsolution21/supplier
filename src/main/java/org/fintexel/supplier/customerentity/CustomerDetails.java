package org.fintexel.supplier.customerentity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerDetails implements UserDetails {
	private long userId;
	private long cId;
	private String name;
	private String username;
	private String email;
	private String password;
	private String status;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	public CustomerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomerDetails(CustomerRegister customer) {
		this.userId = customer.getUserId();
		this.cId = customer.getcId();
		this.name = customer.getName();
		this.username = customer.getUsername();
		this.email = customer.getEmail();
		this.password = customer.getPassword();
		this.status = customer.getStatus();
		this.createdBy = customer.getCreatedBy();
		this.createdOn = customer.getCreatedOn();
		this.updatedBy = customer.getUpdatedBy();
		this.updatedOn = customer.getUpdatedOn();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
