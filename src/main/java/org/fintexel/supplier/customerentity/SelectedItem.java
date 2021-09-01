package org.fintexel.supplier.customerentity;

import java.util.List;
import java.util.Optional;

import org.fintexel.supplier.entity.InventoryDetails;
import org.fintexel.supplier.entity.ItemSubCategory;

public class SelectedItem {
	
	List<InventoryDetails> findByCategoryId;
	List<ItemSubCategory> findById;
	public List<InventoryDetails> getFindByCategoryId() {
		return findByCategoryId;
	}
	public void setFindByCategoryId(List<InventoryDetails> findByCategoryId) {
		this.findByCategoryId = findByCategoryId;
	}
	public List<ItemSubCategory> getFindById() {
		return findById;
	}
	public void setFindById(List<ItemSubCategory> findById) {
		this.findById = findById;
	}
	@Override
	public String toString() {
		return "SelectedItem [findByCategoryId=" + findByCategoryId + ", findById=" + findById + "]";
	}
	public SelectedItem(List<InventoryDetails> findByCategoryId, List<ItemSubCategory> findById) {
		super();
		this.findByCategoryId = findByCategoryId;
		this.findById = findById;
	}
	public SelectedItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
