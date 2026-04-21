package api;

import client.UserClient;
import model.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest {

    UserClient userClient = new UserClient();

    // 1. Успешное создание пользователя
    @Test
    public void createUniqueUser() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        User user = new User(email, "1234", "test");

        userClient.createUser(user)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    // 2. уже существующий пользователь
    @Test
    public void createExistingUser() {

        String email = "test@mail.com";

        User user = new User(email, "1234", "test");

        userClient.createUser(user); // создаём первый раз

        userClient.createUser(user)  // повтор — ошибка
                .then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    // 3. нет email
    @Test
    public void createUserWithoutEmail() {

        User user = new User(null, "1234", "test");

        userClient.createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    // 4. нет password
    @Test
    public void createUserWithoutPassword() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        User user = new User(email, null, "test");

        userClient.createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    // 5. нет name
    @Test
    public void createUserWithoutName() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        User user = new User(email, "1234", null);

        userClient.createUser(user)
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}