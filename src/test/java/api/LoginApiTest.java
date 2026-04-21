package api;

import client.UserClient;
import model.User;
import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginApiTest {

    private UserClient userClient;
    private User user;
    private String email;

    @Before
    public void setUp() {
        userClient = new UserClient();

        email = "test" + System.currentTimeMillis() + "@mail.com";
        user = new User(email, "1234", "test");

        userClient.createUser(user);
    }

    @Test
    @DisplayName("Успешный вход пользователя")
    public void loginSuccess() {

        User loginUser = new User(email, "1234");

        userClient.loginUser(loginUser)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Ошибка входа: неверный логин")
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

        User loginUser = new User(email, "wrongPassword");

        userClient.loginUser(loginUser)
                .then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }
}