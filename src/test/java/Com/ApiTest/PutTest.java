package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PutTest {

    // ─────────────────────────────────────────────
    // PUT → Full Update Trainee (200 OK)
    // Replaces the entire record for id=2
    // ─────────────────────────────────────────────
    @Test
    public void putTrainee_ValidData_ShouldReturnOK() {

        String requestBody = "{ \"name\": \"Sowndariya\", \"email\": \"sowndariyadeveloper@gmail.com\", \"company\": \"Smartcliff\" }";

        int statusCode = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("http://localhost:3000/trainee/2")
                .getStatusCode();

        System.out.println("PUT Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }


    @Test
    public void putTrainee_ValidData_ShouldReturnUpdatedCompany() {

        String requestBody = "{ \"name\": \"Sowndariya\", \"email\": \"sowndariyadeveloper@gmail.com\", \"company\": \"Smartcliff\" }";

        String company = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("http://localhost:3000/trainee/2")
                .jsonPath()
                .getString("company");

        System.out.println("PUT Company: " + company);
        Assert.assertEquals(company, "Smartcliff");
    }
}