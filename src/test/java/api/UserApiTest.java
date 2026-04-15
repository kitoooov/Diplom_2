package api;

import client.UserClient;
import model.User;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserApiTest {

    UserClient userClient = new UserClient();

    // 1. уникальный пользователь
    @Test
    public void createUniqueUser() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        String body = "{ \"email\": \"" + email + "\", " +
                "\"password\": \"1234\", " +
                "\"name\": \"test\" }";

        userClient.createUser(body)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    // 2. уже существующий пользователь
    @Test
    public void createExistingUser() {

        String body = "{ \"email\": \"test@mail.com\", " +
                "\"password\": \"1234\", " +
                "\"name\": \"test\" }";

        userClient.createUser(body); // первый раз создаём

        userClient.createUser(body)  // второй раз — ошибка
                .then()
                .statusCode(403);
    }

    // 3. без обязательного поля
    @Test
    public void createUserWithoutRequiredField() {

        String body = "{ \"email\": \"test@mail.com\" }";

        userClient.createUser(body)
                .then()
                .statusCode(403);
    }
}