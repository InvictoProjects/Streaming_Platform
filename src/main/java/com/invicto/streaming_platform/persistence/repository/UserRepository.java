package com.invicto.streaming_platform.persistence.repository;

import com.invicto.streaming_platform.persistence.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByLogin(String login);
	Optional<User> findByEmail(String email);
	Optional<User> findByResetPasswordToken(String token);
}
