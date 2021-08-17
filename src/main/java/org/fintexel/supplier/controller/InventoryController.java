package org.fintexel.supplier.controller;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.FileUploadResponse;
import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.InventoryResponse;
import org.fintexel.supplier.entity.ItemBrand;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.SubCategoryResponse;
import org.fintexel.supplier.entity.SupDetails;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.FileUploadHelper;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping("/vendor/category")
	public ItemCategory addCategory(@RequestBody ItemCategory itemCategory,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.addCategory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(itemCategory.getCategoryName()))) {
				if (!loginSupplierCode.equals(null)) {
					ItemCategory category = new ItemCategory();
					List<ItemCategory> findByCategoryName = itemCategoryRepo
							.findByCategoryName(itemCategory.getCategoryName());
					if (findByCategoryName.size() < 1) {
						category.setCategoryName(itemCategory.getCategoryName());
						category.setSupplierCode(loginSupplierCode);
						return itemCategoryRepo.save(category);
					} else {
						findByCategoryName.forEach(catagory -> {
							if (catagory.getSupplierCode().equals(loginSupplierCode)) {
								throw new VendorNotFoundException("Category Name Already Exist");
							}
						});
						category.setCategoryName(itemCategory.getCategoryName());
						category.setSupplierCode(loginSupplierCode);
						return itemCategoryRepo.save(category);
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PutMapping("/vendor/category/{id}")
	public ItemCategory updateCategory(@PathVariable() Long id, @RequestBody ItemCategory itemCategory,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.updateCategory()");
		try {
			boolean flag = true;
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(itemCategory.getCategoryName()))) {
				if (!loginSupplierCode.equals(null)) {
					ItemCategory category = new ItemCategory();
					Optional<ItemCategory> findById = itemCategoryRepo.findById(id);
					if (findById.isPresent()) {
						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
							category.setCategoryName(itemCategory.getCategoryName());
							category.setSupplierCode(loginSupplierCode);
							return itemCategoryRepo.save(category);
						} else {
							throw new VendorNotFoundException("You don't have permission to update this category");
						}
					} else {
						throw new VendorNotFoundException("Category Name Not Exist");

					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}

		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PostMapping("/vendor/subCategory")
	public SubCategoryResponse addSubCategory(@RequestBody ItemSubCategory itemSubCategory,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - addSubCategory " + itemSubCategory);
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(itemSubCategory.getSubCategoryName()))
					&& fieldValidation.isEmpty(itemSubCategory.getCategoryId())) {
				if (!loginSupplierCode.equals(null)) {
					List<ItemSubCategory> findBySubCategoryName = itemSubCategoryRepo
							.findBySubCategoryName(itemSubCategory.getSubCategoryName());
					ItemSubCategory category = new ItemSubCategory();
					if (findBySubCategoryName.size() < 1) {
						category.setCategoryId(itemSubCategory.getCategoryId());
						category.setSubCategoryName(itemSubCategory.getSubCategoryName());
						category.setSupplierCode(loginSupplierCode);
						ItemSubCategory save = itemSubCategoryRepo.save(category);
						Optional<ItemCategory> findById = itemCategoryRepo.findById(save.getCategoryId());
						SubCategoryResponse categoryResponse = new SubCategoryResponse();
						categoryResponse.setCategoryId(findById.get().getCategoryId());
						categoryResponse.setCategoryName(findById.get().getCategoryName());
						categoryResponse.setCreatedBy(save.getCreatedBy());
						categoryResponse.setCreatedOn(save.getCreatedOn());
						categoryResponse.setUpdatedBy(save.getUpdatedBy());
						categoryResponse.setUpdatedOn(save.getUpdatedOn());
						categoryResponse.setSubCategoryId(save.getSubCategoryId());
						categoryResponse.setSubCategoryName(save.getSubCategoryName());
						categoryResponse.setSupplierCode(save.getSupplierCode());
						return categoryResponse;
					} else {
						findBySubCategoryName.forEach(subCategor -> {
							if (subCategor.getSupplierCode().equals(loginSupplierCode)) {
								throw new VendorNotFoundException("Sub Category Name Already Exist");
							}
						});

						category.setCategoryId(itemSubCategory.getCategoryId());
						category.setSubCategoryName(itemSubCategory.getSubCategoryName());
						category.setSupplierCode(loginSupplierCode);
						ItemSubCategory save = itemSubCategoryRepo.save(category);
						Optional<ItemCategory> findById = itemCategoryRepo.findById(save.getCategoryId());
						SubCategoryResponse categoryResponse = new SubCategoryResponse();
						categoryResponse.setCategoryId(findById.get().getCategoryId());
						categoryResponse.setCategoryName(findById.get().getCategoryName());
						categoryResponse.setCreatedBy(save.getCreatedBy());
						categoryResponse.setCreatedOn(save.getCreatedOn());
						categoryResponse.setUpdatedBy(save.getUpdatedBy());
						categoryResponse.setUpdatedOn(save.getUpdatedOn());
						categoryResponse.setSubCategoryId(save.getSubCategoryId());
						categoryResponse.setSubCategoryName(save.getSubCategoryName());
						categoryResponse.setSupplierCode(save.getSupplierCode());
						return categoryResponse;
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PutMapping("/vendor/subCategory/{id}")
	public ItemSubCategory updateSubCategory(@PathVariable() Long id, @RequestBody ItemSubCategory itemSubCategory,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - updateSubCategory " + itemSubCategory);
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(itemSubCategory.getSubCategoryName()))
					&& fieldValidation.isEmpty(itemSubCategory.getCategoryId())) {
				if (!loginSupplierCode.equals(null)) {
					Optional<ItemSubCategory> findById = itemSubCategoryRepo.findById(id);
					ItemSubCategory category = new ItemSubCategory();
					if (findById.isPresent()) {
						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
							category.setCategoryId(itemSubCategory.getCategoryId());
							category.setSubCategoryName(itemSubCategory.getSubCategoryName());
							category.setSupplierCode(loginSupplierCode);
							return itemSubCategoryRepo.save(category);
						} else {
							throw new VendorNotFoundException("You don't have permission to update this category");
						}

					} else {
						throw new VendorNotFoundException("Sub Category Name Not Exist");
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PostMapping("/vendor/brand")
	public ItemBrand addBrand(@RequestBody ItemBrand itemBrand, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.addBrand()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if ((fieldValidation.isEmpty(itemBrand.getBrandName()))) {
				if (!loginSupplierCode.equals(null)) {
					List<ItemBrand> findByBrandName = itemBrandRepo.findByBrandName(itemBrand.getBrandName());
					ItemBrand brand = new ItemBrand();
					if (findByBrandName.size() < 1) {
						brand.setBrandName(itemBrand.getBrandName());
						brand.setSupplierCode(loginSupplierCode);
						return itemBrandRepo.save(brand);
					} else {
						findByBrandName.forEach(brands -> {
							if (brands.getSupplierCode().equals(loginSupplierCode)) {
								throw new VendorNotFoundException("Brand Already Exist");
							}
						});

						brand.setBrandName(itemBrand.getBrandName());
						brand.setSupplierCode(loginSupplierCode);
						return itemBrandRepo.save(brand);
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}

			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@PostMapping("/vendor/inventory")
	public InventoryResponse addInventory(@RequestBody InventoryDetails inventoryDetails,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.addInventory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);

			if ((fieldValidation.isEmpty(inventoryDetails.getCategoryId()))
					& (fieldValidation.isEmpty(inventoryDetails.getBrandId()))
					&& fieldValidation.isEmpty(inventoryDetails.getItemDescription())
					& (fieldValidation.isEmpty(inventoryDetails.getSubcategoryId()))) {
//				 List<InventoryDetails> findBySupplierCode = inventoryRepo.findBySupplierCode(loginSupplierCode);
				if (!loginSupplierCode.equals(null)) {
					InventoryDetails details = new InventoryDetails();
					details.setSupplierCode(loginSupplierCode);
					details.setCategoryId(inventoryDetails.getCategoryId());
					details.setBrandId(inventoryDetails.getBrandId());
					details.setSubcategoryId(inventoryDetails.getSubcategoryId());
					details.setItemDescription(inventoryDetails.getItemDescription());
					try {
						if (fieldValidation.isEmpty(inventoryDetails.getQty())
								&& fieldValidation.isEmpty(inventoryDetails.getUnitPrice())
								&& fieldValidation.isEmpty(inventoryDetails.getSku())
								&& fieldValidation.isEmpty(inventoryDetails.getStatus())
								&& fieldValidation.isEmpty(inventoryDetails.getDiscount())
								&& fieldValidation.isEmpty(inventoryDetails.getCreatedBy())
								&& fieldValidation.isEmpty(inventoryDetails.getCreatedOn())) {
							details.setQty(inventoryDetails.getQty());
							details.setUnitPrice(inventoryDetails.getUnitPrice());
							details.setSku(inventoryDetails.getSku());
							details.setStatus(inventoryDetails.getStatus());
							details.setDiscount(inventoryDetails.getDiscount());
							details.setCreatedBy(inventoryDetails.getCreatedBy());
							details.setCreatedOn(inventoryDetails.getCreatedOn());

						}
					} catch (Exception e) {

					}
					InventoryDetails save = inventoryRepo.save(details);
					
					Optional<ItemBrand> findByIdBrand = itemBrandRepo.findById(save.getBrandId());
					
					Optional<ItemCategory> findByIdCategory = itemCategoryRepo.findById(save.getCategoryId());
					
					Optional<ItemSubCategory> findByIdSubCategory = itemSubCategoryRepo.findById(save.getSubcategoryId());
					InventoryResponse inventoryResponse = new InventoryResponse();
					
					inventoryResponse.setBrandId(save.getBrandId());
					inventoryResponse.setBrandName(findByIdBrand.get().getBrandName());
					inventoryResponse.setCategoryId(save.getCategoryId());
					inventoryResponse.setCategoryName(findByIdCategory.get().getCategoryName());
					inventoryResponse.setCreatedBy(save.getCreatedBy());
					inventoryResponse.setCreatedOn(save.getCreatedOn());
					inventoryResponse.setDiscount(save.getDiscount());
					inventoryResponse.setItemDescription(save.getItemDescription());
					inventoryResponse.setItemId(save.getItemId());
					inventoryResponse.setQty(save.getQty());
					inventoryResponse.setSku(save.getSku());
					inventoryResponse.setStatus(save.getStatus());
					inventoryResponse.setSubcategoryId(save.getSubcategoryId());
					inventoryResponse.setSubcategoryName(findByIdSubCategory.get().getSubCategoryName());
					inventoryResponse.setSupplierCode(save.getSupplierCode());
					inventoryResponse.setUnitPrice(save.getUnitPrice());
					inventoryResponse.setUpdatedBy(save.getUpdatedBy());
					inventoryResponse.setUpdatedOn(save.getUpdatedOn());
					
					return inventoryResponse;
					
//				} else {
//					throw new VendorNotFoundException("Inventory Already Exist");
//				}
				} else {

					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor/inventory")
	public List<InventoryDetails> getInventory(@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			List<InventoryDetails> findBySupplierCode = inventoryRepo.findBySupplierCode(loginSupplierCode);
			if (findBySupplierCode.size() < 1) {
				throw new VendorNotFoundException("No Inventory Details");
			} else {
				return findBySupplierCode;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException(e.getMessage());
		}

	}

	@GetMapping("/vendor/category")
	public List<ItemCategory> getCategory(@RequestHeader(name = "Authorization") String token) {
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				List<ItemCategory> listItemCategory = itemCategoryRepo.findBySupplierCode(loginSupplierCode);
				if (listItemCategory.size() < 1) {
					throw new VendorNotFoundException("No Category Details");
				}
				return listItemCategory;
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor/subCategory")
	public List<ItemSubCategory> getSubCategory(@RequestHeader(name = "Authorization") String token) {

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				List<ItemSubCategory> listItemSubCategory = itemSubCategoryRepo.findBySupplierCode(loginSupplierCode);
				if (listItemSubCategory.size() < 1) {
					throw new VendorNotFoundException("No SubCategory Details");
				}
				return listItemSubCategory;
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/vendor/brand")
	public List<ItemBrand> getBrand(@RequestHeader(name = "Authorization") String token) {

		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			if (!loginSupplierCode.equals(null)) {
				List<ItemBrand> listItemBrand = itemBrandRepo.findBySupplierCode(loginSupplierCode);
				if (listItemBrand.size() < 1) {
					throw new VendorNotFoundException("No Brand Details");
				}
				return listItemBrand;
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/inventory/{inventoryId}")
	public Object deleteInventory(@PathVariable() long inventoryId,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.deleteInventory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<InventoryDetails> findById = this.inventoryRepo.findById(inventoryId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Inventory ID does not exist - " + inventoryId);
			} else {
				if (!loginSupplierCode.equals(null)) {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						this.inventoryRepo.deleteById(inventoryId);
						return "Successfully Deleted";
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this inventory");
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/category/{categoryId}")
	public Object deleteCategory(@PathVariable() long categoryId, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.deleteCategory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<ItemCategory> findById = this.itemCategoryRepo.findById(categoryId);
			if (!(findById.isPresent())) {
				throw new VendorNotFoundException("Category ID does not exist - " + categoryId);
			} else {
				if (!loginSupplierCode.equals(null)) {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						this.itemCategoryRepo.deleteById(categoryId);
						return "Successfully Deleted";
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this vendor category");
					}
				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/subCategory/{subCatId}")
	public Object deleteSubCategory(@PathVariable() long subCatId,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.deleteSubCategory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<ItemSubCategory> findById = this.itemSubCategoryRepo.findById(subCatId);
			if (!loginSupplierCode.equals(null)) {
				if (!(findById.isPresent())) {
					throw new VendorNotFoundException("SubCategory ID does not exist - " + subCatId);
				} else {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						this.itemSubCategoryRepo.deleteById(subCatId);
						return "Successfully Deleted";
					} else {
						throw new VendorNotFoundException(
								"You don't have permission to delete this vendor sub category");
					}
				}
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("/vendor/brand/{brandId}")
	public Object deleteBrand(@PathVariable() long brandId, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.deleteBrand()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<ItemBrand> findById = this.itemBrandRepo.findById(brandId);
			if (!loginSupplierCode.equals(null)) {
				if (!(findById.isPresent())) {
					throw new VendorNotFoundException("Brand ID does not exist - " + brandId);
				} else {
					if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
						this.itemBrandRepo.deleteById(brandId);
						return "Successfully Deleted";
					} else {
						throw new VendorNotFoundException("You don't have permission to delete this vendor brand");
					}
				}
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}

	@PutMapping("/vendor/inventory/{inventoryId}")
	public InventoryDetails updateInventory(@RequestBody InventoryDetails inventoryDetails,
			@PathVariable() long inventoryId, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.updateInventory()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<InventoryDetails> findById = this.inventoryRepo.findById(inventoryId);
			if ((fieldValidation.isEmpty(inventoryDetails.getCategoryId()))
					& (fieldValidation.isEmpty(inventoryDetails.getBrandId()))
					&& fieldValidation.isEmpty(inventoryDetails.getItemDescription())
					& (fieldValidation.isEmpty(inventoryDetails.getSubcategoryId()))) {
				if (!loginSupplierCode.equals(null)) {
					if (!findById.isPresent()) {
						throw new VendorNotFoundException("Inventory ID does not exist - " + inventoryId);
					} else {
						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
							InventoryDetails details = new InventoryDetails();
							details.setSupplierCode(loginSupplierCode);
							details.setCategoryId(inventoryDetails.getCategoryId());
							details.setBrandId(inventoryDetails.getBrandId());
							details.setSubcategoryId(inventoryDetails.getSubcategoryId());
							details.setItemDescription(inventoryDetails.getItemDescription());
							details.setItemId(inventoryId);
							try {
								if (fieldValidation.isEmpty(inventoryDetails.getQty())
										&& fieldValidation.isEmpty(inventoryDetails.getUnitPrice())
										&& fieldValidation.isEmpty(inventoryDetails.getSku())
										&& fieldValidation.isEmpty(inventoryDetails.getStatus())
										&& fieldValidation.isEmpty(inventoryDetails.getDiscount())
										&& fieldValidation.isEmpty(inventoryDetails.getCreatedBy())
										&& fieldValidation.isEmpty(inventoryDetails.getCreatedOn())) {
									details.setQty(inventoryDetails.getQty());
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
						} else {
							throw new VendorNotFoundException("You don't have permission to edit this inventory");
						}
					}

				} else {
					throw new VendorNotFoundException("Token not valid");
				}
			} else {
				throw new VendorNotFoundException("Validation Error");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	@PutMapping("/vendor/brand/{brandId}")
	public ItemBrand updateBrand(@PathVariable() long brandId, @RequestBody ItemBrand itemBrand, @RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - InventoryController.updateBrand()");
		try {
			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
			Optional<ItemBrand> findById = this.itemBrandRepo.findById(brandId);
			if (!loginSupplierCode.equals(null)) {
				if ((fieldValidation.isEmpty(itemBrand.getBrandName()))) {
					if (findById.isPresent()) {
						if (findById.get().getSupplierCode().equals(loginSupplierCode)) {
							ItemBrand brand = new ItemBrand();
							brand.setBrandName(itemBrand.getBrandName());
							brand.setSupplierCode(loginSupplierCode);
							brand.setBrandId(findById.get().getBrandId());
							return itemBrandRepo.save(brand);	
						} else {
							throw new VendorNotFoundException("You don't have permission to edit this Brand");
						}
					} else {
						throw new VendorNotFoundException("Brand not present");
					}

				}
				else {
					throw new VendorNotFoundException("Brand name can't be null");
				}
			} else {
				throw new VendorNotFoundException("Token not valid");
			}
		} catch (Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
