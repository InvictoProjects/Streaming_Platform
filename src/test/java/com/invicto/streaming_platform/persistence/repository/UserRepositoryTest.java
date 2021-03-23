package com.invicto.streaming_platform.persistence.repository;

import com.invicto.streaming_platform.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSaveNewUser_thenReturnSavedUser() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());

        User saved = userRepository.save(user);

        assertEquals(user, saved);
    }

    @Test
    void whenSaveUserWithExistingId_thenReturnUpdatedUser() {
        User existingUser = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        entityManager.persist(existingUser);
        entityManager.flush();
        User user = new User(existingUser.getId(),"anotherTestLogin", existingUser.getEmail(),
                existingUser.getPasswordHash(), existingUser.getDateOfBirth());

        User saved = userRepository.save(user);

        assertEquals(user.getLogin(), saved.getLogin());
    }

    @Test
    void whenSaveNull_thenInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.save(null));
    }

    @Test
    void whenFindByIdExistingUser_thenReturnOptionalPresent() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals(user, found.get());
    }

    @Test
    void whenFindByIdNotExistingUser_thenReturnOptionalEmpty() {
        Optional<User> found = userRepository.findById(new Random().nextLong());

        assertTrue(found.isEmpty());
    }

    @Test
    void whenFindByIdNull_thenInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.findById(null));
    }

    @Test
    void whenExistsByIdExistingUser_thenReturnTrue() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        entityManager.persist(user);
        entityManager.flush();

        boolean isExists = userRepository.existsById(user.getId());

        assertTrue(isExists);
    }

    @Test
    void whenExistsByIdNotExistingUser_thenReturnFalse() {
        boolean isExists = userRepository.existsById(new Random().nextLong());

        assertFalse(isExists);
    }

    @Test
    void whenExistsByIdNull_thenInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.existsById(null));
    }

    @Test
    void whenDeleteUser_thenDeleteGivenUser() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        entityManager.persist(user);
        entityManager.flush();
        Long userId = user.getId();

        userRepository.delete(user);
        User notFound = entityManager.find(User.class, userId);

        assertNull(notFound);
    }

    @Test
    void whenDeleteNull_thenInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.delete(null));
    }

    @Test
    void whenFindByLoginExistingUser_thenReturnOptionalPresent() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByLogin(user.getLogin());

        assertTrue(found.isPresent());
        assertEquals(user, found.get());
    }

    @Test
    void whenFindByLoginNotExistingUser_thenReturnOptionalEmpty() {
        Optional<User> found = userRepository.findByLogin("NotExistingLogin");

        assertTrue(found.isEmpty());
    }

    @Test
    void whenFindByEmailExistingUser_thenReturnOptionalPresent() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByEmail(user.getEmail());

        assertTrue(found.isPresent());
        assertEquals(user, found.get());
    }

    @Test
    void whenFindByEmailNotExistingUser_thenReturnOptionalEmpty() {
        Optional<User> found = userRepository.findByEmail("NotExistingEmail");

        assertTrue(found.isEmpty());
    }

    @Test
    void whenFindByResetPasswordTokenExistingUser_thenReturnOptionalPresent() {
        User user = new User("testLogin", "test@gmail.com",
                "testPasswordHash", LocalDate.now());
        user.setResetPasswordToken("testResetPasswordToken");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByResetPasswordToken(user.getResetPasswordToken());

        assertTrue(found.isPresent());
        assertEquals(user, found.get());
    }

    @Test
    void whenFindByResetPasswordTokenNotExistingUser_thenReturnOptionalEmpty() {
        Optional<User> found = userRepository.findByResetPasswordToken("NotExistingResetPasswordToken");

        assertTrue(found.isEmpty());
    }
}