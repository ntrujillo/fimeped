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
import java.util.Objects;

/**
 * A ClinicHistoryAddInf.
 */
@Entity
@Table(name = "CLINICHISTORYADDINF")
public class ClinicHistoryAddInf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "menarche_age")
	private Integer menarcheAge;

	@Column(name = "menopause_age")
	private Integer menopauseAge;

	@Column(name = "cycles")
	private Integer cycles;

	@Column(name = "sexually_active")
	private String sexuallyActive;

	@Column(name = "feat")
	private Integer feat;

	@Column(name = "deliveries")
	private Integer deliveries;

	@Column(name = "abortions")
	private Integer abortions;

	@Column(name = "caesareans")
	private Integer caesareans;

	@Column(name = "living_children")
	private Integer livingChildren;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "last_menarche")
	private LocalDate lastMenarche;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "last_delivery")
	private LocalDate lastDelivery;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "last_citology")
	private LocalDate lastCitology;

	@Column(name = "biopsy")
	private String biopsy;

	@Column(name = "protection_method")
	private String protectionMethod;

	@Column(name = "hormone_terapy")
	private String hormoneTerapy;

	@Column(name = "colposcopy")
	private String colposcopy;

	@Column(name = "mammografhy")
	private String mammografhy;

	@OneToOne
	@JoinColumn(name="codigo_his")
	@JsonIgnore	
	private ClinicHistory clinicHistory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMenarcheAge() {
		return menarcheAge;
	}

	public void setMenarcheAge(Integer menarcheAge) {
		this.menarcheAge = menarcheAge;
	}

	public Integer getMenopauseAge() {
		return menopauseAge;
	}

	public void setMenopauseAge(Integer menopauseAge) {
		this.menopauseAge = menopauseAge;
	}

	public Integer getCycles() {
		return cycles;
	}

	public void setCycles(Integer cycles) {
		this.cycles = cycles;
	}

	public String getSexuallyActive() {
		return sexuallyActive;
	}

	public void setSexuallyActive(String sexuallyActive) {
		this.sexuallyActive = sexuallyActive;
	}

	public Integer getFeat() {
		return feat;
	}

	public void setFeat(Integer feat) {
		this.feat = feat;
	}

	public Integer getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Integer deliveries) {
		this.deliveries = deliveries;
	}

	public Integer getAbortions() {
		return abortions;
	}

	public void setAbortions(Integer abortions) {
		this.abortions = abortions;
	}

	public Integer getCaesareans() {
		return caesareans;
	}

	public void setCaesareans(Integer caesareans) {
		this.caesareans = caesareans;
	}

	public Integer getLivingChildren() {
		return livingChildren;
	}

	public void setLivingChildren(Integer livingChildren) {
		this.livingChildren = livingChildren;
	}

	public LocalDate getLastMenarche() {
		return lastMenarche;
	}

	public void setLastMenarche(LocalDate lastMenarche) {
		this.lastMenarche = lastMenarche;
	}

	public LocalDate getLastDelivery() {
		return lastDelivery;
	}

	public void setLastDelivery(LocalDate lastDelivery) {
		this.lastDelivery = lastDelivery;
	}

	public LocalDate getLastCitology() {
		return lastCitology;
	}

	public void setLastCitology(LocalDate lastCitology) {
		this.lastCitology = lastCitology;
	}

	public String getBiopsy() {
		return biopsy;
	}

	public void setBiopsy(String biopsy) {
		this.biopsy = biopsy;
	}

	public String getProtectionMethod() {
		return protectionMethod;
	}

	public void setProtectionMethod(String protectionMethod) {
		this.protectionMethod = protectionMethod;
	}

	public String getHormoneTerapy() {
		return hormoneTerapy;
	}

	public void setHormoneTerapy(String hormoneTerapy) {
		this.hormoneTerapy = hormoneTerapy;
	}

	public String getColposcopy() {
		return colposcopy;
	}

	public void setColposcopy(String colposcopy) {
		this.colposcopy = colposcopy;
	}

	public String getMammografhy() {
		return mammografhy;
	}

	public void setMammografhy(String mammografhy) {
		this.mammografhy = mammografhy;
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

		ClinicHistoryAddInf clinicHistoryAddInf = (ClinicHistoryAddInf) o;

		if (!Objects.equals(id, clinicHistoryAddInf.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "ClinicHistoryAddInf{" + "id=" + id + ", menarcheAge='" + menarcheAge + "'" + ", menopauseAge='"
				+ menopauseAge + "'" + ", cycles='" + cycles + "'" + ", sexuallyActive='" + sexuallyActive + "'"
				+ ", feat='" + feat + "'" + ", deliveries='" + deliveries + "'" + ", abortions='" + abortions + "'"
				+ ", caesareans='" + caesareans + "'" + ", livingChildren='" + livingChildren + "'" + ", lastMenarche='"
				+ lastMenarche + "'" + ", lastDelivery='" + lastDelivery + "'" + ", lastCitology='" + lastCitology + "'"
				+ ", biopsy='" + biopsy + "'" + ", protectionMethod='" + protectionMethod + "'" + ", hormoneTerapy='"
				+ hormoneTerapy + "'" + ", colposcopy='" + colposcopy + "'" + ", mammografhy='" + mammografhy + '}';
	}
}
