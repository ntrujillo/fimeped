package org.uce.fimeped.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * A Provincia.
 */
@Entity
@Table(name = "PROVINCIA")
public class Provincia implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id   
    private String id;
    

    @NotNull        
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @OneToMany(mappedBy = "provincia")
    @JsonIgnore
    private Set<Canton> cantons = new HashSet<>();

    

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Canton> getCantons() {
        return cantons;
    }

    public void setCantons(Set<Canton> cantons) {
        this.cantons = cantons;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantons == null) ? 0 : cantons.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Provincia other = (Provincia) obj;
		if (cantons == null) {
			if (other.cantons != null)
				return false;
		} else if (!cantons.equals(other.cantons))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Provincia [id=" + id + ", name=" + name + "]";
	}

   
}
