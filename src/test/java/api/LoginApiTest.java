package api;

import client.UserClient;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class LoginApiTest {

    UserClient userClient = new UserClient();

    // успешный вход
    @Test
    public void loginSuccess() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        String registerBody = "{ \"email\": \"" + email + "\", " +
                "\"password\": \"1234\", \"name\": \"test\" }";

        String loginBody = "{ \"email\": \"" + email + "\", " +
                "\"password\": \"1234\" }";

        userClient.createUser(registerBody);

        userClient.loginUser(loginBody)
                .then()
                .statusCode(200);
    }

    // неверный логин/пароль
    @Test
    public void loginWrongCredentials() {

        String body = "{ \"email\": \"wrong@mail.com\", \"password\": \"wrong\" }";

        userClient.loginUser(body)
                .then()
                .statusCode(401);
    }
}