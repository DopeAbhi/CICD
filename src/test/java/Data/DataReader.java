package Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader {
//Not Using in Framework
    public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
        //This is inside the common.io dependency
        //UTF 8 is Standard to Convert JSON file to String
    String jsonContent=    FileUtils.readFileToString(new File(System.getProperty("user.dir")+"/src/test/java/Data/PurchaseOrder.json"),
            StandardCharsets.UTF_8);

        //To Convert String into HashMap Jackson DataBind Dependency is needed
        ObjectMapper mapper = new ObjectMapper();
      List<HashMap<String,String>> data=  mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
        });
      return data;


    }
}
