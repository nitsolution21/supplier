package org.fintexel.supplier.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registration")
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String fathersName;
	private String addressLine1;
	private String addressLine2;
	private String mobileNo;
	private String alternativeMobile;
	private String state;
	private String city;
	private String pin;
	private String profilePic;
	private String gender;
	private String email;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFathersName() {
		return fathersName;
	}
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAlternativeMobile() {
		return alternativeMobile;
	}
	public void setAlternativeMobile(String alternativeMobile) {
		this.alternativeMobile = alternativeMobile;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Vendor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vendor(int id, String firstName, String lastName, String fathersName, String addressLine1,
			String addressLine2, String mobileNo, String alternativeMobile, String state, String city, String pin,
			String profilePic, String gender, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fathersName = fathersName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.mobileNo = mobileNo;
		this.alternativeMobile = alternativeMobile;
		this.state = state;
		this.city = city;
		this.pin = pin;
		this.profilePic = profilePic;
		this.gender = gender;
		this.email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fathersName="
				+ fathersName + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", mobileNo="
				+ mobileNo + ", alternativeMobile=" + alternativeMobile + ", state=" + state + ", city=" + city
				+ ", pin=" + pin + ", profilePic=" + profilePic + ", gender=" + gender + ", email=" + email
				+ ", password=" + password + "]";
	}
	
}
