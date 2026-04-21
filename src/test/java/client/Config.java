package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class Config {

    public static final RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://stellarburgers.education-services.ru/api")
            .setContentType(JSON)
            .build();
}