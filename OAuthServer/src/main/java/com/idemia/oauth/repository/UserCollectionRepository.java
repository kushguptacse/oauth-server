package com.idemia.oauth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.idemia.oauth.collection.UserDataCollection;

public interface UserCollectionRepository extends MongoRepository<UserDataCollection, String> {
	UserDataCollection findByName(String name);
}
