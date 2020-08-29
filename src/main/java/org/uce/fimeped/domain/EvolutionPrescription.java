package org.uce.fimeped.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A EvolutionPrescription.
 */
@Entity
@Table(name = "EVOLUTIONPRESCRIPTION")
public class EvolutionPrescription implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name = "evolution")
    private String evolution;
    
    @Column(name = "prescription")
    private String prescription;
    
    @Column(name = "medicines")
    private String medicines;

    @ManyToOne
    private Episode episode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
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

        EvolutionPrescription evolutionPrescription = (EvolutionPrescription) o;

        if ( ! Objects.equals(id, evolutionPrescription.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EvolutionPrescription{" +
                "id=" + id +
                ", evolution='" + evolution + "'" +
                ", prescription='" + prescription + "'" +
                ", medicines='" + medicines + "'" +
                '}';
    }
}
