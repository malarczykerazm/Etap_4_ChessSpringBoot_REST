package com.capgemini.chess.service.challengeserviecestests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.chess.dataaccess.dao.ChallengeDAO;
import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.challenge.ChallengeCreationService;
import com.capgemini.chess.service.challenge.ChallengeValidationService;
import com.capgemini.chess.service.challenge.impl.ChallengeCreationServiceImpl;
import com.capgemini.chess.service.challenge.impl.ChallengeFindingOpponentsServiceImpl;
import com.capgemini.chess.service.challenge.impl.ChallengeValidationServiceImpl;
import com.capgemini.chess.service.challenge.impl.ChallengesSearchServiceImpl;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;
import com.capgemini.chess.service.user.UserValidationService;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeServiecesTests {

	private Long senderID = 8L;
	private Long receiverID = 5L;
	private ChallengeTO tO;
	private ChallengeCreationService challengeCreation;

	private ChallengeTO challenge1;
	private ChallengeTO challenge2;
	private ChallengeTO challenge3;
	private ChallengeTO challenge4;
	private ChallengeTO challenge5;
	private List<ChallengeTO> challengesList;

	private ProfileTO profile1;
	private ProfileTO profile2;
	private ProfileTO profile3;
	private ProfileTO profile4;
	private ProfileTO profile5;
	private ProfileTO profile6;
	private ProfileTO profile7;
	private List<ProfileTO> profilesList;

	@Mock
	private ChallengeDAO challengeDAO;

	@Mock
	private UserValidationService userValidation;

	@Mock
	private ProfileDAO profileDAO;

	@Mock
	private UserDAO userDAO;

	@Mock
	private ChallengeValidationService challengeValidation;

	@InjectMocks
	private ChallengesSearchServiceImpl challengeSearchImpl;

	@InjectMocks
	private ChallengeFindingOpponentsServiceImpl challengeFindingOpponentsImpl;

	@InjectMocks
	private ChallengeValidationServiceImpl challengeValidationImpl;

	@Rule
	public ExpectedException e = ExpectedException.none();

	@Before
	public void challengeListSetup() {
		challenge1 = new ChallengeTO();
		challenge1.setChallengeID(1L);
		ProfileTO sender1 = new ProfileTO();
		sender1.setID(8L);
		ProfileTO receiver1 = new ProfileTO();
		receiver1.setID(20L);
		challenge1.setSender(sender1);
		challenge1.setReceiver(receiver1);
		challenge1.setChallengeStatus(ChallengeStatus.REJECTED);

		challenge2 = new ChallengeTO();
		challenge2.setChallengeID(2L);
		ProfileTO sender2 = new ProfileTO();
		sender1.setID(20L);
		ProfileTO receiver2 = new ProfileTO();
		receiver1.setID(9L);
		challenge2.setSender(sender2);
		challenge2.setReceiver(receiver2);
		challenge2.setChallengeStatus(ChallengeStatus.ACCEPTED);

		challenge3 = new ChallengeTO();
		challenge3.setChallengeID(3L);
		ProfileTO sender3 = new ProfileTO();
		sender1.setID(20L);
		ProfileTO receiver3 = new ProfileTO();
		receiver1.setID(1L);
		challenge3.setSender(sender3);
		challenge3.setReceiver(receiver3);
		challenge3.setChallengeStatus(ChallengeStatus.AWAITING);

		challenge4 = new ChallengeTO();
		challenge4.setChallengeID(4L);
		ProfileTO sender4 = new ProfileTO();
		sender1.setID(10L);
		ProfileTO receiver4 = new ProfileTO();
		receiver1.setID(20L);
		challenge4.setSender(sender4);
		challenge4.setReceiver(receiver4);
		challenge4.setChallengeStatus(ChallengeStatus.REJECTED);

		challenge5 = new ChallengeTO();
		challenge5.setChallengeID(5L);
		ProfileTO sender5 = new ProfileTO();
		sender1.setID(5L);
		ProfileTO receiver5 = new ProfileTO();
		receiver1.setID(20L);
		challenge5.setSender(sender5);
		challenge5.setReceiver(receiver5);
		challenge5.setChallengeStatus(ChallengeStatus.AWAITING);

		challengesList = new ArrayList<>();
		challengesList.add(challenge1);
		challengesList.add(challenge2);
		challengesList.add(challenge3);
		challengesList.add(challenge4);
		challengesList.add(challenge5);
	}

	@Before
	public void profilesListSetup() {
		profile1 = new ProfileTO();
		profile1.setID(1L);
		profile1.setLevel(4);

		profile2 = new ProfileTO();
		profile2.setID(2L);
		profile2.setLevel(1);

		profile3 = new ProfileTO();
		profile3.setID(3L);
		profile3.setLevel(7);

		profile4 = new ProfileTO();
		profile4.setID(4L);
		profile4.setLevel(0);

		profile5 = new ProfileTO();
		profile5.setID(5L);
		profile5.setLevel(5);

		profile6 = new ProfileTO();
		profile6.setID(6L);
		profile6.setLevel(2);

		profile7 = new ProfileTO();
		profile7.setID(7L);
		profile7.setLevel(8);

		profilesList = new ArrayList<>();
	}

	@Test
	public void shouldCreateNewChallengeWithoutAnID() {
		// given
		challengeCreation = new ChallengeCreationServiceImpl();
		ProfileTO sender = new ProfileTO();
		sender.setID(1L);
		ProfileTO receiver = new ProfileTO();
		receiver.setID(2L);

		// when
		tO = challengeCreation.create(sender, receiver);

		// then
		Assert.assertEquals(new Long(1), tO.getSender().getID());
		Assert.assertEquals(new Long(2), tO.getReceiver().getID());
		Assert.assertEquals(ChallengeStatus.AWAITING, tO.getChallengeStatus());
		Assert.assertEquals(null, tO.getChallengeID());
	}

	@Test
	public void shouldReturnListOfAwaitingChallanges() {

		// given
		List<ChallengeTO> expectedList = new ArrayList<>();
		expectedList.add(challenge3);
		expectedList.add(challenge5);
		ProfileTO searcher = new ProfileTO();
		searcher.setID(20L);

		// when
		Mockito.when(challengeDAO.findByOneOfUsers(searcher)).thenReturn(challengesList);

		// then
		Assert.assertEquals(expectedList, challengeSearchImpl.findAwaitingChallenges(searcher));
		Mockito.verify(challengeDAO).findByOneOfUsers(searcher);
	}

	@Test
	public void shouldFindFiveOpponents() throws UserValidationException {
		// given
		int level = 5;
		int levelRange = 4;
		senderID = 20L;
		List<ProfileTO> expectedList = new ArrayList<>();
		expectedList.add(profile1);
		expectedList.add(profile2);
		expectedList.add(profile3);
		expectedList.add(profile4);
		expectedList.add(profile5);

		ProfileTO tO = new ProfileTO();
		tO.setLevel(level);
		tO.setID(senderID);

		Mockito.when(profileDAO.findProfilesWithinLevelRange(level, levelRange)).thenReturn(profilesList);
		Mockito.doNothing().when(userValidation).validateID(tO.getID());
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile1)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile2)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile3)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile4)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile5)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile6)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile7)).thenReturn(true);

		// when
		profilesList.add(profile1);
		profilesList.add(profile2);
		profilesList.add(profile3);
		profilesList.add(profile4);
		profilesList.add(profile5);
		profilesList.add(profile6);
		profilesList.add(profile7);

		// then
		Assert.assertEquals(expectedList, challengeFindingOpponentsImpl.findPotentialOpponents(tO, levelRange));
		Mockito.verify(profileDAO).findProfilesWithinLevelRange(level, levelRange);
		Mockito.verify(userValidation).validateID(tO.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile1);
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile2);
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile3);
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile4);
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile5);
		Mockito.verify(challengeValidation, Mockito.never()).isPotentialChallengeUnique(tO, profile6);
		Mockito.verify(challengeValidation, Mockito.never()).isPotentialChallengeUnique(tO, profile7);
	}

	@Test
	public void shouldFindThreeOpponents() throws UserValidationException {
		// given
		int level = 5;
		int levelRange = 4;
		senderID = 20L;
		List<ProfileTO> expectedList = new ArrayList<>();
		expectedList.add(profile1);
		expectedList.add(profile2);
		expectedList.add(profile3);

		ProfileTO tO = new ProfileTO();
		tO.setLevel(level);
		tO.setID(senderID);

		Mockito.when(profileDAO.findProfilesWithinLevelRange(level, levelRange)).thenReturn(profilesList);
		Mockito.doNothing().when(userValidation).validateID(tO.getID());
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile1)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile2)).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO, profile3)).thenReturn(true);

		// when
		profilesList.add(profile1);
		profilesList.add(profile2);
		profilesList.add(profile3);
		profilesList.add(profile4);
		profilesList.add(profile5);
		profilesList.add(profile6);
		profilesList.add(profile7);

		// then
		Assert.assertEquals(expectedList, challengeFindingOpponentsImpl.findPotentialOpponents(tO, levelRange));
		Mockito.verify(profileDAO).findProfilesWithinLevelRange(level, levelRange);
		Mockito.verify(userValidation).validateID(tO.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile1);
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile2);
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO, profile3);
	}

	@Test
	public void shouldValidateChallenge() throws ChallengeValidationException {
		// given
		ProfileTO sender = new ProfileTO();
		sender.setID(senderID);
		ProfileTO receiver = new ProfileTO();
		receiver.setID(receiverID);
		ChallengeTO tO = new ChallengeTO();
		tO.setSender(sender);
		tO.setReceiver(receiver);

		ChallengeTO notAwaitingChallenge = new ChallengeTO();
		notAwaitingChallenge.setChallengeStatus(ChallengeStatus.ACCEPTED);

		Mockito.when(profileDAO.findByID(tO.getSender().getID())).thenReturn(new ProfileTO());
		Mockito.when(profileDAO.findByID(tO.getReceiver().getID())).thenReturn(new ProfileTO());
		Mockito.when(challengeDAO.findByUserProfiles(tO.getSender(), tO.getReceiver())).thenReturn(notAwaitingChallenge);

		// when
		challengeValidationImpl.validateChallenge(tO);

		// then
		Mockito.verify(profileDAO).findByID(tO.getSender().getID());
		Mockito.verify(profileDAO).findByID(tO.getReceiver().getID());
		Mockito.verify(challengeDAO).findByUserProfiles(tO.getSender(), tO.getReceiver());
		// NO EXCEPTION
	}

	@Test
	public void shouldNotValidateChallengeDueToNoSenderID() throws ChallengeValidationException {
		// given
		ProfileTO sender = new ProfileTO();
		sender.setID(senderID);
		ProfileTO receiver = new ProfileTO();
		receiver.setID(receiverID);
		ChallengeTO tO = new ChallengeTO();
		tO.setSender(sender);
		tO.setReceiver(receiver);

		ChallengeTO notAwaitingChallenge = new ChallengeTO();
		notAwaitingChallenge.setChallengeStatus(ChallengeStatus.ACCEPTED);

		Mockito.when(profileDAO.findByID(tO.getSender().getID())).thenReturn(null);
		Mockito.when(profileDAO.findByID(tO.getReceiver().getID())).thenReturn(new ProfileTO());
		Mockito.when(challengeDAO.findByUserProfiles(tO.getSender(), tO.getReceiver())).thenReturn(notAwaitingChallenge);

		// expect
		e.expect(ChallengeValidationException.class);
		e.expectMessage("sender");

		// when
		challengeValidationImpl.validateChallenge(tO);

		// then
		// EXCEPTION
	}

	@Test
	public void shouldNotValidateChallengeDueToNoRecieverID() throws ChallengeValidationException {
		// given
		ProfileTO sender = new ProfileTO();
		sender.setID(senderID);
		ProfileTO receiver = new ProfileTO();
		receiver.setID(receiverID);
		ChallengeTO tO = new ChallengeTO();
		tO.setSender(sender);
		tO.setReceiver(receiver);

		ChallengeTO notAwaitingChallenge = new ChallengeTO();

		notAwaitingChallenge.setChallengeStatus(ChallengeStatus.ACCEPTED);
		Mockito.when(profileDAO.findByID(tO.getSender().getID())).thenReturn(new ProfileTO());
		Mockito.when(profileDAO.findByID(tO.getReceiver().getID())).thenReturn(null);
		Mockito.when(challengeDAO.findByUserProfiles(tO.getSender(), tO.getReceiver())).thenReturn(notAwaitingChallenge);

		// expect
		e.expect(ChallengeValidationException.class);
		e.expectMessage("reciever");

		// when
		challengeValidationImpl.validateChallenge(tO);

		// then
		// EXCEPTION
	}

	@Test
	public void shouldNotValidateChallengeDueToUnuniqueChallenge() throws ChallengeValidationException {
		// given
		ProfileTO sender = new ProfileTO();
		sender.setID(senderID);
		ProfileTO receiver = new ProfileTO();
		receiver.setID(receiverID);
		ChallengeTO tO = new ChallengeTO();
		tO.setSender(sender);
		tO.setReceiver(receiver);
		;

		ChallengeTO awaitingChallenge = new ChallengeTO();

		awaitingChallenge.setChallengeStatus(ChallengeStatus.AWAITING);
		Mockito.when(profileDAO.findByID(tO.getSender().getID())).thenReturn(new ProfileTO());
		Mockito.when(profileDAO.findByID(tO.getReceiver().getID())).thenReturn(new ProfileTO());
		Mockito.when(challengeDAO.findByUserProfiles(tO.getSender(), tO.getReceiver())).thenReturn(awaitingChallenge);

		// expect
		e.expect(ChallengeValidationException.class);
		e.expectMessage("challenge is already awaiting");

		// when
		challengeValidationImpl.validateChallenge(tO);

		// then
		// EXCEPTION
	}

}
