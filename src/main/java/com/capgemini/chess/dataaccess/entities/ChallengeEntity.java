package com.capgemini.chess.dataaccess.entities;

import com.capgemini.chess.enums.ChallengeStatus;

public class ChallengeEntity {

	private Long challengeID;
	private Long senderID;
	private Long recieverID;
	private ChallengeStatus challengeStatus;

	public Long getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(Long challengeID) {
		this.challengeID = challengeID;
	}

	public Long getSenderID() {
		return senderID;
	}

	public void setSenderID(Long senderID) {
		this.senderID = senderID;
	}

	public Long getRecieverID() {
		return recieverID;
	}

	public void setRecieverID(Long recieverID) {
		this.recieverID = recieverID;
	}

	public ChallengeStatus getChallengeStatus() {
		return challengeStatus;
	}

	public void setChallengeStatus(ChallengeStatus challengeStatus) {
		this.challengeStatus = challengeStatus;
	}

}
