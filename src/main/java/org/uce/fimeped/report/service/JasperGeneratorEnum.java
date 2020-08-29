package org.uce.fimeped.report.service;

public enum JasperGeneratorEnum {
	PDF, HTML, XLS, DOC;

	static JasperGeneratorEnum[] values;

	public static boolean isMember(String aName) {
		for (JasperGeneratorEnum enumValue : values) {
			if (enumValue.name().equals(aName)) {
				return true;
			}
		}
		return false;
	}

	static {
		values = values();
	}
}