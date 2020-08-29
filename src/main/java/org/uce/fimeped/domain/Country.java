package org.uce.fimeped.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * A Country.
 */
@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id 
    private String id;
    @NotNull        
    @Column(name = "name", nullable = false)
    private String name;    
    @Column(name = "nacionality")
    private String nacionality;
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Set<Provincia> provincias = new HashSet<>();

    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public Set<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(Set<Provincia> provincias) {
        this.provincias = provincias;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nacionality == null) ? 0 : nacionality.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((provincias == null) ? 0 : provincias.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nacionality == null) {
			if (other.nacionality != null)
				return false;
		} else if (!nacionality.equals(other.nacionality))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (provincias == null) {
			if (other.provincias != null)
				return false;
		} else if (!provincias.equals(other.provincias))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", nacionality=" + nacionality + "]";
	}
    
    

}
