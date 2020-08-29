package org.uce.fimeped.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A CurrentRevision.
 */
@Entity
@Table(name = "CURRENTREVISION")
public class CurrentRevision implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "organ")
    private String organ;
    
    @Column(name = "we_ne")
    private String weNe;

    @ManyToOne
    private Episode episode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getWeNe() {
        return weNe;
    }

    public void setWeNe(String weNe) {
        this.weNe = weNe;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrentRevision currentRevision = (CurrentRevision) o;

        if ( ! Objects.equals(id, currentRevision.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CurrentRevision{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", organ='" + organ + "'" +
                ", weNe='" + weNe + "'" +
                '}';
    }
}
