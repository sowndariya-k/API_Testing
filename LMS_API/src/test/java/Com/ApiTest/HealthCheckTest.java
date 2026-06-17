package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class HealthCheckTest extends BaseTest {

    @Test
    public void healthCheck() {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/");
        
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}