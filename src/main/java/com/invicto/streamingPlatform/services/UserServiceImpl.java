package com.invicto.streamingPlatform.services;

import com.invicto.streamingPlatform.persistence.model.User;
import com.invicto.streamingPlatform.storage.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createUser(User user) {
		if (userRepository.existsById(user.getId())) {

			// Throw userAlreadyExists exception

		}
		userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		if (!userRepository.existsById(user.getId())) {

			// Throw userIsNotExist exception

		}
		userRepository.delete(user);
	}

	@Override
	public void updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {

			// Throw userIsNotExist exception

		}
		userRepository.update(user);
	}

	@Override
	public List<User> findByLogin(String login) {
		List<User> users = userRepository.findByLogin(login);
		if (users == null) {

			// Throw userIsNotExist exception

		}
		return users;
	}

	@Override
	public User findByEmailAddress(String emailAddress) {
		User user = userRepository.findByEmailAddress(emailAddress);
		if (user == null) {

			// Throw userIsNotExist exception

		}
		return user;
	}

	@Override
	public User findById(Long id) {
		User user = userRepository.findById(id);
		if (user == null) {

			// Throw userIsNotExist exception

		}
		return user;
	}
}
