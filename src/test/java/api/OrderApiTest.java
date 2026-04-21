package api;

import client.OrderClient;
import client.UserClient;
import model.User;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderApiTest {

    OrderClient orderClient = new OrderClient();
    UserClient userClient = new UserClient();

    String validIngredients =
            "{ \"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"] }";

    String invalidIngredients =
            "{ \"ingredients\": [\"123\"] }";

    String emptyIngredients =
            "{ \"ingredients\": [] }";

    String token;

    @Before
    public void setUp() {

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        User registerUser = new User(email, "1234", "test");
        User loginUser = new User(email, "1234");

        userClient.createUser(registerUser);

        token = userClient.loginUser(loginUser)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    // с авторизацией
    @Test
    public void createOrderWithAuth() {

        orderClient.createOrder(validIngredients, token)
                .then()
                .statusCode(200)
                .body("name", notNullValue());
    }

    // без авторизации
    @Description("Проверка создания уникального пользователя")
    @Test
    public void createOrderWithoutAuth() {

        orderClient.createOrder(validIngredients, null)
                .then()
                .statusCode(200);
    }
   // с ингредиенами
    @Test
    public void createOrderWithIngredients() {

        orderClient.createOrder(validIngredients, token)
                .then()
                .statusCode(200)
                .body("name", notNullValue());
    }

    // без ингредиентов
    @Test
    public void createOrderWithoutIngredients() {

        orderClient.createOrder(emptyIngredients, token)
                .then()
                .statusCode(400);
    }

    // неверный хеш
    @Test
    public void createOrderWithInvalidHash() {

        orderClient.createOrder(invalidIngredients, token)
                .then()
                .statusCode(500);
    }
}