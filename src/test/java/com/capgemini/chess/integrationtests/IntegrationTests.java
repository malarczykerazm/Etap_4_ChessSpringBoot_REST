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
	ChallengeDAO challengeDAO;

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
		user1 = new UserTO();
		profile1 = new ProfileTO();
		profile1.setLevel(4);
		user1.setProfile(profile1);
		user1.setEmail("a1");
		userDAO.save(user1);
		user1.setID(userDAO.findByEmail("a1").getID());
		profile1.setID(userDAO.findByEmail("a1").getID());

		user2 = new UserTO();
		profile2 = new ProfileTO();
		profile2.setLevel(9);
		user2.setProfile(profile2);
		user2.setEmail("a2");
		userDAO.save(user2);
		user2.setID(userDAO.findByEmail("a2").getID());
		profile2.setID(userDAO.findByEmail("a2").getID());

		user3 = new UserTO();
		profile3 = new ProfileTO();
		profile3.setLevel(7);
		user3.setProfile(profile3);
		user3.setEmail("a3");
		userDAO.save(user3);
		user3.setID(userDAO.findByEmail("a3").getID());
		profile3.setID(userDAO.findByEmail("a3").getID());

		user4 = new UserTO();
		profile4 = new ProfileTO();
		profile4.setLevel(0);
		user4.setProfile(profile4);
		user4.setEmail("a4");
		userDAO.save(user4);
		user4.setID(userDAO.findByEmail("a4").getID());
		profile4.setID(userDAO.findByEmail("a4").getID());

		user5 = new UserTO();
		profile5 = new ProfileTO();
		profile5.setLevel(5);
		user5.setProfile(profile5);
		user5.setEmail("a5");
		userDAO.save(user5);
		user5.setID(userDAO.findByEmail("a5").getID());
		profile5.setID(userDAO.findByEmail("a5").getID());

		user6 = new UserTO();
		profile6 = new ProfileTO();
		profile6.setLevel(3);
		user6.setProfile(profile6);
		user6.setEmail("a6");
		userDAO.save(user6);
		user6.setID(userDAO.findByEmail("a6").getID());
		profile6.setID(userDAO.findByEmail("a6").getID());

		user7 = new UserTO();
		profile7 = new ProfileTO();
		profile7.setLevel(6);
		user7.setProfile(profile7);
		user7.setEmail("a7");
		userDAO.save(user7);
		user7.setID(userDAO.findByEmail("a7").getID());
		profile7.setID(userDAO.findByEmail("a7").getID());

		user8 = new UserTO();
		profile8 = new ProfileTO();
		profile8.setLevel(5);
		user8.setProfile(profile8);
		user8.setEmail("a8");
		userDAO.save(user8);
		user8.setID(userDAO.findByEmail("a8").getID());
		profile8.setID(userDAO.findByEmail("a8").getID());

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
		multiPlayerFacade.initNewChallenge(user8.getID(), user1.getID());

		// then
		ChallengeTO expectedChallenge = new ChallengeTO();
		expectedChallenge.setSenderID(user8.getID());
		expectedChallenge.setRecieverID(user1.getID());
		expectedChallenge.setChallengeStatus(ChallengeStatus.AWAITING);

		Assert.assertEquals(expectedChallenge.getSenderID(),
				challengeDAO.findByUserIDs(user8.getID(), user1.getID()).getSenderID());
		Assert.assertEquals(expectedChallenge.getRecieverID(),
				challengeDAO.findByUserIDs(user8.getID(), user1.getID()).getRecieverID());
		Assert.assertEquals(expectedChallenge.getChallengeStatus(),
				challengeDAO.findByUserIDs(user8.getID(), user1.getID()).getChallengeStatus());
	}

	@Test
	public void shouldThrowAnExceptionDueToExistingChallenge() throws ChallengeValidationException {
		// given
		ChallengeTO existingChallenge = new ChallengeTO();
		existingChallenge.setSenderID(user2.getID());
		existingChallenge.setRecieverID(user8.getID());
		existingChallenge.setChallengeStatus(ChallengeStatus.AWAITING);
		challengeDAO.save(existingChallenge);

		// expect
		e.expect(ChallengeValidationException.class);
		e.expectMessage("awaiting");

		// when
		multiPlayerFacade.initNewChallenge(user8.getID(), user2.getID());

		// then
		// EXCEPTION
	}

}
