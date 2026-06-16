package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostTest {

    // ─────────────────────────────────────────────
    // POST → Create New Trainee (201 Created)
    // json-server returns 201 when a new record is created
    // ─────────────────────────────────────────────
    @Test
    public void postTrainee_ValidData_ShouldReturnCreated() {

        String requestBody = "{ \"name\": \"Sowndariya\", \"email\": \"sownd@gmail.com\", \"company\": \"Smartcliff\" }";

        int statusCode = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:3000/trainee")
                .getStatusCode();

        System.out.println("POST Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 201);
    }

    // ─────────────────────────────────────────────
    // POST → Check company field in response
    // ─────────────────────────────────────────────
    @Test
    public void postTrainee_ValidData_ShouldReturnCorrectCompany() {

        String requestBody = "{ \"name\": \"Sowndariya\", \"email\": \"sownd@gmail.com\", \"company\": \"Smartcliff\" }";

        String company = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:3000/trainee")
                .jsonPath()
                .getString("company");

        System.out.println("POST Company: " + company);
        Assert.assertEquals(company, "Smartcliff");
    }
}