package com.capgemini.chess.service.rest;

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
@RequestMapping(value = "/")
public class MultiPlayerRestController {

	@Autowired
	private ProfileDAO profileDAO;

	@Autowired
	private MultiPlayerGameSetupAndPointsFacade multiPlayerFacade;

	/**
	 * The method allows a player to find 5 potential opponents within an
	 * assumed level range.
	 * 
	 * @param searcherID
	 *            ID of the searching user.
	 * @param range
	 *            Assumed level range.
	 * @return List of 5 (or less) potential opponent.
	 * @throws UserValidationException
	 *             Thrown in case of not existing ID of the searcher.
	 */
	@RequestMapping(value = "/challenges/findOpponents", method = RequestMethod.GET)
	public List<ProfileTO> findPotentialOpponents(@RequestParam("searcherID") Long searcherID,
			@RequestParam("range") int range) throws UserValidationException {
		return multiPlayerFacade.findPotentialOpponents(profileDAO.findByID(searcherID), range);
	}

	/**
	 * The method initializes a new challenge between provided competitors.
	 * 
	 * @param senderAndReceiver
	 *            1-D board with two Profile Transfer Objects of the sender and
	 *            the receiver.
	 * @return New challenge Transfer Object.
	 * @throws ChallengeValidationException
	 *             Thrown in case of not existing ID of the searcher, not
	 *             existing ID of the receiver or a challenge between those two,
	 *             that is already awaiting.
	 * @throws WrongNumberOfParametersException
	 *             Thrown in case of too long or too short RequestBody
	 *             (ProfileTOs 1-D board).
	 */
	@RequestMapping(value = "/challenges/init", method = RequestMethod.POST)
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

	/**
	 * The method allows to find all awaiting challenges of a particular user.
	 * 
	 * @param searcher
	 *            Searcher's Transfer Object.
	 * @return List of awaiting challenges.
	 */
	@RequestMapping(value = "/challenges/findAwaiting", method = RequestMethod.POST)
	public List<ChallengeTO> findAwaitingChallenges(@RequestBody ProfileTO searcher) {
		return multiPlayerFacade.findAwaitingChallenges(searcher);
	}

	/**
	 * The method allows to check a particular user's profile.
	 * 
	 * @param iD
	 * @return
	 */
	@RequestMapping(value = "/profiles/check", method = RequestMethod.GET)
	public ProfileTO checkProfile(@RequestParam("id") Long iD) {
		return multiPlayerFacade.checkProfile(iD);
	}

}