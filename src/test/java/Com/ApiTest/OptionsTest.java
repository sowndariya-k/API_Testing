package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class OptionsTest {

    // ─────────────────────────────────────────────
    // OPTIONS → Returns 204 No Content
    // Tells you which HTTP methods are allowed on /trainee
    // Insomnia: code = 204
    // ─────────────────────────────────────────────
    @Test
    public void optionsTrainee_ShouldReturnNoContent() {

        int statusCode = RestAssured
                .given()
                .when()
                .options("http://localhost:3000/trainee")
                .getStatusCode();

        System.out.println("OPTIONS Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 204);
    }
}