package com.capgemini.chess.service.challenge.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.ChallengeDAO;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.challenge.ChallengeSearchService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

@Service
@Scope("singleton")
public class ChallengesSearchServiceImpl implements ChallengeSearchService {

	@Autowired
	ChallengeDAO challengeDAO;

	@Override
	public List<ChallengeTO> findAwaitingChallenges(ProfileTO searcher) {
		return challengeDAO.findByOneOfUsers(searcher).stream()
				.filter(ch -> ch.getChallengeStatus() == ChallengeStatus.AWAITING).collect(Collectors.toList());
	}

}
