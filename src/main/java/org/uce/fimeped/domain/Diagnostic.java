package org.uce.fimeped.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;



/**
 * A Diagnostic.
 */
@Entity
@Table(name = "DIAGNOSTIC")
public class Diagnostic implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "cie")
    private String cie;
    
    @Column(name = "pre_def")
    private String preDef;

    @ManyToOne
    @JoinColumn(name="episode_id")
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

    public String getCie() {
        return cie;
    }

    public void setCie(String cie) {
        this.cie = cie;
    }

    public String getPreDef() {
        return preDef;
    }

    public void setPreDef(String preDef) {
        this.preDef = preDef;
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

        Diagnostic diagnostic = (Diagnostic) o;

        if ( ! Objects.equals(id, diagnostic.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Diagnostic{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", cie='" + cie + "'" +
                ", preDef='" + preDef + "'" +
                '}';
    }
}
