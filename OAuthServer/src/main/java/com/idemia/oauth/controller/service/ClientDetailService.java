package com.idemia.oauth.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idemia.oauth.collection.ClientDetailsCollection;
import com.idemia.oauth.repository.ClientDetailsCollectionRepository;

@Service
public class ClientDetailService {

	@Autowired
	private ClientDetailsCollectionRepository clientDetailsCollectionRepository;

	public Optional<ClientDetailsCollection> getClient(String id) {
		return clientDetailsCollectionRepository.findById(id);
	}

	public ClientDetailsCollection saveClient(ClientDetailsCollection clientDetailsCollection) {
		return clientDetailsCollectionRepository.save(clientDetailsCollection);
	}
	

}
