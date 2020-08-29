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
 * A Episode.
 */
@Entity
@Table(name = "EPISODE")
public class Episode implements Serializable {

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
	@Column(name = "epi_date")
	private LocalDate date;

	@Column(name = "epi_description")
	private String description;

	@Column(name = "epi_user")
	private String user;

	@ManyToOne
	@JoinColumn(name = "codigo_his")
	private ClinicHistory clinicHistory;

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<Reason> reasons = new HashSet<>();

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<CurrentIllness> currentIllnesss = new HashSet<>();

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<CurrentRevision> currentRevisions = new HashSet<>();	

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<PhysicalExam> physicalExams = new HashSet<>();

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<Diagnostic> diagnostics = new HashSet<>();

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<Plan> plans = new HashSet<>();

	@OneToMany(mappedBy = "episode")
	@JsonIgnore
	private Set<EvolutionPrescription> evolutionPrescriptions = new HashSet<>();

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public Set<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(Set<Reason> reasons) {
		this.reasons = reasons;
	}

	public Set<CurrentIllness> getCurrentIllnesss() {
		return currentIllnesss;
	}

	public void setCurrentIllnesss(Set<CurrentIllness> currentIllnesss) {
		this.currentIllnesss = currentIllnesss;
	}

	public Set<CurrentRevision> getCurrentRevisions() {
		return currentRevisions;
	}

	public void setCurrentRevisions(Set<CurrentRevision> currentRevisions) {
		this.currentRevisions = currentRevisions;
	}

	public Set<PhysicalExam> getPhysicalExams() {
		return physicalExams;
	}

	public void setPhysicalExams(Set<PhysicalExam> physicalExams) {
		this.physicalExams = physicalExams;
	}

	public Set<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(Set<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public Set<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Set<Plan> plans) {
		this.plans = plans;
	}

	public Set<EvolutionPrescription> getEvolutionPrescriptions() {
		return evolutionPrescriptions;
	}

	public void setEvolutionPrescriptions(Set<EvolutionPrescription> evolutionPrescriptions) {
		this.evolutionPrescriptions = evolutionPrescriptions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Episode episode = (Episode) o;

		if (!Objects.equals(id, episode.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Episode{" + "id=" + id + ", date='" + date + "'" + ", description='" + description + "'" + '}';
	}
}
