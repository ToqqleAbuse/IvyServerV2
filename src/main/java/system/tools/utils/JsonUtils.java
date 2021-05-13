package system.tools.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {


    public static boolean validJson(String jsonData) {
        try {
            new JSONObject(jsonData);
        } catch (JSONException exception) {
            return false;
        }
        return true;
    }


}
