package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
import com.invicto.streaming_platform.services.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		userRepository.save(user);
	}

	@Override
	public Optional<User> findByLogin(String login) {
		Optional<User> user = userRepository.findByLogin(login);
		return user;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public Optional<User> findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}

	@Override
	public Optional<User> findByLoginOrEmail(String input) {
		Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		Matcher matcher = emailPattern.matcher(input);
		Optional<User> optionalUser;
		if (matcher.find()) {
			optionalUser = userRepository.findByEmail(input);
		} else {
			optionalUser = userRepository.findByLogin(input);
		}
		if (optionalUser.isEmpty()) {
			throw new EntityNotFoundException("User doesn't exist:"+input);
		}
		return optionalUser;
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			optionalUser.get().setResetPasswordToken(token);
			userRepository.save(optionalUser.get());
		} else {
			throw new EntityNotFoundException("User doesn't exist:"+email);
		}
	}

	@Override
	public Optional<User> findByResetPasswordToken(String token) {
		Optional<User> optionalUser = userRepository.findByResetPasswordToken(token);
		if (optionalUser.isEmpty()) {
			throw new EntityNotFoundException("User doesn't exist:"+token);
		}
		return optionalUser;
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
