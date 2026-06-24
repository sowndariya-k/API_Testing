package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.json.JSONObject;

import Com.Payload.NotePayload;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@Listeners(listeners.ExtentReportListener.class)
public class CreateNoteTest extends BaseTest {

	@Test(description = "TC06_01: Full payload should create a note and return 201 with _id")
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
        // success flag
        Boolean success = response.jsonPath().getBoolean("success");
        Assert.assertTrue(success, "success flag must be true");

        // _id captured
        Assert.assertNotNull(noteId, "data._id must not be null");
        Assert.assertFalse(noteId.isEmpty(), "data._id must not be empty");

        // Returned fields match request
        Assert.assertEquals(response.jsonPath().getString("data.title"),
                payload.getString("title"), "Returned title should match request");
        Assert.assertEquals(response.jsonPath().getString("data.content"),
                payload.getString("content"), "Returned content should match request");
        Assert.assertEquals(response.jsonPath().getString("data.color"),
                payload.getString("color"), "Returned color should match request");
        Assert.assertEquals(response.jsonPath().getBoolean("data.isPinned"),
                payload.getBoolean("isPinned"), "Returned isPinned should match request");

        // Tags
        java.util.List<String> tags = response.jsonPath().getList("data.tags");
        Assert.assertNotNull(tags, "data.tags must not be null");
        Assert.assertFalse(tags.isEmpty(), "data.tags must not be empty");
        Assert.assertTrue(tags.contains("qa"), "tags should contain 'qa'");
        Assert.assertTrue(tags.contains("demo"), "tags should contain 'demo'");

        // Timestamps
        Assert.assertNotNull(response.jsonPath().getString("data.lastEdited"),
                "data.lastEdited must be present");
        Assert.assertNotNull(response.jsonPath().getString("data.createdAt"),
                "data.createdAt must be present");
    }
    
    
    //no token 
    @Test(description = "TC06_02: POST /create/notes without token should return 401")
    public void createNote_NoToken() {

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(NotePayload.createNotePayload().toString())
                .when()
                .post(BASE_URL + "/create/notes");

        System.out.println("[TC06_04] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 401,
                "Missing token should return 401");
    }
}