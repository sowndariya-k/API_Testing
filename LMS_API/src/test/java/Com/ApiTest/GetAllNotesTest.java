package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class GetAllNotesTest extends BaseTest {

    @Test(dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote")
    public void getAllNotes() {

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .when()
                .get(BASE_URL + "/getAll/notes");
        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}