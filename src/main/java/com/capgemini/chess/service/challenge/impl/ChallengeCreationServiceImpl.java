package com.capgemini.chess.service.challenge.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.challenge.ChallengeCreationService;
import com.capgemini.chess.service.to.ChallengeTO;

@Service
@Scope("singleton")
public class ChallengeCreationServiceImpl implements ChallengeCreationService {

	@Override
	public ChallengeTO create(Long senderID, Long recieverID) {
		ChallengeTO tO = new ChallengeTO();
		tO.setSenderID(senderID);
		tO.setRecieverID(recieverID);
		tO.setChallengeStatus(ChallengeStatus.AWAITING);
		return tO;
	}

}
