package com.invicto.streaming_platform.persistence.repository;

import com.invicto.streaming_platform.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByLogin(String login);
	Optional<User> findByEmail(String emailAddress);
}
