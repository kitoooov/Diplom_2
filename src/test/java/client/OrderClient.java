package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Создать заказ")
    public Response createOrder(String body, String token) {

        var request = given()
                .spec(Config.spec)
                .body(body);

        if (token != null) {
            request.header("Authorization", token);
        }

        return request.post("/orders");
    }
}