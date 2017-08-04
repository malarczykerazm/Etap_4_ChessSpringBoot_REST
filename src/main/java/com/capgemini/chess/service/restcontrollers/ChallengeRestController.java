package com.capgemini.chess.service.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.exceptions.WrongNumberOfParametersException;
import com.capgemini.chess.service.facade.MultiPlayerGameSetupAndPointsFacade;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

@RestController
@RequestMapping(value = "/challenges")
public class ChallengeRestController {

	@Autowired
	private ProfileDAO profileDAO;

	@Autowired
	private MultiPlayerGameSetupAndPointsFacade multiPlayerFacade;

	@RequestMapping(value = "/findOpponents", method = RequestMethod.GET)
	public List<ProfileTO> findPotentialOpponents(@RequestParam("searcherID") Long searcherID,
			@RequestParam("range") int range) throws UserValidationException {
		return multiPlayerFacade.findPotentialOpponents(profileDAO.findByID(searcherID), range);
	}

	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public ChallengeTO initNewChallenge(@RequestBody ProfileTO[] senderAndReceiver)
			throws ChallengeValidationException, WrongNumberOfParametersException {
		if (senderAndReceiver.length > 2) {
			throw new WrongNumberOfParametersException("To many parameters provided. Two ProfileTOs expected.");
		}
		if (senderAndReceiver.length < 2) {
			throw new WrongNumberOfParametersException("To few parameters provided. Two ProfileTOs expected.");
		}
		return multiPlayerFacade.initNewChallenge(senderAndReceiver[0], senderAndReceiver[1]);
	}
}