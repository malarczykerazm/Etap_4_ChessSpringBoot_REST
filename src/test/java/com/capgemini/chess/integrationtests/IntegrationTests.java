package com.capgemini.chess.integrationtests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.dataaccess.dao.ChallengeDAO;
import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.facade.MultiPlayerGameSetupAndPointsFacade;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;
import com.capgemini.chess.service.to.UserTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ChessApplication.class })
public class IntegrationTests {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private MultiPlayerGameSetupAndPointsFacade multiPlayerFacade;

	@Autowired
	private ChallengeDAO challengeDAO;

	@Rule
	public ExpectedException e = ExpectedException.none();

	private UserTO user1;
	private UserTO user2;
	private UserTO user3;
	private UserTO user4;
	private UserTO user5;
	private UserTO user6;
	private UserTO user7;
	private UserTO user8;

	private ProfileTO profile1;
	private ProfileTO profile2;
	private ProfileTO profile3;
	private ProfileTO profile4;
	private ProfileTO profile5;
	private ProfileTO profile6;
	private ProfileTO profile7;
	private ProfileTO profile8;

	@Before
	public void setup() {
		user1 = userDAO.findByID(1L);
		profile1 = user1.getProfile();
		user2 = userDAO.findByID(2L);
		profile2 = user2.getProfile();
		user3 = userDAO.findByID(3L);
		profile3 = user3.getProfile();
		user4 = userDAO.findByID(4L);
		profile4 = user4.getProfile();
		user5 = userDAO.findByID(5L);
		profile5 = user5.getProfile();
		user6 = userDAO.findByID(6L);
		profile6 = user6.getProfile();
		user7 = userDAO.findByID(7L);
		profile7 = user7.getProfile();
		user8 = userDAO.findByID(8L);
		profile8 = user8.getProfile();
	}

	@Test
	public void shouldReturnFiveOpponentsWithinLevelRange() throws UserValidationException {
		// given
		int levelRange = 2;

		// when
		List<ProfileTO> list = multiPlayerFacade.findPotentialOpponents(user8.getProfile(), levelRange);
		List<Long> actualIDList = new ArrayList<>();
		actualIDList.add(list.get(0).getID());
		actualIDList.add(list.get(1).getID());
		actualIDList.add(list.get(2).getID());
		actualIDList.add(list.get(3).getID());
		actualIDList.add(list.get(4).getID());

		// then
		List<Long> expectedIDList = new ArrayList<>();
		expectedIDList.add(profile1.getID());
		expectedIDList.add(profile3.getID());
		expectedIDList.add(profile5.getID());
		expectedIDList.add(profile6.getID());
		expectedIDList.add(profile7.getID());

		Assert.assertEquals(expectedIDList, actualIDList);
	}

	@Test
	public void shouldThrowAnExceptionDueToNotExistingSenderID() throws UserValidationException {
		// given
		ProfileTO invalidProfile = new ProfileTO();
		invalidProfile.setID(20L);
		invalidProfile.setLevel(5);

		int levelRange = 2;

		// expect
		e.expect(UserValidationException.class);
		e.expectMessage("ID");

		// when
		multiPlayerFacade.findPotentialOpponents(invalidProfile, levelRange);

		// then
		// EXCEPTION
	}

	@Test
	public void shouldInitiateNewChallenge() throws ChallengeValidationException {
		// given

		// when
		multiPlayerFacade.initNewChallenge(user8.getProfile(), user1.getProfile());

		// then
		ChallengeTO expectedChallenge = new ChallengeTO();
		expectedChallenge.setSender(user8.getProfile());
		expectedChallenge.setReceiver(user1.getProfile());
		expectedChallenge.setChallengeStatus(ChallengeStatus.AWAITING);

		Assert.assertEquals(expectedChallenge.getSender(),
				challengeDAO.findByUserProfiles(user8.getProfile(), user1.getProfile()).getSender());
		Assert.assertEquals(expectedChallenge.getReceiver(),
				challengeDAO.findByUserProfiles(user8.getProfile(), user1.getProfile()).getReceiver());
		Assert.assertEquals(expectedChallenge.getChallengeStatus(),
				challengeDAO.findByUserProfiles(user8.getProfile(), user1.getProfile()).getChallengeStatus());
	}

	@Test
	public void shouldThrowAnExceptionDueToExistingChallenge() throws ChallengeValidationException {
		// given
		ChallengeTO existingChallenge = new ChallengeTO();
		existingChallenge.setSender(user2.getProfile());
		existingChallenge.setReceiver(user8.getProfile());
		existingChallenge.setChallengeStatus(ChallengeStatus.AWAITING);
		challengeDAO.save(existingChallenge);

		// expect
		e.expect(ChallengeValidationException.class);
		e.expectMessage("awaiting");

		// when
		multiPlayerFacade.initNewChallenge(user8.getProfile(), user2.getProfile());

		// then
		// EXCEPTION
	}

}
