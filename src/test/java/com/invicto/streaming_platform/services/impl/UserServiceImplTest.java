package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserRepository mockedUserRepository;

    @BeforeEach
    void setUp() {
        mockedUserRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(mockedUserRepository);
    }

    @Test
    void whenCreateNewUser_thenReturnUser() {
        User user = new User(1L, "testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());

        when(mockedUserRepository.existsById(user.getId())).thenReturn(false);
        when(mockedUserRepository.save(any(User.class))).thenReturn(user);
        User created = userService.createUser(user);

        assertEquals(user, created);
        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void whenCreateExistingUser_thenEntityExistsException() {
        User user = new User(1L, "testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());

        when(mockedUserRepository.existsById(user.getId())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.createUser(user));
    }

    @Test
    void whenCreateUserWithoutId_thenReturnUserWithId() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());

        when(mockedUserRepository.existsById(null)).thenThrow(new IllegalArgumentException());
        when(mockedUserRepository.save(any(User.class))).thenReturn(new User(1L, user.getLogin(), user.getEmail(),
                user.getPasswordHash(), user.getDateOfBirth()));
        User created = userService.createUser(user);

        assertNotNull(created.getId());
        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void whenFindByIdExistingUser_thenReturnOptionalPresent() {
        User user = new User(1L, "testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());

        when(mockedUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> found = userService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(user, found.get());
        verify(mockedUserRepository, times(1)).findById(1L);
    }

    @Test
    void whenFindByIdNotExistingUser_thenReturnOptionalEmpty() {
        when(mockedUserRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<User> found = userService.findById(1L);

        assertTrue(found.isEmpty());
        verify(mockedUserRepository, times(1)).findById(1L);
    }

    @Test
    void findByLoginOrEmailWithLogin() {
        String login = "karl201";
        User expectedUser = new User(1L, "karl201", "user@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2));
        when(mockedUserRepository.findByLogin(login)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findByLoginOrEmail(login);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByLoginOrEmailWithEmail() {
        String email = "karl201@gmail.com";
        User expectedUser = new User(1L, "karl201", "karl201@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2));
        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findByLoginOrEmail(email);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByLoginOrEmailEntityNotExistException() {
        String loginOrEmail = "12df";
        when(mockedUserRepository.findByResetPasswordToken(loginOrEmail)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByLoginOrEmail(loginOrEmail));
    }

    @Test
    void updateResetPasswordToken() {
        String oldToken = "12023oihf0923jf2039j";
        String newToken = "12023oihf0923jfsdsscj";
        String email = "some.email@ukr.net";
        Optional<User> testOptionalUser = Optional.of(new User(1L, "roman123", email,
                "230d8h34falkfj", oldToken, LocalDate.of(2000, 2, 2)));
        User user = testOptionalUser.get();
        when(mockedUserRepository.findByEmail(email)).thenReturn(testOptionalUser);
        userService.updateResetPasswordToken(newToken, email);
        assertEquals(newToken, user.getResetPasswordToken());
        verify(mockedUserRepository, timeout(1)).save(testOptionalUser.get());
    }

    @Test
    void updateResetPasswordTokenEntityNotExistException() {
        String newToken = "12023oihasdf0923jfsdsscj";
        String email = "13faisl@ukr.net";
        when(mockedUserRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.updateResetPasswordToken(newToken, email));
    }

    @Test
    void findByResetPasswordToken() {
        String token = "12r034gjwojwe9jfsdklw09j";
        User expectedUser = new User(1L, "roman123", "user@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2));
        when(mockedUserRepository.findByResetPasswordToken(token)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findByResetPasswordToken(token);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByResetPasswordTokenEntityNotExistException() {
        String token = "12r034gjwojwejjjdklw09j";
        when(mockedUserRepository.findByResetPasswordToken(token)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByResetPasswordToken(token));
    }

    @Test
    void updatePasswordHash() {
        String newPasswordHash = "lkjlkhpihpojipojoih";
        User user = new User(1L, "roman123", "user@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2));
        userService.updatePasswordHash(user, newPasswordHash);
        assertEquals(newPasswordHash, user.getPasswordHash());
        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void updatePasswordHashEntityNotExistException() {
        String newPasswordHash = "lkjlkhpihpojipojoih";
        assertThrows(EntityNotFoundException.class, () -> userService.updatePasswordHash(null, newPasswordHash));
    }
}
