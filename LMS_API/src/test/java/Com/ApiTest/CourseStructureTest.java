package Com.ApiTest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@Listeners(listeners.ExtentReportListener.class)
public class CourseStructureTest extends BaseTest {

	 @Test(description = "TC05_01: GET /courses-structure/getAll with valid token returns 200")
    public void getCourseStructure() {

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .when()
                .get(BASE_URL + "/courses-structure/getAll");
        
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    //no token
    @Test(description = "TC05_02: No Authorization header should return 401")
    public void getCourseStructure_NoToken() {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/courses-structure/getAll");

        System.out.println("[TC05_02] Status: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 401,"Missing token should return 401");
    }
    
    //invalid token
    @Test(description = "TC05_03: Malformed token should return 401")
    public void getCourseStructure_InvalidToken() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer bad.token.value")
                .when()
                .get(BASE_URL + "/courses-structure/getAll");

        System.out.println("[TC05_03] Status: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 401,"Invalid token should return 401");
    }
    
    //success message
    @Test(description = "TC05_04: Response must contain success message")
    public void getCourseStructure_SuccessMessage() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(BASE_URL + "/courses-structure/getAll");

        String key   = response.jsonPath().getString("message[0].key");
        String value = response.jsonPath().getString("message[0].value");

        Assert.assertEquals(key, "success", "message[0].key should be 'success'");
        Assert.assertNotNull(value, "message[0].value must not be null");
    }
    
    //required fields on each course
    @Test(description = "TC05_05: Each course must have _id, courseName, courseCode, category, courseLevel, institution")
    public void getCourseStructure_RequiredFields() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(BASE_URL + "/courses-structure/getAll");

        int count = response.jsonPath().getList("data").size();
        Assert.assertTrue(count > 0, "No courses returned");

        for (int i = 0; i < count; i++) {
            String prefix = "data[" + i + "].";
            Assert.assertNotNull(response.jsonPath().getString(prefix + "_id"),"_id missing at index " + i);
            Assert.assertNotNull(response.jsonPath().getString(prefix + "courseName"),"courseName missing at index " + i);
            Assert.assertNotNull(response.jsonPath().getString(prefix + "courseCode"),"courseCode missing at index " + i);
            Assert.assertNotNull(response.jsonPath().getString(prefix + "category"),"category missing at index " + i);
            Assert.assertNotNull(response.jsonPath().getString(prefix + "courseLevel"),"courseLevel missing at index " + i);
            Assert.assertNotNull(response.jsonPath().getString(prefix + "institution"),"institution missing at index " + i);
        }
        

        }
    
  //course level value validate
    @Test(description = "TC05_06: courseLevel must be one of Beginner / Intermediate / Advanced")
    public void getCourseStructure_CourseLevelValues() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(BASE_URL + "/courses-structure/getAll");

        List<String> levels = response.jsonPath().getList("data.courseLevel");
        Assert.assertNotNull(levels, "courseLevel list must not be null");
        List<String> valid = List.of("Beginner", "Intermediate", "Advanced");
        for (String level : levels) {
            Assert.assertTrue(valid.contains(level),
                    "Unexpected courseLevel: '" + level + "'. Must be one of " + valid);
        }
    }
    
    
    
    }
