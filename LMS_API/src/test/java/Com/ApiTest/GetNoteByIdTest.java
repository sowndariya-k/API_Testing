package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetNoteByIdTest extends BaseTest {

    @Test(dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote")
    public void getNoteById() {

        Response response = RestAssured
                .given()
                .header("Authorization",
                        "Bearer " + getToken())
                .when()
                .get(BASE_URL +
                        "/getById/notes/" + noteId);
        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}