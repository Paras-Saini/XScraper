package demo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJson {
    public static boolean toJson(List<HashMap<String, Object>> fatchData, String fileName) {
        boolean status = false;
        ObjectMapper mapper = new ObjectMapper();
        // Converting map to a JSON payload as string
        try {
            String employeePrettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fatchData);
            System.out.println(employeePrettyJson);
            status = true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            status = false;
        }

        String userDir = System.getProperty("user.dir");

        // Writing JSON on a file
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(userDir + "\\src\\test\\resources\\" + fileName + ".json"), fatchData);
            status = true;
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }
}
