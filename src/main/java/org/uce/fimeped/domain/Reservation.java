package org.uce.fimeped.domain;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.uce.fimeped.domain.util.CustomLocalDateSerializer;
import org.uce.fimeped.domain.util.ISO8601LocalDateDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "TN_RESERVACION")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CODIGO_RES")
	private long id;

	@ManyToOne
	@JoinColumn(name = "CODIGO_PER")
	private Person person;

	@Column(name = "CODIGO_USW")
	private Integer user;

	@Column(name = "CODIGO_ESP")
	private Integer especialty;

	@Column(name = "CODIGO_ETR")
	private Integer status;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
	@Column(name = "FECHA_RES")
	private LocalDate date;

	@Column(name = "NUM_TURNO_RES")
	private int turn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getEspecialty() {
		return especialty;
	}

	public void setEspecialty(Integer especialty) {
		this.especialty = especialty;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
}
