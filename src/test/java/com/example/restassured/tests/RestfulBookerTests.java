package com.example.restassured.tests;

import com.example.restassured.config.RestfulBookerEndpoints;
import org.junit.jupiter.api.Test;

import static com.example.restassured.utils.TestUtils.createBooking;
import static com.example.restassured.utils.TestUtils.getAuthToken;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import com.example.restassured.config.BaseConfig;
import io.qameta.allure.*;

public class RestfulBookerTests extends BaseConfig {

    @Epic("Booking API")
    @Feature("Create Booking")
    @Story("User creates a new booking")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void createNewBooking() {

        String requestBody = """
        {
            "firstname" : "Jim",
            "lastname" : "Brown",
            "totalprice" : 111,
            "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "2023-01-01",
                "checkout" : "2023-01-02"
            },
            "additionalneeds" : "Breakfast"
        }
        """;

        given()
            .body(requestBody)
        .when()
            .post(RestfulBookerEndpoints.ALL_BOOKINGS)
        .then()
            .statusCode(200)
            .body("booking.firstname", equalTo("Jim"))
            .body("booking.lastname", equalTo("Brown"))
            .body("booking.totalprice", equalTo(111))
            .body("booking.depositpaid", equalTo(true))
            .body("booking.bookingdates.checkin", equalTo("2023-01-01"))
            .body("booking.bookingdates.checkout", equalTo("2023-01-02"))
            .body("booking.additionalneeds", equalTo("Breakfast"))
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
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("GetBookingJsonSchema.json"));
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
            .body("$", not(empty())); // Ensures the response body is not empty
    }

    @Epic("Booking API")
    @Feature("User updates a booking")
    @Story("User updates an existing booking")
    @Owner("andrew")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void updateBooking_withValidToken_shouldSucceed() {
        // Use utils to get token and booking ID
        String token = getAuthToken();
        int bookingId = createBooking();

        // Updated booking payload
        String updatedBooking = """
            {
              "firstname": "Jane",
              "lastname": "Smith",
              "totalprice": 222,
              "depositpaid": false,
              "bookingdates": {
                "checkin": "2023-02-01",
                "checkout": "2023-02-05"
              },
              "additionalneeds": "Lunch"
            }
        """;

        // PUT request to update the booking
        given()
            .pathParam("bookingId", bookingId)
            .body(updatedBooking)
            .cookie("token", token)
        .when()
            .put( RestfulBookerEndpoints.SINGLE_BOOKING )
        .then()
            .statusCode(200)
            .body("firstname", equalTo("Jane"))
            .body("lastname", equalTo("Smith"))
            .body("totalprice", equalTo(222))
            .body("depositpaid", equalTo(false))
            .body("bookingdates.checkin", equalTo("2023-02-01"))
            .body("bookingdates.checkout", equalTo("2023-02-05"))
            .body("additionalneeds", equalTo("Lunch"));
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
