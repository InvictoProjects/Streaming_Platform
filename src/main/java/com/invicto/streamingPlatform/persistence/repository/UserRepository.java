package com.invicto.streamingPlatform.persistence.repository;

import com.invicto.streamingPlatform.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByLogin(String login);
	Optional<User> findByEmailAddress(String emailAddress);
}
