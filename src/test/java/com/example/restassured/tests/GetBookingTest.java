package com.example.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.example.restassured.base.BaseTest;
import io.qameta.allure.*;

public class GetBookingTest extends BaseTest {

    @Epic("Booking API")
    @Feature("User retrieves all booking IDs")
    @Story("User retrieves all booking IDs")
    @Owner("andrew")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void getAllBookingIds() {
        given()
        .when()
            .get("/booking")
        .then()
            .statusCode(200)
            .body("$", not(empty())); // Ensures the response body is not empty
    }
}

