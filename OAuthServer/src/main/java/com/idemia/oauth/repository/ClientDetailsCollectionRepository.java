package com.idemia.oauth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.idemia.oauth.collection.ClientDetailsCollection;

public interface ClientDetailsCollectionRepository extends MongoRepository<ClientDetailsCollection, String> {

//	ClientDetailsCollection findByUanNumberHash(String uanNumberHash);
//
//	ClientDetailsCollection findByAadhaarNumberHash(String aadhaarNumberHash);
	
}
