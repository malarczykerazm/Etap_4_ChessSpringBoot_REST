package com.capgemini.chess.service.challenge.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.challenge.ChallengeCreationService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

@Service
@Scope("singleton")
public class ChallengeCreationServiceImpl implements ChallengeCreationService {

	@Override
	public ChallengeTO create(ProfileTO sender, ProfileTO receiver) {
		ChallengeTO tO = new ChallengeTO();
		tO.setSender(sender);
		tO.setReceiver(receiver);
		tO.setChallengeStatus(ChallengeStatus.AWAITING);
		return tO;
	}

}
