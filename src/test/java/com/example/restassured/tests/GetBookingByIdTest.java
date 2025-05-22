package com.example.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.example.restassured.base.BaseTest;
import io.qameta.allure.*;

public class GetBookingByIdTest extends BaseTest {

    @Epic("Booking API")
    @Feature("User retrieves booking by ID")
    @Story("User retrieves booking details using booking ID")
    @Owner("andrew")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void getBookingById_shouldReturnBookingDetails() {
        // Step 1: Get list of booking IDs
        Response bookingListResponse = 
            given()
            .when()
                .get("/booking")
            .then()
                .statusCode(200)
                .extract().response();       

        // Step 2: Extract the first booking ID
        int bookingId = bookingListResponse.jsonPath().getInt("[0].bookingid");


        // Step 3: Use the booking ID to fetch full booking details
        given()
            .pathParam("id", bookingId)
        .when()
            .get("/booking/{id}")
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
}
