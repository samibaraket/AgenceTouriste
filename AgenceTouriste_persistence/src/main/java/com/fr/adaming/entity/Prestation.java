package com.fr.adaming.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Prestation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date debutPresta;
	private Date finPresta;
	private String villeDepartArrivee;
	private String destination;
	private int nbPersonnes;
	private float commission;
	private String avis;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="transport")
	private List<Transport> transport;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="logement")
	private List<Logement> logement;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="activite")
	private List<Activite> activite;

	public Prestation(Date debutPresta, Date finPresta, String villeDepartArrivee, String destination, int nbPersonnes,
			float commission, String avis) {
		super();
		this.debutPresta = debutPresta;
		this.finPresta = finPresta;
		this.villeDepartArrivee = villeDepartArrivee;
		this.destination = destination;
		this.nbPersonnes = nbPersonnes;
		this.commission = commission;
		this.avis = avis;
	}

}
