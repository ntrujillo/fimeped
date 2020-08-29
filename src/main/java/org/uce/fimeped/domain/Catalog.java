package org.uce.fimeped.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "CATALOG")
public class Catalog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	private CatalogId catalogId;

	private String value;

	@MapsId("tab_id")
	@ManyToOne
	@JoinColumn(name = "tab_id", referencedColumnName = "tab_id")
	private Tabla tabla;

	public CatalogId getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(CatalogId catalogId) {
		this.catalogId = catalogId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Tabla getTabla() {
		return tabla;
	}

	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}

	

}
