package Com.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;
public class GetUserTest {
 
	@Test
	public void getUserTest() {
		Response response=RestAssured
				.given()
				.when()
				.get("https://jsonplaceholder.typicode.com/users/1");
	    System.out.println(":status code:"+response.getStatusCode());
	    response.prettyPrint();
	    Assert.assertEquals(response.getStatusCode(),200);
	    String name=response.jsonPath().getString("name");
	    Assert.assertEquals(name, "Leanne Graham");
	}
}
