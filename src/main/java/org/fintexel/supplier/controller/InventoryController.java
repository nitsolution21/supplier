package org.fintexel.supplier.controller;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemBrand;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.InventoryRepo;
import org.fintexel.supplier.repository.ItemBrandRepo;
import org.fintexel.supplier.repository.ItemCategoryRepo;
import org.fintexel.supplier.repository.ItemSubCategoryRepo;
import org.fintexel.supplier.validation.FieldValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class InventoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private ItemCategoryRepo itemCategoryRepo;
	@Autowired
	private ItemSubCategoryRepo itemSubCategoryRepo;
	@Autowired
	private ItemBrandRepo itemBrandRepo;
	@Autowired
	private InventoryRepo inventoryRepo;
	@Autowired
	private FieldValidation fieldValidation;
	@Autowired
	private LoginUserDetails loginUserDetails;

	@PostMapping("/category")
	public ItemCategory addCategory(@RequestBody ItemCategory itemCategory) {
		LOGGER.info("Inside - InventoryController.addCategory()");
		try {
			if ((fieldValidation.isEmpty(itemCategory.getCategoryName()))) {

				List<ItemCategory> findByCategoryName = itemCategoryRepo
						.findByCategoryName(itemCategory.getCategoryName());
				if (findByCategoryName.size() < 1) {
					return itemCategoryRepo.save(itemCategory);
				} else {
					throw new VendorNotFoundException("Category Name Already Exist");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PostMapping("/subCategory")
	public ItemSubCategory addSubCategory(@RequestBody ItemSubCategory itemSubCategory) {
		LOGGER.info("Inside - addSubCategory " + itemSubCategory);
		try {
			if ((fieldValidation.isEmpty(itemSubCategory.getSubCategoryName()))) {
				List<ItemSubCategory> findBySubCategoryName = itemSubCategoryRepo
						.findBySubCategoryName(itemSubCategory.getSubCategoryName());
				if (findBySubCategoryName.size() < 1) {
					return itemSubCategoryRepo.save(itemSubCategory);
				} else {
					throw new VendorNotFoundException("Sub Category Name Already Exist");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/brand")
	public ItemBrand addBrand(@RequestBody ItemBrand itemBrand) {
		LOGGER.info("Inside - InventoryController.addBrand()");
		try {
			if ((fieldValidation.isEmpty(itemBrand.getBrandName()))) {
 
				List<ItemBrand> findByBrandName = itemBrandRepo.findByBrandName(itemBrand.getBrandName());
				if (findByBrandName.size() < 1) {
					return itemBrandRepo.save(itemBrand);
				} else {
					throw new VendorNotFoundException("Item Brand Name Already Exist");
				}
			}else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}

	@PostMapping("/vendor/inventory")
	public InventoryDetails addInventory(@RequestBody InventoryDetails inventoryDetails, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.addInventory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			
			if ((fieldValidation.isEmpty(inventoryDetails.getCategoryId()))
					& (fieldValidation.isEmpty(inventoryDetails.getBrandId()))
					& (fieldValidation.isEmpty(inventoryDetails.getSubcategoryId()))					
					) {		
				 Optional<InventoryDetails> findBySupplierCode = inventoryRepo.findBySupplierCode(loginSupplierCode);
				if (!findBySupplierCode.isPresent()) {
					InventoryDetails details = new InventoryDetails();
					details.setSupplierCode(loginSupplierCode);
					details.setCategoryId(inventoryDetails.getCategoryId());
					details.setBrandId(inventoryDetails.getBrandId());
					details.setSubcategoryId(inventoryDetails.getSubcategoryId());
					try {
						if (fieldValidation.isEmpty(inventoryDetails.getQty()) 
								&& fieldValidation.isEmpty(inventoryDetails.getItemDescription())
								&& fieldValidation.isEmpty(inventoryDetails.getUnitPrice())
								&& fieldValidation.isEmpty(inventoryDetails.getSku())
								&& fieldValidation.isEmpty(inventoryDetails.getStatus())
								&& fieldValidation.isEmpty(inventoryDetails.getDiscount())
								&& fieldValidation.isEmpty(inventoryDetails.getCreatedBy())
								&& fieldValidation.isEmpty(inventoryDetails.getCreatedOn())											
								) {
									details.setQty(inventoryDetails.getQty());
									details.setItemDescription(inventoryDetails.getItemDescription());
									details.setUnitPrice(inventoryDetails.getUnitPrice());
									details.setSku(inventoryDetails.getSku());
									details.setStatus(inventoryDetails.getStatus());
									details.setDiscount(inventoryDetails.getDiscount());
									details.setCreatedBy(inventoryDetails.getCreatedBy());
									details.setCreatedOn(inventoryDetails.getCreatedOn());

						}
					} catch (Exception e) {
						
					}
					return inventoryRepo.save(details);
//				} else {
//					throw new VendorNotFoundException("Inventory Already Exist");
//				}
			}else {
				
				throw new VendorNotFoundException("The inventory already present");
			}
			}else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}		
	}

	@GetMapping("/vendor/inventory")
	public InventoryDetails getInventory(@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			 Optional<InventoryDetails> findBySupplierCode = inventoryRepo.findBySupplierCode(loginSupplierCode);
			if (!findBySupplierCode.isPresent()) {
				throw new VendorNotFoundException("No Inventory Details");
			}else {
				return findBySupplierCode.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}

	@GetMapping("/category")
	public List<ItemCategory> getCategory() {
		List<ItemCategory> listItemCategory = itemCategoryRepo.findAll();
		if (listItemCategory.isEmpty()) {
			throw new VendorNotFoundException("No Category Details");
		}
		return itemCategoryRepo.findAll();
	}

	@GetMapping("/subCategory")
	public List<ItemSubCategory> getSubCategory() {
		List<ItemSubCategory> listItemSubCategory = itemSubCategoryRepo.findAll();
		if (listItemSubCategory.isEmpty()) {
			throw new VendorNotFoundException("No SubCategory Details");
		}
		return itemSubCategoryRepo.findAll();
	}

	@GetMapping("/brand")
	public List<ItemBrand> getBrand() {
		List<ItemBrand> listItemBrand = itemBrandRepo.findAll();
		if (listItemBrand.isEmpty()) {
			throw new VendorNotFoundException("No Brand Details");
		}
		return itemBrandRepo.findAll();
	}

	@DeleteMapping("/inventory/{inventoryId}")
	public Object deleteInventory(@PathVariable() long inventoryId, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.deleteInventory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<InventoryDetails> findById = this.inventoryRepo.findById(inventoryId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Inventory ID does not exist - " + inventoryId);
			} else {
				if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
					this.inventoryRepo.deleteById(inventoryId);
					return "Successfully Deleted";
				} else {
					throw new VendorNotFoundException("You don't have permission to delete this inventory");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/category/{categoryId}")
	public Object deleteCategory(@PathVariable() long categoryId) {
		LOGGER.info("Inside - InventoryController.deleteCategory()");
		try {
			Optional<ItemCategory> findById = this.itemCategoryRepo.findById(categoryId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Category ID does not exist - " + categoryId);
			} else {
				this.itemCategoryRepo.deleteById(categoryId);
				return "Successfully Deleted";
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/subCategory/{subCatId}")
	public Object deleteSubCategory(@PathVariable() long subCatId) {
		LOGGER.info("Inside - InventoryController.deleteSubCategory()");
		try {
			Optional<ItemSubCategory> findById = this.itemSubCategoryRepo.findById(subCatId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("SubCategory ID does not exist - " + subCatId);
			} else {
				this.itemSubCategoryRepo.deleteById(subCatId);
				return "Successfully Deleted";
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/brand/{brandId}")
	public Object deleteBrand(@PathVariable() long brandId) {
		LOGGER.info("Inside - InventoryController.deleteBrand()");
		try {
			Optional<ItemBrand> findById = this.itemBrandRepo.findById(brandId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Brand ID does not exist - " + brandId);
			} else {
				this.itemBrandRepo.deleteById(brandId);
				return "Successfully Deleted";
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
