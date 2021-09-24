package org.fintexel.supplier.entity;

public class BulkUploadSuccessError {
	public String rowNo;
	public String reason;
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "BulkUploadSuccessError [rowNo=" + rowNo + ", reason=" + reason + "]";
	}
	public BulkUploadSuccessError() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BulkUploadSuccessError(String rowNo, String reason) {
		super();
		this.rowNo = rowNo;
		this.reason = reason;
	}
	
	
	
	

}
