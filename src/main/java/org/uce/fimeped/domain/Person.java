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
 * A Person.
 */
@Entity
@Table(name = "GN_PERSONA")
public class Person implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CODIGO_PER")
    private Long id;
    
    @Column(name = "INSTITUCION_SISTEMA_PER")
    private String systemInstitution;
    
    @Column(name = "UNIDAD_OPERATIVA_PER")
    private String operatingUnit;
    
    @Column(name = "COD_UO_PER")
    private Integer operatingUnitCode;
    
    @Column(name = "TIPO_PER")
    private String personType;
    
    @Column(name = "FACULTAD_PER")
    private String personFaculty;
    
    @Column(name = "CARRERA_PER")
    private String personCareer;
    
    @Column(name = "SEMESTRE_PER")
    private Integer personSemester;
    
    @Column(name = "PRIMER_NOMBRE_PER")
    private String firstName;
    
    @Column(name = "SEGUNDO_NOMBRE_PER")
    private String secondName;
    
    @Column(name = "APELLIDO_PATERNO_PER")
    private String paternalSurname;
    
    @Column(name = "APELLIDO_MATERNO_PER")
    private String maternalSurname;
    
    @Column(name = "CEDULA_PER")
    private String cedula;
    
    @Column(name = "DIRECCION_PER")
    private String address;
    
    @Column(name = "TELEFONO_PER")
    private String phone;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "FECHA_NACIMIENTO_PER")
    private LocalDate birthDate;
    
    @Column(name = "LUGAR_NACIMIENTO_PER")
    private String birthPlace;
    
    @Column(name = "NACIONALIDAD_PER")
    private String nationality;
    
    @Column(name = "EDAD_PER")
    private Integer age;
    
    @Column(name = "SEXO_PER")
    private String gender;
    
    @Column(name = "ESTADO_CIVIL_PER")
    private String maritalStatus;
    
    @Column(name = "INSTRUCCION_ANIO_APROBADO_PER")
    private String approvedInstructionYear;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "FECHA_ADMISION_PER")
    private LocalDate admissionDate;
    
    @Column(name = "OCUPACION_PER")
    private String occupation;
    
    @Column(name = "EMPRESA_TRABAJA_PER")
    private String companyWork;
    
    @Column(name = "TIPO_SEGURO_PER")
    private String insuranceType;
    
    @Column(name = "REFERIDO_PER")
    private String referred;
    
    @Column(name = "PARENTESCO_NOMBRE_PER")
    private String kinshipName;
    
    @Column(name = "PARENTESCO_PER")
    private String kinship;
    
    @Column(name = "DIRECCION_PARENTESCO_PER")
    private String kinshipAddress;
    
    @Column(name = "TELEFONO_PARENTESCO_PER")
    private String kinshipPhone;
    
    @Column(name = "BARRIO_PER")
    private String neighborhood;
    
    @Column(name = "PARROQUIA_PER")
    private String parroquia;
    
    @Column(name = "CANTON_PER")
    private String canton;
    
    @Column(name = "PROVINCIA_PER")
    private String provincia;
    
    @Column(name = "ZONA_PER")
    private String zone;
    
    @Column(name = "GRUPO_CULTURAL_PER")
    private String culturalGroup;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<ClinicHistory> clinicHistorys = new HashSet<>();
    
    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<Reservation> reservations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemInstitution() {
        return systemInstitution;
    }

    public void setSystemInstitution(String systemInstitution) {
        this.systemInstitution = systemInstitution;
    }

    public String getOperatingUnit() {
        return operatingUnit;
    }

    public void setOperatingUnit(String operatingUnit) {
        this.operatingUnit = operatingUnit;
    }

    public Integer getOperatingUnitCode() {
        return operatingUnitCode;
    }

    public void setOperatingUnitCode(Integer operatingUnitCode) {
        this.operatingUnitCode = operatingUnitCode;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonFaculty() {
        return personFaculty;
    }

    public void setPersonFaculty(String personFaculty) {
        this.personFaculty = personFaculty;
    }

    public String getPersonCareer() {
        return personCareer;
    }

    public void setPersonCareer(String personCareer) {
        this.personCareer = personCareer;
    }

    public Integer getPersonSemester() {
        return personSemester;
    }

    public void setPersonSemester(Integer personSemester) {
        this.personSemester = personSemester;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getApprovedInstructionYear() {
        return approvedInstructionYear;
    }

    public void setApprovedInstructionYear(String approvedInstructionYear) {
        this.approvedInstructionYear = approvedInstructionYear;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompanyWork() {
        return companyWork;
    }

    public void setCompanyWork(String companyWork) {
        this.companyWork = companyWork;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getReferred() {
        return referred;
    }

    public void setReferred(String referred) {
        this.referred = referred;
    }

    public String getKinshipName() {
        return kinshipName;
    }

    public void setKinshipName(String kinshipName) {
        this.kinshipName = kinshipName;
    }

    public String getKinship() {
        return kinship;
    }

    public void setKinship(String kinship) {
        this.kinship = kinship;
    }

    public String getKinshipAddress() {
        return kinshipAddress;
    }

    public void setKinshipAddress(String kinshipAddress) {
        this.kinshipAddress = kinshipAddress;
    }

    public String getKinshipPhone() {
        return kinshipPhone;
    }

    public void setKinshipPhone(String kinshipPhone) {
        this.kinshipPhone = kinshipPhone;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCulturalGroup() {
        return culturalGroup;
    }

    public void setCulturalGroup(String culturalGroup) {
        this.culturalGroup = culturalGroup;
    }

    public Set<ClinicHistory> getClinicHistorys() {
        return clinicHistorys;
    }

    public void setClinicHistorys(Set<ClinicHistory> clinicHistorys) {
        this.clinicHistorys = clinicHistorys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if ( ! Objects.equals(id, person.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", systemInstitution='" + systemInstitution + "'" +
                ", operatingUnit='" + operatingUnit + "'" +
                ", operatingUnitCode='" + operatingUnitCode + "'" +
                ", personType='" + personType + "'" +
                ", personFaculty='" + personFaculty + "'" +
                ", personCareer='" + personCareer + "'" +
                ", personSemester='" + personSemester + "'" +
                ", firstName='" + firstName + "'" +
                ", secondName='" + secondName + "'" +
                ", paternalSurname='" + paternalSurname + "'" +
                ", maternalSurname='" + maternalSurname + "'" +
                ", cedula='" + cedula + "'" +
                ", address='" + address + "'" +
                ", phone='" + phone + "'" +
                ", birthDate='" + birthDate + "'" +
                ", birthPlace='" + birthPlace + "'" +
                ", nationality='" + nationality + "'" +
                ", age='" + age + "'" +
                ", gender='" + gender + "'" +
                ", maritalStatus='" + maritalStatus + "'" +
                ", approvedInstructionYear='" + approvedInstructionYear + "'" +
                ", admissionDate='" + admissionDate + "'" +
                ", occupation='" + occupation + "'" +
                ", companyWork='" + companyWork + "'" +
                ", insuranceType='" + insuranceType + "'" +
                ", referred='" + referred + "'" +
                ", kinshipName='" + kinshipName + "'" +
                ", kinship='" + kinship + "'" +
                ", kinshipAddress='" + kinshipAddress + "'" +
                ", kinshipPhone='" + kinshipPhone + "'" +
                ", neighborhood='" + neighborhood + "'" +
                ", parroquia='" + parroquia + "'" +
                ", canton='" + canton + "'" +
                ", provincia='" + provincia + "'" +
                ", zone='" + zone + "'" +
                ", culturalGroup='" + culturalGroup + "'" +
                '}';
    }
}
