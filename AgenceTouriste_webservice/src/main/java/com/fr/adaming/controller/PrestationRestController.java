package com.fr.adaming.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fr.adaming.dto.PrestationCreateDTO;
import com.fr.adaming.entity.Prestation;
import com.fr.adaming.service.IProduitService;

/**
 * @author Mohamed
 * @author Thomas S
 */
@RestController
@RequestMapping(path = "api/")
public class PrestationRestController implements IPrestationRestController {

	@Autowired
	private IProduitService<Prestation> prestaService;
	private static final String FORMATDATE = "dd/MM/yyyy";

	/**
	 * @param dtoPresta dto prestation
	 * @return String + nom presta
	 * @throws ParseException parseException
	 */
	@PostMapping(path = "prestation")
	public String createPrestation(@RequestBody PrestationCreateDTO dtoPresta) throws ParseException {
		prestaService.createPrestation(new Prestation(dtoPresta.getNom(),
				new SimpleDateFormat(FORMATDATE).parse(dtoPresta.getDebutPresta()),
				new SimpleDateFormat(FORMATDATE).parse(dtoPresta.getFinPresta()), dtoPresta.getVilleDepartArrivee(),
				dtoPresta.getDestination(), dtoPresta.getNbPersonnesMax(), dtoPresta.getCommission()));
		return "Prestation creee : " + dtoPresta.getNom();
	}

	/**
	 * @param presta un objet prestation
	 * @return String + l'objet prestation
	 */
	@PutMapping(path = "prestation")
	public String updatePrestation(@RequestBody Prestation presta) {
		presta = prestaService.updatePrestation(presta);
		return "Prestation modifiee : " + presta;
	}

	/**
	 * @param id id de la prestation
	 * @return nom de la presta + String
	 */
	@DeleteMapping(path = "prestation/{id}")
	public String deletePrestation(@PathVariable Long id) {
		Prestation presta = prestaService.readPrestationById(id);
		prestaService.deletePrestationById(id);
		return presta.getNom() + " a ete supprimee";
	}

	/**
	 * @return liste de prestations
	 */
	@GetMapping(path = "prestation")
	public List<Prestation> readAll() {
		return prestaService.readAllPrestation();
	}

	/**
	 * @param id id de la prestation
	 * @return String + prestation
	 */
	@GetMapping(path = "prestation/{idPresta}")
	public String readById(@PathVariable Long id) {
		Prestation presta = prestaService.readPrestationById(id);
		return "Prestation : " + presta;
	}

	/**
	 * @param debut date de debut de la presta
	 * @param fin   date de fin de la presta
	 * @return String + liste de prestation
	 */
	@GetMapping(path = "prestation/{debut}/conf/{fin}")
	public String readByDatesDePresta(@PathVariable("debut") Date debut, @PathVariable("fin") Date fin) {
		List<Prestation> listPrestation = prestaService.readByDebutPrestaAndFinPresta(debut, fin);
		return "Pour des dates comprises entre " + debut + " et " + fin + ", voici le(s) prestation(s) : \n"
				+ listPrestation;
	}

	/**
	 * @param villeResidence ville de residence
	 * @param destination    ville de destination
	 * @return String + liste de prestations
	 */
	@GetMapping(path = "prestation/{villeResidence}/conf/{destination}")
	public String readByResidenceAndDestinationPresta(@PathVariable("villeResidence") String villeResidence,
			@PathVariable("destination") String destination) {
		List<Prestation> listPrestation = prestaService.readByVilleDepartArriveeAndDestination(villeResidence,
				destination);
		return "Pour votre ville de residence " + villeResidence + " pour " + destination
				+ ", voici le(s) prestation(s) : \n" + listPrestation;
	}

	/**
	 * @param dtoPresta dto presta
	 * @return le prix total combine entre le prix transport + logement + activite
	 *         et en comptant la commission de l'agence
	 * @throws ParseException parseException
	 */
	@PostMapping(path = "prestation/{presta}")
	public String calculPrixTotal(@RequestBody PrestationCreateDTO dtoPresta) throws ParseException {
		Prestation p = prestaService
				.readByDebutPrestaAndFinPresta(new SimpleDateFormat(FORMATDATE).parse(dtoPresta.getDebutPresta()),
						new SimpleDateFormat(FORMATDATE).parse(dtoPresta.getFinPresta()))
				.get(0);
		prestaService.calculPrixTotal(p);
		return Double.toString(p.getPrixTotal());
	}
}
