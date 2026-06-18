package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class RolesTest extends BaseTest {

    @Test
    public void getRoles() {

        Response response = RestAssured
                .given()
                .header("Authorization",
                        "Bearer " + getToken())
                .when()
                .get(BASE_URL + "/roles/getAll");
        
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}