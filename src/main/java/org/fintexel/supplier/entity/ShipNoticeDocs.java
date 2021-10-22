package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_SHIP_NOTICE_DOCS")
public class ShipNoticeDocs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SND_ID")
	private long sndId;
	@Column(name = "SHNO_ID")
	private long shnoId;
	@Column(name = "SND_PATH")
	private String sndPath;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "CREATED_BY")
	private String createdBy;

	public ShipNoticeDocs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipNoticeDocs(long sndId, long shnoId, String sndPath, Date createdOn, String createdBy) {
		super();
		this.sndId = sndId;
		this.shnoId = shnoId;
		this.sndPath = sndPath;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "ShipNoticeDocs [sndId=" + sndId + ", shnoId=" + shnoId + ", sndPath=" + sndPath + ", createdOn="
				+ createdOn + ", createdBy=" + createdBy + "]";
	}

	public long getSndId() {
		return sndId;
	}

	public void setSndId(long sndId) {
		this.sndId = sndId;
	}

	public long getShnoId() {
		return shnoId;
	}

	public void setShnoId(long shnoId) {
		this.shnoId = shnoId;
	}

	public String getSndPath() {
		return sndPath;
	}

	public void setSndPath(String sndPath) {
		this.sndPath = sndPath;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
