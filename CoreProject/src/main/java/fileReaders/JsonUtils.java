package fileReaders;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class JsonUtils {


    public static JsonObject getJsonObject(String jsonFilePath) {
        JsonObject jsonObject = null;
        JsonParser parser = new JsonParser();
        try {
            Object obj = ((parser).parse(new FileReader(jsonFilePath)));
            jsonObject = (JsonObject) obj;
        } catch (Exception e) {

        }
        return jsonObject;
    }

    public static JSONObject getJSONObject(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException err) {
        }
        return jsonObject;
    }


    public static String getStringFromFile(String filePath) {
        String string = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            string = IOUtils.toString(fis, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }


    public static void writeToTextFile(String layoutJsonFilePath, String json) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(layoutJsonFilePath);
            out.println(json);
        } catch (Exception e) {

        } finally {
            if (out != null)
                out.close();
        }
    }
}
