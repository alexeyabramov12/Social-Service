package ru.skillbox.diplom.group33.social.service.utils.account;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;

public class SecurityUtils {

    public static Long getJwtUsersId() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return jwtUser.getId();
    }

    public static JwtUser getJwtUsers() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (JwtUser) authentication.getPrincipal();
    }
}