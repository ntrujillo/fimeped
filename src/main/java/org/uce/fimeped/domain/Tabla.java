package org.uce.fimeped.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TABLA")
public class Tabla implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tab_id")
	private String tabId;

	private String description;

	@OneToMany(mappedBy = "tabla")
	private List<Catalog> items;

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Catalog> getItems() {
		return items;
	}

	public void setItems(List<Catalog> items) {
		this.items = items;
	}

}
