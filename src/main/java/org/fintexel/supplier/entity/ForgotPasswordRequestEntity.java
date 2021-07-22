package org.fintexel.supplier.entity;

public class ForgotPasswordRequestEntity {
	private String email;
	private String url;
	
	public ForgotPasswordRequestEntity(String email, String url) {
		super();
		this.email = email;
		this.url = url;
	}

	public ForgotPasswordRequestEntity() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
