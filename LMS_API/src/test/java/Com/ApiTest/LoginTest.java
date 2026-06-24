package Com.ApiTest;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class LoginTest extends BaseTest {
	
	private static final String LOGIN_URL = BASE_URL + "/user/login";

	@Test(description = "TC02_01: Valid credentials should return 201 with a token")
    public void login_ValidCredentials() {

        JSONObject payload = new JSONObject();
        payload.put("email",    "sam@gmail.com");
        payload.put("password", "123");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(LOGIN_URL);

        System.out.println("[TC02_01] " + response.asPrettyString());

        // Status
        Assert.assertEquals(response.getStatusCode(), 201,
                "Expected HTTP 201 on successful login");

        // Token exists and is non-empty
        String tkn = response.jsonPath().getString("token");
        Assert.assertNotNull(tkn, "token field must not be null");
        Assert.assertFalse(tkn.isEmpty(), "token must not be empty");

        // Success message
        String msgValue = response.jsonPath().getString("message[0].value");
        Assert.assertNotNull(msgValue, "message[0].value must be present");
        Assert.assertTrue(msgValue.toLowerCase().contains("success"),
                "message should indicate success, got: " + msgValue);

        // User sub-object
        String role = response.jsonPath().getString("user.role.originalRole");
        Assert.assertEquals(role, "Admin", "Logged-in user should have role Admin");
    }
	
	
	//wrong password
	@Test(description = "TC02_02: Wrong password should return 400")
    public void login_WrongPassword() {

        JSONObject payload = new JSONObject();
        payload.put("email",    "sam@gmail.com");
        payload.put("password", "wrongpassword");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(LOGIN_URL);

        System.out.println("[TC02_02] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 400,
                "Wrong password should return 400");

        String body = response.asString();
        Assert.assertTrue(body.contains("incorrect") || body.contains("invalid") || body.contains("Password"),
                "Error message should mention password issue, got: " + body);
    }
	
	
	//invalid email
	@Test(description = "TC02_03: Unknown email should return 400")
    public void login_InvalidEmail() {

        JSONObject payload = new JSONObject();
        payload.put("email",    "unknown@notexist.com");
        payload.put("password", "123");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(LOGIN_URL);

        System.out.println("[TC02_03] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 400,
                "Unknown email should return 400");

        String body = response.asString();
        Assert.assertTrue(body.contains("invalid") || body.contains("Email") || body.contains("not found"),
                "Error should mention email issue, got: " + body);
    }
	
	
	
    //Missing email
	
    @Test(description = "TC02_04: Missing email field should return 400")
    public void login_MissingEmail() {

        JSONObject payload = new JSONObject();
        payload.put("password", "123");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(LOGIN_URL);

        System.out.println("[TC02_04] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 400,
                "Missing email should return 400");
    }

    
    //Missing password
    @Test(description = "TC02_05: Missing password field should return 400")
    public void login_MissingPassword() {

        JSONObject payload = new JSONObject();
        payload.put("email", "sam@gmail.com");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(LOGIN_URL);

        System.out.println("[TC02_05] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 400,
                "Missing password should return 400");
    }

    
    // Empty body
    @Test(description = "TC02_06: Empty body should return 400")
    public void login_EmptyBody() {

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{}")
                .when()
                .post(LOGIN_URL);

        System.out.println("[TC02_06] Status: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 400,
                "Empty body should return 400");
    }

   //Response body 
    @Test(description = "TC02_07: Response body must contain userId, institution, institutionName")
    public void login_ResponseBodyFields() {

        JSONObject payload = new JSONObject();
        payload.put("email",    "sam@gmail.com");
        payload.put("password", "123");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(LOGIN_URL);

        Assert.assertNotNull(response.jsonPath().getString("userId"),
                "userId must be present");
        Assert.assertNotNull(response.jsonPath().getString("institution"),
                "institution must be present");
        Assert.assertNotNull(response.jsonPath().getString("institutionName"),
                "institutionName must be present");
        Assert.assertEquals(response.jsonPath().getString("user.email"), "sam@gmail.com",
                "Returned email should match the login email");
    }

}