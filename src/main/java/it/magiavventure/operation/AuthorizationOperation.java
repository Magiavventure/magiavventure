package it.magiavventure.operation;

import it.magiavventure.model.auth.Login;
import it.magiavventure.model.auth.LoginResponse;
import it.magiavventure.service.AuthorizationService;
import it.magiavventure.jwt.service.JwtService;
import it.magiavventure.mongo.model.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization/v1")
public class AuthorizationOperation {

    private final AuthorizationService authorizationService;
    private final JwtService jwtService;
    
    @PostMapping("/loginById")
    public User loginById(@RequestBody @Valid Login login, HttpServletResponse response) {
        LoginResponse loginResponse = authorizationService.loginById(login.getId());
        response.setHeader(jwtService.getTokenHeader(), loginResponse.getToken());
        return loginResponse.getUser();
    }




}
