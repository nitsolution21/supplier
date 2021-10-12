package org.fintexel.supplier.controller;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.fintexel.supplier.entity.CurrencyMaster;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.InvoceItem;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.PurchesOrderByPoIdResponse;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerProfile;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.GetPendingPoResponceForSuppiler;
import org.fintexel.supplier.customerentity.GetPurchesOrder;
import org.fintexel.supplier.customerentity.InvoiceStraching;
import org.fintexel.supplier.customerentity.Logo;
import org.fintexel.supplier.customerentity.POFlowableItem;
import org.fintexel.supplier.customerentity.PrsonceLoginCustomerDetails;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerProfileRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.LogoRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderAttachmentRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderItemsRepo;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.entity.flowableentity.FlowableRegistration;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.CurrencyMasterRepo;
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
import org.springframework.http.HttpStatus;
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
	private LogoRepo logoRepo;
	
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
	private CurrencyMasterRepo currencyMasterRepo;
	
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
	
	private double totalGross, totalTax, totalSubTotal, totalAmountWithoutTax;
	

	
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
							Logo logo = new Logo();
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
							    	try {
							    		CurrencyMaster currencyMaster = currencyMasterRepo.findById((long)Integer.parseInt(supBank.getCurrency())).get();
							    		supBank.setCurrency(currencyMaster.getCurrency());
							    	}catch(Exception e) {
							    		
							    	}
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
								logo = logoRepo.findById((int)cIdFromToken).get();
							}catch(Exception e) {
								
							}
							try {
								supplierAllDetailsForPO.setLogo(logo.getLogo());
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
	
//	
//	@GetMapping("/pendingCustomer")
//	public ResponseEntity<?> getPendingCustomer() {
//	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomer()");
//		List<JSONObject> response = new ArrayList<JSONObject>();
////	JSONArray response = new JSONArray();
//		try {
//			
//			List<PurchesOrder> findByStatus = purchesOrderRepo.findByStatus("WAITING FOR APPROVAL");
//			if(findByStatus.size()<0) {
//				throw new VendorNotFoundException("No Pending Data");
//			}else {
//				findByStatus.forEach(obj -> {
//					JSONObject temp = new JSONObject();
//					CustomerProfile customerProfile = customerProfileRepo.findById((long)obj.getcId()).get();
//					temp.put("listPurchesorder", obj);
//					temp.put("customerName", customerProfile.getCustomerName());
//					System.out.println("oj  ==  "+temp.toString());
//					response.add(temp);
//				});
//				System.out.print("ooooff   "+response.toString());
//				 return ResponseEntity.ok(response.toString());
//			}
//		}catch(Exception e) {
//			throw new VendorNotFoundException(e.getMessage());
//		}
//	}
	
	
	@GetMapping("/pendingCustomer")
	public List<PurchesOrder> getPendingCustomer() {
	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomer()");
		List<JSONObject> response = new ArrayList<JSONObject>();
		try {
			
			List<PurchesOrder> findByStatus = purchesOrderRepo.findByStatus("WAITING FOR APPROVAL");
			if(findByStatus.size()<0) {
				throw new VendorNotFoundException("No Pending Data");
			}else {
				return findByStatus;
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
					value = "APPROVED_BY_SUPPLIER";
				}else if(value.equals("TOCONFIRM")){
					System.out.print("****  "+loginSupplierCode );
					value = "APPROVED_BY_IT";
				}else if(value.equals("CLOSED")) {
					System.out.print("****  "+loginSupplierCode );
					value = "COMPLETED";
				}else if(value.equals("ALL")) {
					System.out.print("****  "+loginSupplierCode );
					value = "ALL";


				}
				List<PurchesOrder> findByStatusWithSupplierCode=new ArrayList<PurchesOrder>();
				 findByStatusWithSupplierCode = purchesOrderRepo.findByStatusWithSupplierCode(value , loginSupplierCode);
				
				if(value.equals("ALL")) {
					findByStatusWithSupplierCode = purchesOrderRepo.findByhSupplierCode(loginSupplierCode);
				}
				
				
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
			
			LOGGER.info("Request PO ID"+id);
			PurchesOrderStatus purchesOrderStatus = new PurchesOrderStatus();
			try {
				purchesOrderStatus = purchesOrderStatusRepo.findById(id).get();
			}catch(Exception e) {
				
			}
			
			PurchesOrder purchesOrder = purchesOrderRepo.findById(id).get();
			LOGGER.info("Data form data base "+purchesOrder.toString());
			if(loginSupplierCode.equals(purchesOrder.getSupplierCode())) {
//				purchesOrderStatus.setPOStatus(value);
//				purchesOrderStatusRepo.save(purchesOrderStatus);
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
	
	
	
	
	
	
	
	@PostMapping("/invoice/{status}")
	public CustomeResponseEntity invoice(@PathVariable("status") String status , @RequestBody() InvoiceStraching invoiceStraching , @RequestHeader(name ="Authorization") String token) {
		String taskID1_ = "", taskID2_ = "", processInstID_ = "";
		
		String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
		System.out.println("loginSupplierCode  "+loginSupplierCode);
		LOGGER.info("Inside - PurchaseOrderController.invoice()");
		try {
			
			if(fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getPOId()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvDate()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvDesc()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvTaxid()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getRemitTo()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getBillTo()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getShipCharges()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getHandlingCharges()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getTotalGross()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getTotalTax()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getSubtotal()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvAmount()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getStatus()) &&
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvAttachment()) &&
					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getInvRegNum()) 
//					fieldValidation.isEmpty(invoiceStraching.getSupplierInvoice().getCreatedBy()) 
					
				
					
					
					) {
				
				DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime lastLoginNow = LocalDateTime.now();
				Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(lastLoginNow.format(lastLogingFormat));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
				String strDate = dateFormat.format(lastLogin);  

				
				invoiceStraching.getSupplierInvoice().setCreatedBy(loginSupplierCode);
				
				PurchesOrder purchesOrder = purchesOrderRepo.findById(invoiceStraching.getSupplierInvoice().getPOId()).get();
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 Date date1 = sdf.parse( sdf.format(invoiceStraching.getSupplierInvoice().getInvDate()));
//		            Date date1 = invoiceStraching.getSupplierInvoice().getInvDate();
		            Date date2 = purchesOrder.getCreatedOn();
		            System.out.println(date1   +  "   " + date2);
		            if(date2.after(date1)){
		                System.out.println("Date2 is after Date1");
		                throw new VendorNotFoundException("Invoice date is before po creation date");
		            }
		         
				System.out.println("loginSupplierCode  "+invoiceStraching.getSupplierInvoice().getPOId());
				List<PurchesOrderItems> findByPOId = purchesOrderItemsRepo.findByPOId(invoiceStraching.getSupplierInvoice().getPOId());
				
				if(!(findByPOId.size()+"").equals(invoiceStraching.getPurchesOrderItems().size()+"")) {
					System.out.println("findByPOId.size()  "+findByPOId.size());
					System.out.println("invoiceStraching.getPurchesOrderItems()  "+invoiceStraching.getPurchesOrderItems());
					throw new VendorNotFoundException("PO Item List Not Match ");
				}
				
				List<PurchesOrderItems> purchesOrderItems = invoiceStraching.getPurchesOrderItems();
				
				SupplierInvoice save = supplierInvoiceRepo.save(invoiceStraching.getSupplierInvoice());
				System.out.println("loginSupplierCode  "+loginSupplierCode);
//				i=0;
//				for(PurchesOrderItems obj : purchesOrderItems) {
//					if(!(obj.getItemId() == findByPOId.get(i).getItemId()))
//						throw new VendorNotFoundException("PO Items List are Not Match ");
//					
//				}
				
				
				i=0;
				for(PurchesOrderItems obj : purchesOrderItems) {
					SupplierInvoiceItem supplierInvoiceItem = new SupplierInvoiceItem();
					supplierInvoiceItem.setInvId(save.getInvId());
					supplierInvoiceItem.setPoitemId(invoiceStraching.getPurchesOrderItems().get(i).getPOItemId());
					supplierInvoiceItem.setItemDescription(obj.getItemDescription());
					supplierInvoiceItem.setItemCategoryName(obj.getItemCategoryText());
					supplierInvoiceItem.setItemSubcategoryName(obj.getItemSubcategoryText());
					supplierInvoiceItem.setItemQty(invoiceStraching.getPurchesOrderItems().get(i).getQty());
					supplierInvoiceItem.setItemPrice(obj.getUnitPrice());
//					supplierInvoiceItem.setItemGross(invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice());
					supplierInvoiceItem.setItemTax(obj.getTax());
					supplierInvoiceItem.setItemSubtotal((invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice() * 10 / 100) +(invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice()));
					supplierInvoiceItem.setItemTotal((invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice() * 10 / 100) +(invoiceStraching.getPurchesOrderItems().get(i).getQty()  *  obj.getUnitPrice()));
					SupplierInvoiceItem save2 = supplierInvoiceItemRepo.save(supplierInvoiceItem);
					i++;
				}
					
					if(status.equals("SUBMIT")) {
						
					
					
						/*
						 * ----------- REQUEST PROCESS ID with PROCESS DEFINITION KEY
						 * -------------------------------------------------------
						 */
	
				
				
				
				
						SupDetails supDetails = supDetailsRepo.findById(loginSupplierCode).get();
						System.out.println("supDetails  "+supDetails.toString());
						
						VendorRegister vendorRegister = vendorRegisterRepo.findById(supDetails.getRegisterId()).get();
						System.out.println("vendorRegister  "+vendorRegister.toString());
						
				
						
						SupBank supBank = supBankRepo.findByIsPrimaryWithSupplierCode(0, loginSupplierCode).get();
						System.out.println("supBank  "+supBank.toString());
						
						SupAddress supAddress = supAddressRepo.findByIsPrimaryWithSupplierCode(0, loginSupplierCode).get();
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
						username.put("value", save.getInvAmount()+"");
	//					username.put("value", "");
						formReqBody.put(username);
						
						
	
						JSONObject password = new JSONObject();
						password.put("name", "invoicedate");
						password.put("scope", "local");
						password.put("type", "string");
						password.put("value", "2021-10-12");
	//					password.put("value", "");
						formReqBody.put(password);
						
						System.out.println("supAddress.getCountry()##### "+save.getInvDate());
	
						JSONObject registrationid = new JSONObject();
						registrationid.put("name", "invoicemode");
						registrationid.put("scope", "local");
						registrationid.put("type", "string");
						registrationid.put("value", "manual");
						formReqBody.put(registrationid);
	
						
						JSONObject invoicenumber = new JSONObject();
						invoicenumber.put("name", "invoicenumber");
						invoicenumber.put("scope", "local");
						invoicenumber.put("type", "string");
						invoicenumber.put("value", save.getInvId()+"");
	//					invoicenumber.put("value", "");
						formReqBody.put(invoicenumber);
						
						
						
						JSONObject invoicetype = new JSONObject();
						invoicetype.put("name", "invoicetype");
						invoicetype.put("scope", "local");
						invoicetype.put("type", "string");
						invoicetype.put("value", "PO");
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
						podate.put("value", purchesOrder.getCreatedOn()+"" );
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
						taskdate.put("value", strDate );
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
						
						
						//-------------------file upload---------------
						
						UploadController uploadController = new UploadController();
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						MultiValueMap<String, Object> body
						  = new LinkedMultiValueMap<>();
						body.add("file", uploadController.createPdfFlowable());
						
						
						
						HttpEntity<MultiValueMap<String, Object>> requestEntity
						 = new HttpEntity<>(body, headers);

						String serverUrl = "http://65.2.162.230:8080/DB-task/app/rest/process-instances/" + taskID1_ + "/raw-content";

						RestTemplate restTemplatefile = new RestTemplate();
						ResponseEntity<String> responsefile = restTemplatefile
						  .postForEntity(serverUrl, requestEntity, String.class);
						
						
						System.out.println("restTemplatefile  "+restTemplatefile);
				
							//	HttpMethod.POST, formReqEntity, String.class, 1);
						
						
						//-------------------done-----------------
	
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
	
						DateTimeFormatter lastLogingFormat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						LocalDateTime lastLoginNow1 = LocalDateTime.now();
						Date lastLogin1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(lastLoginNow.format(lastLogingFormat));
						
						
						JSONObject autoCompleate = new JSONObject();
						autoCompleate.put("taskIdActual", taskID1_);
						autoCompleate.put("invoicemode","Manual");
						autoCompleate.put("workunitid", "");
						autoCompleate.put("taskdate", strDate);
						autoCompleate.put("vendorname",vendorRegister.getSupplierCompName() );
						autoCompleate.put("vendorid", vendorRegister.getRegisterId() );
						autoCompleate.put("vendoremail", vendorRegister.getEmail());
						autoCompleate.put("vendoraddress", supAddress.getAddress1());
						autoCompleate.put("city", supAddress.getCity());
						autoCompleate.put("state", supAddress.getRegion());
						autoCompleate.put("pincode", supAddress.getPostalCode() );
						autoCompleate.put("vendoraccount", supBank.getBankAccountNo() );
						autoCompleate.put("country", supAddress.getCountry());
						autoCompleate.put("invoicetype", "PO" );
						autoCompleate.put("invoicenumber", save.getInvId()+"" );
						autoCompleate.put("invoiceamount", save.getInvAmount()+"");
						autoCompleate.put("invoicedate", save.getInvDate() );
						autoCompleate.put("podate", purchesOrder.getCreatedOn());
						autoCompleate.put("ponumber", purchesOrder.getPoNumber()+"");
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
						
						
//						http://65.2.162.230:8080/DB-task/app/rest/task-forms/bd0507e5-2b34-11ec-83b3-0a5bf303a9fe
						
//						
//						ResponseEntity<String> exchange = restTemplate.exchange(
//								"http://65.2.162.230:8080/DB-task/app/rest/task-forms/"+taskID2_, HttpMethod.POST,
//								autoClaimEntity, String.class);
						
						
//						JSONObject autoCompleateValidation = new JSONObject();
//						autoCompleateValidation.put("taskIdActual", taskID1_);
//						autoCompleateValidation.put("invoicemode","Manual");
//						autoCompleateValidation.put("workunitid", "");
//						autoCompleateValidation.put("taskdate", strDate);
//						autoCompleateValidation.put("vendorname",vendorRegister.getSupplierCompName() );
//						autoCompleateValidation.put("vendorid", vendorRegister.getRegisterId() );
//						autoCompleateValidation.put("vendoremail", vendorRegister.getEmail());
//						autoCompleateValidation.put("vendoraddress", supAddress.getAddress1());
//						autoCompleateValidation.put("city", supAddress.getCity());
//						autoCompleateValidation.put("state", supAddress.getRegion());
//						autoCompleateValidation.put("pincode", supAddress.getPostalCode() );
//						autoCompleateValidation.put("vendoraccount", supBank.getBankAccountNo() );
//						autoCompleateValidation.put("country", supAddress.getCountry());
//						autoCompleateValidation.put("invoicetype", "PO" );
//						autoCompleateValidation.put("invoicenumber", save.getInvId()+"" );
//						autoCompleateValidation.put("invoiceamount", save.getInvAmount()+"");
//						autoCompleateValidation.put("invoicedate", save.getInvDate() );
//						autoCompleateValidation.put("podate", purchesOrder.getCreatedOn());
//						autoCompleateValidation.put("ponumber", purchesOrder.getPoNumber()+"");
//						autoCompleateValidation.put("manualvalidation", "No");
//						
//	
//						JSONObject autoCompleateValidation_ = new JSONObject();
//						autoCompleateValidation_.put("formId", "8c237107-2b00-11ec-8f40-0a5bf303a9fe");//cf1fb287-0974-11ec-8348-0a5bf303a9fe
//						autoCompleateValidation_.put("values", autoCompleateValidation);
//	
//				
//	
//						HttpEntity<String> autoCompeleteEntityValidation = new HttpEntity<String>(autoCompleateValidation_.toString(),
//								autoCompleteHeader);
//						
//						 ResponseEntity<String> exchange = restTemplate.exchange(
//									"http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID2_, HttpMethod.POST,
//									autoCompeleteEntityValidation, String.class);
//						 
//
//						 queryRequest_1 = restTemplate.exchange("http://65.2.162.230:8080/flowable-rest/service/query/tasks",
//									HttpMethod.POST, baseAuthEntity, String.class, 1);
//							taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());
//		
//							String taskID3_ = (String) taskJA.getJSONObject(0).get("id");
//							LOGGER.info("Registration TaskID_3 : " + taskID3_);
//						 
//						 
//							
//							ResponseEntity autoClaimResponseQu = restTemplate.exchange(
//									"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID3_ + "/action/claim", HttpMethod.PUT,
//									autoClaimEntity, String.class);
//						 
//							
//						 
//						 
//							JSONObject poCheck = new JSONObject();
//							poCheck.put("formId", "8c237108-2b00-11ec-8f40-0a5bf303a9fe");
//							
//		
//							JSONObject poCheckValues = new JSONObject();
//							autoCompleate_.put("taskIdActual", taskID3_);
//							autoCompleate_.put("automatching", "Yes");
//							poCheck.put("values", autoCompleate_);
//							
//		
//							LOGGER.info("Body  " + autoCompleate_);
//							LOGGER.info("headers  " + autoCompleteHeader);
//		
//							HttpEntity<String> poCheckEntity = new HttpEntity<String>(poCheck.toString(),
//									autoCompleteHeader);
//						 
//						 
//							restTemplate.exchange("http://65.2.162.230:8080/DB-task/app/rest/task-forms/" + taskID3_,
//									HttpMethod.POST, poCheckEntity, String.class, 1);
//						 
//					
//							
//						 
//							
//							
//							
//							
//							
//							queryRequest_1 = restTemplate.exchange("http://65.2.162.230:8080/flowable-rest/service/query/tasks",
//									HttpMethod.POST, baseAuthEntity, String.class, 1);
//							taskJA = new JSONArray(new JSONObject(queryRequest_1.getBody()).get("data").toString());
//		
//							String taskID4_ = (String) taskJA.getJSONObject(0).get("id");
//							LOGGER.info("Registration TaskID_3 : " + taskID4_);
//						 
//						 
//							
//							ResponseEntity autoClaimResponseQp = restTemplate.exchange(
//									"http://65.2.162.230:8080/DB-task/app/rest/tasks/" + taskID4_ + "/action/claim", HttpMethod.PUT,
//									autoClaimEntity, String.class);
//							
//							
//							
//							
//							
//							
//							
//							JSONObject qp = new JSONObject();
//							qp.put("formId", "8c237100-2b00-11ec-8f40-0a5bf303a9fe");
//							
//		
//							JSONObject qpValues = new JSONObject();
//							qpValues.put("taskIdActual", taskID4_);
//							qpValues.put("workunitid", "Yes");
//							qpValues.put("invoicenumber", "Yes");
//							qpValues.put("invoiceamount", "Yes");
//							qpValues.put("fielddetailsvalidated", "true");
//							qpValues.put("calculationschecked", "true");
//							qpValues.put("standardtimetakenforinvoiceprocessing", "true");
//							qpValues.put("exceptionresolvedintime", "true");
//							qp.put("values", qpValues);
//							
//		
//							LOGGER.info("Body  " + autoCompleate_);
//							LOGGER.info("headers  " + autoCompleteHeader);
//		
//							HttpEntity<String> qpEntity = new HttpEntity<String>(qp.toString(),
//									autoCompleteHeader);
//						 
//						 
//							restTemplate.exchange("http://65.2.162.230:8080/DB-task/app/rest/task-forms" + taskID4_,
//									HttpMethod.POST, qpEntity, String.class, 1);
//						
//						 
//						
//						System.out.println("autoClaimResponseQu =============   " + autoClaimResponseQu.toString()   +"       "+taskID3_);
//					
	
	
						
						
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
					loginCustomerDetails.setPoNumber("PO - 00" + Integer.toString((Integer.parseInt(posize) + 1)));
					break;
				case 1:
					loginCustomerDetails.setPoNumber("PO - 00" + Integer.toString((Integer.parseInt(posize) + 1)));
					break;
				case 2:
					loginCustomerDetails.setPoNumber("PO - 0" + Integer.toString((Integer.parseInt(posize) + 1)));
					break;
				default:
					loginCustomerDetails.setPoNumber("PO - " + Integer.toString((Integer.parseInt(posize) + 1)));
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
								Optional<SupDetails> findSuppilerById = supDetailsRepo.findById(po.getSupplierCode());
								
								order.setSupplierName(findSuppilerById.get().getSupplierCompName());
								
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
					
					DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime lastLoginNow = LocalDateTime.now();
					Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(lastLoginNow.format(lastLogingFormat));
					Optional<PurchesOrder> findPoByPoNumber = purchesOrderRepo.findByPoNumber(requestPurchesOrder.getPurchesOrder().getPoNumber());
					
//					LOGGER.info("Call databade>>>>>>{{{}}}}"+findPoByPoNumber.get());

					if (findPoByPoNumber.isPresent()) {
						requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
						requestPurchesOrder.getPurchesOrder().setStatus("DRAFT");
						requestPurchesOrder.getPurchesOrder().setPOId(findPoByPoNumber.get().getPOId());
						LOGGER.info("DETAILS IS >>>>>>}}}}}"+requestPurchesOrder.getPurchesOrder());
						requestPurchesOrder.getPurchesOrder().setCreatedOn(lastLogin);
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
						
						DateTimeFormatter lastLogingFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						LocalDateTime lastLoginNow = LocalDateTime.now();
						Date lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(lastLoginNow.format(lastLogingFormat));
						requestPurchesOrder.getPurchesOrder().setCreatedOn(lastLogin);
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
	
	
	@GetMapping("/getPOByPOId/{poId}")
	public List<PurchesOrderByPoIdResponse> getPOByPOId(@PathVariable long poId, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginCustomerAllPOFromSupplier()");
		
		try {
			this.totalGross = 0.0;
			this.totalTax = 0.0;
			this.totalSubTotal = 0.0;
			this.totalAmountWithoutTax = 0.0;
			List<PurchesOrderByPoIdResponse> itemList = new ArrayList<PurchesOrderByPoIdResponse>();
			
			//String posize = Integer.toString(purchesOrderRepo.findBycId((int) companyProfileIdByCustomerId).size());
			if (poId == -1) {
				throw new VendorNotFoundException("Po not found");
			}
			else {
				
//				List<RequestPurchesOrder> purchesOrders = new ArrayList<RequestPurchesOrder>();
				
				 Optional<PurchesOrder> findPOBycId = purchesOrderRepo.findById(poId);
				if (findPOBycId.isPresent()) {
					
//						LOGGER.info(po.getB)
						if (findPOBycId.get().getStatus().equals("DRAFT")) {
							try {
								
								PurchesOrderByPoIdResponse order = new PurchesOrderByPoIdResponse();
								
								try {
									Optional<SupplierInvoice> findInvoiceById = supplierInvoiceRepo.findById(poId);
									order.setInvoiceStatus(findInvoiceById.get().getStatus());
								} catch (Exception e) {
									// TODO: handle exception
								}
								
								String invoiceSize = Integer.toString(supplierInvoiceRepo.findAll().size());
								 
								switch (invoiceSize.length()) {
								case 0:
									order.setInvoiceNumber("INV - 00" + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
									break;
								case 1:
									order.setInvoiceNumber("INV - 00" + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
									break;
								case 2:
									order.setInvoiceNumber("INV - 0" + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
									break;
								default:
									order.setInvoiceNumber("INV - " + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
									break;
								}
								
								
								Optional<CustomerProfile> findCustomerById = customerProfileRepo.findById((long) findPOBycId.get().getcId());
								
								order.setCustomerName(findCustomerById.get().getCustomerName());
								
								Optional<SupDetails> findBySupplierCode = supDetailsRepo.findBySupplierCode(findPOBycId.get().getSupplierCode());
								order.setSupplierName(findBySupplierCode.get().getSupplierCompName());
								order.setRegistrationNumber(findBySupplierCode.get().getRegistrationNo());
								order.setcId(findPOBycId.get().getcId());
								order.setPOId(findPOBycId.get().getPOId());
								order.setPoNumber(findPOBycId.get().getPoNumber());
								order.setUserId(findPOBycId.get().getUserId());
								order.setUserId(findPOBycId.get().getUserId());
								order.setSupplierCode(findPOBycId.get().getSupplierCode());
								order.setDepartmentId(findPOBycId.get().getDepartmentId());
								order.setCusAddrId(findPOBycId.get().getCusAddrId());
								
								Optional<CustomerAddress> findCustomerAddressById = customerAddressRepo.findById(findPOBycId.get().getCusAddrId());
								JSONObject customerAddressJsonObject = new JSONObject(findCustomerAddressById.get());
								order.setCusAddrText(customerAddressJsonObject.toString());
								
								order.setSupAddrId(findPOBycId.get().getSupAddrId());
								
								Optional<SupAddress> findSuppAddressById = supAddressRepo.findById(findPOBycId.get().getSupAddrId());
								JSONObject suppAddressJsonObject = new JSONObject(findSuppAddressById.get());
								order.setSupAddrText(suppAddressJsonObject.toString());
								
								order.setContractId(findPOBycId.get().getContractId());
								order.setContractTerms(findPOBycId.get().getContractTerms());
								order.setComment(findPOBycId.get().getComment());
								order.setShipToId(findPOBycId.get().getShipToId());
								
								order.setBillToId(findPOBycId.get().getBillToId());
								order.setBillToText(findPOBycId.get().getBillToText());
								
								Optional<CustomerAddress> findShipToAddressById = customerAddressRepo.findById(findPOBycId.get().getShipToId());
								JSONObject shipToAddressJsonObject = new JSONObject(findShipToAddressById.get());
								order.setShipToText(shipToAddressJsonObject.toString());
								
								order.setDeliveryToId(findPOBycId.get().getDeliveryToId());
								
//								Optional<CustomerRegister> finDeliveryDetailsdById = customerRegisterRepo.findById(po.getDeliveryToId());
//								JSONObject deliveryDetailsJsonObject = new JSONObject(finDeliveryDetailsdById.get());
//								order.setDeliveryToText(deliveryDetailsJsonObject.toString());
								order.setDeliveryToText(findPOBycId.get().getDeliveryToText());
								
								order.setCurType(findPOBycId.get().getCurType());
								order.setAmount(findPOBycId.get().getAmount());
								order.setStatus(findPOBycId.get().getStatus());
								order.setStatusComment(findPOBycId.get().getStatusComment());
								order.setCreatedBy(findPOBycId.get().getCreatedBy());
								order.setCreatedOn(findPOBycId.get().getCreatedOn());
								List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(findPOBycId.get().getPOId());
								
								List<InvoceItem> items = new ArrayList<InvoceItem>();
								
								findPoItemByPOId.forEach(item -> {
									InvoceItem invoceItem = new InvoceItem();
									totalGross = totalGross + item.getItemGross();
									totalTax = totalTax  + item.getTax();
									totalSubTotal = totalSubTotal + item.getAmount();
									totalAmountWithoutTax = totalAmountWithoutTax + (item.getQty() * item.getUnitPrice());
									try {
										Optional<SupplierInvoiceItem> findById = supplierInvoiceItemRepo.findById(item.getItemId());
										if (findById.isPresent()) {
											invoceItem.setPOItemId(item.getPOItemId());
											invoceItem.setPOId(item.getPOId());
											invoceItem.setItemId(item.getItemId());
											invoceItem.setItemDescription(item.getItemDescription());
											invoceItem.setQty(item.getQty());
											invoceItem.setCurType(item.getCurType());
											invoceItem.setUnitPrice(item.getUnitPrice());
											invoceItem.setItemGross(item.getItemGross());
											invoceItem.setTax(item.getTax());
											invoceItem.setAmount(item.getAmount());
											invoceItem.setItemTotal(item.getItemTotal());
											invoceItem.setCategoryId(item.getCategoryId());
											invoceItem.setSubcategoryId(item.getSubcategoryId());
											invoceItem.setBrandId(item.getBrandId());
											invoceItem.setItemCategoryText(item.getItemCategoryText());
											invoceItem.setItemSubcategoryText(item.getItemSubcategoryText());
											invoceItem.setItemBrandText(item.getItemBrandText());
											invoceItem.setItemShipCharges(findById.get().getItemShipCharges());
											invoceItem.setItemHandlingCharges(findById.get().getItemHandlingCharges());
										} else {
											invoceItem.setPOItemId(item.getPOItemId());
											invoceItem.setPOId(item.getPOId());
											invoceItem.setItemId(item.getItemId());
											invoceItem.setItemDescription(item.getItemDescription());
											invoceItem.setQty(item.getQty());
											invoceItem.setCurType(item.getCurType());
											invoceItem.setUnitPrice(item.getUnitPrice());
											invoceItem.setItemGross(item.getItemGross());
											invoceItem.setTax(item.getTax());
											invoceItem.setAmount(item.getAmount());
											invoceItem.setItemTotal(item.getItemTotal());
											invoceItem.setCategoryId(item.getCategoryId());
											invoceItem.setSubcategoryId(item.getSubcategoryId());
											invoceItem.setBrandId(item.getBrandId());
											invoceItem.setItemCategoryText(item.getItemCategoryText());
											invoceItem.setItemSubcategoryText(item.getItemSubcategoryText());
											invoceItem.setItemBrandText(item.getItemBrandText());
											invoceItem.setItemShipCharges(0);
											invoceItem.setItemHandlingCharges(0);
											
											
										}
									} catch (Exception e) {
										// TODO: handle exception
									}
									
									items.add(invoceItem);
								});
								
								order.setTotalGross(Double.parseDouble(new DecimalFormat("##.##").format(totalGross)));
								order.setTotalTax(Double.parseDouble(new DecimalFormat("##.##").format(totalTax)));
								order.setTotalSubTotal(Double.parseDouble(new DecimalFormat("##.##").format(totalSubTotal)));
								order.setTotalAmountWithoutTax(Double.parseDouble(new DecimalFormat("##.##").format(totalAmountWithoutTax)));
								itemList.add(order);
								
								
								//purchesOrders.add(order);
								
								
							} catch (Exception e) {
								throw new VendorNotFoundException(e.getMessage());
							}
						} else {
							
							PurchesOrderByPoIdResponse order = new PurchesOrderByPoIdResponse();
							
							try {
								Optional<SupplierInvoice> findInvoiceById = supplierInvoiceRepo.findById(poId);
								order.setInvoiceStatus(findInvoiceById.get().getStatus());
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							String invoiceSize = Integer.toString(supplierInvoiceRepo.findAll().size());
							 
							switch (invoiceSize.length()) {
							case 0:
								order.setInvoiceNumber("INV - 00" + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
								break;
							case 1:
								order.setInvoiceNumber("INV - 00" + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
								break;
							case 2:
								order.setInvoiceNumber("INV - 0" + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
								break;
							default:
								order.setInvoiceNumber("INV - " + Integer.toString((Integer.parseInt(invoiceSize) + 1)));
								break;
							}
							
							
							Optional<CustomerProfile> findCustomerById = customerProfileRepo.findById((long) findPOBycId.get().getcId());
							
							order.setCustomerName(findCustomerById.get().getCustomerName());
							
							
							
							Optional<SupDetails> findBySupplierCode = supDetailsRepo.findBySupplierCode(findPOBycId.get().getSupplierCode());
							 
							order.setRegistrationNumber(findBySupplierCode.get().getRegistrationNo());
							order.setSupplierName(findBySupplierCode.get().getSupplierCompName());
							
							order.setcId(findPOBycId.get().getcId());
							order.setPOId(findPOBycId.get().getPOId());
							order.setPoNumber(findPOBycId.get().getPoNumber());
							order.setUserId(findPOBycId.get().getUserId());
							order.setUserId(findPOBycId.get().getUserId());
							order.setSupplierCode(findPOBycId.get().getSupplierCode());
							order.setDepartmentId(findPOBycId.get().getDepartmentId());
							order.setCusAddrId(findPOBycId.get().getCusAddrId());
							order.setCusAddrText(findPOBycId.get().getCusAddrText());
							order.setSupAddrText(findPOBycId.get().getSupAddrText());
							order.setContractId(findPOBycId.get().getContractId());
							order.setContractTerms(findPOBycId.get().getContractTerms());
							order.setComment(findPOBycId.get().getComment());
							order.setShipToId(findPOBycId.get().getShipToId());
							order.setShipToText(findPOBycId.get().getShipToText());
							order.setDeliveryToId(findPOBycId.get().getDeliveryToId());
							order.setDeliveryToText(findPOBycId.get().getDeliveryToText());
							order.setBillToId(findPOBycId.get().getBillToId());
							order.setBillToText(findPOBycId.get().getBillToText());
							order.setCurType(findPOBycId.get().getCurType());
							order.setAmount(findPOBycId.get().getAmount());
							order.setStatus(findPOBycId.get().getStatus());
							order.setStatusComment(findPOBycId.get().getStatusComment());
							order.setCreatedBy(findPOBycId.get().getCreatedBy());
							order.setCreatedOn(findPOBycId.get().getCreatedOn());
							
							List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(findPOBycId.get().getPOId());
							
							List<InvoceItem> items = new ArrayList<InvoceItem>();
							
							findPoItemByPOId.forEach(item -> {
								InvoceItem invoceItem = new InvoceItem();
								totalGross = totalGross + item.getItemGross();
								totalTax = totalTax  + item.getTax();
								totalSubTotal = totalSubTotal + item.getAmount();
								totalAmountWithoutTax = totalAmountWithoutTax + (item.getQty() * item.getUnitPrice());
								
								try {
									Optional<SupplierInvoiceItem> findById = supplierInvoiceItemRepo.findById(item.getItemId());
									if (findById.isPresent()) {
										invoceItem.setPOItemId(item.getPOItemId());
										invoceItem.setPOId(item.getPOId());
										invoceItem.setItemId(item.getItemId());
										invoceItem.setItemDescription(item.getItemDescription());
										invoceItem.setQty(item.getQty());
										invoceItem.setCurType(item.getCurType());
										invoceItem.setUnitPrice(item.getUnitPrice());
										invoceItem.setItemGross(item.getItemGross());
										invoceItem.setTax(item.getTax());
										invoceItem.setAmount(item.getAmount());
										invoceItem.setItemTotal(item.getItemTotal());
										invoceItem.setCategoryId(item.getCategoryId());
										invoceItem.setSubcategoryId(item.getSubcategoryId());
										invoceItem.setBrandId(item.getBrandId());
										invoceItem.setItemCategoryText(item.getItemCategoryText());
										invoceItem.setItemSubcategoryText(item.getItemSubcategoryText());
										invoceItem.setItemBrandText(item.getItemBrandText());
										invoceItem.setItemShipCharges(findById.get().getItemShipCharges());
										invoceItem.setItemHandlingCharges(findById.get().getItemHandlingCharges());
									} else {
										invoceItem.setPOItemId(item.getPOItemId());
										invoceItem.setPOId(item.getPOId());
										invoceItem.setItemId(item.getItemId());
										invoceItem.setItemDescription(item.getItemDescription());
										invoceItem.setQty(item.getQty());
										invoceItem.setCurType(item.getCurType());
										invoceItem.setUnitPrice(item.getUnitPrice());
										invoceItem.setItemGross(item.getItemGross());
										invoceItem.setTax(item.getTax());
										invoceItem.setAmount(item.getAmount());
										invoceItem.setItemTotal(item.getItemTotal());
										invoceItem.setCategoryId(item.getCategoryId());
										invoceItem.setSubcategoryId(item.getSubcategoryId());
										invoceItem.setBrandId(item.getBrandId());
										invoceItem.setItemCategoryText(item.getItemCategoryText());
										invoceItem.setItemSubcategoryText(item.getItemSubcategoryText());
										invoceItem.setItemBrandText(item.getItemBrandText());
										invoceItem.setItemShipCharges(0);
										invoceItem.setItemHandlingCharges(0);
										
										
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
								
								items.add(invoceItem);
							});
							
							order.setTotalGross(Double.parseDouble(new DecimalFormat("##.##").format(totalGross)));
							order.setTotalTax(Double.parseDouble(new DecimalFormat("##.##").format(totalTax)));
							order.setTotalSubTotal(Double.parseDouble(new DecimalFormat("##.##").format(totalSubTotal)));
							order.setTotalAmountWithoutTax(Double.parseDouble(new DecimalFormat("##.##").format(totalAmountWithoutTax)));
							
							order.setPurchesOrderItems(items);
							
							itemList.add(order);
//							purchesOrders.add(po);
							
							
						}
				
					
					return itemList;
				} else {
					throw new VendorNotFoundException("PO not found for this company");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("getInvoice")
	List<SupplierInvoice> getAllInvoice() {
		LOGGER.info("Inside - PurchaseOrderController.getAllInvice()");
		try {
			List<SupplierInvoice> findAllInvoice = supplierInvoiceRepo.findAll();
			if (findAllInvoice.size() > 0) {
				return findAllInvoice;
			} else {
				throw new VendorNotFoundException("Data not found");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getInvoiceItem/{invoiceId}")
	List<SupplierInvoiceItem> getAllInvoiceItemByInvoiceId(@PathVariable long invoiceId) {
		LOGGER.info("Inside - PurchaseOrderController.getAllInvoiceItemByInvoiceId()");
		try {
			List<SupplierInvoiceItem> findByInvId = supplierInvoiceItemRepo.findByInvId(invoiceId);
			if (findByInvId.size() > 0) {
				return findByInvId;
			} else {
				throw new VendorNotFoundException("Item not found for this Invoice");
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
}
