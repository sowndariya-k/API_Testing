package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class HealthCheckTest extends BaseTest {

	 @Test(description = "TC01_01: GET / should return 200 and body 'API Running'")
	    public void healthCheck_HappyPath() {

	        Response response = RestAssured
	                .given()
	                .when()
	                .get(BASE_URL + "/");

	        System.out.println("[TC01_01] Response: " + response.asString());

	        // Status code
	        Assert.assertEquals(response.getStatusCode(), 200,
	                "Expected HTTP 200 for health check");

	        // Body content
	        String body = response.getBody().asString().trim();
	        Assert.assertFalse(body.isEmpty(),
	                "Response body must not be empty");
	        Assert.assertTrue(body.contains("API Running"),
	                "Body should contain 'API Running', got: " + body);
	    }
	 
	 //Response time
	 @Test(description = "TC01_02: GET / should respond within 5000 ms")
	    public void healthCheck_ResponseTime() {

	        Response response = RestAssured
	                .given()
	                .when()
	                .get(BASE_URL + "/");

	        long timeMs = response.getTime();
	        System.out.println("[TC01_02] Response time: " + timeMs + " ms");

	        Assert.assertTrue(timeMs < 5000,
	                "Response time exceeded 5000 ms; actual: " + timeMs + " ms");
	    }
	 
	 
	 //Response header
	 @Test(description = "TC01_03: GET / response should contain a Content-Type header")
	    public void healthCheck_ContentTypeHeader() {

	        Response response = RestAssured
	                .given()
	                .when()
	                .get(BASE_URL + "/");

	        String contentType = response.getHeader("Content-Type");
	        System.out.println("[TC01_03] Content-Type: " + contentType);

	        Assert.assertNotNull(contentType,
	                "Content-Type header must be present in the response");
	    }
}