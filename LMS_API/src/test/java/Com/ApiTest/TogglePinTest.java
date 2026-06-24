package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class TogglePinTest extends BaseTest {

	
	//isPinned false to true
	@Test(
	        description    = "TC10_01: First toggle should set isPinned to true",
	        dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
	    )
	    public void togglePin_FirstToggle_PinsNote() {

	        // Ensure note starts as unpinned (reset if needed)
	        // Just call toggle once
	        Response response = RestAssured
	                .given()
	                .header("Authorization", "Bearer " + getToken())
	                .when()
	                .put(BASE_URL + "/toggle-pin/notes/" + noteId);

	        System.out.println("[TC10_01] " + response.asPrettyString());

	        Assert.assertEquals(response.getStatusCode(), 200,
	                "Expected HTTP 200 on toggle-pin");

	        Boolean isPinned = response.jsonPath().getBoolean("data.isPinned");
	        Assert.assertNotNull(isPinned, "data.isPinned must be present");
	        // After first toggle from false, must be true
	        Assert.assertTrue(isPinned,
	                "First toggle should set isPinned to true (note was created with isPinned=false)");
	    }


	    //Second toggle (true to false)

	    @Test(
	        description    = "TC10_02: Second toggle should set isPinned back to false",
	        dependsOnMethods = "Com.ApiTest.TogglePinTest.togglePin_FirstToggle_PinsNote"
	    )
	    public void togglePin_SecondToggle_UnpinsNote() {

	        Response response = RestAssured
	                .given()
	                .header("Authorization", "Bearer " + getToken())
	                .when()
	                .put(BASE_URL + "/toggle-pin/notes/" + noteId);

	        System.out.println("[TC10_02] " + response.asPrettyString());

	        Assert.assertEquals(response.getStatusCode(), 200,
	                "Expected HTTP 200 on second toggle");

	        Boolean isPinned = response.jsonPath().getBoolean("data.isPinned");
	        Assert.assertFalse(isPinned,
	                "Second toggle should set isPinned back to false");
	    }
	    
	    //no token 
	    @Test(
	            description    = "TC10_03: Toggle without token should return 401",
	            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
	        )
	        public void togglePin_NoToken() {

	            Response response = RestAssured
	                    .given()
	                    .when()
	                    .put(BASE_URL + "/toggle-pin/notes/" + noteId);

	            Assert.assertEquals(response.getStatusCode(), 401,
	                    "Missing token should return 401");
	        }
	    
	    //non -existent id
	    @Test(description = "TC10_04: Toggle with a non-existent note ID should return 404")
	    public void togglePin_NotFound() {

	        Response response = RestAssured
	                .given()
	                .header("Authorization", "Bearer " + getToken())
	                .when()
	                .put(BASE_URL + "/toggle-pin/notes/000000000000000000000000");

	        System.out.println("[TC10_04] Status: " + response.getStatusCode());

	        Assert.assertEquals(response.getStatusCode(), 404,
	                "Non-existent note ID should return 404");
	    }
	    
	    //success flag
	    @Test(
	            description    = "TC10_05: success flag must be true in toggle response",
	            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
	        )
	        public void togglePin_SuccessFlag() {

	            Response response = RestAssured
	                    .given()
	                    .header("Authorization", "Bearer " + getToken())
	                    .when()
	                    .put(BASE_URL + "/toggle-pin/notes/" + noteId);

	            Assert.assertEquals(response.getStatusCode(), 200);
	            Assert.assertTrue(response.jsonPath().getBoolean("success"),
	                    "success must be true");

	            // Toggle back to restore state
	            RestAssured.given()
	                    .header("Authorization", "Bearer " + getToken())
	                    .put(BASE_URL + "/toggle-pin/notes/" + noteId);
	        }
	    
	    //last edited updated
	    @Test(
	            description    = "TC10_06: lastEdited must be present and non-null after toggle",
	            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
	        )
	        public void togglePin_LastEditedPresent() {

	            Response response = RestAssured
	                    .given()
	                    .header("Authorization", "Bearer " + getToken())
	                    .when()
	                    .put(BASE_URL + "/toggle-pin/notes/" + noteId);

	            String lastEdited = response.jsonPath().getString("data.lastEdited");
	            Assert.assertNotNull(lastEdited, "lastEdited must be present after toggle");
	            Assert.assertFalse(lastEdited.isEmpty(), "lastEdited must not be empty");

	            // Restore
	            RestAssured.given()
	                    .header("Authorization", "Bearer " + getToken())
	                    .put(BASE_URL + "/toggle-pin/notes/" + noteId);
	        }

}