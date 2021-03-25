package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
import com.invicto.streaming_platform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User user) {
		if (user.getId() != null && userRepository.existsById(user.getId())) {
			throw new EntityExistsException("User with id " + user.getId() + " is already exists");
		}
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(@NonNull User user) {
		if (user.getId() == null) {
			throw new IllegalArgumentException("User id must not be null");
		} else if (!userRepository.existsById(user.getId())) {
			throw new EntityNotFoundException(String.format("User with id %s does not exist", user.getId()));
		}
		userRepository.delete(user);
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public User updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			throw new EntityNotFoundException("User with id " + user.getId() + " does not exist");
		}
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public Optional<User> findByEmail(@NonNull String email) {
		if (!email.contains("@")) {
			throw new IllegalArgumentException(String.format("Email %s is incorrect", email));
		}
		return userRepository.findByEmail(email);
	}


	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User findByLoginOrEmail(String input) {
		Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		Matcher matcher = emailPattern.matcher(input);
		if (matcher.find()) {
			return userRepository.findByEmail(input)
					.orElseThrow(() -> new EntityNotFoundException("User doesn't exist:"+input));
		} else {
			return userRepository.findByLogin(input)
					.orElseThrow(() -> new EntityNotFoundException("User doesn't exist:"+input));
		}
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("User doesn't exist:"+email));
		user.setResetPasswordToken(token);
		userRepository.save(user);
	}

	@Override
	public User findByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token)
				.orElseThrow(() -> new EntityNotFoundException("User doesn't exist:"+token));

	}

	@Override
	public void updatePasswordHash(User user, String newPasswordHash) {
		try {
			user.setPasswordHash(newPasswordHash);
			user.setResetPasswordToken(null);
			userRepository.save(user);
		} catch (NullPointerException e) {
			throw new EntityNotFoundException("User doesn't exist");
		}
	}
}
