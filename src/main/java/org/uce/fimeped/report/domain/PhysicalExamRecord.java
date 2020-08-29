package org.uce.fimeped.report.domain;

import java.util.Date;

public class PhysicalExamRecord {

	private long code;
	private Date date;
	private String description;
	private String bodyPart;
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

	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	public String getWeNe() {
		return weNe;
	}

	public void setWeNe(String weNe) {
		this.weNe = weNe;
	}

}
