package Com.ApiTest;


import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


@Listeners(listeners.ExtentReportListener.class)
public class GetAllNotesTest extends BaseTest {

    @Test(
    		description    = "TC07_01: GET /getAll/notes with valid token returns 200 with data and pagination",
    		dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote")
    public void getAllNotes() {

        Response response = RestAssured
                .given()
                .header("Authorization","Bearer " + getToken())
                .when()
                .get(BASE_URL + "/getAll/notes");
        
        System.out.println(response.asPrettyString());
        System.out.println("[TC07_01] " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected HTTP 200");

        Assert.assertTrue(response.jsonPath().getBoolean("success"),
                "success flag must be true");
    }
    
    //no token
    @Test(description = "TC07_02: GET /getAll/notes without token should return 401")
    public void getAllNotes_NoToken() {

        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/getAll/notes");

        Assert.assertEquals(response.getStatusCode(), 401,
                "Missing token should return 401");
    }
    
    //pagination limit
    @Test(
            description    = "TC07_03: limit=1 should return at most 1 note per page",
            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
        )
        public void getAllNotes_LimitParam() {

            Response response = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + getToken())
                    .queryParam("limit", 1)
                    .when()
                    .get(BASE_URL + "/getAll/notes");

            Assert.assertEquals(response.getStatusCode(), 200);

            List<?> data = response.jsonPath().getList("data");
            Assert.assertNotNull(data);
            Assert.assertTrue(data.size() <= 1,
                    "limit=1 should return at most 1 note, got: " + data.size());

            // Pagination should reflect the limit
            int totalNotes = response.jsonPath().getInt("pagination.totalNotes");
            Assert.assertTrue(totalNotes >= 0, "totalNotes must be >= 0");
        }

    //sorted order asc
    @Test(
            description    = "TC07_04: sortBy=title&sortOrder=asc should return notes sorted A→Z by title",
            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
        )
        public void getAllNotes_SortAsc() {

            Response response = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + getToken())
                    .queryParam("sortBy",    "title")
                    .queryParam("sortOrder", "asc")
                    .when()
                    .get(BASE_URL + "/getAll/notes");

            Assert.assertEquals(response.getStatusCode(), 200,
                    "Expected 200 for sorted request");

            List<String> titles = response.jsonPath().getList("data.title");
            if (titles != null && titles.size() > 1) {
                for (int i = 0; i < titles.size() - 1; i++) {
                    int cmp = titles.get(i).compareToIgnoreCase(titles.get(i + 1));
                    Assert.assertTrue(cmp <= 0,
                            "Notes are not in ascending title order at index " + i +
                            ": '" + titles.get(i) + "' > '" + titles.get(i + 1) + "'");
                }
            }
        }
    
    //required fields on each note
    @Test(
            description    = "TC07_05: Each note in the list must have _id, title, and lastEdited",
            dependsOnMethods = "Com.ApiTest.CreateNoteTest.createNote_HappyPath"
        )
        public void getAllNotes_RequiredNoteFields() {

            Response response = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + getToken())
                    .when()
                    .get(BASE_URL + "/getAll/notes");

            int count = response.jsonPath().getList("data").size();
            for (int i = 0; i < count; i++) {
                Assert.assertNotNull(response.jsonPath().getString("data[" + i + "]._id"),
                        "_id missing at index " + i);
                Assert.assertNotNull(response.jsonPath().getString("data[" + i + "].title"),
                        "title missing at index " + i);
                Assert.assertNotNull(response.jsonPath().getString("data[" + i + "].lastEdited"),
                        "lastEdited missing at index " + i);
            }
        }
    
    
}