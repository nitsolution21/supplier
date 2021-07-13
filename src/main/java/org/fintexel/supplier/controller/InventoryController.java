package org.fintexel.supplier.controller;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemBrand;
import org.fintexel.supplier.entity.ItemCategory;
import org.fintexel.supplier.entity.ItemSubCategory;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.repository.InventoryRepo;
import org.fintexel.supplier.repository.ItemBrandRepo;
import org.fintexel.supplier.repository.ItemCategoryRepo;
import org.fintexel.supplier.repository.ItemSubCategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
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
	
	@PostMapping("/addCategory")
	public ItemCategory addCategory(@RequestBody ItemCategory itemCategory) {
		return itemCategoryRepo.save(itemCategory);
	}
	
	@PostMapping("/addSubCategory")
	public ItemSubCategory addSubCategory(@RequestBody ItemSubCategory itemSubCategory) {
		LOGGER.info("Inside - addSubCategory"+itemSubCategory);
		return itemSubCategoryRepo.save(itemSubCategory);
	}
	
	@PostMapping("/addBrand")
	public ItemBrand addBrand(@RequestBody ItemBrand itemBrand) {
		return itemBrandRepo.save(itemBrand);
	}
	
	@PostMapping("/addInventory")
	public InventoryDetails addInventory(@RequestBody InventoryDetails inventoryDetails) {
		return inventoryRepo.save(inventoryDetails);
	}
	
	@GetMapping("/inventory")
	public List<InventoryDetails> getInventory() {
		List<InventoryDetails> listInventoryDetails = inventoryRepo.findAll();
		if(listInventoryDetails.isEmpty()) {
			throw new VendorNotFoundException("No Inventory Details");
		}
		return inventoryRepo.findAll();
	}
	

	@GetMapping("/category")
	public List<ItemCategory> getCategory() {
		List<ItemCategory> listItemCategory = itemCategoryRepo.findAll();
		if(listItemCategory.isEmpty()) {
			throw new VendorNotFoundException("No Category Details");
		}
		return itemCategoryRepo.findAll();
	}
	

	@GetMapping("/subCategory")
	public List<ItemSubCategory> getSubCategory() {
		List<ItemSubCategory> listItemSubCategory = itemSubCategoryRepo.findAll();
		if(listItemSubCategory.isEmpty()) {
			throw new VendorNotFoundException("No SubCategory Details");
		}
		return itemSubCategoryRepo.findAll();
	}
	

	@GetMapping("/brand")
	public List<ItemBrand> getBrand() {
		List<ItemBrand> listItemBrand = itemBrandRepo.findAll();
		if(listItemBrand.isEmpty()) {
			throw new VendorNotFoundException("No Brand Details");
		}
		return itemBrandRepo.findAll();
	}
	
	@DeleteMapping("/inventory/{inventoryId}")
	public Object deleteInventory(@PathVariable() long inventoryId) {
		LOGGER.info("Inside - InventoryController.deleteInventory()");
		try {
			Optional<InventoryDetails> findById = this.inventoryRepo.findById(inventoryId);
			if(!(findById.isPresent())) {
				throw new VendorNotFoundException("Inventory ID does not exist - "+inventoryId);
			}else {
				this.inventoryRepo.deleteById(inventoryId);
				return "Inventory deleted - "+inventoryId;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@DeleteMapping("/category/{categoryId}")
	public Object deleteCategory(@PathVariable() long categoryId) {
		LOGGER.info("Inside - InventoryController.deleteCategory()");
		try {
			Optional<ItemCategory> findById = this.itemCategoryRepo.findById(categoryId);
			if(!(findById.isPresent())) {
				throw new VendorNotFoundException("Category ID does not exist - "+categoryId);
			}else {
				this.itemCategoryRepo.deleteById(categoryId);
				return "Category deleted - "+categoryId;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@DeleteMapping("/subCategory/{subCatId}")
	public Object deleteSubCategory(@PathVariable() long subCatId) {
		LOGGER.info("Inside - InventoryController.deleteSubCategory()");
		try {
			Optional<ItemSubCategory> findById = this.itemSubCategoryRepo.findById(subCatId);
			if(!(findById.isPresent())) {
				throw new VendorNotFoundException("SubCategory ID does not exist - "+subCatId);
			}else {
				this.itemSubCategoryRepo.deleteById(subCatId);
				return "SubCategory deleted - "+subCatId;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	@DeleteMapping("/brand/{brandId}")
	public Object deleteBrand(@PathVariable() long brandId) {
		LOGGER.info("Inside - InventoryController.deleteBrand()");
		try {
			Optional<ItemBrand> findById = this.itemBrandRepo.findById(brandId);
			if(!(findById.isPresent())) {
				throw new VendorNotFoundException("Brand ID does not exist - "+brandId);
			}else {
				this.itemBrandRepo.deleteById(brandId);
				return "Brand deleted - "+brandId;
			}
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
}
