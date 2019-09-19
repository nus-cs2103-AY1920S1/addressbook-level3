package seedu.address.websocket;

import com.fasterxml.jackson.core.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.address.logic.commands.CommandResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NusmodApi {
    private static final String BASEURL = "https://api.nusmods.com/v2";
    private static final String ACAD_YEAR = "/2018-2019";
    private static final String MODULES = "/modules";
    private static final String SLASH = "/";
    private static final String JSON_EXTENTION = ".json";

    public static JSONObject getModules(String moduleCode){
        JSONObject obj = null;
        String output = "";
        try {
            // Generate URL
            URL query = new URL(BASEURL + ACAD_YEAR + MODULES + SLASH + moduleCode + JSON_EXTENTION);
            System.out.println(query.toString());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(query.openStream()))) {
                String line;

                //System.out.println("----------");
                while((line = in.readLine()) != null) {
                    output += line;
                    System.out.println(line);
                }
            } catch (IOException ioe) {
                System.out.println("invalid argument");
                output += "Invalid Module Code";
            }
        } catch (MalformedURLException mue) {
            System.out.println("invalid url");
        }

        try{
            JSONParser parser = new JSONParser();
            obj = (JSONObject)parser.parse(output);
        } catch (ParseException pe){
            System.out.println("Failed to parse JSON object");
        }
        return obj;
    }
}
