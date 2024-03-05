package it.magiavventure.operation;

import it.magiavventure.model.auth.Login;
import it.magiavventure.model.auth.LoginResponse;
import it.magiavventure.service.AuthorizationService;
import it.magiavventure.jwt.service.JwtService;
import it.magiavventure.mongo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("Authorization operation tests")
class AuthorizationOperationTest {

    @InjectMocks
    private AuthorizationOperation authorizationOperation;

    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private JwtService jwtService;



    @Test
    @DisplayName("Login user by id")
    void loginUser_byId() {
        Login login = Login.builder().id(UUID.randomUUID()).build();
        LoginResponse loginResponse = LoginResponse
                .builder()
                .user(User.builder().id(UUID.randomUUID()).build())
                .token("token")
                .build();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(authorizationService.loginById(login.getId()))
                .thenReturn(loginResponse);
        Mockito.when(jwtService.getTokenHeader())
                .thenReturn("mg-a-token");

        User user = authorizationOperation.loginById(login, response);

        Mockito.verify(authorizationService).loginById(login.getId());
        Mockito.verify(jwtService).getTokenHeader();

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user, loginResponse.getUser());
        Assertions.assertEquals("token", loginResponse.getToken());
        Assertions.assertTrue(response.containsHeader("mg-a-token"));
        Assertions.assertEquals("token", response.getHeader("mg-a-token"));
    }
}
