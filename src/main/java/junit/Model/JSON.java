package junit.Model;
import org.json.JSONObject;

import java.io.*;

public class JSON {


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
