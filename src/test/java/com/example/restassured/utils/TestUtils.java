package com.example.restassured.utils;

import com.example.restassured.config.RestfulBookerEndpoints;
import static io.restassured.RestAssured.*;

import java.util.Map;
import java.util.HashMap;

public class TestUtils {

    public static String getAuthToken() {
        Map<String, Object> authBody = new HashMap<>();
        authBody.put("username", "admin");
        authBody.put("password", "password123");

        return given()
            .body(authBody)
        .when()
            .post(RestfulBookerEndpoints.AUTH)
        .then()
            .statusCode(200)
            .extract().path("token");
    }

    public static int createBooking() {
        return createBooking("Default", "User", 123, true, "2025-01-01", "2025-01-02", "Breakfast");
    }

    public static int createBooking(String firstname, String lastname, int totalprice, boolean depositpaid,
                                    String checkin, String checkout, String additionalneeds) {

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);
        requestBody.put("totalprice", totalprice);
        requestBody.put("depositpaid", depositpaid);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", additionalneeds);

        return given()
                    .contentType("application/json")
                    .body(requestBody)
                .when()
                    .post(RestfulBookerEndpoints.ALL_BOOKINGS)
                .then()
                    .statusCode(200)
                    .extract()
                    .path("bookingid");
    }
}
