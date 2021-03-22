package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void deleteUser() {
        User user = new User();
        LocalDate dateOfBirth = LocalDate.now();

        user.setDateOfBirth(dateOfBirth);
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setLogin("userLogin");
        user.setPasswordHash("123456");

        when(mockedUserRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(user);

        verify(mockedUserRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserWithoutDateOfBirth() {
        User user = new User();

        user.setId(2L);
        user.setLogin("123LoginWithNumbers");
        user.setEmail("user123@gmail.com");
        user.setPasswordHash("user123");

        when(mockedUserRepository.existsById(2L)).thenReturn(true);
        userService.deleteUser(user);

        verify(mockedUserRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserWithoutPassword() {
        User user = new User();
        LocalDate dateOfBirth = LocalDate.now();

        user.setDateOfBirth(dateOfBirth);
        user.setId(3L);
        user.setLogin("user2Login");
        user.setEmail("user2@gmail.com");

        when(mockedUserRepository.existsById(3L)).thenReturn(true);
        userService.deleteUser(user);

        verify(mockedUserRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserWithOnlyId() {
        User user = new User();
        user.setId(4L);

        when(mockedUserRepository.existsById(4L)).thenReturn(true);
        userService.deleteUser(user);

        verify(mockedUserRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserThrowsExceptionIfIdNull() {
        User user = new User();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(user);
        });
    }

    @Test
    void deleteUserThrowsExceptionIfIdDoesNotExist() {
        User user = new User();
        user.setId(5L);

        when(mockedUserRepository.existsById(5L)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(user);
        });
    }
}
