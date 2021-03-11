package com.invicto.streamingPlatform.services;

import com.invicto.streamingPlatform.persistence.model.User;

import java.util.Optional;

public interface UserService {

	public void createUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);
	public Optional<User> findByLogin(String login);
	public Optional<User> findByEmailAddress(String emailAddress);
	public Optional<User> findById(Long id);
}
