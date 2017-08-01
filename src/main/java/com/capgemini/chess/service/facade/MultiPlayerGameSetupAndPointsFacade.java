package com.capgemini.chess.service.facade;

import java.util.List;

import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;
/**
 * The class allows a player to find 5 potential oppenents within an assumed level range,
 * initiate a new challenge with a chosen user, find challenges of a user that are awaiting
 * and check a chosen user's profile.
 * @author EMALARCZ
 *
 */
public interface MultiPlayerGameSetupAndPointsFacade {

	/**
	 * The method allows a player to find 5 potential opponents within an assumed level range.
	 * @param tO Profile Transfer Object of the searching user.
	 * @param range Assumed level range.
	 * @return List of 5 potential opponent.
	 * @throws UserValidationException Thrown in case of not existing ID of the searcher.
	 */
	List<ProfileTO> findPotentialOpponents(ProfileTO tO, int range) throws UserValidationException;
	
	/**
	 * The method allows to initiate a new challenge with a chosen user.
	 * @param senderID ID of the sender of the challenge.
	 * @param recieverID ID of the receiver of the challenge.
	 * @return New challenge Transfer Object.
	 * @throws ChallengeValidationException Thrown in case of not existing ID of the searcher,
	 * .not existing ID of the receiver or a challenge between those two, that is already awaiting.
	 */
	ChallengeTO initNewChallenge(Long senderID, Long recieverID) throws ChallengeValidationException;
	
	/**
	 * The method allows to find all awaiting challenges of a particular user.
	 * @param searcherID ID of the user.
	 * @return List of awaiting challenges.
	 */
	List<ChallengeTO> findAwaitingChallenges(Long searcherID);
	
	/**
	 * The method allows to check a particular user's profile.
	 * @param iD ID of the user.
	 * @return Profile Transport Object of the user.
	 */
	ProfileTO checkProfile(Long iD);
	
}
