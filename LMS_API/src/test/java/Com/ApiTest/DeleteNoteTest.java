package Com.ApiTest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class DeleteNoteTest extends BaseTest {

	@Test(
	    description= "TC11_01: DELETE note Id should return 200",
        dependsOnMethods = {
            "Com.ApiTest.CreateNoteTest.createNote",
            "Com.ApiTest.UpdateNoteTest.updateNote",
            "Com.ApiTest.TogglePinTest.togglePin"
        }
    )
    public void deleteNote() {
        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .when()
                .delete(BASE_URL +"/delete/notes/ById/" + noteId);

        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("success"),
                "success flag must be true");

        int deletedCount = response.jsonPath().getInt("deletedCount");
        Assert.assertEquals(deletedCount, 1,
                "deletedCount must be 1 for a single note deletion");

        List<String> deletedIds = response.jsonPath().getList("deletedIds");
        Assert.assertNotNull(deletedIds, "deletedIds array must be present");
        Assert.assertTrue(deletedIds.contains(noteId),
                "deletedIds should contain the deleted note's _id");
    }
	
	
	//no token 
	@Test(
	        description= "TC11_02: DELETE without token should return 401",
	        dependsOnMethods= "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
	    )
	    public void deleteNote_NoToken() {

	        Response response = RestAssured
	                .given()
	                .when()
	                .delete(BASE_URL + "/delete/notes/ById/" + noteId);
	        Assert.assertEquals(response.getStatusCode(), 401,
	                "Missing token should return 401");
	    }
    
    //not existent ID 
    @Test(description = "TC11_03: Valid-format but non-existent note ID should return 404")
    public void deleteNote_NotFound() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .delete(BASE_URL + "/delete/notes/ById/000000000000000000000000");

        System.out.println("[TC11_03] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 404,
                "Non-existent note ID should return 404");
    }

}