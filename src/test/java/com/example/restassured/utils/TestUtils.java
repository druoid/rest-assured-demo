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
        return createBooking("John", "Doe", 123, true, "2025-01-01", "2025-01-02", "Breakfast");
    }

    public static int createBooking(String firstname, String lastname, int totalprice, boolean depositpaid,
                                    String checkin, String checkout, String additionalneeds) {

        Map<String, Object> requestBody = buildBookingPayload(
                firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);

        return given()
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post(RestfulBookerEndpoints.BOOKINGS)
            .then()
                .statusCode(200)
                .extract()
                .path("bookingid");
    }

    public static Map<String, Object> buildBookingPayload(
            String firstname,
            String lastname,
            Object totalprice,
            boolean depositpaid,
            String checkin,
            String checkout,
            String additionalneeds) {

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", firstname);
        booking.put("lastname", lastname);
        booking.put("totalprice", totalprice);
        booking.put("depositpaid", depositpaid);
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", additionalneeds);

        return booking;
    }


}
