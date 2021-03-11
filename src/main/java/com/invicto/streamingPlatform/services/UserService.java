package com.invicto.streamingPlatform.services;

import com.invicto.streamingPlatform.persistence.model.User;

public interface UserService {

	public void createUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);
	public User findByLogin(String login);
	public User findByEmailAddress(String emailAddress);
	public User findById(Long id);
}
