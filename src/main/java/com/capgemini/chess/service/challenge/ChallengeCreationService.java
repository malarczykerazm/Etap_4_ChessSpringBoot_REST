package com.capgemini.chess.service.challenge;

import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeCreationService {

	ChallengeTO create(Long senderID, Long recieverID);

}
