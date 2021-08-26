package org.fintexel.supplier.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.customerentity.CustomerAddress;
import org.fintexel.supplier.customerentity.CustomerContact;
import org.fintexel.supplier.customerentity.PurchesOrder;
import org.fintexel.supplier.customerentity.SupplierAllDetailsForPO;
import org.fintexel.supplier.customerrepository.CustomerContactRepo;
import org.fintexel.supplier.customerrepository.PurchesOrderRepo;
import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemCategory;
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
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.GetCustomerDetails;
import org.fintexel.supplier.repository.InventoryRepo;
import org.fintexel.supplier.repository.ItemCategoryRepo;
import org.fintexel.supplier.repository.SupAddressRepo;
import org.fintexel.supplier.repository.SupBankRepo;
import org.fintexel.supplier.repository.SupDepartmentRepo;
import org.fintexel.supplier.repository.SupDetailsRepo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer/po")
public class PurchaseOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
	
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
	private CustomerDepartmentsRepo customerDepartmentsRepo; 
	
	@Autowired
	private CustomerAddressRepo customerAddressRepo;
	
	

	

	
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
						SupDetails supDetails = supDetailsRepo.findById(obj.getSupplierCode()).get();
						String supplierCode = supDetails.getSupplierCode();
						if(supplierCode.equals(code)) {
							
							SupplierAllDetailsForPO supplierAllDetailsForPO = new SupplierAllDetailsForPO();
							SupDetails supDetails2 = supDetailsRepo.findById(supplierCode).get();
							SupAddress supAddress = supAddressRepo.findByIsPrimary(1).get();
							SupBank supBank = supBankRepo.findByIsPrimary(1).get();
							List<InventoryDetails> findBySupplierCodeInventory = inventoryRepo.findBySupplierCode(supplierCode);
							List<SupDepartment> findBySupplierCodeDepertment = supDepartmentRepo.findBySupplierCode(supplierCode);
							List<ItemCategory> findBySupplierCodeCategory = itemCategoryRepo.findBySupplierCode(supplierCode);
							supplierAllDetailsForPO.setFindBySupplierCodeDepertment(findBySupplierCodeDepertment);
							supplierAllDetailsForPO.setSupAddress(supAddress);
							supplierAllDetailsForPO.setSupDetails(supDetails2);
							supplierAllDetailsForPO.setSupBank(supBank);
//							supplierAllDetailsForPO.setFindBySupplierCodeInventory(findBySupplierCodeInventory);
							supplierAllDetailsForPO.setFindBySupplierCodeCategory(findBySupplierCodeCategory);
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
	
	
	
	
	@GetMapping("/categoryByInventory")
	public void getCategoryByInventory() {
		LOGGER.info("Inside - PurchaseOrderController.getContractSuppliers()");
		
		try {
			
			
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	
	@GetMapping("/pendingCustomer")
	public List<PurchesOrder> getPendingCustomer() {
	LOGGER.info("Inside - PurchaseOrderController.getPendingCustomer()");
		
		try {
			
			List<PurchesOrder> findByStatus = purchesOrderRepo.findByStatus("YET TO SUBMIT");
			if(findByStatus.size()<0) {
				throw new VendorNotFoundException("No Pending Data");
			}else {
				return findByStatus;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/getLoginCustomerDetails")
	public PrsonceLoginCustomerDetails getLoginCustomerDetails(@RequestHeader(name = "Authorization") String token) {
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
				
				return loginCustomerDetails;
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@GetMapping("/getLoginCustomerAllPO")
	public List<PurchesOrder> getLoginCustomerAllPO(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - PurchaseOrderController.getLoginCustomerAllPO()");
		try {
			long customerIdFromToken = getCustomerDetails.getCustomerIdFromToken(token);
			long companyProfileIdByCustomerId = getCustomerDetails.getCompanyProfileIdByCustomerId(customerIdFromToken);
			
			if (companyProfileIdByCustomerId == -1) {
				throw new VendorNotFoundException("Customer not found");
			}
			else {
				List<PurchesOrder> purchesOrders = new ArrayList<PurchesOrder>();
				
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
								
								purchesOrders.add(order);
								
								
							} catch (Exception e) {
								throw new VendorNotFoundException(e.getMessage());
							}
						} else {
							
							purchesOrders.add(po);
							
						}
					});
					
					return purchesOrders;
				} else {
					throw new VendorNotFoundException("PO not found for this company");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
