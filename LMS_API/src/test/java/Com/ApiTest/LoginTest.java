package Com.ApiTest;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {
    	JSONObject payload = new JSONObject();
    	payload.put("email","sam@gmail.com");
    	payload.put("password","123");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(BASE_URL + "/user/login");
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}