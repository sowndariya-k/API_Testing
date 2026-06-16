package Com.ApiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class GetTest {
    // GET Valid Trainee
    @Test
    public void getTrainee_ValidId_ShouldReturnOK() {

        int statusCode = RestAssured
                .given()
                .when()
                .get("http://localhost:3000/trainee/2")
                .getStatusCode();

        System.out.println("GET Valid Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }


    // GET  Check name field of trainee id=2

    @Test
    public void getTrainee_ValidId_ShouldReturnCorrectName() {

        String name = RestAssured
                .given()
                .when()
                .get("http://localhost:3000/trainee/2")
                .jsonPath()
                .getString("name");

        System.out.println("Trainee Name: " + name);
        Assert.assertEquals(name, "Sowndariya");
    }

    // GET - Invalid Trainee (404 Not Found)

    @Test
    public void getTrainee_InvalidId_ShouldReturnNotFound() {

        int statusCode = RestAssured
                .given()
                .when()
                .get("http://localhost:3000/trainee/99999")
                .getStatusCode();

        System.out.println("GET Invalid Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
}