package junit.Model;
import org.json.JSONObject;

import java.io.*;

public class JSON {


  //Function where we load the JSON file. We create a BufferReader to
  //read the file. At the same time an JSON Object is created where the
  //data of the "database.json" will be saved.

  public JSONObject loadJSONFile(){
    String json_information = "";
    try {
      BufferedReader file = new BufferedReader(new FileReader("database.json"));
      json_information = file.readLine();
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (new JSONObject(json_information));
  }

  //The following function save the player username and his/her puntuation
  //in a json Object. The json object is saved in a new created file.
  //If the username already exists in the database we save it in case his new
  //puntuation is greater. In the other hand, it is not saved.

  public void saveJSONFile(String username, int puntuation) throws IOException {


    JSONObject json = loadJSONFile();
    if(json.isNull(username)){
      json.put(username,puntuation);
    }else if((int)json.get(username) < puntuation){
      json.put(username,puntuation);
    }
    FileWriter file = new FileWriter("database.json");
    file.write(json.toString());
    file.flush();
  }


}
