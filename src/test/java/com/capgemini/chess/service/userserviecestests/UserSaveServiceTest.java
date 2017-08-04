package com.capgemini.chess.service.userserviecestests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.exceptions.ProfileValidationException;
import com.capgemini.chess.service.to.UserTO;
import com.capgemini.chess.service.user.impl.UserSaveServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserSaveServiceTest {

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private UserSaveServiceImpl userSaveService;

	@Test
	public void shouldSaveRequestedUser() throws ProfileValidationException {
		// given
		UserTO tO = new UserTO();

		// when
		userSaveService.save(tO);

		// then
		Mockito.inOrder(userDAO);
		Mockito.verify(userDAO, Mockito.times(1)).save(tO);
	}

}
