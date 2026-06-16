package Com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class HeadTest {
	@Test
	public void headRequestTest() {

	    Response response = RestAssured
	            .given()
	            .when()
	            .head("https://jsonplaceholder.typicode.com/posts/1");

	    System.out.println(response.getStatusCode());

	    Assert.assertEquals(response.getStatusCode(), 200);
	}
}
