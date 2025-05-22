package com.example.restassured.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import io.qameta.allure.restassured.AllureRestAssured;

public class BaseTest {

    @BeforeAll
    public static void setupBase() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.filters(new AllureRestAssured());
    }
}
