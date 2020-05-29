package de.tschmitz.web.auth;

import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * User data repository.
 */
public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByLogin(String login);
}
