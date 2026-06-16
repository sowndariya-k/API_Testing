package Com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OptionsTest {

    @Test
    public void optionsRequestTest() {

        Response response = RestAssured
                .given()
                .when()
                .options("https://jsonplaceholder.typicode.com/posts");

        System.out.println("Status Code: " + response.getStatusCode());

        String methods = response.getHeader("Access-Control-Allow-Methods");
        System.out.println("Allowed Methods: " + methods);

        Assert.assertEquals(response.getStatusCode(), 204);
    }
}