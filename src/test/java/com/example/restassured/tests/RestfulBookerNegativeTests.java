package com.example.restassured.tests;

import com.example.restassured.config.BaseConfig;
import com.example.restassured.config.RestfulBookerEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.example.restassured.utils.TestUtils.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.qameta.allure.*;

import java.util.Map;
import java.util.HashMap;

public class RestfulBookerNegativeTests extends BaseConfig {

    @Epic("Booking API")
    @Feature("Create Booking")
    @Story("User creates a new booking with missing field should fail")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Tag("negative")
    @Test
    public void createNewBooking_withMissingField_shouldFail() {

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-01-02");
        bookingDates.put("checkout", "2025-01-02");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lastname", "Plummer");
        requestBody.put("totalprice", 99.99);
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Fruit");

        given()
            .body(requestBody)
        .when()
            .post(RestfulBookerEndpoints.ALL_BOOKINGS)
        .then()
            .statusCode(400); //should throw a 400 but throws a 500
    }

    @Epic("Booking API")
    @Feature("Create Booking")
    @Story("User creates a new booking with invalid type should fail")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Tag("negative")
    @Test
    public void createNewBooking_withInvalidType_shouldFail() {

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-01-01");
        bookingDates.put("checkout", "2025-01-02");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", "Harry");
        requestBody.put("lastname", "Plummer");
        requestBody.put("totalprice", "99.99"); //Invalid type
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Fruit");

        given()
            .body(requestBody)
        .when()
            .post(RestfulBookerEndpoints.ALL_BOOKINGS)
        .then()
            .statusCode(400); //should throw a 400 but succeeds with a 200
    }

    @Epic("Booking API")
    @Feature("User retrieves booking by ID")
    @Story("User retrieves booking details using invalid booking ID should fail")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Tag("negative")
    @Test
    public void getBookingByInvalidId_shouldFail() {

        given()
            .pathParam("bookingId", -1000)
        .when()
            .get( RestfulBookerEndpoints.SINGLE_BOOKING )
        .then()
            .statusCode(404);
    }

    @Epic("Booking API")
    @Feature("User updates a booking")
    @Story("User attempts to update an existing booking without token should fail")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Tag("negative")
    @Test
    public void updateBooking_withoutToken_shouldFail() {

        int bookingId = createBooking();

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2025-01-01");
        bookingDates.put("checkout", "2025-01-02");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", "Harry");
        requestBody.put("lastname", "Plummer");
        requestBody.put("totalprice", 99.99);
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Fruit");

        given()
            .pathParam("bookingId", bookingId)
            .body(requestBody)
        .when()
            .put(RestfulBookerEndpoints.SINGLE_BOOKING)
        .then()
            .statusCode(403);

    }

    @Epic("Booking API")
    @Feature("Delete a Booking")
    @Story("User attempts to delete non existent booking should fail")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Tag("negative")
    @Test
    public void deleteBooking_withInvalidId_shouldFail() {
        // Get token and booking ID using utility methods
        String token = getAuthToken();
        int bookingId = -10;

        // Delete the booking
        given()
            .pathParam("bookingId", bookingId)
            .cookie("token", token)
        .when()
            .delete(RestfulBookerEndpoints.SINGLE_BOOKING)
        .then()
            .statusCode(405);
    }

    @Epic("Booking API")
    @Feature("Get booking by id schema is validated")
    @Story("Get booking by id invalid schema should throw assertion error")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testGetBookingInvalidSchemaJSON_shouldFail() {

        int bookingId = createBooking();

        assertThrows(AssertionError.class, () -> {
            given()
                .pathParam("bookingId", bookingId)
            .when()
                .get(RestfulBookerEndpoints.SINGLE_BOOKING)
            .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/GetBookingJsonSchemaInvalid.json"));
        });
    }
}


