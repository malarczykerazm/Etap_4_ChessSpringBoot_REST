package com.capgemini.chess.service.challenge;

import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeValidationService {

	void validateChallenge(ChallengeTO tO) throws ChallengeValidationException;

	boolean isPotentialChallengeUnique(Long senderID, Long recieverID);

}
