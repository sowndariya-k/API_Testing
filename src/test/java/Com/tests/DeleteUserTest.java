package Com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteUserTest {

    @Test
    public void deletePost() {

        Response response = RestAssured
                .given()
                .when()
                .delete("https://jsonplaceholder.typicode.com/posts/1");

        System.out.println("Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    
}