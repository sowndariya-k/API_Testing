package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;

import Com.Payload.NotePayload;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateNoteTest extends BaseTest {

    @Test
    public void createNote() {

        JSONObject payload =
                NotePayload.createNotePayload();

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .header("Content-Type","application/json")
                .body(payload.toString())
                .when()
                .post(BASE_URL + "/create/notes");

        noteId=response.jsonPath().getString("data._id");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201);
    }
}