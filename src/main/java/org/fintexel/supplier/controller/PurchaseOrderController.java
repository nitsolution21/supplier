package org.fintexel.supplier.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.fintexel.supplier.customerentity.CustomerContact;
import org.fintexel.supplier.customerentity.PurchesOrder;
import org.fintexel.supplier.customerentity.PurchesOrderItems;
import org.fintexel.supplier.customerentity.PurchesOrderStatus;
import org.fintexel.supplier.customerentity.RequestPurchesOrder;
import org.fintexel.supplier.customerentity.SelectedItem;
import org.fintexel.supplier.customerentity.SupplierAllDetailsForPO;
import org.fintexel.supplier.customerentity.SupplierInvoice;
import org.fintexel.supplier.customerentity.SupplierInvoiceItem;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderStatusRepo;
import org.fintexel.supplier.customerrepository.SupplierInvoiceItemRepo;
import org.fintexel.supplier.customerrepository.SupplierInvoiceRepo;
import org.fintexel.supplier.entity.ApproveMap;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerProfile;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.GetPendingPoResponceForSuppiler;
import org.fintexel.supplier.customerentity.GetPurchesOrder;
import org.fintexel.supplier.customerentity.InvoiceStraching;
import org.fintexel.supplier.customerentity.POFlowableItem;
import org.fintexel.supplier.customerentity.PrsonceLoginCustomerDetails;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerProfileRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderAttachmentRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderItemsRepo;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.InventoryRepo;
import org.fintexel.supplier.repository.ItemCategoryRepo;
import org.fintexel.supplier.repository.ItemSubCategoryRepo;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.SupBankRepo;
import org.fintexel.supplier.repository.SupDepartmentRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.repository.flowablerepo.FlowableRegistrationRepo;
import org.fintexel.supplier.validation.FieldValidation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer/po")
public class PurchaseOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
	
	@Autowired
	private CustomerProfileRepo customerProfileRepo;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private GetCustomerDetails getCustomerDetails;
	
	@Autowired
	private CustomerContactRepo customerContactRepo;
	
	@Autowired
	private SupDetailsRepo supDetailsRepo;
	
	@Autowired
	private SupAddressRepo supAddressRepo;
	
	@Autowired
	private SupBankRepo supBankRepo;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;
	
	@Autowired
	private SupDepartmentRepo supDepartmentRepo;
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@Autowired
	private PurchesOrderRepo purchesOrderRepo;
	
	@Autowired
	private ItemCategoryRepo itemCategoryRepo;
	
	@Autowired
	private PurchesOrderItemsRepo purchesOrderItemsRepo; 
	
	@Autowired
	private CustomerDepartmentsRepo customerDepartmentsRepo; 
	
	@Autowired
	private CustomerAddressRepo customerAddressRepo;
	
	@Autowired
	private ItemSubCategoryRepo itemSubCategoryRepo;
	
	@Autowired
	private PurchesOrderStatusRepo purchesOrderStatusRepo;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private SupplierInvoiceRepo supplierInvoiceRepo;
	
	@Autowired
	private SupplierInvoiceItemRepo supplierInvoiceItemRepo;
	
	@Autowired
	private FlowableRegistrationRepo flowableRegistrationRepo;
	
	@Autowired
	private VendorRegisterRepo vendorRegisterRepo;
	
	@Autowired
	private PurchesOrderAttachmentRepo purchesOrderAttachmentRepo;
	


	private Integer i;
	

	
	@GetMapping("/contractSuppliers")
	public List<SupDetails> getContractSuppliers(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getContractSuppliers()");
		
		try {
			
			long cIdFromToken = getCustomerDetails.getCIdFromToken(token);
			if(cIdFromToken == -1) {
				throw new VendorNotFoundException("Customer Data Not Found");
			}else {
				
				List<CustomerContact> customerContactList = customerContactRepo.findBycId(cIdFromToken);
				
				if(customerContactList.size()>0) {
					List<SupDetails> listSupplierDetails = new ArrayList<>();
					
					for(CustomerContact obj : customerContactList) {
						SupDetails supDetails = supDetailsRepo.findById(obj.getSupplierCode()).get();
						listSupplierDetails.add(supDetails);
					}
					
					return listSupplierDetails;
					
				}else {
					throw new VendorNotFoundException("No Contract Found With This Customer");
				}
				
			}

		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}
	
	
	@GetMapping("/contractSupplierDetails/{code}")
	public SupplierAllDetailsForPO getContractSupplierDetails(@PathVariable("code") String code , @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getContractSuppliers()");
		
		try {
			
			long cIdFromToken = getCustomerDetails.getCIdFromToken(token);
			if(cIdFromToken == -1) {
				throw new VendorNotFoundException("Customer Data Not Found");
			}else {
				
				List<CustomerContact> customerContactList = customerContactRepo.findBycId(cIdFromToken);
				
				if(customerContactList.size()>0) {
					for(CustomerContact obj : customerContactList) {
						SupDetails supDetails = supDetailsRepo.findById(code).get();
						String supplierCode = supDetails.getSupplierCode();
						System.out.println("*****  " + supDetails.toString());
						if(obj.getSupplierCode().equals(code)) {
							SupplierAllDetailsForPO supplierAllDetailsForPO = new SupplierAllDetailsForPO();
							SupDetails supDetails2=new SupDetails();
							SupAddress supAddress=new SupAddress();
							SupBank supBank = new SupBank();
							List<InventoryDetails> findBySupplierCodeInventory = new ArrayList<InventoryDetails>();
							List<SupDepartment> findBySupplierCodeDepertment = new ArrayList<SupDepartment>();
							List<ItemCategory> findBySupplierCodeCategory = new ArrayList<ItemCategory>();
							try {
								supDetails2 = supDetailsRepo.findById(supplierCode).get();
							}catch(Exception e) {
								throw new VendorNotFoundException("Supplier Details is Not Created");
							}
							try {
//								 Optional<SupAddress> findByIsPrimary = supAddressRepo.findByIsPrimaryWithSupplierCode(1, supplierCode);
//								 
//								 if(!findByIsPrimary.isPresent()) {
									 supAddress = supAddressRepo.findBySupplierCodeWithLastRow(0, supplierCode).get();
//									 
//								 }else {
//									 supAddress = findByIsPrimary.get();
//								 }
//								 System.out.println("%%%%%%%%%%@@@@@@@@ "+ findByIsPrimary.toString());
								 
							}catch(Exception e) {
								throw new VendorNotFoundException("Supplier Address is Not Created");
							}
							try {
//								Optional<SupBank> findByIsPrimaryWithSupplierCode = supBankRepo.findByIsPrimaryWithSupplierCode(1, supplierCode);
//							    if(findByIsPrimaryWithSupplierCode.isPresent()) {
//							    	supBank = findByIsPrimaryWithSupplierCode.get();
//							    }else {
							    	supBank = supBankRepo.findBySupplierCodeWithLastRow(0, supplierCode).get();
//							    }
							}catch(Exception e) {
//								throw new VendorNotFoundException("Supplier Bank is Not Created");
							}
							try {
								findBySupplierCodeInventory = inventoryRepo.findBySupplierCode(supplierCode);
							}catch(Exception e) {
//								throw new VendorNotFoundException("Supplier inventory is Not Created");
							}
							try {
								findBySupplierCodeDepertment = supDepartmentRepo.findBySupplierCode(supplierCode);
							}catch(Exception e) {
//								throw new VendorNotFoundException("Supplier Depertment is Not Created");
							}
							try {
								findBySupplierCodeCategory = itemCategoryRepo.findBySupplierCodeWithHaveSubCategory(supplierCode);
							}catch(Exception e) {
//								throw new VendorNotFoundException("Supplier Category is Not Created");
							}
							try {
								supplierAllDetailsForPO.setFindBySupplierCodeDepertment(findBySupplierCodeDepertment);
								supplierAllDetailsForPO.setSupAddress(supAddress);
								supplierAllDetailsForPO.setSupDetails(supDetails2);
								supplierAllDetailsForPO.setSupBank(supBank);
//								supplierAllDetailsForPO.setFindBySupplierCodeInventory(findBySupplierCodeInventory);
								supplierAllDetailsForPO.setFindBySupplierCodeCategory(findBySupplierCodeCategory);
							}catch(Exception e) {
								
							}
							
							
							
							
						
							
							
							return supplierAllDetailsForPO;
							
						}
					}
					
					throw new VendorNotFoundException("No Contract Made With This Customer and Supplier");
					
				}else {
					throw new VendorNotFoundException("No Contract Found With This Customer");
				}
				
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	@GetMapping("/subCategoryByCategory/{id}")
	public SelectedItem getSubCategoryByCategory(@PathVariable("id") Long id , @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getSubCategoryByCategory()");
		
		try {
			System.out.println("&&&&&   "+ id);
			long cIdFromToken = getCustomerDetails.getCIdFromToken(token);
			if(cIdFromToken == -1) {
				throw new VendorNotFoundException("Customer Data Not Found");
			}else {
				
//				List<CustomerContact> customerContactList = customerContactRepo.findBycId(cIdFromToken);
//				
//				if(customerContactList.size()>0) {					
//						ItemCategory itemCategory = itemCategoryRepo.findById(id).get();
//						System.out.println("&&&&&   "+ itemCategory.toString());
//						List<InventoryDetails> findBySupplierCode = inventoryRepo.findBySupplierCode(itemCategory.getSupplierCode());
						List<ItemSubCategory> findByIdSubCatagory = itemSubCategoryRepo.findByCategoryId(id);
						List<InventoryDetails> findByCategoryIdInventory = inventoryRepo.findByCategoryId(id);
						SelectedItem selectedItem = new SelectedItem();
						selectedItem.setFindByCategoryId(findByCategoryIdInventory);
						selectedItem.setFindById(findByIdSubCatagory);
						
						return selectedItem;
//						if(findByCategoryId.get(0).getSupplierCode().equals(findBySupplierCode.get(0).getSupplierCode())) {
//							
//						}else {
						
//						for(CustomerContact obj : customerContactList) {
//							SupDetails supDetails = supDetailsRepo.findById(obj.getSupplierCode()).get();
//							String supplierCode = supDetails.getSupplierCode();
//							if(supplierCode.equals(itemCategory.getSupplierCode())) {
//								System.out.println("if  "+ findByCategoryIdInventory.size());
//								if(itemCategory.getSupplierCode().equals(supplierCode)) {
//									List<ItemSubCategory> findByIdSubCatagory = itemSubCategoryRepo.findByCategoryId(id);
//									SelectedItem selectedItem = new SelectedItem();
//									selectedItem.setFindByCategoryId(findByCategoryIdInventory);
//									selectedItem.setFindById(findByIdSubCatagory);
//									return selectedItem;
////								}else {
////									throw new VendorNotFoundException("No Contract Made With This Customer and Supplier");
////								}
//								
//							}
//						}
//						
//							throw new VendorNotFoundException("No Contract Made With This Customer and Supplier");
////						}
					
//				}else {
//					throw new VendorNotFoundException("No Contract Found With This Customer");
//				}
//				
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	@GetMapping("/pendingCustomer")
	public List<JSONObject> getPendingCustomer() {
	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomer()");
		List<JSONObject> response = new ArrayList<JSONObject>();
		try {
			
			List<PurchesOrder> findByStatus = purchesOrderRepo.findByStatus("WAITING FOR APPROVAL");
			if(findByStatus.size()<0) {
				throw new VendorNotFoundException("No Pending Data");
			}else {
				findByStatus.forEach(obj -> {
					JSONObject temp = new JSONObject();
					CustomerProfile customerProfile = customerProfileRepo.findById((long)obj.getcId()).get();
					temp.put("listPurchesorder", findByStatus);
					temp.put("customerName", customerProfile.getCustomerName());
					response.add(temp);
				});

				return response;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/pendingCustomer/details/{id}")
	public List<POFlowableItem> getPendingCustomerDetails(@PathVariable("id") int id) {
	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomerDetails()");
	List<POFlowableItem> poFlowableItem = new ArrayList<POFlowableItem>();
		try {
			
			List<PurchesOrder> findByCId = purchesOrderRepo.findBycId(id);
			if(findByCId.size()<0) {
				throw new VendorNotFoundException("No Pending Data");
			}else {
				findByCId.forEach(obj->{
					POFlowableItem temp = new POFlowableItem();
					temp.setPurchesOrder(obj);
					List<PurchesOrderItems> findByPOId = purchesOrderItemsRepo.findByPOId(obj.getPOId());
					temp.setPurchesOrderItems(findByPOId);
					poFlowableItem.add(temp);
					
				});
				return poFlowableItem;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	

	@PostMapping("/pendingCustomerStatus")
	public CustomeResponseEntity pendingCustomerStatus(@RequestBody() ArrayList<ApproveMap> approveMap) {
		LOGGER.info("Inside - PurchaseOrderController.pendingCustomerStatus()");
		
		try {
			
			for(ApproveMap obj : approveMap) {
				PurchesOrder purchesOrder = purchesOrderRepo.findById(obj.getId()).get();
				try {
					PurchesOrderStatus purchesOrderStatus = purchesOrderStatusRepo.findById(obj.getId()).get();
					purchesOrderStatus.setPOStatus (obj.getStatus());
					purchesOrderStatus.setComment(obj.getComment());
					purchesOrderStatusRepo.save(purchesOrderStatus);
				}catch(Exception e) {
					
				}
				
				System.out.println("purchesOrder  "+purchesOrder.toString());
				purchesOrder.setStatus(obj.getStatus());
				purchesOrder.setStatusComment(obj.getComment());
				
				
				purchesOrderRepo.save(purchesOrder);
				
				
				
				
				
			}
			return new CustomeResponseEntity("SUCCESS" , "DATA UPDATED SUCCESSFULLY");
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	/*   SUPPLIER SIDE PO */
	
	
	@GetMapping("/item/{value}")
	public List<Map<String, String>> itemToConfirm(@PathVariable("value") String value , @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.itemToConfirm()");
	List<Map<String, String>> itemToConfirmResponse = new ArrayList<>();	
		try {
			
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			System.out.print("****  "+loginSupplierCode );
			if (!loginSupplierCode.equals(null)) {
				
				if(value.equals("TOSHIP")) {
					value = "APPROVED BY SUPPLIER";
				}else if(value.equals("TOCONFIRM")){
					System.out.print("****  "+loginSupplierCode );
					value = "APPROVED BY IT";
				}else if(value.equals("CLOSED")) {
					System.out.print("****  "+loginSupplierCode );
					value = "COMPLETED";
				}
				List<PurchesOrder> findByStatusWithSupplierCode = purchesOrderRepo.findByStatusWithSupplierCode(value , loginSupplierCode);
				
				System.out.print("****  "+loginSupplierCode +  "  ---- " +findByStatusWithSupplierCode.size());
				
				if(findByStatusWithSupplierCode.size()<1) {
					throw new VendorNotFoundException("No Data Found ");
				}
				for(PurchesOrder obj : findByStatusWithSupplierCode) {
					
					String username = customerRegisterRepo.findById(obj.getUserId()).get().getUsername();
					
					Map<String, String> temp = new HashMap<>();
					temp.put("customerName", username);
					temp.put("POId", obj.getPOId()+"");
					temp.put("cId", obj.getcId()+"");
					temp.put("poNumber", obj.getPoNumber());
					temp.put("issuedate", obj.getCreatedOn()+"");
					temp.put("totalValue", (obj.getAmount()+obj.getAmount()*10/100)+"");
					temp.put("taxableValue", (obj.getAmount()*10/100)+"");
					temp.put("status", obj.getStatus());
					itemToConfirmResponse.add(temp);
				}
				return itemToConfirmResponse;
				
			}else {
				throw new VendorNotFoundException("Token Expir");
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	@GetMapping("/itemByPOId/{POId}")
	public List<PurchesOrderItems> getitemByPOId(@PathVariable("POId") Long id) {
		LOGGER.info("Inside - PurchaseOrderController.getitemByPOId()");
		try {
			
			List<PurchesOrderItems> findByPOId = purchesOrderItemsRepo.findByPOId(id);
			if(findByPOId.size()<1) {
				throw new VendorNotFoundException("No Data Found");
			}
			return findByPOId;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	
	
	@PostMapping("/item/changeStatus/{id}")
	public CustomeResponseEntity changeItemStatus(@PathVariable("id") Long id,@RequestBody() String value , @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.changeItemStatus()");
		try {
			
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			
			
			PurchesOrderStatus purchesOrderStatus = purchesOrderStatusRepo.findById(id).get();
			PurchesOrder purchesOrder = purchesOrderRepo.findById(id).get();
			if(loginSupplierCode.equals(purchesOrder.getSupplierCode())) {
				purchesOrderStatus.setPOStatus(value);
				purchesOrderStatusRepo.save(purchesOrderStatus);
				purchesOrder.setStatus(value);
				purchesOrderRepo.save(purchesOrder);
				
				return new CustomeResponseEntity("SUCCESS" , "DATA UPDATED");
			}else {
				throw new VendorNotFoundException("This PO Not Under By This Supplier");
			}
			
			
			
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	
	
	@PostMapping("/invoice")
	public CustomeResponseEntity invoice(@RequestBody() InvoiceStraching invoiceStraching , @RequestHeader(name ="Authorization") String token) {
		String taskID1_ = "", taskID2_ = "", processInstID_ = "";
		
		String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
		System.out.println("loginSupplierCode  "+loginSupplierCode);
		LOGGER.info("Inside - PurchaseOrderController.invoice()");
		try {
			
			if(fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getPOId()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvDate()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvDesc()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvTaxid()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getRemitTo()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getBillTo()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getShipCharges()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getHandlingCharges()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getTotalGross()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getTotalTax()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getSubtotal()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getTotalAmount()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getStatus()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvAttachment()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getCreatedBy()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getCreatedOn()) 
					
					
					) {
				
				
				
				PurchesOrder purchesOrder = purchesOrderRepo.findById(invoiceStraching.getSupplierInvoice().getPOId()).get();
				System.out.println("loginSupplierCode  "+invoiceStraching.getSupplierInvoice().getPOId());
				List<PurchesOrderItems> findByPOId = purchesOrderItemsRepo.findByPOId(invoiceStraching.getSupplierInvoice().getPOId());
				
				if(!(findByPOId.size()+"").equals(invoiceStraching.getPurchesOrderItems()+"")) {
					throw new VendorNotFoundException("PO Item List Not Match ");
				}
				
				List<PurchesOrderItems> purchesOrderItems = invoiceStraching.getPurchesOrderItems();
				
				SupplierInvoice save = supplierInvoiceRepo.save(invoiceStraching.getSupplierInvoice());
				System.out.println("loginSupplierCode  "+loginSupplierCode);
				i=0;
				for(PurchesOrderItems obj : purchesOrderItems) {
					if(!(obj.getItemId() == findByPOId.get(i).getItemId()))
						throw new VendorNotFoundException("PO Items List are Not Match ");
					
				}
				
				
				i=0;
				for(PurchesOrderItems obj : purchesOrderItems) {
					SupplierInvoiceItem supplierInvoiceItem = new SupplierInvoiceItem();
					supplierInvoiceItem.setInvId(save.getInvId());
					supplierInvoiceItem.setPoitemId(invoiceStraching.getPurchesOrderItems().get(i).getPOItemId());
					
					supplierInvoiceItem.setItemQty(invoiceStraching.getPurchesOrderItems().get(i).getQty());
					supplierInvoiceItem.setItemPrice(obj.getUnitPrice());
					supplierInvoiceItem.setItemGross(invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice());
					supplierInvoiceItem.setItemTax(obj.getTax());
					supplierInvoiceItem.setItemSubtotal((invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice() * 10 / 100) +(invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice()));
					supplierInvoiceItem.setItemTotal((invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice() * 10 / 100) +(invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice()));
					SupplierInvoiceItem save2 = supplierInvoiceItemRepo.save(supplierInvoiceItem);
					
					/*
					 * ----------- REQUEST PROCESS ID with PROCESS DEFINITION KEY
					 * -------------------------------------------------------
					 */

			
			
			
			
					SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
					System.out.println("supDetails  "+supDetails.toString());
					
					VendorRegister vendorRegister = vendorRegisterRepo.findById(supDetails.getRegisterId()).get();
					System.out.println("vendorRegister  "+vendorRegister.toString());
					
			
					
					SupBank supBank = supBankRepo.findByIsPrimaryWithSupplierCode(1, loginSupplierCode).get();
					System.out.println("supBank  "+supBank.toString());
					
					SupAddress supAddress = supAddressRepo.findByIsPrimaryWithSupplierCode(1, loginSupplierCode).get();
					System.out.println("supAddress  "+supAddress.toString());
//					supDepartmentRepo.findBySupplierCode(loginSupplierCode);
			
			
			
			
			
					Optional<FlowableRegistration> findByAuthorAndTitle = flowableRegistrationRepo
							.findByAuthorAndTitle("demo-AP");
					RestTemplate restTemplate = new RestTemplate();

					HttpHeaders BaseAuthHeader = new HttpHeaders();
					BaseAuthHeader.setContentType(MediaType.APPLICATION_JSON);
					BaseAuthHeader.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
					BaseAuthHeader.setBasicAuth("admin", "test");
					
					/*
					 * ============================== ProcessInstance Request
					 * ================================================
					 */
					Map<String, Object> pDMap = new HashMap<>();
					pDMap.put("processDefinitionId", findByAuthorAndTitle.get().getId());
					HttpEntity<Map<String, Object>> pDEntity = new HttpEntity<>(pDMap, BaseAuthHeader);
					ResponseEntity<String> response = restTemplate.postForEntity(
							"http://65.2.162.230:8080/flowable-rest/service/runtime/process-instances", pDEntity,
							String.class);
				
			
					
					
				
					
					
					/*
					 * ============================== Query Task 1
					 * ================================================
					 */

					Map<String, Object> queryMap = new HashMap<>();
					JSONObject jsonObject = new JSONObject(response.getBody());
					processInstID_ = (String) jsonObject.get("id");
					queryMap.put("processInstanceId", processInstID_);

//					filterVendorReg.setProcessId(processInstID_);
					LOGGER.info("ProcessInstanceID : " + processInstID_);

					HttpEntity<Map<String, Object>> baseAuthEntity = new HttpEntity<>(queryMap, BaseAuthHeader);
					ResponseEntity<String> queryRequest_1 = restTemplate.exchange(
							"http://65.2.162.230:8080/flowable-rest/service/query/tasks", HttpMethod.POST, baseAuthEntity,
							String.class, 1);
					
					
					
					
					/*
					 * ----------- POST FORM VARIABLES
					 * -------------------------------------------------------
					 */

					JSONArray taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());
					JSONArray formReqBody = new JSONArray();

					taskID1_ = (String) taskJA.getJSONObject(0).get("id");

					LOGGER.info("Registration TaskID_1 : " + taskID1_);

					JSONObject suppliername = new JSONObject();
					suppliername.put("name", "city");
					suppliername.put("scope", "local");
					suppliername.put("type", "string");
					suppliername.put("value", supAddress.getCity());
					formReqBody.put(suppliername);
					
					System.out.println("supAddress.getCity() "+supAddress.getCity());

					JSONObject supplieremail = new JSONObject();
					supplieremail.put("name", "country");
					supplieremail.put("scope", "local");
					supplieremail.put("type", "string");
					supplieremail.put("value", supAddress.getCountry());
					formReqBody.put(supplieremail);

					System.out.println("supAddress.getCountry() "+supAddress.getCountry());
					
					JSONObject username = new JSONObject();
					username.put("name", "invoiceamount");
					username.put("scope", "local");
					username.put("type", "string");
					username.put("value", save.getTotalAmount());
//					username.put("value", "");
					formReqBody.put(username);
					
					

					JSONObject password = new JSONObject();
					password.put("name", "invoicedate");
					password.put("scope", "local");
					password.put("type", "string");
					password.put("value", save.getCreatedOn());
//					password.put("value", "");
					formReqBody.put(password);

					JSONObject registrationid = new JSONObject();
					registrationid.put("name", "invoicemode");
					registrationid.put("scope", "local");
					registrationid.put("type", "string");
					registrationid.put("value", "");
					formReqBody.put(registrationid);

					
					JSONObject invoicenumber = new JSONObject();
					invoicenumber.put("name", "invoicenumber");
					invoicenumber.put("scope", "local");
					invoicenumber.put("type", "string");
					invoicenumber.put("value", save.getInvId());
//					invoicenumber.put("value", "");
					formReqBody.put(invoicenumber);
					
					
					
					JSONObject invoicetype = new JSONObject();
					invoicetype.put("name", "invoicetype");
					invoicetype.put("scope", "local");
					invoicetype.put("type", "string");
					invoicetype.put("value", "");
					formReqBody.put(invoicetype);

					
					
					JSONObject pincode = new JSONObject();
					pincode.put("name", "pincode");
					pincode.put("scope", "local");
					pincode.put("type", "string");
					pincode.put("value", supAddress.getPostalCode()+"");
					formReqBody.put(pincode);
					
					System.out.println("supAddress.getPostalCode() "+supAddress.getPostalCode());

					
					JSONObject podate = new JSONObject();
					podate.put("name", "podate");
					podate.put("scope", "local");
					podate.put("type", "string");
//					podate.put("value", "" );
					podate.put("value", purchesOrder.getCreatedOn() );
					formReqBody.put(podate);

					
					JSONObject ponumber = new JSONObject();
					ponumber.put("name", "ponumber");
					ponumber.put("scope", "local");
					ponumber.put("type", "string");
//					ponumber.put("value", "");
					ponumber.put("value", purchesOrder.getPoNumber());
					formReqBody.put(ponumber);

					
					JSONObject state = new JSONObject();
					state.put("name", "state");
					state.put("scope", "local");
					state.put("type", "string");
					state.put("value", supAddress.getRegion());
					formReqBody.put(state);
					
					System.out.println("supAddress.getRegion() "+supAddress.getRegion());

					
					JSONObject taskdate = new JSONObject();
					taskdate.put("name", "taskdate");
					taskdate.put("scope", "local");
					taskdate.put("type", "string");
					taskdate.put("value", "" );
					formReqBody.put(taskdate);

					
					JSONObject vendoraccount = new JSONObject();
					vendoraccount.put("name", "vendoraccount");
					vendoraccount.put("scope", "local");
					vendoraccount.put("type", "string");
					vendoraccount.put("value", supBank.getBankAccountNo()+"");
					formReqBody.put(vendoraccount);
					
					System.out.println("supBank.getBankAccountNo() "+supBank.getBankAccountNo()+"");

					
					JSONObject vendoraddress = new JSONObject();
					vendoraddress.put("name", "vendoraddress");
					vendoraddress.put("scope", "local");
					vendoraddress.put("type", "string");
					vendoraddress.put("value", supAddress.getAddress1());
					formReqBody.put(vendoraddress);
					
					
					System.out.println("supAddress.getAddress1() "+supAddress.getAddress1());
					
					
					JSONObject vendoremail = new JSONObject();
					vendoremail.put("name", "vendoremail");
					vendoremail.put("scope", "local");
					vendoremail.put("type", "string");
					vendoremail.put("value", vendorRegister.getEmail());
					formReqBody.put(vendoremail);
					
					System.out.println("vendorRegister.getEmail() "+vendorRegister.getEmail());
					
					
					JSONObject vendorid = new JSONObject();
					vendorid.put("name", "vendorid");
					vendorid.put("scope", "local");
					vendorid.put("type", "string");
					vendorid.put("value", vendorRegister.getRegisterId()+"");
					formReqBody.put(vendorid);
					
					System.out.println("vendorRegister.getRegisterId() "+vendorRegister.getRegisterId());
					
					JSONObject vendorname = new JSONObject();
					vendorname.put("name", "vendorname");
					vendorname.put("scope", "local");
					vendorname.put("type", "string");
					vendorname.put("value", vendorRegister.getSupplierCompName());
					formReqBody.put(vendorname);
					
					
					System.out.println("vendorRegister.getSupplierCompName() "+vendorRegister.getSupplierCompName());
					

					
					
					
					HttpEntity<String> formReqEntity = new HttpEntity<String>(formReqBody.toString(), BaseAuthHeader);

//					filterVendorReg.setTaskId(taskID1_);

					ResponseEntity<String> formResponse = restTemplate.exchange(
							"http://65.2.162.230:8080/flowable-rest/service/runtime/tasks/" + taskID1_ + "/variables",
							HttpMethod.POST, formReqEntity, String.class, 1);

					
					
					
					
					
					
					
					HttpHeaders loginHeader = new HttpHeaders();
					loginHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					MultiValueMap<String, String> loginMap = new LinkedMultiValueMap<String, String>();

					loginMap.add("j_username", "indexer");
					loginMap.add("j_password", "123");
					loginMap.add("submit", "Login");
					loginMap.add("_spring_security_remember_me", "true");

					HttpEntity<MultiValueMap<String, String>> loginReq = new HttpEntity<MultiValueMap<String, String>>(
							loginMap, loginHeader);
					ResponseEntity<String> loginResponse = restTemplate
							.postForEntity("http://65.2.162.230:8080/DB-idm/app/authentication", loginReq, String.class);
					JSONObject cookieJO = new JSONObject(loginResponse.getHeaders());
					String coockie_ = cookieJO.get("Set-Cookie").toString().replace("[", "").replace("]", "").replace("\"",
							"");

					LOGGER.info("Coockie : " + coockie_);

					/*
					 * ----------- AUTO CLAIMING Registration
					 * -------------------------------------------------------
					 */

					HttpHeaders autoCliamHeader = new HttpHeaders();
					autoCliamHeader.add("Cookie", coockie_);
					HttpEntity autoClaimEntity = new HttpEntity(null, autoCliamHeader);
					ResponseEntity autoClaimResponse = restTemplate.exchange(
							"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID1_ + "/action/claim", HttpMethod.PUT,
							autoClaimEntity, String.class);
					System.out.println("auto complite froom shantanu:    " + autoClaimResponse);

					/*
					 * ----------- AUTO COMPLETE Registration
					 * -------------------------------------------------------
					 */

					HttpHeaders autoCompleteHeader = new HttpHeaders();
					autoCompleteHeader.add("Cookie", coockie_);
					autoCompleteHeader.setContentType(MediaType.APPLICATION_JSON);

					JSONObject autoCompleate = new JSONObject();
					autoCompleate.put("taskIdActual", taskID1_);
					autoCompleate.put("invoicemode","");
					autoCompleate.put("workunitid", "");
					autoCompleate.put("taskdate", "");
					autoCompleate.put("vendorname",vendorRegister.getSupplierCompName() );
					autoCompleate.put("vendorid", vendorRegister.getRegisterId() );
					autoCompleate.put("vendoremail", vendorRegister.getEmail());
					autoCompleate.put("vendoraddress", supAddress.getAddress1());
					autoCompleate.put("city", supAddress.getCity());
					autoCompleate.put("state", supAddress.getRegion());
					autoCompleate.put("pincode", supAddress.getPostalCode() );
					autoCompleate.put("vendoraccount", supBank.getBankAccountNo() );
					autoCompleate.put("country", supAddress.getCountry());
					autoCompleate.put("invoicetype", "" );
					autoCompleate.put("invoicenumber", save.getInvId()+"" );
					autoCompleate.put("invoiceamount", save.getTotalAmount()+"");
					autoCompleate.put("invoicedate", save.getCreatedOn()+"" );
					autoCompleate.put("podate", purchesOrder.getCreatedOn()+"");
					autoCompleate.put("ponumber", purchesOrder.getPoNumber()+"");
					

					JSONObject autoCompleate_ = new JSONObject();
					autoCompleate_.put("formId", "cf1fb287-0974-11ec-8348-0a5bf303a9fe");
					autoCompleate_.put("values", autoCompleate);

					LOGGER.info("Body  " + autoCompleate_);
					LOGGER.info("headers  " + autoCompleteHeader);

					HttpEntity<String> autoCompeleteEntity = new HttpEntity<String>(autoCompleate_.toString(),
							autoCompleteHeader);
					ResponseEntity autoCompleteResponse = restTemplate.exchange(
							"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID1_, HttpMethod.POST,
							autoCompeleteEntity, String.class);
					LOGGER.info("Result  " + autoCompleteResponse.getHeaders());

					/*
					 * ----------- QUERY TO FETCH TASKID_2
					 * -------------------------------------------------------
					 */

					queryRequest_1 = restTemplate.exchange("http://65.2.162.230:8080/flowable-rest/service/query/tasks",
							HttpMethod.POST, baseAuthEntity, String.class, 1);
					taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());

					taskID2_ = (String) taskJA.getJSONObject(0).get("id");
					LOGGER.info("Registration TaskID_2 : " + taskID2_);

					// ----------- AUTO CLAIMING REGISTRATION APPROVAL
					// -------------------------------------------------------
					autoClaimResponse = restTemplate.exchange(
							"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID2_ + "/action/claim", HttpMethod.PUT,
							autoClaimEntity, String.class);
					
					
					System.out.println("response =============   ");
					System.out.println("response =============   "+queryRequest_1);


					
					
				}
				
				
				
				
				
			
				
				
				
				
				return new CustomeResponseEntity("SUCCESS","DATA ADD SUCCESSFULLY");
				
				
				
			}else {
				throw new VendorNotFoundException("Validation Error");
			}
////			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	
	@GetMapping("/getLoginCustomerDetails/{supplierCode}")
	public PrsonceLoginCustomerDetails getLoginCustomerDetails(@PathVariable String supplierCode,@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginDetails()");
		// Test push
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				PrsonceLoginCustomerDetails loginCustomerDetails = new PrsonceLoginCustomerDetails();
				
				Optional<CustomerAddress> findAddressByIsPrimary = customerAddressRepo.findLastCompanyPrimaryAddress(0, companyProfileIdByCustomerId);
				if (findAddressByIsPrimary.isPresent()) {
					loginCustomerDetails.setCustomerAddress(findAddressByIsPrimary.get());
				}
				
				List<CustomerDepartments> findDepartmentBycId = customerDepartmentsRepo.findBycId(companyProfileIdByCustomerId);
				if (findDepartmentBycId.size() > 0) {
					loginCustomerDetails.setCustomerDepartments(findDepartmentBycId);
				}
				
				Optional<CustomerProfile> findCustomerProfileById = customerProfileRepo.findById(companyProfileIdByCustomerId);
				
				if (findCustomerProfileById.isPresent()) {
					loginCustomerDetails.setCustomerName(findCustomerProfileById.get().getCustomerName());
				}
				
				String posize = Integer.toString(purchesOrderRepo.findBycId((int) companyProfileIdByCustomerId).size());
				 
				switch (posize.length()) {
				case 0:
					loginCustomerDetails.setPoNumber("PO - 00" + Integer.parseInt(posize+1));
					break;
				case 1:
					loginCustomerDetails.setPoNumber("PO - 00" + Integer.parseInt(posize+1));
					break;
				case 2:
					loginCustomerDetails.setPoNumber("PO - 0" + Integer.parseInt(posize+1));
					break;
				default:
					loginCustomerDetails.setPoNumber("PO - " + Integer.parseInt(posize+1));
					break;
				}
				
				Optional<CustomerContact> findContactTrams = customerContactRepo.findContactTrams(companyProfileIdByCustomerId, supplierCode);
				
				if (findContactTrams.isPresent()) {
					loginCustomerDetails.setContractTerms(findContactTrams.get().getContractTerms());
					
					loginCustomerDetails.setContractId(findContactTrams.get().getContractId());
				}
				
				return loginCustomerDetails;
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getLoginCustomerAllPO")
	public List<GetPurchesOrder> getLoginCustomerAllPO(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginCustomerAllPO()");
		try {
			List<GetPurchesOrder> itemList = new ArrayList<GetPurchesOrder>();
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
//				List<RequestPurchesOrder> purchesOrders = new ArrayList<RequestPurchesOrder>();
				List<PurchesOrder> findPOBycId = purchesOrderRepo.findBycId((int) companyProfileIdByCustomerId);
				//LOGGER.info("customerIdFromToken present:   ||||||||------||||||   ");
				if (findPOBycId.size() > 0) {
					findPOBycId.forEach(po -> {
//						LOGGER.info(po.getB)
						if (po.getStatus().equals("DRAFT")) {
							try {
								
								GetPurchesOrder order = new GetPurchesOrder();
				
								order.setcId(po.getcId());
								order.setPOId(po.getPOId());
								order.setPoNumber(po.getPoNumber());
								order.setUserId(po.getUserId());
								order.setUserId(po.getUserId());
								order.setSupplierCode(po.getSupplierCode());
								order.setDepartmentId(po.getDepartmentId());
								order.setCusAddrId(po.getCusAddrId());
								
								
								Optional<CustomerDepartments> findDepartmentById = customerDepartmentsRepo.findById(po.getDepartmentId());
								order.setDepartmentName(findDepartmentById.get().getDepartmentName());
								
								
								Optional<CustomerAddress> findCustomerAddressById = customerAddressRepo.findById(po.getCusAddrId());
								JSONObject customerAddressJsonObject = new JSONObject(findCustomerAddressById.get());
								order.setCusAddrText(customerAddressJsonObject.toString());
								
								order.setSupAddrId(po.getSupAddrId());
								
								Optional<SupAddress> findSuppAddressById = supAddressRepo.findById(po.getSupAddrId());
								JSONObject suppAddressJsonObject = new JSONObject(findSuppAddressById.get());
								order.setSupAddrText(suppAddressJsonObject.toString());
								
								order.setContractId(po.getContractId());
								order.setContractTerms(po.getContractTerms());
								order.setComment(po.getComment());
								order.setShipToId(po.getShipToId());
								
								order.setBillToId(po.getBillToId());
								order.setBillToText(po.getBillToText());
								
								Optional<CustomerAddress> findShipToAddressById = customerAddressRepo.findById(po.getShipToId());
								JSONObject shipToAddressJsonObject = new JSONObject(findShipToAddressById.get());
								order.setShipToText(shipToAddressJsonObject.toString());
								
								order.setDeliveryToId(po.getDeliveryToId());
								
//								Optional<CustomerRegister> finDeliveryDetailsdById = customerRegisterRepo.findById(po.getDeliveryToId());
//								JSONObject deliveryDetailsJsonObject = new JSONObject(finDeliveryDetailsdById.get());
//								order.setDeliveryToText(deliveryDetailsJsonObject.toString());
								order.setDeliveryToText(po.getDeliveryToText());
								
								order.setCurType(po.getCurType());
								order.setAmount(po.getAmount());
								order.setStatus(po.getStatus());
								order.setStatusComment(po.getStatusComment());
								order.setCreatedBy(po.getCreatedBy());
								order.setCreatedOn(po.getCreatedOn());
								List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(po.getPOId());
								
								List<PurchesOrderItems> items = new ArrayList<PurchesOrderItems>();
								
								findPoItemByPOId.forEach(item -> {
									items.add(item);
								});
								
								order.setPurchesOrderItems(items);
								
								itemList.add(order);
								
								
								//purchesOrders.add(order);
								
								
							} catch (Exception e) {
								throw new VendorNotFoundException(e.getMessage());
							}
						} else {
							LOGGER.info("Not draft:   ||||||||------||||||   ");
							GetPurchesOrder order = new GetPurchesOrder();
							
							order.setcId(po.getcId());
							order.setPOId(po.getPOId());
							order.setPoNumber(po.getPoNumber());
							order.setUserId(po.getUserId());
							order.setUserId(po.getUserId());
							order.setSupplierCode(po.getSupplierCode());
							order.setDepartmentId(po.getDepartmentId());
							order.setCusAddrId(po.getCusAddrId());
							order.setCusAddrText(po.getCusAddrText());
							order.setSupAddrText(po.getSupAddrText());
							order.setContractId(po.getContractId());
							order.setContractTerms(po.getContractTerms());
							order.setComment(po.getComment());
							order.setShipToId(po.getShipToId());
							order.setShipToText(po.getShipToText());
							order.setDeliveryToId(po.getDeliveryToId());
							order.setDeliveryToText(po.getDeliveryToText());
							order.setBillToId(po.getBillToId());
							order.setBillToText(po.getBillToText());
							order.setCurType(po.getCurType());
							order.setAmount(po.getAmount());
							order.setStatus(po.getStatus());
							order.setStatusComment(po.getStatusComment());
							order.setCreatedBy(po.getCreatedBy());
							order.setCreatedOn(po.getCreatedOn());
							
							Optional<CustomerDepartments> findDepartmentById = customerDepartmentsRepo.findById(po.getDepartmentId());
							order.setDepartmentName(findDepartmentById.get().getDepartmentName());
							
							
							List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(po.getPOId());
							
							
							List<PurchesOrderItems> items = new ArrayList<PurchesOrderItems>();
							
							findPoItemByPOId.forEach(item -> {
								items.add(item);
							});
							
							order.setPurchesOrderItems(items);
							
							
							itemList.add(order);
//							purchesOrders.add(po);
							
							
						}
					});
					
					return itemList;
				} else {
					throw new VendorNotFoundException("PO not found for this company");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getUserByDepartmentId/{departmentId}")
	public List<CustomeResponseEntity> getCustomerByDepartmentId(@PathVariable long departmentId) {
		LOGGER.info("Inside - PurchaseOrderController.getCustomerByDepartmentId()");
		try {
			
//			Optional<CustomerRegister> findUserByDepartment = customerRegisterRepo.findUserByDepartment(departmentId);
//			if (findUserByDepartment.isPresent()) {
//				return findUserByDepartment.get();
//			} else {
//				throw new VendorNotFoundException("Data note found");
//			}
			List<CustomeResponseEntity> customeResponseEntities = new ArrayList<CustomeResponseEntity>();
			
			Optional<CustomerDepartments> findDepartmentById = customerDepartmentsRepo.findById(departmentId);
			
			if (findDepartmentById.isPresent()) {
				customeResponseEntities.add(new CustomeResponseEntity("SUCCRSS", findDepartmentById.get().getPrimaryContact()));
				customeResponseEntities.add(new CustomeResponseEntity("SUCCRSS", findDepartmentById.get().getSecondaryContact()));
				return customeResponseEntities;
			} else {
				throw new VendorNotFoundException("Department not found");
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@PostMapping("/savePO")
	public CustomeResponseEntity savePO(@RequestBody RequestPurchesOrder requestPurchesOrder, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.savePO()");
		try {
			LOGGER.info("Data &&&&&&  "+requestPurchesOrder.getPurchesOrder());
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				if (
						fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getPoNumber()) 
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getUserId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getSupplierCode())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getDepartmentId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getCusAddrId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getCusAddrText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getSupAddrId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getSupAddrText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getContractId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getContractTerms())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getComment())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getShipToId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getShipToText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getBillToId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getBillToText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getDeliveryToId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getDeliveryToText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getAmount())
					) {
					
					
					Optional<PurchesOrder> findPoByPoNumber = purchesOrderRepo.findByPoNumber(requestPurchesOrder.getPurchesOrder().getPoNumber());
					
//					LOGGER.info("Call databade>>>>>>{{{}}}}"+findPoByPoNumber.get());

					if (findPoByPoNumber.isPresent()) {
						requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
						requestPurchesOrder.getPurchesOrder().setStatus("DRAFT");
						requestPurchesOrder.getPurchesOrder().setPOId(findPoByPoNumber.get().getPOId());
						LOGGER.info("DETAILS IS >>>>>>}}}}}"+requestPurchesOrder.getPurchesOrder());
						PurchesOrder savePurchesOrder = purchesOrderRepo.save(requestPurchesOrder.getPurchesOrder());
						if (!savePurchesOrder.equals(null)) {
//							List<PurchesOrderItems> findItemByPOId = purchesOrderItemsRepo.findByPOId(findPoByPoNumber.get().getPOId());
							requestPurchesOrder.getPurchesOrderItems().forEach(item -> {
								Optional<PurchesOrderItems> finItemdById = purchesOrderItemsRepo.findById(item.getPOItemId());
								item.setPOId(savePurchesOrder.getPOId());
								item.setItemGross(item.getUnitPrice() * item.getQty());
								item.setPOItemId(finItemdById.get().getPOItemId());
								purchesOrderItemsRepo.save(item);
							});
							
							return new CustomeResponseEntity("SUCCESS","PO submit successfully");
							
						} else {
							throw new VendorNotFoundException("Data Not save in data base");
						}
						
					} else {
						LOGGER.info("Frist Save <<<>>>><<>>>>>  ");
						requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
						requestPurchesOrder.getPurchesOrder().setStatus("DRAFT");
						
						LOGGER.info("Before save data "+requestPurchesOrder.getPurchesOrder());
						
						PurchesOrder savePurchesOrder = purchesOrderRepo.save(requestPurchesOrder.getPurchesOrder());
						
						LOGGER.info("After save data "+savePurchesOrder);
//						System.out.println("SU:2021-08-29:16  " + savePurchesOrder.toString());
						if (!savePurchesOrder.equals(null)) {
							
						requestPurchesOrder.getPurchesOrderItems().forEach(item -> {
							item.setItemGross(item.getUnitPrice() * item.getQty());
							item.setPOId(savePurchesOrder.getPOId());
							purchesOrderItemsRepo.save(item);
						});
						
						return new CustomeResponseEntity("SUCCESS","PO submit successfully");
							
						} else {
							throw new VendorNotFoundException("Data Not save in data base");
						}
					}
					
				} else {
					throw new VendorNotFoundException("validation error");
				}
				
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@PostMapping("/submitPO") 
	public CustomeResponseEntity submitPO(@RequestBody RequestPurchesOrder requestPurchesOrder, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.submitPO()");
		try {
			LOGGER.info("Data &&&&&&  "+requestPurchesOrder.getPurchesOrder());
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			if (companyProfileIdByCustomerId == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				if (
						fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getPoNumber()) 
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getUserId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getSupplierCode())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getDepartmentId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getCusAddrId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getCusAddrText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getSupAddrId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getSupAddrText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getContractId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getContractTerms())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getComment())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getShipToId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getShipToText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getBillToId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getBillToText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getDeliveryToId())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getDeliveryToText())
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getAmount())
					) {
					
					LOGGER.info("After validation");
					
					Optional<PurchesOrder> findPoByPoNumber = purchesOrderRepo.findByPoNumber(requestPurchesOrder.getPurchesOrder().getPoNumber());
		
//					LOGGER.info("Call databade>>>>>>{{{}}}}"+findPoByPoNumber.get());
					
					if (findPoByPoNumber.isPresent()) {
						requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
						requestPurchesOrder.getPurchesOrder().setStatus("WAITING FOR APPROVAL");
						requestPurchesOrder.getPurchesOrder().setPOId(findPoByPoNumber.get().getPOId());
						
						LOGGER.info("Before Update PO  >>>><<<<<<<"+requestPurchesOrder.getPurchesOrder());
						
						PurchesOrder savePurchesOrder = purchesOrderRepo.save(requestPurchesOrder.getPurchesOrder());
						
						LOGGER.info("After Update PO  >>>><<<<<<<"+requestPurchesOrder.getPurchesOrder());
						if (!savePurchesOrder.equals(null)) {
//							List<PurchesOrderItems> findItemByPOId = purchesOrderItemsRepo.findByPOId(findPoByPoNumber.get().getPOId());
							LOGGER.info("Item is.....||||.....___||__"+requestPurchesOrder.getPurchesOrderItems());
							requestPurchesOrder.getPurchesOrderItems().forEach(item -> {
								List<PurchesOrderItems> finItemdById = purchesOrderItemsRepo.findByPOId(savePurchesOrder.getPOId());
								item.setPOId(savePurchesOrder.getPOId());
								item.setItemGross(item.getUnitPrice() * item.getQty());
								finItemdById.forEach(setID -> {
									item.setPOItemId(setID.getPOItemId());
								});
								purchesOrderItemsRepo.save(item);
							});
							
							try {
								if (fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrderAttachment().getAtName()) && fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrderAttachment().getAtPath())) {
									requestPurchesOrder.getPurchesOrderAttachment().setPOId(savePurchesOrder.getPOId());
									purchesOrderAttachmentRepo.save(requestPurchesOrder.getPurchesOrderAttachment());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							return new CustomeResponseEntity("SUCCESS","PO submit successfully");
							
						} else {
							throw new VendorNotFoundException("Data Not save in data base");
						}
						
					} else {
						requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
						requestPurchesOrder.getPurchesOrder().setStatus("WAITING FOR APPROVAL");
						
						PurchesOrder savePurchesOrder = purchesOrderRepo.save(requestPurchesOrder.getPurchesOrder());
//						System.out.println("SU:2021-08-29:16  " + savePurchesOrder.toString());
						if (!savePurchesOrder.equals(null)) {
							
						requestPurchesOrder.getPurchesOrderItems().forEach(item -> {
							item.setItemGross(item.getUnitPrice() * item.getQty());
							item.setPOId(savePurchesOrder.getPOId());
							purchesOrderItemsRepo.save(item);
						});
						
						try {
							if (fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrderAttachment().getAtName()) && fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrderAttachment().getAtPath())) {
								requestPurchesOrder.getPurchesOrderAttachment().setPOId(savePurchesOrder.getPOId());
								purchesOrderAttachmentRepo.save(requestPurchesOrder.getPurchesOrderAttachment());
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						return new CustomeResponseEntity("SUCCESS","PO submit successfully");
							
						} else {
							throw new VendorNotFoundException("Data Not save in data base");
						}
					}
					
				} else {
					throw new VendorNotFoundException("validation error");
				}
				
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/getLoginCustomerAllPOFromSupplier/{cId}")
	public List<GetPurchesOrder> getLoginCustomerAllPOFromSupplier(@PathVariable long cId) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginCustomerAllPOFromSupplier()");
		try {
			List<GetPurchesOrder> itemList = new ArrayList<GetPurchesOrder>();
//			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
//			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			
			if (cId == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
//				List<RequestPurchesOrder> purchesOrders = new ArrayList<RequestPurchesOrder>();
				
				List<PurchesOrder> findPOBycId = purchesOrderRepo.findBycId((int) cId);
				if (findPOBycId.size() > 0) {
					findPOBycId.forEach(po -> {
//						LOGGER.info(po.getB)
						if (po.getStatus().equals("DRAFT")) {
							try {
								
								GetPurchesOrder order = new GetPurchesOrder();
				
								order.setcId(po.getcId());
								order.setPOId(po.getPOId());
								order.setPoNumber(po.getPoNumber());
								order.setUserId(po.getUserId());
								order.setUserId(po.getUserId());
								order.setSupplierCode(po.getSupplierCode());
								order.setDepartmentId(po.getDepartmentId());
								order.setCusAddrId(po.getCusAddrId());
								
								Optional<CustomerAddress> findCustomerAddressById = customerAddressRepo.findById(po.getCusAddrId());
								JSONObject customerAddressJsonObject = new JSONObject(findCustomerAddressById.get());
								order.setCusAddrText(customerAddressJsonObject.toString());
								
								order.setSupAddrId(po.getSupAddrId());
								
								Optional<SupAddress> findSuppAddressById = supAddressRepo.findById(po.getSupAddrId());
								JSONObject suppAddressJsonObject = new JSONObject(findSuppAddressById.get());
								order.setSupAddrText(suppAddressJsonObject.toString());
								
								order.setContractId(po.getContractId());
								order.setContractTerms(po.getContractTerms());
								order.setComment(po.getComment());
								order.setShipToId(po.getShipToId());
								
								order.setBillToId(po.getBillToId());
								order.setBillToText(po.getBillToText());
								
								Optional<CustomerAddress> findShipToAddressById = customerAddressRepo.findById(po.getShipToId());
								JSONObject shipToAddressJsonObject = new JSONObject(findShipToAddressById.get());
								order.setShipToText(shipToAddressJsonObject.toString());
								
								order.setDeliveryToId(po.getDeliveryToId());
								
//								Optional<CustomerRegister> finDeliveryDetailsdById = customerRegisterRepo.findById(po.getDeliveryToId());
//								JSONObject deliveryDetailsJsonObject = new JSONObject(finDeliveryDetailsdById.get());
//								order.setDeliveryToText(deliveryDetailsJsonObject.toString());
								order.setDeliveryToText(po.getDeliveryToText());
								
								order.setCurType(po.getCurType());
								order.setAmount(po.getAmount());
								order.setStatus(po.getStatus());
								order.setStatusComment(po.getStatusComment());
								order.setCreatedBy(po.getCreatedBy());
								order.setCreatedOn(po.getCreatedOn());
								List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(po.getPOId());
								
								List<PurchesOrderItems> items = new ArrayList<PurchesOrderItems>();
								
								findPoItemByPOId.forEach(item -> {
									items.add(item);
								});
								
								order.setPurchesOrderItems(items);
								
								itemList.add(order);
								
								
								//purchesOrders.add(order);
								
								
							} catch (Exception e) {
								throw new VendorNotFoundException(e.getMessage());
							}
						} else {
							
							GetPurchesOrder order = new GetPurchesOrder();
							
							order.setcId(po.getcId());
							order.setPOId(po.getPOId());
							order.setPoNumber(po.getPoNumber());
							order.setUserId(po.getUserId());
							order.setUserId(po.getUserId());
							order.setSupplierCode(po.getSupplierCode());
							order.setDepartmentId(po.getDepartmentId());
							order.setCusAddrId(po.getCusAddrId());
							order.setCusAddrText(po.getCusAddrText());
							order.setSupAddrText(po.getSupAddrText());
							order.setContractId(po.getContractId());
							order.setContractTerms(po.getContractTerms());
							order.setComment(po.getComment());
							order.setShipToId(po.getShipToId());
							order.setShipToText(po.getShipToText());
							order.setDeliveryToId(po.getDeliveryToId());
							order.setDeliveryToText(po.getDeliveryToText());
							order.setBillToId(po.getBillToId());
							order.setBillToText(po.getBillToText());
							order.setCurType(po.getCurType());
							order.setAmount(po.getAmount());
							order.setStatus(po.getStatus());
							order.setStatusComment(po.getStatusComment());
							order.setCreatedBy(po.getCreatedBy());
							order.setCreatedOn(po.getCreatedOn());
							
							List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(po.getPOId());
							
							List<PurchesOrderItems> items = new ArrayList<PurchesOrderItems>();
							
							findPoItemByPOId.forEach(item -> {
								items.add(item);
							});
							
							order.setPurchesOrderItems(items);
							
							itemList.add(order);
//							purchesOrders.add(po);
							
							
						}
					});
					
					return itemList;
				} else {
					throw new VendorNotFoundException("PO not found for this company");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getPendingPoForSuppiler/{suppilerCode}")
	public List<GetPendingPoResponceForSuppiler> getPendingPoForSuppiler(@PathVariable String suppilerCode) {
		if (!suppilerCode.equals(null)) {
			List<GetPendingPoResponceForSuppiler> allPendingPOForSuppiler = purchesOrderRepo.getAllPendingPOForSuppiler(suppilerCode);
			if (allPendingPOForSuppiler.size() > 0) {
				return allPendingPOForSuppiler;
			} else {
				throw new VendorNotFoundException("No data found");
			}
		} else {
			throw new VendorNotFoundException("Don't get any supplier code");
		}
	}
	
}
