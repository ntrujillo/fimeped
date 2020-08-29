package org.uce.fimeped.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.uce.fimeped.domain.util.CustomLocalDateSerializer;
import org.uce.fimeped.domain.util.ISO8601LocalDateDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A VitalSign.
 */
@Entity
@Table(name = "VITALSIGN")
public class VitalSign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "blood_pressure")
	private String bloodPressure;

	@Column(name = "heart_rate")
	private Integer heartRate;

	@Column(name = "breathing_frecuency")
	private Integer breathingFrecuency;

	@Column(name = "oral_temperature")
	private Double oralTemperature;

	@Column(name = "axillary_temperature")
	private Double axillaryTemperature;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "size")
	private Integer size;

	@Column(name = "head_circumference")
	private Double headCircumference;

	@Column(name = "body_mass")
	private Double bodyMass;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "date")
	private LocalDate date;

	@Column(name = "age")
	private int age;

	@ManyToOne
	@JoinColumn(name = "codigo_his")
	private ClinicHistory clinicHistory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Integer getBreathingFrecuency() {
		return breathingFrecuency;
	}

	public void setBreathingFrecuency(Integer breathingFrecuency) {
		this.breathingFrecuency = breathingFrecuency;
	}

	public Double getOralTemperature() {
		return oralTemperature;
	}

	public void setOralTemperature(Double oralTemperature) {
		this.oralTemperature = oralTemperature;
	}

	public Double getAxillaryTemperature() {
		return axillaryTemperature;
	}

	public void setAxillaryTemperature(Double axillaryTemperature) {
		this.axillaryTemperature = axillaryTemperature;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Double getHeadCircumference() {
		return headCircumference;
	}

	public void setHeadCircumference(Double headCircumference) {
		this.headCircumference = headCircumference;
	}

	public Double getBodyMass() {
		return bodyMass;
	}

	public void setBodyMass(Double bodyMass) {
		this.bodyMass = bodyMass;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public ClinicHistory getClinicHistory() {
		return clinicHistory;
	}

	public void setClinicHistory(ClinicHistory clinicHistory) {
		this.clinicHistory = clinicHistory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		VitalSign vitalSign = (VitalSign) o;

		if (!Objects.equals(id, vitalSign.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "VitalSign{" + "id=" + id + ", bloodPressure='" + bloodPressure + "'" + ", heartRate='" + heartRate + "'"
				+ ", breathingFrecuency='" + breathingFrecuency + "'" + ", oralTemperature='" + oralTemperature + "'"
				+ ", axillaryTemperature='" + axillaryTemperature + "'" + ", weight='" + weight + "'" + ", size='"
				+ size + "'" + ", headCircumference='" + headCircumference + "'" + ", bodyMass='" + bodyMass + "'"
				+ '}';
	}
}
