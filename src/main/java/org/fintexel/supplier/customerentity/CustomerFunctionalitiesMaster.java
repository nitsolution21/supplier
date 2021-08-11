package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FUNCTIONALITIES")
public class CustomerFunctionalitiesMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "F_ID")
	private long fId;
	
	@Column(name = "F_NAME")
	private String fName;
	
	@Column(name = "F_DESC")
	private String fDesc;

	public CustomerFunctionalitiesMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerFunctionalitiesMaster(long fId, String fName, String fDesc) {
		super();
		this.fId = fId;
		this.fName = fName;
		this.fDesc = fDesc;
	}

	public long getfId() {
		return fId;
	}

	public void setfId(long fId) {
		this.fId = fId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getfDesc() {
		return fDesc;
	}

	public void setfDesc(String fDesc) {
		this.fDesc = fDesc;
	}

	
	
	
}
