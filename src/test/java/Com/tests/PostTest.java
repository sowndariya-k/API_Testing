package Com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostTest {

    @Test
    public void createPost() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Test Title");
        payload.put("body", "Test Body");
        payload.put("userId", 1);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts");

        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), "Test Title");
        Assert.assertEquals(response.jsonPath().getInt("userId"), 1);
    }
}