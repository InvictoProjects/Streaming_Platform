package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
import com.invicto.streaming_platform.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

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
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public User updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			throw new EntityNotFoundException("User with id" + user.getId() + "does not exist");
		}
		return userRepository.save(user);
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
		if (matcher.find()) {
			return userRepository.findByEmail(input);
		} else {
			return userRepository.findByLogin(input);
		}
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			optionalUser.get().setResetPasswordToken(token);
			userRepository.save(optionalUser.get());
		} else {
			//throw new UserNotFoundException("Could not find any customer with the email " + email);
		}
	}

	@Override
	public Optional<User> findByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(User customer, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		customer.setPassword(encodedPassword);

		customer.setResetPasswordToken(null);
		userRepository.save(customer);
	}
}
