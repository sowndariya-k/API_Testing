package Com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserPutTest {

    @Test
    public void updatePostUsingPut() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 1);
        payload.put("title", "updated title");
        payload.put("body", "updated body");
        payload.put("userId", 1);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("https://jsonplaceholder.typicode.com/posts/1");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "updated title");
    }
}