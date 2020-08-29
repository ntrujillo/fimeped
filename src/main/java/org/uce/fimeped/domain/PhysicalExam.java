package org.uce.fimeped.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A PhysicalExam.
 */
@Entity
@Table(name = "PHYSICALEXAM")
public class PhysicalExam implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "body_part")
    private String bodyPart;
    
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

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
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

        PhysicalExam physicalExam = (PhysicalExam) o;

        if ( ! Objects.equals(id, physicalExam.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PhysicalExam{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", bodyPart='" + bodyPart + "'" +
                ", weNe='" + weNe + "'" +
                '}';
    }
}
