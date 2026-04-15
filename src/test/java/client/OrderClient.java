package client;

import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URL =
            "https://stellarburgers.education-services.ru/api";
    public Response createOrder(String body, String token) {

        var request = given()
                .header("Content-type", "application/json")
                .body(body);

        if (token != null) {
            request.header("Authorization", token);
        }

        return request.post(BASE_URL + "/orders");
    }
}