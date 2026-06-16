package Com.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class ApiValidatorTest {

	@Test
	public void validateUserData() {

	    String email = RestAssured
	            .given()
	            .when()
	            .get("https://jsonplaceholder.typicode.com/users/1")
	            .jsonPath()
	            .getString("email");

	    System.out.println("Email: " + email);

	    Assert.assertEquals(email, "Sincere@april.biz");
	}
}