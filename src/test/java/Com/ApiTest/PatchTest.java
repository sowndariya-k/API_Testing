package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PatchTest {

    // ─────────────────────────────────────────────
    // PATCH → Partial Update Trainee (200 OK)
    // Only updates the company field for id=5
    // ─────────────────────────────────────────────
    @Test
    public void patchTrainee_ValidData_ShouldReturnOK() {

        String requestBody = "{ \"company\": \"Smartcliff\" }";

        int statusCode = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("http://localhost:3000/trainee/2")
                .getStatusCode();

        System.out.println("PATCH Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    // ─────────────────────────────────────────────
    // PATCH → Check company field after partial update
    // Insomnia: json.company = "Smartcliff"
    // ─────────────────────────────────────────────
    @Test
    public void patchTrainee_ValidData_ShouldReturnUpdatedCompany() {

        String requestBody = "{ \"company\": \"ZOHO\" }";

        String company = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("http://localhost:3000/trainee/2")
                .jsonPath()
                .getString("company");

        System.out.println("PATCH Company: " + company);
        Assert.assertEquals(company, "ZOHO");
    }
}