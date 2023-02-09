package ru.skillbox.diplom.group33.social.service.service.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.diplom.group33.social.service.config.security.JwtTokenProvider;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.UserDto;
import ru.skillbox.diplom.group33.social.service.mapper.auth.UserMapper;
import ru.skillbox.diplom.group33.social.service.model.auth.Role;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.repository.auth.RoleRepository;
import ru.skillbox.diplom.group33.social.service.repository.auth.UserRepository;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.service.captcha.CaptchaService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final CaptchaService captchaService;
    private final AccountService accountService;

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
        if (!captchaService.passCaptcha(registrationDto)) {
            log.warn("user failed captcha");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return createUser(registrationDto);
    }

    public UserDto createUser(RegistrationDto registrationDto) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("USER");
        roles.add(role);

        User registeredUser = mapper.registrationToUser(registrationDto);
        registeredUser.setRoles(roles);
        registeredUser.setPassword(encoder.encode(registrationDto.getPassword1()));
        registeredUser.setIsDeleted(false);

        accountService.createAccount(registeredUser);

        return mapper.registrationToUserDto(registrationDto);
    }
}
