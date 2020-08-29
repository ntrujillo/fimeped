package org.uce.fimeped.report.domain;

public class Report {

	private String institution;
	private String firstName;
	private String lastName;
	private String gender;
	private long clinicHistory;

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getClinicHistory() {
		return clinicHistory;
	}

	public void setClinicHistory(long clinicHistory) {
		this.clinicHistory = clinicHistory;
	}

}
