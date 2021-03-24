package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
	User createUser(User user);
	void deleteUser(User user);
	User updateUser(User user);
	List<User> findAll();
	Optional<User> findByLogin(String login);
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long id);
	User findByLoginOrEmail(String input);
	void updateResetPasswordToken(String token, String email);
	User findByResetPasswordToken(String token);
	void updatePasswordHash(User user, String newPassword);
}
