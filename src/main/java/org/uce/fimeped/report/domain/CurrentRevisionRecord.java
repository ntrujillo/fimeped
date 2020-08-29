package org.uce.fimeped.report.domain;

import java.util.Date;

public class CurrentRevisionRecord {

	private long code;
	private Date date;
	private String description;
	private String organ;
	private String weNe;

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

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getWeNe() {
		return weNe;
	}

	public void setWeNe(String weNe) {
		this.weNe = weNe;
	}

}
