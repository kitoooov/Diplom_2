package api;

import client.UserClient;
import model.User;
import org.junit.Test;

import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginApiTest {

    UserClient userClient = new UserClient();

    @Test
    @DisplayName("Успешный вход пользователя")
    public void loginSuccess() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        User registerUser = new User(email, "1234", "test");
        User loginUser = new User(email, "1234");

        userClient.createUser(registerUser);

        userClient.loginUser(loginUser)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Ошибка входа: неверный email")
    public void loginWrongEmail() {

        User loginUser = new User("wrong@mail.com", "1234");

        userClient.loginUser(loginUser)
                .then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Ошибка входа: неверный пароль")
    public void loginWrongPassword() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        User registerUser = new User(email, "1234", "test");
        userClient.createUser(registerUser);

        User loginUser = new User(email, "wrongPassword");

        userClient.loginUser(loginUser)
                .then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }
}