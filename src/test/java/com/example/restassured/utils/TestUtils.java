package com.example.restassured.util;

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
            .header("Content-Type", "application/json")
            .body(authBody)
        .when()
            .post("/auth")
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
            .header("Content-Type", "application/json")
            .body(bookingBody)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .extract().path("bookingid");
    }
}
