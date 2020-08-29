package org.uce.fimeped.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Plan.
 */
@Entity
@Table(name = "PLANS")
public class Plan implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name = "description")
    private String description;

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

        Plan plan = (Plan) o;

        if ( ! Objects.equals(id, plan.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", description='" + description + "'" +
                '}';
    }
}
