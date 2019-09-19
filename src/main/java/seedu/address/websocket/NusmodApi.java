package seedu.address.websocket;

import com.fasterxml.jackson.core.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.address.logic.commands.CommandResult;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NusmodApi {
    private static final String BASEURL = "https://api.nusmods.com/v2";
    private static final String BASEURL2 = "http://api.nusmods.com/v2";

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

            try{
                // Establishing connection
                HttpsURLConnection conn = (HttpsURLConnection)query.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responsecode = conn.getResponseCode();
                if(responsecode != 200){
                    throw new RuntimeException("Connection Error!\n"
                            + "HttpsResponseCode: " + responsecode
                    );
                } else {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(query.openStream()))) {
                        String line;

                        //System.out.println("----------");
                        while((line = br.readLine()) != null) {
                            output += line;
                            System.out.println(line);
                        }
                    } catch (IOException ioe) {
                        System.out.println("invalid argument");
                        output += "Invalid Module Code";
                    }
                }

            } catch (IOException ioe){
                System.out.println("Failed to establish connection with " + BASEURL);
            }

        } catch (MalformedURLException mue) {
            System.out.println("invalid url");
        }

        try{
            // Parse into JSON object
            JSONParser parser = new JSONParser();
            obj = (JSONObject)parser.parse(output);
        } catch (ParseException pe){
            System.out.println("Failed to parse JSON object");
        }
        return obj;
    }

    
}
