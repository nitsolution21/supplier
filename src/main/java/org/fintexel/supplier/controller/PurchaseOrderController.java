package org.fintexel.supplier.controller;


import java.util.ArrayList;
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
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderStatusRepo;
import org.fintexel.supplier.entity.ApproveMap;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.SupAddress;
import org.fintexel.supplier.entity.SupBank;
import org.fintexel.supplier.entity.SupDepartment;
import org.fintexel.supplier.customerentity.CustomerDepartments;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.PrsonceLoginCustomerDetails;
import org.fintexel.supplier.customerrepository.CustomerAddressRepo;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.CustomerDepartmentsRepo;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderItemsRepo;
import org.fintexel.supplier.entity.SupDetails;
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
import org.fintexel.supplier.validation.FieldValidation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer/po")
public class PurchaseOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
	
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
								
							}
							try {
								 Optional<SupAddress> findByIsPrimary = supAddressRepo.findByIsPrimary(1);
								 System.out.println("%%%%%%%%%%@@@@@@@@ "+ findByIsPrimary.toString());
								 supAddress = findByIsPrimary.get();
							}catch(Exception e) {
								
							}
							try {
								supBank = supBankRepo.findByIsPrimary(1).get();
							}catch(Exception e) {
								
							}
							try {
								findBySupplierCodeInventory = inventoryRepo.findBySupplierCode(supplierCode);
							}catch(Exception e) {
								
							}
							try {
								findBySupplierCodeDepertment = supDepartmentRepo.findBySupplierCode(supplierCode);
							}catch(Exception e) {
								
							}
							try {
								findBySupplierCodeCategory = itemCategoryRepo.findBySupplierCode(supplierCode);
							}catch(Exception e) {
								
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
				
				List<CustomerContact> customerContactList = customerContactRepo.findBycId(cIdFromToken);
				
				if(customerContactList.size()>0) {					
						ItemCategory itemCategory = itemCategoryRepo.findById(id).get();
						System.out.println("&&&&&   "+ itemCategory.toString());
//						List<InventoryDetails> findBySupplierCode = inventoryRepo.findBySupplierCode(itemCategory.getSupplierCode());
						List<InventoryDetails> findByCategoryIdInventory = inventoryRepo.findByCategoryId(itemCategory.getCategoryId());
						
//						if(findByCategoryId.get(0).getSupplierCode().equals(findBySupplierCode.get(0).getSupplierCode())) {
//							
//						}else {
						
						for(CustomerContact obj : customerContactList) {
							SupDetails supDetails = supDetailsRepo.findById(obj.getSupplierCode()).get();
							String supplierCode = supDetails.getSupplierCode();
							if(supplierCode.equals(itemCategory.getSupplierCode())) {
								System.out.println("if  "+ findByCategoryIdInventory.size());
//								if(itemCategory.getSupplierCode().equals(supplierCode)) {
									Optional<ItemSubCategory> findByIdSubCatagory = itemSubCategoryRepo.findById(findByCategoryIdInventory.get(0).getCategoryId());
									SelectedItem selectedItem = new SelectedItem();
									selectedItem.setFindByCategoryId(findByCategoryIdInventory);
									selectedItem.setFindById(findByIdSubCatagory);
									return selectedItem;
//								}else {
//									throw new VendorNotFoundException("No Contract Made With This Customer and Supplier");
//								}
								
							}
						}
						
							throw new VendorNotFoundException("No Contract Made With This Customer and Supplier");
//						}
					
				}else {
					throw new VendorNotFoundException("No Contract Found With This Customer");
				}
				
			}
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	@GetMapping("/pendingCustomer")
	public List<PurchesOrder> getPendingCustomer() {
	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomer()");
		
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
	public List<PurchesOrder> getPendingCustomerDetails(@PathVariable("id") int id) {
	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomerDetails()");
		
		try {
			
			List<PurchesOrder> findByCId = purchesOrderRepo.findBycId(id);
			if(findByCId.size()<0) {
				throw new VendorNotFoundException("No Pending Data");
			}else {
				return findByCId;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	

	@PostMapping("/pendingCustomerStatus")
	public void pendingCustomerStatus(@RequestBody() ArrayList<ApproveMap> approveMap) {
		LOGGER.info("Inside - PurchaseOrderController.pendingCustomerStatus()");
		
		try {
			
			for(ApproveMap obj : approveMap) {
				PurchesOrder purchesOrder = purchesOrderRepo.findById(obj.getId()).get();
				PurchesOrderStatus purchesOrderStatus = purchesOrderStatusRepo.findById(obj.getId()).get();
				purchesOrder.setStatus(obj.getStatus());
				purchesOrder.setStatusComment(obj.getComment());
				purchesOrderStatus.setPOStatus (obj.getStatus());
				purchesOrderStatus.setComment(obj.getComment());
				
				purchesOrderRepo.save(purchesOrder);
				purchesOrderStatusRepo.save(purchesOrderStatus);
				
			}
			
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
			if (!loginSupplierCode.equals(null)) {
				
				if(value.equals("TOSHIP")) {
					value = "APPROVED BY IT";
				}else if(value.equals("TOCONFIRM")){
					System.out.print("****  "+loginSupplierCode );
					value = "WAITING FOR APPROVAL";
				}else if(value.equals("CLOSED")) {
					System.out.print("****  "+loginSupplierCode );
					value = "COMPLETED";
				}
				List<PurchesOrder> findByStatusWithSupplierCode = purchesOrderRepo.findByStatusWithSupplierCode(value , loginSupplierCode);
				
				System.out.print("****  "+loginSupplierCode +  "  ---- " +findByStatusWithSupplierCode.size());
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/getLoginCustomerDetails/{supplierCode}")
	public PrsonceLoginCustomerDetails getLoginCustomerDetails(@PathVariable String supplierCode,@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginDetails()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			if (companyProfileIdByCustomerId == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				PrsonceLoginCustomerDetails loginCustomerDetails = new PrsonceLoginCustomerDetails();
				
				Optional<CustomerAddress> findAddressByIsPrimary = customerAddressRepo.findByIsPrimary(1);
				if (findAddressByIsPrimary.isPresent()) {
					loginCustomerDetails.setCustomerAddress(findAddressByIsPrimary.get());
				}
				
				List<CustomerDepartments> findDepartmentBycId = customerDepartmentsRepo.findBycId(companyProfileIdByCustomerId);
				if (findDepartmentBycId.size() > 0) {
					loginCustomerDetails.setCustomerDepartments(findDepartmentBycId);
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
	public Map<PurchesOrder, List<PurchesOrderItems>> getLoginCustomerAllPO(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginCustomerAllPO()");
		try {
			Map<PurchesOrder, List<PurchesOrderItems>> itemList = new HashMap<PurchesOrder, List<PurchesOrderItems>>();
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			
			if (customerIdFromToken == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
//				List<RequestPurchesOrder> purchesOrders = new ArrayList<RequestPurchesOrder>();
				
				List<PurchesOrder> findPOBycId = purchesOrderRepo.findBycId((int) companyProfileIdByCustomerId);
				if (findPOBycId.size() > 0) {
					findPOBycId.forEach(po -> {
						if (po.getStatus().equals("DRAFT")) {
							try {
								
								PurchesOrder order = new PurchesOrder();
				
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
								
								Optional<CustomerAddress> findShipToAddressById = customerAddressRepo.findById(po.getShipToId());
								JSONObject shipToAddressJsonObject = new JSONObject(findShipToAddressById.get());
								order.setShipToText(shipToAddressJsonObject.toString());
								
								order.setDeliveryToId(po.getDeliveryToId());
								
								Optional<CustomerRegister> finDeliveryDetailsdById = customerRegisterRepo.findById(po.getDeliveryToId());
								JSONObject deliveryDetailsJsonObject = new JSONObject(finDeliveryDetailsdById.get());
								order.setDeliveryToText(deliveryDetailsJsonObject.toString());
								
								order.setCurType(po.getCurType());
								order.setAmount(po.getAmount());
								order.setStatus(po.getStatus());
								order.setStatusComment(po.getStatusComment());
								order.setCreatedBy(po.getCreatedBy());
								order.setCreatedOn(po.getCreatedOn());
								List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(po.getPOId());
								itemList.put(po, findPoItemByPOId);
								
								
								
								//purchesOrders.add(order);
								
								
							} catch (Exception e) {
								throw new VendorNotFoundException(e.getMessage());
							}
						} else {
							
							List<PurchesOrderItems> findPoItemByPOId = purchesOrderItemsRepo.findByPOId(po.getPOId());
							itemList.put(po, findPoItemByPOId);
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
			System.out.println("Data &&&&&&  "+requestPurchesOrder.getPurchesOrder());
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			if (companyProfileIdByCustomerId == -1) {
				throw new VendorNotFoundException("Customer not found");
			} else {
				if (fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getPoNumber()) 
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
					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getAmount())) {
					
					requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
					requestPurchesOrder.getPurchesOrder().setStatus("DRAFT");
					
					PurchesOrder savePurchesOrder = purchesOrderRepo.save(requestPurchesOrder.getPurchesOrder());
					if (!savePurchesOrder.equals(null)) {
						
					requestPurchesOrder.getPurchesOrderItems().forEach(item -> {
						
						item.setPOId(savePurchesOrder.getPOId());
						
						purchesOrderItemsRepo.save(item);
					});
					
					return new CustomeResponseEntity("SUCCESS","PO save successfully");
						
					} else {
						throw new VendorNotFoundException("Data Not save in data base");
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
			System.out.println("Data &&&&&&  "+requestPurchesOrder.getPurchesOrder());
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
//					&& fieldValidation.isEmpty(requestPurchesOrder.getPurchesOrder().getAmount())
					) {
					
					requestPurchesOrder.getPurchesOrder().setcId((int) companyProfileIdByCustomerId);
					requestPurchesOrder.getPurchesOrder().setStatus("WAITING FOR APPROVAL");
					
					PurchesOrder savePurchesOrder = purchesOrderRepo.save(requestPurchesOrder.getPurchesOrder());
					if (!savePurchesOrder.equals(null)) {
						
					requestPurchesOrder.getPurchesOrderItems().forEach(item -> {
						
						item.setPOId(savePurchesOrder.getPOId());
						
						purchesOrderItemsRepo.save(item);
					});
					
					return new CustomeResponseEntity("SUCCESS","PO submit successfully");
						
					} else {
						throw new VendorNotFoundException("Data Not save in data base");
					}
				} else {
					throw new VendorNotFoundException("validation error");
				}
				
			}
			
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
