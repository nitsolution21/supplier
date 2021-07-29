package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUST_DEPTS")
public class CustomerDepartment {
	@Id
	@Column(name = "CID") private Long cId;
	@Column(name = "DEPARTMENT_ID") private Long departmentId;
	
	
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	
	
	
	@Override
	public String toString() {
		return "CustomerDepartment [cId=" + cId + ", departmentId=" + departmentId + "]";
	}
	
	
	
	
	public CustomerDepartment(Long cId, Long departmentId) {
		super();
		this.cId = cId;
		this.departmentId = departmentId;
	}
	
	
	
	
	public CustomerDepartment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}
