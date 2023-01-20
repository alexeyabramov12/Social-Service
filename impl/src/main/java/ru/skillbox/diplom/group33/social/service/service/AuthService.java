package ru.skillbox.diplom.group33.social.service.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.security.JwtTokenProvider;
import ru.skillbox.diplom.group33.social.service.dto.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.dto.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.UserDto;
import ru.skillbox.diplom.group33.social.service.mapper.SimpleMapperImpl;
import ru.skillbox.diplom.group33.social.service.model.Role;
import ru.skillbox.diplom.group33.social.service.model.User;
import ru.skillbox.diplom.group33.social.service.repository.RoleRepository;
import ru.skillbox.diplom.group33.social.service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final SimpleMapperImpl mapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AuthenticateResponseDto login(@NonNull AuthenticateDto authenticateDto) {

        final User user = userRepository.findByEmail(authenticateDto.getEmail()).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (encoder.matches(authenticateDto.getPassword(), user.getPassword())) {
                String token = jwtTokenProvider.createAccessToken(user);
                return new AuthenticateResponseDto(token, token);
            } else {
                log.info("Invalid password");
                throw new BadCredentialsException("Invalid password");
            }
    }

    public UserDto register(RegistrationDto registrationDto) {

        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            log.warn("User already exists");
            return null;
        }

        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("USER");
        roles.add(role);

        User registeredUser = mapper.registrationToUser(registrationDto);
        registeredUser.setRoles(roles);
        registeredUser.setPassword(encoder.encode(registrationDto.getPassword1()));

        userRepository.save(registeredUser);

        return mapper.registrationToUserDto(registrationDto);
    }

    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.warn("User already exists");
            return null;
        }

        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("MODERATOR");
        roles.add(role);

        RegistrationDto user = mapper.userDtoToRegistration(userDto);
        User userToSave = mapper.registrationToUser(user);
        userToSave.setRoles(roles);
        userRepository.save(userToSave);

        return userDto;
    }
}
