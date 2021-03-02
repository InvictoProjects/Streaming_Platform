package com.invicto.streamingPlatform.services;

import com.invicto.streamingPlatform.persistence.model.User;

import java.util.List;

public interface UserService {

	public void createUser(User user);
	public void deleteUser(User user);
	public void updateUser(User user);
	public List<User> findByLogin(String login);
	public User findById(Long id);
}
