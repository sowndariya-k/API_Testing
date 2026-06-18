package Com.Payload;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Listeners;


@Listeners(listeners.ExtentReportListener.class)
public class NotePayload {

    public static JSONObject createNotePayload() {

        JSONObject payload = new JSONObject();

        payload.put("title", "API Test Note");
        payload.put("content", "Created by Tester");
        payload.put("isPinned", false);
        payload.put("color", "#ffeb3b");

        JSONArray tags = new JSONArray();
        tags.put("qa");
        tags.put("demo");

        payload.put("tags", tags);

        return payload;
    }
}