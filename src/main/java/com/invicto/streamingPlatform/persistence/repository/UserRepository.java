package com.invicto.streamingPlatform.persistence.repository;

import com.invicto.streamingPlatform.persistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByLogin(String login);
	Optional<User> findByEmail(String emailAddress);
	Optional<User> findByResetPasswordToken(String token);
}
