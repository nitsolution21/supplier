package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_PO_ATTACHMENTS")
public class PurchesOrderAttachment {

	@Id
	@Column(name = "AT_ID") private Long atId;
	@Column(name = "PO_ID") private Long POId;
	@Column(name = "AT_NAME") private String atName;
	@Column(name = "AT_PATH")private String atPath;
	@Column(name = "AT_COMMENT") private String atComment;
	@JsonFormat(pattern = "YY-MM-DD")
	@Column(name = "CREATED_ON") private Date createdOn;
	@Column(name = "CREATED_BY") private String createdBy;
	public Long getAtId() {
		return atId;
	}
	public void setAtId(Long atId) {
		this.atId = atId;
	}
	public Long getPOId() {
		return POId;
	}
	public void setPOId(Long pOId) {
		POId = pOId;
	}
	public String getAtName() {
		return atName;
	}
	public void setAtName(String atName) {
		this.atName = atName;
	}
	public String getAtPath() {
		return atPath;
	}
	public void setAtPath(String atPath) {
		this.atPath = atPath;
	}
	public String getAtComment() {
		return atComment;
	}
	public void setAtComment(String atComment) {
		this.atComment = atComment;
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
	@Override
	public String toString() {
		return "PurchesOrderAttachment [atId=" + atId + ", POId=" + POId + ", atName=" + atName + ", atPath=" + atPath
				+ ", atComment=" + atComment + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}
	public PurchesOrderAttachment(Long atId, Long pOId, String atName, String atPath, String atComment, Date createdOn,
			String createdBy) {
		super();
		this.atId = atId;
		POId = pOId;
		this.atName = atName;
		this.atPath = atPath;
		this.atComment = atComment;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}
	public PurchesOrderAttachment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
