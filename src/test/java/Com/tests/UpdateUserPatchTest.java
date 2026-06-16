package Com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserPatchTest {

    @Test
    public void updatePostUsingPatch() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "patched title");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("https://jsonplaceholder.typicode.com/posts/1");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "patched title");
    }
}