package com.example.restassured.tests;

import com.example.restassured.util.TestUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.example.restassured.base.BaseTest;
import io.qameta.allure.*;

public class DeleteBookingTest extends BaseTest {

    @Epic("Booking API")
    @Feature("Delete a Booking")
    @Story("User deletes an existing booking")
    @Owner("andrew")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void deleteBooking_withValidToken_shouldSucceed() {
        // Get token and booking ID using utility methods
        String token = TestUtils.getAuthToken();
        int bookingId = TestUtils.createBooking();

        // Delete the booking
        given()
            .cookie("token", token)
        .when()
            .delete("/booking/" + bookingId)
        .then()
            .statusCode(201); // per API spec

        // Verify it's deleted
        when()
            .get("/booking/" + bookingId)
        .then()
            .statusCode(404);
    }
}
