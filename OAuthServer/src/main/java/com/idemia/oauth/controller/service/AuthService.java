package com.idemia.oauth.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idemia.oauth.collection.UserDataCollection;
import com.idemia.oauth.controller.exception.CustomMessageException;
import com.idemia.oauth.repository.UserCollectionRepository;
import com.idemia.oauth.response.RequestModel;

@Service
public class AuthService {
	@Autowired
	private UserCollectionRepository userCollectionRepository;

	public void saveUser(UserDataCollection user) {
		userCollectionRepository.save(user);
	}

	public boolean validateUser(UserDataCollection userCollection, RequestModel requestModel) throws CustomMessageException {
		if (userCollection.getName() == null || userCollection.getName().isEmpty()) {
			throw new CustomMessageException("username is mandatory");
		}

		if (userCollection.getPassword() == null || userCollection.getPassword().isEmpty()) {
			throw new CustomMessageException("password is mandatory");
		}

		UserDataCollection model = userCollectionRepository.findByName(userCollection.getName());
		boolean res = false;
		if (model != null && model.getPassword().equals(userCollection.getPassword())) {
			res = true;
		}
		if (model != null && model.getClientId().equals(requestModel.getClientId())) {
			res = true;
		}
		return res;
	}

}
