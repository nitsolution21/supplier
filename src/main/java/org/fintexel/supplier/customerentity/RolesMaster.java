package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ROLES")
public class RolesMaster {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private long roleId;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "Level")
	private int level;

	public RolesMaster(long roleId, String role, int level) {
		super();
		this.roleId = roleId;
		this.role = role;
		this.level = level;
	}

	public RolesMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "RolesMaster [roleId=" + roleId + ", role=" + role + ", level=" + level + "]";
	}
	
}
