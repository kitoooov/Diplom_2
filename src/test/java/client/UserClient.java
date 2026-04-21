package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;

import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Создать пользователя")
    public Response createUser(User user) {
        return given()
                .spec(Config.spec)
                .body(user)
                .when()
                .post("/auth/register");
    }

    @Step("Логин пользователя")
    public Response loginUser(User user) {
        return given()
                .spec(Config.spec)
                .body(user)
                .when()
                .post("/auth/login");
    }
}
