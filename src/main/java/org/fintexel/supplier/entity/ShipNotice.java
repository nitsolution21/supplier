package org.fintexel.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_SHIP_NOTICE")
public class ShipNotice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SHNO_ID")
	private long shnoId;
	@Column(name = "PO_ID")
	private long poId;
	@Column(name = "SHNO_MODE")
	private String shnoMode;
	@Column(name = "SHNO_DATE")
	private Date shnoDate;
	@Column(name = "SHNO_NOTE")
	private String shnoNote;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public ShipNotice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipNotice(long shnoId, long poId, String shnoMode, Date shnoDate, String shnoNote, Date createdOn,
			String createdBy, Date updatedOn, String updatedBy) {
		super();
		this.shnoId = shnoId;
		this.poId = poId;
		this.shnoMode = shnoMode;
		this.shnoDate = shnoDate;
		this.shnoNote = shnoNote;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "ShipNotice [shnoId=" + shnoId + ", poId=" + poId + ", shnoMode=" + shnoMode + ", shnoDate=" + shnoDate
				+ ", shnoNote=" + shnoNote + ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn="
				+ updatedOn + ", updatedBy=" + updatedBy + "]";
	}

	public long getShnoId() {
		return shnoId;
	}

	public void setShnoId(long shnoId) {
		this.shnoId = shnoId;
	}

	public long getPoId() {
		return poId;
	}

	public void setPoId(long poId) {
		this.poId = poId;
	}

	public String getShnoMode() {
		return shnoMode;
	}

	public void setShnoMode(String shnoMode) {
		this.shnoMode = shnoMode;
	}

	public Date getShnoDate() {
		return shnoDate;
	}

	public void setShnoDate(Date shnoDate) {
		this.shnoDate = shnoDate;
	}

	public String getShnoNote() {
		return shnoNote;
	}

	public void setShnoNote(String shnoNote) {
		this.shnoNote = shnoNote;
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
