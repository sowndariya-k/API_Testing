package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class HeadTest {

    // ─────────────────────────────────────────────
    // HEAD → Returns 400 Bad Request
    //
    // WHY 400?
    // json-server does not properly support HEAD requests.
    // HEAD should return same headers as GET but with no body.
    // Since json-server rejects it → returns 400 Bad Request.
    // Your Insomnia test expects "Bad Request" → same here.
    // ─────────────────────────────────────────────
    @Test
    public void headTrainee_ShouldReturnBadRequest() {

        int statusCode = RestAssured
                .given()
                .when()
                .head("http://localhost:3000/trainee")
                .getStatusCode();

        System.out.println("HEAD Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }
}