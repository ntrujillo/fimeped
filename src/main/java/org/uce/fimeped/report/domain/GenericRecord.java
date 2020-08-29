package org.uce.fimeped.report.domain;

import java.util.Date;

public class GenericRecord {

	private long code;
	private Date date;
	private String description;

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

}
