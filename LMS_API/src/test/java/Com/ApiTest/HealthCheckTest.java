package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
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