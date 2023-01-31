package ru.skillbox.diplom.group33.social.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUserFactory;
import ru.skillbox.diplom.group33.social.service.model.User;
import ru.skillbox.diplom.group33.social.service.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repository.findByEmail(email).
                orElseThrow(() ->new UsernameNotFoundException("User not found"));

        return JwtUserFactory.create(user);
    }
}
