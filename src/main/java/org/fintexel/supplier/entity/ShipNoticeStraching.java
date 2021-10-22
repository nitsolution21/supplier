package org.fintexel.supplier.entity;

import java.util.Date;
import java.util.List;

public class ShipNoticeStraching {
	private long shnoId;
	private long poId;
	private String shnoMode;
	private Date shnoDate;
	private String shnoNote;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
	private List<ShipNoticeDocs> ShipNoticeDocs;

	public ShipNoticeStraching() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipNoticeStraching(long shnoId, long poId, String shnoMode, Date shnoDate, String shnoNote, Date createdOn,
			String createdBy, Date updatedOn, String updatedBy,
			List<org.fintexel.supplier.entity.ShipNoticeDocs> shipNoticeDocs) {
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
		ShipNoticeDocs = shipNoticeDocs;
	}

	@Override
	public String toString() {
		return "ShipNoticeStraching [shnoId=" + shnoId + ", poId=" + poId + ", shnoMode=" + shnoMode + ", shnoDate="
				+ shnoDate + ", shnoNote=" + shnoNote + ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + ", ShipNoticeDocs=" + ShipNoticeDocs + "]";
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

	public List<ShipNoticeDocs> getShipNoticeDocs() {
		return ShipNoticeDocs;
	}

	public void setShipNoticeDocs(List<ShipNoticeDocs> shipNoticeDocs) {
		ShipNoticeDocs = shipNoticeDocs;
	}

}
