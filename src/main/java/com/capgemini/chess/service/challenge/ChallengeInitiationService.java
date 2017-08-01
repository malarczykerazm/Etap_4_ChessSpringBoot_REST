package com.capgemini.chess.service.challenge;

import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeInitiationService {

	ChallengeTO init(Long senderID, Long recieverID) throws ChallengeValidationException;

}
