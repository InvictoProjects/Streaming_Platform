package com.invicto.streaming_platform.services.impl;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void updateOnlyLogin() {
        User user = new User(1L, "login", "adress@gmail.com",
                "passwordHash", LocalDate.now());

        when(mockedUserRepository.save(user)).thenReturn(user);
        when(mockedUserRepository.existsById(user.getId())).thenReturn(true);

        user.setLogin("updatedLogin");

        User updatedUser = userService.updateUser(user);
        assertEquals(user, updatedUser);

        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void updateOnlyEmail() {
        User user = new User(1L, "login", "adress@gmail.com",
                "passwordHash", LocalDate.now());

        when(mockedUserRepository.save(user)).thenReturn(user);
        when(mockedUserRepository.existsById(user.getId())).thenReturn(true);

        user.setEmail("updated@gmail.com");

        User updatedUser = userService.updateUser(user);
        assertEquals(user, updatedUser);

        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void updateOnlyPasswordHash() {
        User user = new User(1L, "login", "adress@gmail.com",
                "passwordHash", LocalDate.now());

        when(mockedUserRepository.save(user)).thenReturn(user);
        when(mockedUserRepository.existsById(user.getId())).thenReturn(true);

        user.setPasswordHash("updatedPasswordHash");

        User updatedUser = userService.updateUser(user);
        assertEquals(user, updatedUser);

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
    void updateOnlyDateOfBirth() {
        User user = new User(1L, "login", "adress@gmail.com",
                "passwordHash", LocalDate.now());

        when(mockedUserRepository.save(user)).thenReturn(user);
        when(mockedUserRepository.existsById(user.getId())).thenReturn(true);

        user.setDateOfBirth(LocalDate.of(2021, 3, 1));

        User updatedUser = userService.updateUser(user);
        assertEquals(user, updatedUser);

        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void updateNotExistingUser() {
        User user = new User();

        when(mockedUserRepository.save(user)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user));
    }

    @Test
    void findByLogin() {
        User user = new User(1L, "testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        String login = user.getLogin();

        when(mockedUserRepository.findByLogin(login)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByLogin(login);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(mockedUserRepository, times(1)).findByLogin(login);
    }

    @Test
    void findByLoginNotExistingUser() {
        String login = "login";
        Optional<User> found = mockedUserRepository.findByLogin(login);

        assertTrue(found.isEmpty());
    }
    
    @Test  
    void findAll() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        List<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(mockedUserRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.findAll();

        assertEquals(users, foundUsers);
        verify(mockedUserRepository, times(1)).findAll();
    }

    @Test
    void updateUser() {
        User user = new User(1L, "login", "adress@gmail.com",
                "passwordHash", LocalDate.now());

        when(mockedUserRepository.save(user)).thenReturn(user);
        when(mockedUserRepository.existsById(user.getId())).thenReturn(true);

        user.setLogin("updatedLogin");
        user.setEmail("updated@gmail.com");
        user.setPasswordHash("updatedPasswordHash");
        user.setDateOfBirth(LocalDate.of(2021, 3, 1));

        User updatedUser = userService.updateUser(user);
        assertEquals(user, updatedUser);


        verify(mockedUserRepository, times(1)).save(user);
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

    @Test
    void findByEmail() {
        String existingEmail = "existingEmail@ukr.net";

        User user = new User();
        user.setId(0L);
        user.setEmail(existingEmail);

        when(mockedUserRepository.findByEmail(existingEmail)).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail(existingEmail);

        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        verify(mockedUserRepository, times(1)).findByEmail(existingEmail);
    }

    @Test
    void findByEmailThrowsExceptionIfEmailIsIncorrect() {
        String incorrectEmail = "1234";
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.findByEmail(incorrectEmail));
    }

    @Test
    void findByEmailThrowsExceptionIfEmailIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> userService.findByEmail(null));
    }

    @Test
    void findByEmailThrowsExceptionIfEmailDoesNotExist() {
        String notExistingEmail = "justEmail12@gmail.com";

        when(mockedUserRepository.findByEmail(notExistingEmail)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(notExistingEmail));
    }
}
