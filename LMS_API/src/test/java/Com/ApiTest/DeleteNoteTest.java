package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class DeleteNoteTest extends BaseTest {

    @Test(
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
    }
}