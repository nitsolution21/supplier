package org.fintexel.supplier.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class VendorDetails implements UserDetails {
	
	private String supplierCompName;
	private String username;
	private String password;
	private List<GrantedAuthority> status;
	private int createdBy;
	private Date createdOn;
	private int updatedBy;
	private Date updatedOn;

	public VendorDetails() {
		super();
	}
	
	public VendorDetails(VendorRegister vendor) {
		this.supplierCompName = vendor.getSupplierCompName();
		this.username = vendor.getUsername();
		this.password = vendor.getPassword();
		this.createdBy = vendor.getCreatedBy();
		this.createdOn = vendor.getCreatedOn();
		this.updatedBy = vendor.getUpdatedBy();
		this.updatedOn = vendor.getUpdatedOn();
		this.status = Arrays.stream(vendor.getStatus().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return status;
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