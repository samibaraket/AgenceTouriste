package com.fr.adaming.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fr.adaming.dto.LogementCreateDTO;
import com.fr.adaming.entity.Logement;
import com.fr.adaming.enumeration.typeLogEnum;
import com.fr.adaming.service.ILogementService;

@RestController
@RequestMapping(path = "api/")
public class LogementRestController {

	// Methode CRUD Logement
	@Autowired
	private ILogementService logementService;

	@RequestMapping(path = "logement", method = RequestMethod.POST)
	public String createLogement(@RequestBody LogementCreateDTO logement) {
		logementService.createLogement(new Logement(logement.getPrestaLog(), logement.getNom(), logement.getVille(),
				logement.getPrix(), logement.getTypeLog(), logement.getPension(), logement.getQualite()));
		return logement.getNom() + " est un logement cree";
	}

	@RequestMapping(path = "logement", method = RequestMethod.PUT)
	public String updateLogement(@RequestBody Logement logement) {
		logementService.updateLogement(logement);
		return logement.getNom() + " est un logement modifie";
	}

	@RequestMapping(path = "logement", method = RequestMethod.GET)
	public List<Logement> readAllLogement() {
		List<Logement> listLogement = logementService.readAllLogement();
		return listLogement;
	}

	@RequestMapping(path = "logement/{id}", method = RequestMethod.GET)
	public String readByIdLogement(@PathVariable Long id) {
		Logement logement = logementService.readLogementById(id);
		return "Logement : " + logement;
	}

	@RequestMapping(path = "logement/{id}", method = RequestMethod.DELETE)
	public String deleteLogement(@PathVariable Long id) {
		Logement logement = logementService.readLogementById(id);
		logementService.deleteLogementById(id);
		return logement.getNom() + " a correctement ete supprime";
	}

	@RequestMapping(path = "logement/{prestaLog}", method = RequestMethod.GET)
	public String readLogementByPresta(@PathVariable String prestaLog) {
		List<Logement> listlogement = logementService.readLogementByPrestaLog(prestaLog);
		return "Logement(s) fourni(s) par " + prestaLog + " :\n" + listlogement;
	}

	@RequestMapping(path = "logement/{ville}", method = RequestMethod.GET)
	public String readByVille(@PathVariable String ville) {
		List<Logement> listLogement = logementService.readByVille(ville);
		return "Pour la ville de " + ville + ", voici le(s) logement(s) : \n" + listLogement;
	}

	@RequestMapping(path = "logement/{typeLog}", method = RequestMethod.GET)
	public String readByTypeOfLogement(@PathVariable typeLogEnum type) {
		List<Logement> listLogement = logementService.readByTypeLog(type);
		return "Pour le type " + type + ", voici le(s) logement(s) : \n" + listLogement;
	}

	@RequestMapping(path = "logement/{prixLog}", method = RequestMethod.GET)
	public String readByPrixLogement(@PathVariable Double prix) {
		List<Logement> listLogement = logementService.readByPrixLogement(prix);
		return "Pour la modique somme de " + prix + ", voici le(s) logement(s) : \n" + listLogement;
	}

}
