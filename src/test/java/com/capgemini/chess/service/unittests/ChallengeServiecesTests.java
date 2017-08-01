package com.capgemini.chess.service.unittests;

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
	private Long recieverID = 5L;
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
		challenge1.setSenderID(8L);
		challenge1.setRecieverID(20L);
		challenge1.setChallengeStatus(ChallengeStatus.REJECTED);

		challenge2 = new ChallengeTO();
		challenge2.setChallengeID(2L);
		challenge2.setSenderID(20L);
		challenge2.setRecieverID(9L);
		challenge2.setChallengeStatus(ChallengeStatus.ACCEPTED);

		challenge3 = new ChallengeTO();
		challenge3.setChallengeID(3L);
		challenge3.setSenderID(20L);
		challenge3.setRecieverID(1L);
		challenge3.setChallengeStatus(ChallengeStatus.AWAITING);

		challenge4 = new ChallengeTO();
		challenge4.setChallengeID(4L);
		challenge4.setSenderID(10L);
		challenge4.setRecieverID(20L);
		challenge4.setChallengeStatus(ChallengeStatus.REJECTED);

		challenge5 = new ChallengeTO();
		challenge5.setChallengeID(5L);
		challenge5.setSenderID(5L);
		challenge5.setRecieverID(20L);
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
		senderID = new Long(1);
		recieverID = new Long(2);

		// when
		tO = challengeCreation.create(senderID, recieverID);

		// then
		Assert.assertEquals(new Long(1), tO.getSenderID());
		Assert.assertEquals(new Long(2), tO.getRecieverID());
		Assert.assertEquals(ChallengeStatus.AWAITING, tO.getChallengeStatus());
		Assert.assertEquals(null, tO.getChallengeID());
	}

	@Test
	public void shouldReturnListOfAwaitingChallanges() {

		// given
		List<ChallengeTO> expectedList = new ArrayList<>();
		expectedList.add(challenge3);
		expectedList.add(challenge5);
		Long searcherID = 20L;

		// when
		Mockito.when(challengeDAO.findByOneOfUsersID(searcherID)).thenReturn(challengesList);

		// then
		Assert.assertEquals(expectedList, challengeSearchImpl.findAwaitingChallenges(searcherID));
		Mockito.verify(challengeDAO).findByOneOfUsersID(searcherID);
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
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile1.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile2.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile3.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile4.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile5.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile6.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile7.getID())).thenReturn(true);

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
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile1.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile2.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile3.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile4.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile5.getID());
		Mockito.verify(challengeValidation, Mockito.never()).isPotentialChallengeUnique(tO.getID(), profile6.getID());
		Mockito.verify(challengeValidation, Mockito.never()).isPotentialChallengeUnique(tO.getID(), profile7.getID());
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
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile1.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile2.getID())).thenReturn(true);
		Mockito.when(challengeValidation.isPotentialChallengeUnique(tO.getID(), profile3.getID())).thenReturn(true);

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
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile1.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile2.getID());
		Mockito.verify(challengeValidation).isPotentialChallengeUnique(tO.getID(), profile3.getID());
	}

	@Test
	public void shouldValidateChallenge() throws ChallengeValidationException {
		// given
		ChallengeTO tO = new ChallengeTO();
		tO.setSenderID(senderID);
		tO.setRecieverID(recieverID);

		ChallengeTO notAwaitingChallenge = new ChallengeTO();
		notAwaitingChallenge.setChallengeStatus(ChallengeStatus.ACCEPTED);

		Mockito.when(userDAO.findByID(tO.getSenderID())).thenReturn(new ProfileTO());
		Mockito.when(userDAO.findByID(tO.getRecieverID())).thenReturn(new ProfileTO());
		Mockito.when(challengeDAO.findByUserIDs(tO.getSenderID(), tO.getRecieverID())).thenReturn(notAwaitingChallenge);

		// when
		challengeValidationImpl.validateChallenge(tO);

		// then
		Mockito.verify(userDAO).findByID(tO.getSenderID());
		Mockito.verify(userDAO).findByID(tO.getRecieverID());
		Mockito.verify(challengeDAO).findByUserIDs(tO.getSenderID(), tO.getRecieverID());
		// NO EXCEPTION
	}

	@Test
	public void shouldNotValidateChallengeDueToNoSenderID() throws ChallengeValidationException {
		// given
		ChallengeTO tO = new ChallengeTO();
		tO.setSenderID(senderID);
		tO.setRecieverID(recieverID);

		ChallengeTO notAwaitingChallenge = new ChallengeTO();
		notAwaitingChallenge.setChallengeStatus(ChallengeStatus.ACCEPTED);

		Mockito.when(userDAO.findByID(tO.getSenderID())).thenReturn(null);
		Mockito.when(userDAO.findByID(tO.getRecieverID())).thenReturn(new ProfileTO());
		Mockito.when(challengeDAO.findByUserIDs(tO.getSenderID(), tO.getRecieverID())).thenReturn(notAwaitingChallenge);

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
		ChallengeTO tO = new ChallengeTO();
		tO.setSenderID(senderID);
		tO.setRecieverID(recieverID);

		ChallengeTO notAwaitingChallenge = new ChallengeTO();

		notAwaitingChallenge.setChallengeStatus(ChallengeStatus.ACCEPTED);
		Mockito.when(userDAO.findByID(tO.getSenderID())).thenReturn(new ProfileTO());
		Mockito.when(userDAO.findByID(tO.getRecieverID())).thenReturn(null);
		Mockito.when(challengeDAO.findByUserIDs(tO.getSenderID(), tO.getRecieverID())).thenReturn(notAwaitingChallenge);

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
		ChallengeTO tO = new ChallengeTO();
		tO.setSenderID(senderID);
		tO.setRecieverID(recieverID);

		ChallengeTO awaitingChallenge = new ChallengeTO();

		awaitingChallenge.setChallengeStatus(ChallengeStatus.AWAITING);
		Mockito.when(userDAO.findByID(tO.getSenderID())).thenReturn(new ProfileTO());
		Mockito.when(userDAO.findByID(tO.getRecieverID())).thenReturn(new ProfileTO());
		Mockito.when(challengeDAO.findByUserIDs(tO.getSenderID(), tO.getRecieverID())).thenReturn(awaitingChallenge);

		// expect
		e.expect(ChallengeValidationException.class);
		e.expectMessage("challenge is already awaiting");

		// when
		challengeValidationImpl.validateChallenge(tO);

		// then
		// EXCEPTION
	}

}
