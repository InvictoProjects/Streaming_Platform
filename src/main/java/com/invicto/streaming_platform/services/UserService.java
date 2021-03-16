package com.invicto.streaming_platform.services;

import com.invicto.streaming_platform.persistence.model.User;

import java.util.Optional;

public interface UserService {

	public void createUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);
	public Optional<User> findByLogin(String login);
	public Optional<User> findByEmail(String emailAddress);
	public Optional<User> findById(Long id);
}
