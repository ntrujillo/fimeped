package org.uce.fimeped.report.domain;

import java.util.Date;

public class DiagnosticRecord {
	private long code;
	private Date date;
	private String description;
	private String cie;
	private String catalogPreDef;

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCie() {
		return cie;
	}

	public void setCie(String cie) {
		this.cie = cie;
	}

	public String getCatalogPreDef() {
		return catalogPreDef;
	}

	public void setCatalogPreDef(String catalogPreDef) {
		this.catalogPreDef = catalogPreDef;
	}

}
