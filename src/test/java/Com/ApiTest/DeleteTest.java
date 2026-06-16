package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class DeleteTest {

    @Test
    public void deleteTrainee_ValidId_ShouldReturnOK() {

        int statusCode = RestAssured
                .given()
                .when()
                .delete("http://localhost:3000/trainee/my541FkJGGY")
                .getStatusCode();

        System.out.println("DELETE Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
}