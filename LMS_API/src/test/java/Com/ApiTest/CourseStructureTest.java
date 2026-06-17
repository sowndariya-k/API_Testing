package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CourseStructureTest extends BaseTest {

    @Test
    public void getCourseStructure() {

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .when()
                .get(BASE_URL + "/courses-structure/getAll");
        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}