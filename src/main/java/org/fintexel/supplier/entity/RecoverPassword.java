package org.fintexel.supplier.entity;

public class RecoverPassword {
	private String  token;
	private String newPasswoed;
	
	public RecoverPassword(String token, String newPasswoed) {
		super();
		this.token = token;
		this.newPasswoed = newPasswoed;
	}

	public RecoverPassword() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPasswoed() {
		return newPasswoed;
	}

	public void setNewPasswoed(String newPasswoed) {
		this.newPasswoed = newPasswoed;
	}
	
	
}
