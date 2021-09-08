package org.fintexel.supplier.customerentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_GEO")
public class GeoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GEO_ID") private long geoId;
	@Column(name = "NAME") private String name;
	@Column(name = "PARENT_ID") private long parentId;
	@Column(name = "TYPE") private String type;
	
	public GeoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GeoEntity(long geoId, String name, long parentId, String type) {
		super();
		this.geoId = geoId;
		this.name = name;
		this.parentId = parentId;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "GeoEntity [geoId=" + geoId + ", name=" + name + ", parentId=" + parentId + ", type=" + type + "]";
	}
	
	public long getGeoId() {
		return geoId;
	}
	
	public void setGeoId(long geoId) {
		this.geoId = geoId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
