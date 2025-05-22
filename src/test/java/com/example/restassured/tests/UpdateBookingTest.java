package com.example.restassured.tests;

import com.example.restassured.util.TestUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.example.restassured.base.BaseTest;
import io.qameta.allure.*;

public class UpdateBookingTest extends BaseTest {

    @Epic("Booking API")
    @Feature("User updates a booking")
    @Story("User updates an existing booking")
    @Owner("andrew")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void updateBooking_withValidToken_shouldSucceed() {
        // Use utils to get token and booking ID
        String token = TestUtils.getAuthToken();
        int bookingId = TestUtils.createBooking();

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
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .cookie("token", token)
            .body(updatedBooking)
        .when()
            .put("/booking/" + bookingId)
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
}
