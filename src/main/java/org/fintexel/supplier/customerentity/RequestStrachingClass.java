package org.fintexel.supplier.customerentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RequestStrachingClass {
	
	private Long contractId;
	private Long cId;
	private String supplierCode;
	private String contractType;
	private Long contractTerms;
	private String contractProof;
	private String contractLocation;
	@JsonFormat(pattern="yyyy-MM-dd") private Date contractEndDate;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	private boolean other;
	 private Long addressId;
	 private String addressType;
	 private String address1;
	 private String address2;
	 private String city;
	 private String postalCode;
	 private String country;
	 private String region;
	 private String addressProof;
	 private String addressProofPath;
	 private String status;
	 private int isPrimary;
		private long itemId;
		private String itemDescription;
		private int qty;
		private float unitPrice;
		private int sku;
		private long categoryId;
		private long brandId;
		private long subcategoryId;
		private float discount;
		private String tax;
		private String amount;
		public Long getContractId() {
			return contractId;
		}
		public void setContractId(Long contractId) {
			this.contractId = contractId;
		}
		public Long getcId() {
			return cId;
		}
		public void setcId(Long cId) {
			this.cId = cId;
		}
		public String getSupplierCode() {
			return supplierCode;
		}
		public void setSupplierCode(String supplierCode) {
			this.supplierCode = supplierCode;
		}
		public String getContractType() {
			return contractType;
		}
		public void setContractType(String contractType) {
			this.contractType = contractType;
		}
		public Long getContractTerms() {
			return contractTerms;
		}
		public void setContractTerms(Long contractTerms) {
			this.contractTerms = contractTerms;
		}
		public String getContractProof() {
			return contractProof;
		}
		public void setContractProof(String contractProof) {
			this.contractProof = contractProof;
		}
		public String getContractLocation() {
			return contractLocation;
		}
		public void setContractLocation(String contractLocation) {
			this.contractLocation = contractLocation;
		}
		public Date getContractEndDate() {
			return contractEndDate;
		}
		public void setContractEndDate(Date contractEndDate) {
			this.contractEndDate = contractEndDate;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public Date getCreatedOn() {
			return createdOn;
		}
		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
		}
		public String getUpdatedBy() {
			return updatedBy;
		}
		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}
		public Date getUpdatedOn() {
			return updatedOn;
		}
		public void setUpdatedOn(Date updatedOn) {
			this.updatedOn = updatedOn;
		}
		public boolean isOther() {
			return other;
		}
		public void setOther(boolean other) {
			this.other = other;
		}
		public Long getAddressId() {
			return addressId;
		}
		public void setAddressId(Long addressId) {
			this.addressId = addressId;
		}
		public String getAddressType() {
			return addressType;
		}
		public void setAddressType(String addressType) {
			this.addressType = addressType;
		}
		public String getAddress1() {
			return address1;
		}
		public void setAddress1(String address1) {
			this.address1 = address1;
		}
		public String getAddress2() {
			return address2;
		}
		public void setAddress2(String address2) {
			this.address2 = address2;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPostalCode() {
			return postalCode;
		}
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getAddressProof() {
			return addressProof;
		}
		public void setAddressProof(String addressProof) {
			this.addressProof = addressProof;
		}
		public String getAddressProofPath() {
			return addressProofPath;
		}
		public void setAddressProofPath(String addressProofPath) {
			this.addressProofPath = addressProofPath;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getIsPrimary() {
			return isPrimary;
		}
		public void setIsPrimary(int isPrimary) {
			this.isPrimary = isPrimary;
		}
		public long getItemId() {
			return itemId;
		}
		public void setItemId(long itemId) {
			this.itemId = itemId;
		}
		public String getItemDescription() {
			return itemDescription;
		}
		public void setItemDescription(String itemDescription) {
			this.itemDescription = itemDescription;
		}
		public int getQty() {
			return qty;
		}
		public void setQty(int qty) {
			this.qty = qty;
		}
		public float getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(float unitPrice) {
			this.unitPrice = unitPrice;
		}
		public int getSku() {
			return sku;
		}
		public void setSku(int sku) {
			this.sku = sku;
		}
		public long getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(long categoryId) {
			this.categoryId = categoryId;
		}
		public long getBrandId() {
			return brandId;
		}
		public void setBrandId(long brandId) {
			this.brandId = brandId;
		}
		public long getSubcategoryId() {
			return subcategoryId;
		}
		public void setSubcategoryId(long subcategoryId) {
			this.subcategoryId = subcategoryId;
		}
		public float getDiscount() {
			return discount;
		}
		public void setDiscount(float discount) {
			this.discount = discount;
		}
		public String getTax() {
			return tax;
		}
		public void setTax(String tax) {
			this.tax = tax;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		@Override
		public String toString() {
			return "RequestStrachingClass [contractId=" + contractId + ", cId=" + cId + ", supplierCode=" + supplierCode
					+ ", contractType=" + contractType + ", contractTerms=" + contractTerms + ", contractProof="
					+ contractProof + ", contractLocation=" + contractLocation + ", contractEndDate=" + contractEndDate
					+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy
					+ ", updatedOn=" + updatedOn + ", other=" + other + ", addressId=" + addressId + ", addressType="
					+ addressType + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city
					+ ", postalCode=" + postalCode + ", country=" + country + ", region=" + region + ", addressProof="
					+ addressProof + ", addressProofPath=" + addressProofPath + ", status=" + status + ", isPrimary="
					+ isPrimary + ", itemId=" + itemId + ", itemDescription=" + itemDescription + ", qty=" + qty
					+ ", unitPrice=" + unitPrice + ", sku=" + sku + ", categoryId=" + categoryId + ", brandId="
					+ brandId + ", subcategoryId=" + subcategoryId + ", discount=" + discount + ", tax=" + tax
					+ ", amount=" + amount + "]";
		}
		public RequestStrachingClass(Long contractId, Long cId, String supplierCode, String contractType,
				Long contractTerms, String contractProof, String contractLocation, Date contractEndDate,
				String createdBy, Date createdOn, String updatedBy, Date updatedOn, boolean other, Long addressId,
				String addressType, String address1, String address2, String city, String postalCode, String country,
				String region, String addressProof, String addressProofPath, String status, int isPrimary, long itemId,
				String itemDescription, int qty, float unitPrice, int sku, long categoryId, long brandId,
				long subcategoryId, float discount, String tax, String amount) {
			super();
			this.contractId = contractId;
			this.cId = cId;
			this.supplierCode = supplierCode;
			this.contractType = contractType;
			this.contractTerms = contractTerms;
			this.contractProof = contractProof;
			this.contractLocation = contractLocation;
			this.contractEndDate = contractEndDate;
			this.createdBy = createdBy;
			this.createdOn = createdOn;
			this.updatedBy = updatedBy;
			this.updatedOn = updatedOn;
			this.other = other;
			this.addressId = addressId;
			this.addressType = addressType;
			this.address1 = address1;
			this.address2 = address2;
			this.city = city;
			this.postalCode = postalCode;
			this.country = country;
			this.region = region;
			this.addressProof = addressProof;
			this.addressProofPath = addressProofPath;
			this.status = status;
			this.isPrimary = isPrimary;
			this.itemId = itemId;
			this.itemDescription = itemDescription;
			this.qty = qty;
			this.unitPrice = unitPrice;
			this.sku = sku;
			this.categoryId = categoryId;
			this.brandId = brandId;
			this.subcategoryId = subcategoryId;
			this.discount = discount;
			this.tax = tax;
			this.amount = amount;
		}
		public RequestStrachingClass() {
			super();
			// TODO Auto-generated constructor stub
		}
		


	
	
	

}
