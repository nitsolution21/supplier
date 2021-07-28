package org.fintexel.supplier.entity.flowableentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACT_FO_FORM_DEFINITION")
public class FlowableForm {
	@Id
	@Column(name = "ID_")
	private String id;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "VERSION_")
	private int version;
	
	@Column(name = "KEY_")
	private String key;
	
	@Column(name = "CATEGORY_")
	private String category;
	
	@Column(name = "DEPLOYMENT_ID_")
	private String deploymenyId;
	
	@Column(name = "TENANT_ID_")
	private String tenantId;
	
	@Column(name = "RESOURCE_NAME_")
	private String resourceName;
	
	@Column(name = "DESCRIPTION_")
	private String description;

	public FlowableForm(String id, String name, int version, String key, String category,
			String deploymenyId, String tenantId, String resourceName, String description) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
		this.key = key;
		this.category = category;
		this.deploymenyId = deploymenyId;
		this.tenantId = tenantId;
		this.resourceName = resourceName;
		this.description = description;
	}

	public FlowableForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDeploymenyId() {
		return deploymenyId;
	}

	public void setDeploymenyId(String deploymenyId) {
		this.deploymenyId = deploymenyId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
