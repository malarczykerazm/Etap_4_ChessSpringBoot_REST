package com.capgemini.chess.service.challenge.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.challenge.ChallengeCreationService;
import com.capgemini.chess.service.challenge.ChallengeInitiationService;
import com.capgemini.chess.service.challenge.ChallengeSaveService;
import com.capgemini.chess.service.challenge.ChallengeValidationService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

@Service
@Scope("singleton")
public class ChallengeInitiationServiceImpl implements ChallengeInitiationService {

	@Autowired
	ChallengeCreationService challengeCreationService;

	@Autowired
	ChallengeSaveService challengeSaveService;

	@Autowired
	ChallengeValidationService challengeValidation;

	@Override
	public ChallengeTO init(ProfileTO sender, ProfileTO receiver) throws ChallengeValidationException {
		ChallengeTO challenge = challengeCreationService.create(sender, receiver);
		challengeValidation.validateChallenge(challenge);
		return challengeSaveService.save(challenge);
	}

}
