package junit;
import junit.Model.JSON;
import org.json.JSONObject;
import java.io.FileNotFoundException;

public class MockJSON extends JSON {

    //MockOBJECT to "simulate" a database while JSON Database does not exist.

    public static JSONObject addUserPuntuation() throws FileNotFoundException {
        String json_information = "{\"Test_Add\":10}";
        JSONObject testMockJSON = new JSONObject(json_information);
        return testMockJSON;
    }

    public static JSONObject getUsersPuntuations(){
        String json_information = "{\"Test_Get\":10}";
        return (new JSONObject(json_information));
    }

    public void updateUserPuntuation(){
        //do nothing
         }

}
