package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUS_USERS")
public class CustomerUsers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID") private Long userId;
	@Column(name = "NAME") private String name;
	@Column(name = "USERNAME") private String userName;
	@Column(name = "EMAIAL") private String email;
	@Column(name = "PASSWORD") private String password;
	@Column(name = "STATUS") private String status;
	@Column(name = "ROLE_ID") private Long roleId;
	@Column(name = "DEPARTMENT_ID") private Long departmentId;
	@Column(name = "CREATED_BY") private String createdBy;
	@Column(name = "CREATED_ON") private String createdOn;
	@Column(name = "UPDATED_BY") private String updatedBy;
	@Column(name = "UPDATED_ON") private String updatedOn;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "CustomerUsers [userId=" + userId + ", name=" + name + ", userName=" + userName + ", email=" + email
				+ ", password=" + password + ", status=" + status + ", roleId=" + roleId + ", departmentId="
				+ departmentId + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + "]";
	}
	public CustomerUsers(Long userId, String name, String userName, String email, String password, String status,
			Long roleId, Long departmentId, String createdBy, String createdOn, String updatedBy, String updatedOn) {
		super();
		this.userId = userId;
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.status = status;
		this.roleId = roleId;
		this.departmentId = departmentId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	public CustomerUsers() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}
