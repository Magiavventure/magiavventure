package it.magiavventure.service;

import it.magiavventure.error.MessageCode;
import it.magiavventure.mapper.UserMapper;
import it.magiavventure.model.auth.LoginResponse;
import it.magiavventure.common.error.MagiavventureException;
import it.magiavventure.jwt.service.JwtService;
import it.magiavventure.mongo.entity.EUser;
import it.magiavventure.mongo.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    public LoginResponse loginById(UUID id) {
        log.debug("Execute login by id for '{}'", id);
        EUser eUser = userService.findEntityById(id);
        validateUser(eUser);
        User user = userMapper.map(eUser);
        String token = jwtService.buildJwt(user);
        return LoginResponse
                .builder()
                .user(user)
                .token(token)
                .build();
    }

    private void validateUser(EUser eUser) {
        log.debug("Execute user validation after login for '{}'", eUser);
        LocalDateTime banExpiration = eUser.getBanExpiration();
        if(Objects.nonNull(banExpiration) && banExpiration.isAfter(LocalDateTime.now())) {
            userService.evictUserCache(eUser);
            throw MagiavventureException.of(MessageCode.USER_BLOCKED);
        }
    }
}
