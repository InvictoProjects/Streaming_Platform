package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
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
        Optional<User> expectedOptionalUser = Optional.of(new User(1L, "karl201", "user@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2)));
        when(mockedUserRepository.findByLogin(login)).thenReturn(expectedOptionalUser);
        Optional<User> actualOptionalUser = userService.findByLoginOrEmail(login);
        assertEquals(expectedOptionalUser, actualOptionalUser);
    }

    @Test
    void findByLoginOrEmailWithEmail() {
        String email = "karl201@gmail.com";
        Optional<User> expectedOptionalUser = Optional.of(new User(1L, "karl201", "karl201@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2)));
        when(mockedUserRepository.findByEmail(email)).thenReturn(expectedOptionalUser);
        Optional<User> actualOptionalUser = userService.findByLoginOrEmail(email);
        assertEquals(expectedOptionalUser, actualOptionalUser);
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
        Optional<User> expectedOptionalUser = Optional.of(new User(1L, "roman123", "user@gmail.com",
                "230d8h34falkfj", LocalDate.of(2000, 2, 2)));
        when(mockedUserRepository.findByResetPasswordToken(token)).thenReturn(expectedOptionalUser);
        Optional<User> actualOptionalUser = userService.findByResetPasswordToken(token);
        assertEquals(expectedOptionalUser, actualOptionalUser);
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
