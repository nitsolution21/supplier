package org.fintexel.supplier.entity.flowableentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACT_RE_PROCDEF")
public class FlowableRegistration {
	@Id
	@Column(name = "ID_")
	private String id;
	
	@Column(name = "KEY_")
	private String key;
	
	@Column(name = "VERSION_")
	private int version;

	@Column(name = "REV_")
	private String rev;
	
	
	@Column(name = "CATEGORY_")
	private String category;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "DEPLOYMENT_ID_")
	private String deploymenyId;
	
	@Column(name = "RESOURCE_NAME_")
	private String resourceName;
	
	@Column(name = "DGRM_RESOURCE_NAME_")
	private String dgrmResourceName;
	
	@Column(name = "DESCRIPTION_")
	private String description;
	
	@Column(name = "HAS_START_FORM_KEY_")
	private String hasStartFormKey;
	
	@Column(name = "HAS_GRAPHICAL_NOTATION_")
	private String hasGraphicalNotation;
	
	@Column(name = "SUSPENSION_STATE_")
	private int suspensionState;
	
	@Column(name = "TENANT_ID_")
	private String tenantId;
	
	@Column(name = "ENGINE_VERSION_")
	private String engineVersion;
	
	@Column(name = "DERIVED_FROM_")
	private String derivedFrom;
	
	@Column(name = "DERIVED_FROM_ROOT_")
	private String derivedFromRoot;
	
	@Column(name = "DERIVED_VERSION_")
	private String derivedVersion;

	public FlowableRegistration(String id, String key, int version, String rev, String category, String name,
			String deploymenyId, String resourceName, String dgrmResourceName, String description,
			String hasStartFormKey, String hasGraphicalNotation, int suspensionState, String tenantId,
			String engineVersion, String derivedFrom, String derivedFromRoot, String derivedVersion) {
		super();
		this.id = id;
		this.key = key;
		this.version = version;
		this.rev = rev;
		this.category = category;
		this.name = name;
		this.deploymenyId = deploymenyId;
		this.resourceName = resourceName;
		this.dgrmResourceName = dgrmResourceName;
		this.description = description;
		this.hasStartFormKey = hasStartFormKey;
		this.hasGraphicalNotation = hasGraphicalNotation;
		this.suspensionState = suspensionState;
		this.tenantId = tenantId;
		this.engineVersion = engineVersion;
		this.derivedFrom = derivedFrom;
		this.derivedFromRoot = derivedFromRoot;
		this.derivedVersion = derivedVersion;
	}

	public FlowableRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "FlowableRegistration [id=" + id + ", key=" + key + ", version=" + version + ", rev=" + rev
				+ ", category=" + category + ", name=" + name + ", deploymenyId=" + deploymenyId + ", resourceName="
				+ resourceName + ", dgrmResourceName=" + dgrmResourceName + ", description=" + description
				+ ", hasStartFormKey=" + hasStartFormKey + ", hasGraphicalNotation=" + hasGraphicalNotation
				+ ", suspensionState=" + suspensionState + ", tenantId=" + tenantId + ", engineVersion=" + engineVersion
				+ ", derivedFrom=" + derivedFrom + ", derivedFromRoot=" + derivedFromRoot + ", derivedVersion="
				+ derivedVersion + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeploymenyId() {
		return deploymenyId;
	}

	public void setDeploymenyId(String deploymenyId) {
		this.deploymenyId = deploymenyId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDgrmResourceName() {
		return dgrmResourceName;
	}

	public void setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHasStartFormKey() {
		return hasStartFormKey;
	}

	public void setHasStartFormKey(String hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}

	public String getHasGraphicalNotation() {
		return hasGraphicalNotation;
	}

	public void setHasGraphicalNotation(String hasGraphicalNotation) {
		this.hasGraphicalNotation = hasGraphicalNotation;
	}

	public int getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(int suspensionState) {
		this.suspensionState = suspensionState;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public void setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
	}

	public String getDerivedFrom() {
		return derivedFrom;
	}

	public void setDerivedFrom(String derivedFrom) {
		this.derivedFrom = derivedFrom;
	}

	public String getDerivedFromRoot() {
		return derivedFromRoot;
	}

	public void setDerivedFromRoot(String derivedFromRoot) {
		this.derivedFromRoot = derivedFromRoot;
	}

	public String getDerivedVersion() {
		return derivedVersion;
	}

	public void setDerivedVersion(String derivedVersion) {
		this.derivedVersion = derivedVersion;
	}
	

	
	
	
}
