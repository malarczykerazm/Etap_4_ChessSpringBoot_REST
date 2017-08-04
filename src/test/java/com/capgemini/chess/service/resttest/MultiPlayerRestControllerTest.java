package com.capgemini.chess.service.resttest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exceptions.WrongNumberOfParametersException;
import com.capgemini.chess.service.facade.MultiPlayerGameSetupAndPointsFacade;
import com.capgemini.chess.service.rest.MultiPlayerRestController;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class MultiPlayerRestControllerTest {

	private MockMvc mockMvc;

	private List<ProfileTO> profiles;
	private ProfileTO profile1;
	private ProfileTO profile2;
	private ProfileTO profile3;
	private ProfileTO profile4;
	private ProfileTO profile5;
	private ProfileTO profile6;
	private ProfileTO profile7;
	private ProfileTO profile8;

	@Mock
	private MultiPlayerGameSetupAndPointsFacade multiPlayerFacade;

	@Mock
	private ProfileDAO profileDAO;

	@InjectMocks
	private MultiPlayerRestController multiPlayerRestController;

	@Rule
	public ExpectedException e = ExpectedException.none();

	@Before
	public void setup() {
		profiles = new ArrayList<ProfileTO>();
		profile1 = new ProfileTO();
		profile1.setID(1L);
		profile1.setName("Janusz");
		profile1.setSurname("Kowalczyk");
		profile1.setAboutMe("Inżynier.");
		profile1.setLevel(4);
		profiles.add(profile1);

		profile2 = new ProfileTO();
		profile2.setID(2L);
		profile2.setName("Marylin");
		profile2.setSurname("");
		profile2.setAboutMe("art_ysTa");
		profile2.setLevel(9);
		profiles.add(profile2);

		profile3 = new ProfileTO();
		profile3.setID(3L);
		profile3.setName("Janusz");
		profile3.setSurname("Jurczyk");
		profile3.setAboutMe("O sobie.");
		profile3.setLevel(7);
		profiles.add(profile3);

		profile4 = new ProfileTO();
		profile4.setID(4L);
		profile4.setName("Agata");
		profile4.setSurname("Musielak");
		profile4.setAboutMe("the show must go on");
		profile4.setLevel(0);
		profiles.add(profile4);

		profile5 = new ProfileTO();
		profile5.setID(5L);
		profile5.setName("Tomasz");
		profile5.setSurname("Kowalczyk");
		profile5.setAboutMe(".");
		profile5.setLevel(5);
		profiles.add(profile5);

		profile6 = new ProfileTO();
		profile6.setID(6L);
		profile6.setName("Maria");
		profile6.setSurname("Tomczak");
		profile6.setAboutMe("nauczycielka");
		profile6.setLevel(3);
		profiles.add(profile6);

		profile7 = new ProfileTO();
		profile7.setID(7L);
		profile7.setName("marcin");
		profile7.setSurname("zbieć");
		profile7.setAboutMe("aktor");
		profile7.setLevel(6);
		profiles.add(profile7);

		profile8 = new ProfileTO();
		profile8.setID(8L);
		profile8.setName("Bartosz");
		profile8.setSurname("Elong");
		profile8.setAboutMe("Szachista");
		profile8.setLevel(5);
		profiles.add(profile8);

		mockMvc = MockMvcBuilders.standaloneSetup(multiPlayerRestController).build();
	}

	@Test
	public void shouldFindPotentialOpponents() throws Exception {

		// given
		int range = 2;

		List<ProfileTO> potentialOpponents = new ArrayList<ProfileTO>();
		potentialOpponents.add(profile5);
		potentialOpponents.add(profile6);
		potentialOpponents.add(profile7);
		potentialOpponents.add(profile8);

		String jsonPotentialOpponents = new ObjectMapper().writeValueAsString(potentialOpponents);

		given(profileDAO.findByID(profile1.getID())).willReturn(profile1);
		given(multiPlayerFacade.findPotentialOpponents(profile1, 2)).willReturn(potentialOpponents);

		// when
		ResultActions resultActions = mockMvc.perform(get("/challenges/findOpponents?searcherID=1&range=2"));

		// then
		Mockito.verify(profileDAO, Mockito.times(1)).findByID(profile1.getID());
		Mockito.verify(multiPlayerFacade, Mockito.times(1)).findPotentialOpponents(profile1, range);
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().json(jsonPotentialOpponents));
	}

	@Test
	public void shouldInitNewChallenge() throws Exception {

		// given
		ChallengeTO challenge1 = new ChallengeTO();
		challenge1.setChallengeID(1L);
		challenge1.setSender(profile1);
		challenge1.setReceiver(profile2);
		challenge1.setChallengeStatus(ChallengeStatus.AWAITING);

		String jsonChallenge1 = new ObjectMapper().writeValueAsString(challenge1);

		ProfileTO[] requestedBody = new ProfileTO[2];
		requestedBody[0] = profile1;
		requestedBody[1] = profile2;

		String jsonRequestedBody = new ObjectMapper().writeValueAsString(requestedBody);

		given(multiPlayerFacade.initNewChallenge(profile1, profile2)).willReturn(challenge1);

		// when
		ResultActions resultActions = mockMvc
				.perform(post("/challenges/init").contentType(MediaType.APPLICATION_JSON).content(jsonRequestedBody));

		// then
		Mockito.verify(multiPlayerFacade, Mockito.times(1)).initNewChallenge(profile1, profile2);
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().json(jsonChallenge1));
	}

	@Test
	public void shouldNotInitNewChallengeDueToMoreThenTwoObjects() throws Exception, WrongNumberOfParametersException {

		// given
		ChallengeTO challenge1 = new ChallengeTO();
		challenge1.setChallengeID(1L);
		challenge1.setSender(profile1);
		challenge1.setReceiver(profile2);
		challenge1.setChallengeStatus(ChallengeStatus.AWAITING);

		ProfileTO[] requestedBody = new ProfileTO[3];
		requestedBody[0] = profile1;
		requestedBody[1] = profile2;
		requestedBody[2] = profile3;

		String jsonRequestedBody = new ObjectMapper().writeValueAsString(requestedBody);

		given(multiPlayerFacade.initNewChallenge(profile1, profile2)).willReturn(challenge1);

		// expect
		e.expectMessage("many");

		// when
		mockMvc.perform(post("/challenges/init").contentType(MediaType.APPLICATION_JSON).content(jsonRequestedBody));

		// then
		// EXCEPTION
	}

	@Test
	public void shouldNotInitNewChallengeDueToLessThenTwoObjects() throws Exception, WrongNumberOfParametersException {

		// given
		ChallengeTO challenge1 = new ChallengeTO();
		challenge1.setChallengeID(1L);
		challenge1.setSender(profile1);
		challenge1.setReceiver(profile2);
		challenge1.setChallengeStatus(ChallengeStatus.AWAITING);
		ProfileTO[] requestedBody = new ProfileTO[1];
		requestedBody[0] = profile1;
		String jsonRequestedBody = new ObjectMapper().writeValueAsString(requestedBody);
		given(multiPlayerFacade.initNewChallenge(profile1, profile2)).willReturn(challenge1);

		// expect
		e.expectMessage("few");

		// when
		mockMvc.perform(post("/challenges/init").contentType(MediaType.APPLICATION_JSON).content(jsonRequestedBody));

		// then
		// EXCEPTION
	}

	@Test
	public void shouldFindAwaitingChallenges() throws Exception {

		// given
		List<ChallengeTO> awaitingChallenges = new ArrayList<ChallengeTO>();

		ChallengeTO challenge1 = new ChallengeTO();
		challenge1.setChallengeID(1L);
		challenge1.setSender(profile1);
		challenge1.setReceiver(profile2);
		challenge1.setChallengeStatus(ChallengeStatus.AWAITING);
		awaitingChallenges.add(challenge1);

		ChallengeTO challenge2 = new ChallengeTO();
		challenge2.setChallengeID(2L);
		challenge2.setSender(profile1);
		challenge2.setReceiver(profile3);
		challenge2.setChallengeStatus(ChallengeStatus.AWAITING);
		awaitingChallenges.add(challenge2);

		String jsonSearcher = new ObjectMapper().writeValueAsString(profile1);

		String jsonAwaitingChallenges = new ObjectMapper().writeValueAsString(awaitingChallenges);

		given(multiPlayerFacade.findAwaitingChallenges(profile1)).willReturn(awaitingChallenges);

		// when
		ResultActions resultActions = mockMvc.perform(
				post("/challenges/findAwaiting").contentType(MediaType.APPLICATION_JSON).content(jsonSearcher));

		// then
		Mockito.verify(multiPlayerFacade, Mockito.times(1)).findAwaitingChallenges(profile1);
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().json(jsonAwaitingChallenges));
	}

	@Test
	public void shouldReturnProfileByID() throws Exception {

		// given
		Long iD = 8L;

		String jsonProfile = new ObjectMapper().writeValueAsString(profile8);

		given(multiPlayerFacade.checkProfile(iD)).willReturn(profile8);

		// when
		ResultActions resultActions = mockMvc.perform(get("/profiles/check?id=8"));

		// then
		Mockito.verify(multiPlayerFacade, Mockito.times(1)).checkProfile(iD);
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(content().json(jsonProfile));
	}

}
