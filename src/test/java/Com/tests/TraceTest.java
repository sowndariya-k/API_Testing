package Com.tests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class TraceTest {
	@Test
	public void traceRequestTest() {

	    Response response = RestAssured
	            .given()
	            .when()
	            .request("TRACE",
	                    "https://jsonplaceholder.typicode.com/posts/1");

	    System.out.println(response.getStatusCode());
	}

}
