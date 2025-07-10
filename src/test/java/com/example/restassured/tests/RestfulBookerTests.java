package com.example.restassured.tests;

import com.example.restassured.config.RestfulBookerEndpoints;
import org.junit.jupiter.api.Test;

import static com.example.restassured.utils.TestUtils.*;
import static com.example.restassured.utils.TestUtils.getAuthToken;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import com.example.restassured.config.BaseConfig;
import io.qameta.allure.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Map;
import java.util.HashMap;


public class RestfulBookerTests extends BaseConfig {

    @Epic("Booking API")
    @Feature("Create Booking")
    @Story("User creates a new booking")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest
    @CsvFileSource(resources = "/testdata/booking_data.csv", numLinesToSkip = 1)
    public void createNewBooking(String firstname, String lastname, int totalprice, boolean depositpaid,
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

        given()
            .body(requestBody)
        .when()
            .post(RestfulBookerEndpoints.ALL_BOOKINGS)
        .then()
            .statusCode(200)
            .body("booking.firstname", equalTo(firstname))
            .body("booking.lastname", equalTo(lastname))
            .body("booking.totalprice", equalTo(totalprice))
            .body("booking.depositpaid", equalTo(depositpaid))
            .body("booking.bookingdates.checkin", equalTo(checkin))
            .body("booking.bookingdates.checkout", equalTo(checkout))
            .body("booking.additionalneeds", equalTo(additionalneeds))
            .body("bookingid", notNullValue());
    }


    @Epic("Booking API")
    @Feature("User retrieves booking by ID")
    @Story("User retrieves booking details using booking ID")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void getBookingById_shouldReturnBookingDetails() {

        int bookingId = createBooking();

        given()
            .pathParam("bookingId", bookingId)
        .when()
            .get( RestfulBookerEndpoints.SINGLE_BOOKING )
        .then()
            .statusCode(200)
            .body("firstname", notNullValue())
            .body("lastname", notNullValue())
            .body("totalprice", notNullValue())
            .body("depositpaid", notNullValue())
            .body("additionalneeds", notNullValue())
            .body("bookingdates.checkin", notNullValue())
            .body("bookingdates.checkout", notNullValue());
    }

    @Epic("Booking API")
    @Feature("Get booking by id schema is validated")
    @Story("Get booking by id schema is validated")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testGetBookingSchemaJSON() {

        int bookingId = createBooking();

        given()
            .pathParam("bookingId", bookingId)
        .when()
            .get(RestfulBookerEndpoints.SINGLE_BOOKING)
        .then()
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/GetBookingJsonSchema.json"));
    }

    @Epic("Booking API")
    @Feature("User retrieves all booking IDs")
    @Story("User retrieves all booking IDs")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void getAllBookingIds() {
        given()
        .when()
            .get( RestfulBookerEndpoints.ALL_BOOKINGS )
        .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Epic("Booking API")
    @Feature("User updates a booking")
    @Story("User updates an existing booking")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest
    @CsvFileSource(resources = "/testdata/booking_data.csv", numLinesToSkip = 1)
    public void updateBooking_withValidToken_shouldSucceed(String firstname, String lastname, int totalprice,
                                                           boolean depositpaid, String checkin, String checkout,
                                                           String additionalneeds) {

        String token = getAuthToken();
        int bookingId = createBooking();

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<String, Object> updatedBooking = new HashMap<>();
        updatedBooking.put("firstname", firstname);
        updatedBooking.put("lastname", lastname);
        updatedBooking.put("totalprice", totalprice);
        updatedBooking.put("depositpaid", depositpaid);
        updatedBooking.put("bookingdates", bookingDates);
        updatedBooking.put("additionalneeds", additionalneeds);

        given()
            .pathParam("bookingId", bookingId)
            .body(updatedBooking)
            .cookie("token", token)
        .when()
            .put(RestfulBookerEndpoints.SINGLE_BOOKING)
        .then()
            .statusCode(200)
            .body("firstname", equalTo(firstname))
            .body("lastname", equalTo(lastname))
            .body("totalprice", equalTo(totalprice))
            .body("depositpaid", equalTo(depositpaid))
            .body("bookingdates.checkin", equalTo(checkin))
            .body("bookingdates.checkout", equalTo(checkout))
            .body("additionalneeds", equalTo(additionalneeds));
    }

    @Epic("Booking API")
    @Feature("Delete a Booking")
    @Story("User deletes an existing booking")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void deleteBooking_withValidToken_shouldSucceed() {
        // Get token and booking ID using utility methods
        String token = getAuthToken();
        int bookingId = createBooking();

        // Delete the booking
        given()
            .pathParam("bookingId", bookingId)
            .cookie("token", token)
        .when()
            .delete(RestfulBookerEndpoints.SINGLE_BOOKING)
        .then()
            .statusCode(201); // per API spec

        // Verify it's deleted
        given()
            .pathParam("bookingId", bookingId)
        .when()
            .get(RestfulBookerEndpoints.SINGLE_BOOKING)
        .then()
            .statusCode(404);
    }
}
