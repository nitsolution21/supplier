package org.fintexel.supplier.exceptions;

public class VendorNotFoundException extends RuntimeException {

	public VendorNotFoundException() {
	}

	public VendorNotFoundException(String message) {
		super(message);
	}

	public VendorNotFoundException(Throwable cause) {
		super(cause);
	}

	public VendorNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VendorNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
