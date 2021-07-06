package org.fintexel.supplier.exceptions;

public class VendorErrorResponse extends RuntimeException{

	private int statuss;
	private String messagee;
	private long timeStampp;
	
	public VendorErrorResponse() {
		
	}

	public VendorErrorResponse(int statuss, String messagee, long timeStampp) {
		super();
		this.statuss = statuss;
		this.messagee = messagee;
		this.timeStampp = timeStampp;
	}
	public VendorErrorResponse(String message) {
		super(message);
	}

	public VendorErrorResponse(Throwable cause) {
		super(cause);
	}

	public VendorErrorResponse(String message, Throwable cause) {
		super(message, cause);
	}

	public VendorErrorResponse(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public int getStatuss() {
		return statuss;
	}

	public void setStatuss(int statuss) {
		this.statuss = statuss;
	}

	public String getMessagee() {
		return messagee;
	}

	public void setMessagee(String messagee) {
		this.messagee = messagee;
	}

	public long getTimeStampp() {
		return timeStampp;
	}

	public void setTimeStampp(long timeStampp) {
		this.timeStampp = timeStampp;
	}

	
}







