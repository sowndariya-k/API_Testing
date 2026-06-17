package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class InstitutionTest extends BaseTest {

    @Test
    public void getAllInstitution() {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/getAll/institution");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}