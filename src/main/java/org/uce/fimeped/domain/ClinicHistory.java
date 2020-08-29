package org.uce.fimeped.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.uce.fimeped.domain.util.CustomLocalDateSerializer;
import org.uce.fimeped.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClinicHistory.
 */
@Entity
@Table(name = "HS_HISTORIA_CLINICA")
public class ClinicHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO_HIS")
	private Long id;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "FECHA_CRE_HIS")
	private LocalDate createDate;

	@OneToOne(mappedBy = "clinicHistory")
	private ClinicHistoryAddInf clinic_add_inf;

	@ManyToOne
	@JoinColumn(name="CODIGO_PER")
	private Person person;

	@OneToMany(mappedBy = "clinicHistory")
	@JsonIgnore
	private Set<Episode> episodes = new HashSet<>();

	@OneToMany(mappedBy = "clinicHistory")
	@JsonIgnore
	private Set<PersonalHistory> clinicPersonals = new HashSet<>();

	@OneToMany(mappedBy = "clinicHistory")
	@JsonIgnore
	private Set<FamilyHistory> clinicFamilys = new HashSet<>();
	
	
	@OneToMany(mappedBy = "clinicHistory")
	@JsonIgnore
	private Set<VitalSign> vitalSigns = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public ClinicHistoryAddInf getClinic_add_inf() {
		return clinic_add_inf;
	}

	public void setClinic_add_inf(ClinicHistoryAddInf clinicHistoryAddInf) {
		this.clinic_add_inf = clinicHistoryAddInf;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Set<Episode> episodes) {
		this.episodes = episodes;
	}

	public Set<PersonalHistory> getClinicPersonals() {
		return clinicPersonals;
	}

	public void setClinicPersonals(Set<PersonalHistory> clinicPersonals) {
		this.clinicPersonals = clinicPersonals;
	}

	public Set<FamilyHistory> getClinicFamilys() {
		return clinicFamilys;
	}

	public void setClinicFamilys(Set<FamilyHistory> clinicFamilys) {
		this.clinicFamilys = clinicFamilys;
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

		ClinicHistory clinicHistory = (ClinicHistory) o;

		if (!Objects.equals(id, clinicHistory.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "ClinicHistory{" + "id=" + id + ", createDate='" + createDate + "'" + '}';
	}
}
