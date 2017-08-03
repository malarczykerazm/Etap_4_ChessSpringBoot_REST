package com.capgemini.chess.dataaccess.entities;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.to.ProfileTO;

public class ChallengeEntity {

	private Long challengeID;
	private ProfileTO sender;
	private ProfileTO receiver;
	private ChallengeStatus challengeStatus;

	public Long getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(Long challengeID) {
		this.challengeID = challengeID;
	}

	public ProfileTO getSender() {
		return sender;
	}

	public void setSender(ProfileTO sender) {
		this.sender = sender;
	}

	public ProfileTO getReceiver() {
		return receiver;
	}

	public void setReciever(ProfileTO receiver) {
		this.receiver = receiver;
	}

	public ChallengeStatus getChallengeStatus() {
		return challengeStatus;
	}

	public void setChallengeStatus(ChallengeStatus challengeStatus) {
		this.challengeStatus = challengeStatus;
	}

}
