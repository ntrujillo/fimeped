package org.uce.fimeped.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.uce.fimeped.domain.util.CustomLocalDateSerializer;
import org.uce.fimeped.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonalHistory.
 */
@Entity
@Table(name = "PERSONALHISTORY")
public class PersonalHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "date")
	private LocalDate date;

	@Column(name = "description")
	private String description;

	@Column(name = "illness")
	private String illness;

	@ManyToOne
	@JoinColumn(name="codigo_his")
	private ClinicHistory clinicHistory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	public ClinicHistory getClinicHistory() {
		return clinicHistory;
	}

	public void setClinicHistory(ClinicHistory clinicHistory) {
		this.clinicHistory = clinicHistory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PersonalHistory personalHistory = (PersonalHistory) o;

		if (!Objects.equals(id, personalHistory.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "PersonalHistory{" + "id=" + id + ", date='" + date + "'" + ", description='" + description + "'"
				+ ", illness='" + illness + "'" + '}';
	}
}
