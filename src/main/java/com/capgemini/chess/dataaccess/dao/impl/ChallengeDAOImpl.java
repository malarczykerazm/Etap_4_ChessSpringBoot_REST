package com.capgemini.chess.dataaccess.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.dataaccess.dao.ChallengeDAO;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.mapper.ChallengeMapper;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

@Repository
@Scope("singleton")
public class ChallengeDAOImpl implements ChallengeDAO {

	private final Map<Long, ChallengeEntity> challenges = new HashMap<>();

	ChallengeDAOImpl() {
		this.initChallenges();
	}

	@Override
	public ChallengeTO save(ChallengeTO tO) {
		ChallengeEntity challenge = ChallengeMapper.map(tO);
		Long iD = generateID();
		challenge.setChallengeID(iD);
		challenges.put(iD, challenge);
		return ChallengeMapper.map(challenge);
	}

	@Override
	public ChallengeTO findByUserProfiles(ProfileTO sender, ProfileTO receiver) {
		return challenges.values().stream().map(ch -> ChallengeMapper.map(ch))
				.filter(ch -> (ch.getSender().equals(sender) && ch.getReceiver().equals(receiver))
						|| (ch.getSender().equals(receiver) && ch.getReceiver().equals(sender)))
				.findFirst().orElse(null);
	}

	@Override
	public List<ChallengeTO> findByOneOfUsers(ProfileTO user) {
		return challenges.values().stream().map(ch -> ChallengeMapper.map(ch))
				.filter(ch -> ch.getSender().equals(user) || ch.getReceiver().equals(user))
				.collect(Collectors.toList());
	}

	private Long generateID() {
		return challenges.keySet().stream().max((i1, i2) -> i1.compareTo(i2)).orElse(0L) + 1;
	}

	private void initChallenges() {
		ProfileTO profile1 = new ProfileTO();
		profile1.setID(1L);
		profile1.setName("Janusz");
		profile1.setSurname("Kowalczyk");
		profile1.setAboutMe("Inżynier.");
		profile1.setLevel(4);

		ProfileTO profile2 = new ProfileTO();
		profile2.setID(2L);
		profile2.setName("Marylin");
		profile2.setSurname("");
		profile2.setAboutMe("art_ysTa");
		profile2.setLevel(9);

		ChallengeTO challenge1 = new ChallengeTO();
		challenge1.setChallengeID(1L);
		challenge1.setSender(profile1);
		challenge1.setReceiver(profile2);
		challenge1.setChallengeStatus(ChallengeStatus.AWAITING);
		challenges.put(1L, ChallengeMapper.map(challenge1));
	}

}
