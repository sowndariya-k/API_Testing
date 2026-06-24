package Com.ApiTest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class RolesTest extends BaseTest {
	
	private static final String ROLES_URL = BASE_URL + "/roles/getAll";

	@Test(description = "TC04_01: GET /roles/getAll with valid token returns 200 and non-empty list")
    public void getRoles_HappyPath() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(ROLES_URL);

        System.out.println("[TC04_01] " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected HTTP 200");

        List<?> roles = response.jsonPath().getList("roles");
        Assert.assertNotNull(roles, "roles array must be present");
        Assert.assertFalse(roles.isEmpty(), "roles list must not be empty");
    }
	
	//no token 
	
	@Test(description = "TC04_02: Request without Authorization header should return 401")
    public void getRoles_NoToken() {

        Response response = RestAssured
                .given()
                .when()
                .get(ROLES_URL);

        System.out.println("[TC04_02] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 401,
                "Missing token should return 401");

        String body = response.asString();
        Assert.assertTrue(body.contains("not logged in") || body.contains("unauthorized") ||
                           body.contains("Unauthorized") || body.contains("error"),
                "Error body should indicate authentication failure, got: " + body);
    }


    //Invalid token

    @Test(description = "TC04_03: Invalid/malformed token should return 401")
    public void getRoles_InvalidToken() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer this.is.notavalidtoken")
                .when()
                .get(ROLES_URL);

        System.out.println("[TC04_03] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 401,
                "Invalid token should return 401");
    }


    //Success message key
    @Test(description = "TC04_04: Success message key must equal 'success'")
    public void getRoles_SuccessMessageKey() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(ROLES_URL);

        String key = response.jsonPath().getString("message[0].key");
        Assert.assertEquals(key, "success",
                "message[0].key should be 'success'");
    }

    
    
    //Required fields on each role
    
    @Test(description = "TC04_05: Each role must have _id, originalRole and roleValue")
    public void getRoles_RequiredFields() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(ROLES_URL);

        int count = response.jsonPath().getList("roles").size();
        Assert.assertTrue(count > 0, "No roles returned");

        for (int i = 0; i < count; i++) {
            String id           = response.jsonPath().getString("roles[" + i + "]._id");
            String originalRole = response.jsonPath().getString("roles[" + i + "].originalRole");
            String roleValue    = response.jsonPath().getString("roles[" + i + "].roleValue");

            Assert.assertNotNull(id,           "_id must be present at index " + i);
            Assert.assertNotNull(originalRole, "originalRole must be present at index " + i);
            Assert.assertNotNull(roleValue,    "roleValue must be present at index " + i);
            Assert.assertFalse(originalRole.isEmpty(), "originalRole must not be empty at index " + i);
            Assert.assertFalse(roleValue.isEmpty(),    "roleValue must not be empty at index " + i);
        }
    }
    
    //known role present
    @Test(description = "TC04_06: Roles list must include Admin, Student, and Program Coordinator")
    public void getRoles_KnownRolesPresent() {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(ROLES_URL);

        List<String> originalRoles = response.jsonPath().getList("roles.originalRole");
        Assert.assertNotNull(originalRoles, "roles.originalRole list must not be null");

        Assert.assertTrue(originalRoles.contains("Admin"),
                "Roles should contain 'Admin'");
        Assert.assertTrue(originalRoles.contains("Student"),
                "Roles should contain 'Student'");
        Assert.assertTrue(originalRoles.contains("Program Coordinator"),
                "Roles should contain 'Program Coordinator'");
    }

}