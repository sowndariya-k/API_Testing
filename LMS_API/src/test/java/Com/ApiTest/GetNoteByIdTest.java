package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@Listeners(listeners.ExtentReportListener.class)
public class GetNoteByIdTest extends BaseTest {
	
	@Test(
		        description    = "TC08_01: GET /getById/notes/:id with valid id returns 200 and full note object",
		        dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		    )
		    public void getNoteById_HappyPath() {

		        Response response = RestAssured
		                .given()
		                .header("Authorization", "Bearer " + getToken())
		                .when()
		                .get(BASE_URL + "/getById/notes/" + noteId);

		        System.out.println("[TC08_01] " + response.asPrettyString());

		        Assert.assertEquals(response.getStatusCode(), 200,
		                "Expected HTTP 200 for valid note id");

		        Assert.assertTrue(response.jsonPath().getBoolean("success"),
		                "success flag must be true");

		        // All note fields
		        Assert.assertNotNull(response.jsonPath().getString("data._id"),        "data._id missing");
		        Assert.assertNotNull(response.jsonPath().getString("data.title"),       "data.title missing");
		        Assert.assertNotNull(response.jsonPath().getString("data.content"),     "data.content missing");
		        Assert.assertNotNull(response.jsonPath().getString("data.color"),       "data.color missing");
		        Assert.assertNotNull(response.jsonPath().get("data.isPinned"),          "data.isPinned missing");
		        Assert.assertNotNull(response.jsonPath().getString("data.lastEdited"),  "data.lastEdited missing");
		    }
	
	
	//no token
	 @Test(
		        description    = "TC08_02: GET /getById/notes/:id without token returns 401",
		        dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		    )
		    public void getNoteById_NoToken() {

		        Response response = RestAssured
		                .given()
		                .when()
		                .get(BASE_URL + "/getById/notes/" + noteId);

		        System.out.println("[TC08_02] Status: " + response.getStatusCode());

		        Assert.assertEquals(response.getStatusCode(), 401,
		                "Missing token should return 401");
		    }
	 
	 //non- existence id
	 @Test(description = "TC08_03: Non-existent note ID should return 404")
	    public void getNoteById_NotFound() {
	        String fakeId = "000000000000000000000000";

	        Response response = RestAssured
	                .given()
	                .header("Authorization", "Bearer " + getToken())
	                .when()
	                .get(BASE_URL + "/getById/notes/" + fakeId);

	        System.out.println("[TC08_03] Status: " + response.getStatusCode());

	        Assert.assertEquals(response.getStatusCode(), 404,
	                "Non-existent note ID should return 404");

	        String body = response.asString();
	        Assert.assertTrue(body.contains("not found") || body.contains("Not Found") ||
	                           body.contains("false"),
	                "Body should indicate 'not found', got: " + body);
	    }
	 
	 //id and title matches
	 @Test(
		        description    = "TC08_04: Returned id and title must match the value set at creation",
		        dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		    )
		    public void getNoteById_TitleMatchesCreation() {

		        Response response = RestAssured
		                .given()
		                .header("Authorization", "Bearer " + getToken())
		                .when()
		                .get(BASE_URL + "/getById/notes/" + noteId);

		        String returnedId = response.jsonPath().getString("data._id");
		        Assert.assertEquals(returnedId, noteId,
		                "Returned _id must match the requested note ID");
		        String title = response.jsonPath().getString("data.title");
		        Assert.assertEquals(title, "API Test Note",
		                "Title should match the value provided during creation");
		    }
}