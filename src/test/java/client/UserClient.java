package client;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_URL =
            "https://stellarburgers.education-services.ru/api";
    public Response createUser(String body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(BASE_URL + "/auth/register");
    }

    public Response loginUser(String body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(BASE_URL + "/auth/login");
    }
}

