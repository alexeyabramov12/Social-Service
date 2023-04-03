package ru.skillbox.diplom.group33.social.service.utils.security;


import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;

@UtilityClass
public class SecurityUtils {

    public static JwtUser getJwtUserFromSecurityContext() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getJwtUserIdFromSecurityContext() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.getId();
    }

    public long getFakeJwtUserIdFromSecurityContext() {
        return 1L;
    }

}

