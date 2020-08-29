package org.uce.fimeped.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "ICD10")
public class ICD10 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "parent")
	private ICD10 parentICD;

	@OneToMany(mappedBy = "parentICD")
	private List<ICD10> records;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ICD10 getParentICD() {
		return parentICD;
	}

	public void setParentICD(ICD10 parentICD) {
		this.parentICD = parentICD;
	}

	public List<ICD10> getRecords() {
		return records;
	}

	public void setRecords(List<ICD10> records) {
		this.records = records;
	}

}
