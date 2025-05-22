package com.example.restassured.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.example.restassured.base.BaseTest;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateBookingTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateBookingTest.class);

    @Epic("Booking API")
    @Feature("Create Booking")
    @Story("User creates a new booking")
    @Owner("andrew")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void createNewBooking() {
        logger.info("Starting test: createBooking");

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

        logger.debug("Request body: {}", requestBody);

        Allure.step("Sending POST /booking request");

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/booking");

        Allure.step("Validating response status is 200 & response body");

        response.then()
            .statusCode(200)
            .body("booking.firstname", equalTo("Jim"))
            .body("booking.lastname", equalTo("Brown"))
            .body("booking.totalprice", equalTo(111))
            .body("booking.depositpaid", equalTo(true))
            .body("booking.bookingdates.checkin", equalTo("2023-01-01"))
            .body("booking.bookingdates.checkout", equalTo("2023-01-02"))
            .body("booking.additionalneeds", equalTo("Breakfast"))
            .body("bookingid", notNullValue());

        logger.info("Response status: {}", response.statusCode());
        logger.debug("Response body: {}", response.asString());   
    }
}
