package org.uce.fimeped.domain;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class CatalogId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "tab_id", insertable = false, updatable = false)
	private String tabId;
	@Column(name = "cat_id", insertable = false, updatable = false)
	private String catId;

	public CatalogId() {
		super();
	}

	public CatalogId(String tabId, String catId) {
		super();
		this.tabId = tabId;
		this.catId = catId;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catId == null) ? 0 : catId.hashCode());
		result = prime * result + ((tabId == null) ? 0 : tabId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatalogId other = (CatalogId) obj;
		if (catId == null) {
			if (other.catId != null)
				return false;
		} else if (!catId.equals(other.catId))
			return false;
		if (tabId == null) {
			if (other.tabId != null)
				return false;
		} else if (!tabId.equals(other.tabId))
			return false;
		return true;
	}
}
