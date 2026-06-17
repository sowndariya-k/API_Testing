package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TogglePinTest extends BaseTest {

    @Test(dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote")
    public void togglePin() {

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .when()
                .put(BASE_URL +
                        "/toggle-pin/notes/" + noteId);
        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}