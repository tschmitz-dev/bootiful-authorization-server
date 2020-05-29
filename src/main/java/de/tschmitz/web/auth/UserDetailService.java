package de.tschmitz.web.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * UserDetailsService implementation that loads user via UserRepository.
 *
 * Will be injected automatically by the UserDetailsServiceAutoConfiguration.
 */
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username)
                .map(entity -> new User(entity.getLogin(), entity.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(entity.getRole().name()))))
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
