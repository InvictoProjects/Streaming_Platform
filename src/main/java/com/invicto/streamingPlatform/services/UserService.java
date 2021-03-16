package com.invicto.streamingPlatform.services;

import com.invicto.streamingPlatform.persistence.model.User;

import java.util.Optional;

public interface UserService {

	void createUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	Optional<User> findByLogin(String login);
	Optional<User> findByEmail(String emailAddress);
	Optional<User> findById(Long id);
	Optional<User> findByLoginOrEmail(String input);
	void updateResetPasswordToken(String token, String email);
	Optional<User> findByResetPasswordToken(String token);
	void updatePassword(User customer, String newPassword);
}
