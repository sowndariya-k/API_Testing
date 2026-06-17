package Com.ApiTest;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UpdateNoteTest extends BaseTest {

    @Test(dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote")
    public void updateNote() {

        JSONObject payload = new JSONObject();
        payload.put("title", "Updated Note");
        payload.put("content", "Updated Content");

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .header("Content-Type","application/json")
                .body(payload.toString())
                .when()
                .put(BASE_URL +"/update/notes/" + noteId);
        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}