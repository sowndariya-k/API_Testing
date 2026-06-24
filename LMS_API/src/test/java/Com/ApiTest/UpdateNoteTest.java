package Com.ApiTest;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Com.Payload.NotePayload;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class UpdateNoteTest extends BaseTest {

	 @Test(
		        description    = "TC09_01: PUT /update/notes/:id with title and content update returns 200",
		        dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		    )
		    public void updateNote_HappyPath() {

		        JSONObject payload = NotePayload.updateNotePayload();

		        Response response = RestAssured
		                .given()
		                .header("Authorization", "Bearer " + getToken())
		                .header("Content-Type", "application/json")
		                .body(payload.toString())
		                .when()
		                .put(BASE_URL + "/update/notes/" + noteId);

		        System.out.println("[TC09_01] " + response.asPrettyString());

		        Assert.assertEquals(response.getStatusCode(), 200,
		                "Expected HTTP 200 on note update");

		        Assert.assertTrue(response.jsonPath().getBoolean("success"),
		                "success flag must be true");

		        Assert.assertNotNull(response.jsonPath().getString("message"),
		                "message must be present");
		    }

	 
		    //No token

		    @Test(
		        description    = "TC09_02: PUT /update/notes/:id without token returns 401",
		        dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		    )
		    public void updateNote_NoToken() {

		        Response response = RestAssured
		                .given()
		                .header("Content-Type", "application/json")
		                .body(NotePayload.updateNotePayload().toString())
		                .when()
		                .put(BASE_URL + "/update/notes/" + noteId);

		        Assert.assertEquals(response.getStatusCode(), 401,
		                "Missing token should return 401");
		    }

		    
		    //Non-existent ID
		    @Test(description = "TC09_03: Updating a non-existent note ID should return 404")
		    public void updateNote_NotFound() {

		        JSONObject payload = NotePayload.updateNotePayload();

		        Response response = RestAssured
		                .given()
		                .header("Authorization", "Bearer " + getToken())
		                .header("Content-Type", "application/json")
		                .body(payload.toString())
		                .when()
		                .put(BASE_URL + "/update/notes/000000000000000000000000");

		        System.out.println("[TC09_03] Status: " + response.getStatusCode());

		        Assert.assertEquals(response.getStatusCode(), 404,
		                "Non-existent note ID should return 404");
		    }
		    
		    //return title reflects updated
		    @Test(
		            description    = "TC09_04: Returned data.title must reflect the updated value",
		            dependsOnMethods = "Com.ApiTest.UpdateNoteTest.updateNote_HappyPath"
		        )
		        public void updateNote_TitleUpdated() {

		            Response response = RestAssured
		                    .given()
		                    .header("Authorization", "Bearer " + getToken())
		                    .when()
		                    .get(BASE_URL + "/getById/notes/" + noteId);

		            String title = response.jsonPath().getString("data.title");
		            Assert.assertEquals(title, "Updated Note Title",
		                    "Title should reflect the updated value");
		        }
		    
		    //returned content reflects updated
		    @Test(
		            description    = "TC09_05: Returned data.content must reflect the updated value",
		            dependsOnMethods = "Com.ApiTest.UpdateNoteTest.updateNote_HappyPath"
		        )
		        public void updateNote_ContentUpdated() {

		            Response response = RestAssured
		                    .given()
		                    .header("Authorization", "Bearer " + getToken())
		                    .when()
		                    .get(BASE_URL + "/getById/notes/" + noteId);

		            String content = response.jsonPath().getString("data.content");
		            Assert.assertEquals(content, "Updated content via test",
		                    "Content should reflect the updated value");
		        }
		    
		    //last edited is updated
		    @Test(
		            description    = "TC09_06: lastEdited timestamp must be present and non-null after update",
		            dependsOnMethods = "Com.ApiTest.UpdateNoteTest.updateNote_HappyPath"
		        )
		        public void updateNote_LastEditedUpdated() {

		            JSONObject payload = new JSONObject();
		            payload.put("title", "Timestamp Check Update");

		            Response response = RestAssured
		                    .given()
		                    .header("Authorization", "Bearer " + getToken())
		                    .header("Content-Type", "application/json")
		                    .body(payload.toString())
		                    .when()
		                    .put(BASE_URL + "/update/notes/" + noteId);

		            String lastEdited = response.jsonPath().getString("data.lastEdited");
		            Assert.assertNotNull(lastEdited, "lastEdited must be present in update response");
		            Assert.assertFalse(lastEdited.isEmpty(), "lastEdited must not be empty");
		        }
		    
		    //updated color
		    @Test(
		            description    = "TC09_07: Updating only the color field should return 200 and reflect new color",
		            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		        )
		        public void updateNote_ColorOnly() {

		            JSONObject payload = new JSONObject();
		            payload.put("color", "#ff5722");

		            Response response = RestAssured
		                    .given()
		                    .header("Authorization", "Bearer " + getToken())
		                    .header("Content-Type", "application/json")
		                    .body(payload.toString())
		                    .when()
		                    .put(BASE_URL + "/update/notes/" + noteId);

		            Assert.assertEquals(response.getStatusCode(), 200,
		                    "Color-only update should return 200");

		            String returnedColor = response.jsonPath().getString("data.color");
		            Assert.assertEquals(returnedColor, "#ff5722",
		                    "Returned color must match the update value");
		        }

		    
		    //success flag
		    @Test(
		            description    = "TC09_8: success flag must be true in update response",
		            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
		        )
		        public void updateNote_SuccessFlag() {

		            JSONObject payload = new JSONObject();
		            payload.put("title", "Success Flag Check");

		            Response response = RestAssured
		                    .given()
		                    .header("Authorization", "Bearer " + getToken())
		                    .header("Content-Type", "application/json")
		                    .body(payload.toString())
		                    .when()
		                    .put(BASE_URL + "/update/notes/" + noteId);

		            Assert.assertEquals(response.getStatusCode(), 200);
		            Assert.assertTrue(response.jsonPath().getBoolean("success"),
		                    "success flag must be true");
		        }

}