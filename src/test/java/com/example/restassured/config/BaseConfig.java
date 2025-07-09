package com.example.restassured.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.lessThan;

public class BaseConfig {

    @BeforeAll
    public static void setupBase() {

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setContentType("application/json")
                .setAccept("application/json")
                .addFilter( new RequestLoggingFilter())
                .addFilter( new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime((lessThan(3000L)))
                .build();
    }
}
