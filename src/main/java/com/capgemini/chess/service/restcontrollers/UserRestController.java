package com.capgemini.chess.service.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.to.UserTO;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public UserTO findUserByID(@RequestParam("iD") Long iD) throws UserValidationException {
		userDAO.initUsersAndProfiles();
		return userDAO.findByID(iD);
	}

}
