package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class HeadTest {
    @Test
    public void headTrainee_ShouldReturnBadRequest() {

        int statusCode = RestAssured
                .given()
                .when()
                .head("http://localhost:3000/")
                .getStatusCode();

        System.out.println("HEAD Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
}
