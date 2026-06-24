package Com.ApiTest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class InstitutionTest extends BaseTest {
	
	private static final String INST_URL = BASE_URL + "/getAll/institution";

    @Test
    public void getAllInstitution() {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/getAll/institution");
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
        List<?> list = response.jsonPath().getList("getAllInstitution");
        Assert.assertNotNull(list, "getAllInstitution array must be present");
        Assert.assertFalse(list.isEmpty(), "getAllInstitution must not be empty");
    }
    
    //success message
    @Test(description = "TC03_02: Response should contain a success message")
    public void getInstitution_SuccessMessage() {

        Response response = RestAssured
                .given()
                .when()
                .get(INST_URL);

        String msgKey = response.jsonPath().getString("message[0].key");
        Assert.assertEquals(msgKey, "success",
                "message[0].key should be 'success'");

        String msgValue = response.jsonPath().getString("message[0].value");
        Assert.assertNotNull(msgValue, "message[0].value must not be null");
        Assert.assertTrue(msgValue.toLowerCase().contains("retrieved") ||
                           msgValue.toLowerCase().contains("success"),
                "message should indicate successful retrieval, got: " + msgValue);
    }

    //required fields 
    @Test(description = "TC03_03: Each institution object must have inst_id, inst_name and address")
    public void getInstitution_RequiredFields() {

        Response response = RestAssured
                .given()
                .when()
                .get(INST_URL);

        int count = response.jsonPath().getList("getAllInstitution").size();
        Assert.assertTrue(count > 0, "No institutions returned");

        for (int i = 0; i < count; i++) {
            String instId   = response.jsonPath().getString("getAllInstitution[" + i + "].inst_id");
            String instName = response.jsonPath().getString("getAllInstitution[" + i + "].inst_name");
            String address  = response.jsonPath().getString("getAllInstitution[" + i + "].address");
            String id       = response.jsonPath().getString("getAllInstitution[" + i + "]._id");

            Assert.assertNotNull(instId,   "inst_id must be present at index " + i);
            Assert.assertNotNull(instName, "inst_name must be present at index " + i);
            Assert.assertNotNull(address,  "address must be present at index " + i);
            Assert.assertNotNull(id,       "_id must be present at index " + i);
        }
    }
    
    //public endpoint without token
    @Test(description = "TC03_04: Endpoint is public — request without Authorization returns 200")
    public void getInstitution_NoAuthRequired() {

        Response response = RestAssured
                .given()
                // intentionally no Authorization header
                .when()
                .get(INST_URL);

        Assert.assertEquals(response.getStatusCode(), 200,
                "Public endpoint should be accessible without a token");
    }
    
    //response time
    @Test(description = "TC03_05: GET /getAll/institution should respond within 5000 ms")
    public void getInstitution_ResponseTime() {

        Response response = RestAssured
                .given()
                .when()
                .get(INST_URL);

        long timeMs = response.getTime();
        System.out.println("[TC03_05] Response time: " + timeMs + " ms");

        Assert.assertTrue(timeMs < 5000,
                "Response time exceeded 5000 ms; actual: " + timeMs + " ms");
    }
}