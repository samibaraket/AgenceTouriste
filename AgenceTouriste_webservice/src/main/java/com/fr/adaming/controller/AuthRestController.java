package com.fr.adaming.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fr.adaming.dto.LoginDTO;
import com.fr.adaming.dto.RegisterDTO;
import com.fr.adaming.entity.User;
import com.fr.adaming.service.IUserService;

/**
 * @author Mohamed EL AGREBI
 * @author Thomas S
 */
@RestController
@RequestMapping(path = "auth/")
public class AuthRestController implements IAuthRestController {

	@Autowired
	private IUserService<User> userService;

	/**
	 * @param logDto prend les caracteristiques d un objet User necessaires a la
	 *               connexion
	 * @return String attestant de la connexion + identite de l utilisateur connecte
	 */
	@PostMapping(path = "login")
	public String loginRest(@Valid @RequestBody LoginDTO logDto) {
		User user = new User();
		user.setEmail(logDto.getEmail());
		user.setPwd(logDto.getPwd());
		user = userService.login(user.getEmail(), user.getPwd());
		return "Bonjour " + user.getPrenom() + " " + user.getNom() + ", vous etes bien connecte";
	}

	/**
	 * @param regDto prend les caracteristiques d un objet User necessaires a l
	 *               isncription
	 * @return String attestant de l inscription + identite de l utilisateur inscrit
	 */
	@PostMapping(path = "register")
	public String register(@Valid @RequestBody RegisterDTO regDto) {
		User user = new User(regDto.getNom(), regDto.getPrenom(), regDto.getEmail(), regDto.getPwd());
		User user1 = userService.register(user);
		return " " + user1.getPrenom() + " " + user1.getNom() + " a ete correctement enregistre(e) ";
	}

}
