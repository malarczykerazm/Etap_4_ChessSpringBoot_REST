package com.capgemini.chess.service.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.challenge.ChallengeFindingOpponentsService;
import com.capgemini.chess.service.to.ProfileTO;

@RestController
@RequestMapping(value = "/challenges")
public class ChallengeRestController {

	@Autowired
	ProfileDAO profileDAO;

	@Autowired
	private ChallengeFindingOpponentsService findOpponents;

	@RequestMapping(value = "/findOpponents", method = RequestMethod.GET)
	public List<ProfileTO> findPotentialOpponents(@RequestParam("searcherID") Long searcherID,
			@RequestParam("range") int range) throws UserValidationException {
		return findOpponents.findPotentialOpponents(profileDAO.findByID(searcherID), range);
	}

}