package com.example.restassured.utils;

import com.example.restassured.config.RestfulBookerEndpoints;

import static io.restassured.RestAssured.*;

public class TestUtils {

    public static String getAuthToken() {
        String authBody = """
            {
              "username": "admin",
              "password": "password123"
            }
        """;

        return given()
            .body(authBody)
        .when()
            .post(RestfulBookerEndpoints.AUTH)
        .then()
            .statusCode(200)
            .extract().path("token");
    }

    public static int createBooking() {
        String bookingBody = """
            {
              "firstname": "DeleteMe",
              "lastname": "Tester",
              "totalprice": 999,
              "depositpaid": true,
              "bookingdates": {
                "checkin": "2023-10-01",
                "checkout": "2023-10-10"
              },
              "additionalneeds": "None"
            }
        """;

        return given()
            .body(bookingBody)
        .when()
            .post(RestfulBookerEndpoints.ALL_BOOKINGS)
        .then()
            .statusCode(200)
            .extract().path("bookingid");
    }
}
