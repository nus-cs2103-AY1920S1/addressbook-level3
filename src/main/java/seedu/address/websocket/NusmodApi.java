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

    public NusmodApi(){

    }

    public JSONObject getModules(String moduleCode){
        String output = "";
        try {
            // Generate URL
            URL query = new URL(BASEURL + ACAD_YEAR + MODULES + SLASH + moduleCode + JSON_EXTENTION);
            System.out.println(query.toString());

            HttpsURLConnection conn = establishHttpsConnection(query);

            if(conn == null){
                System.out.println("Unable to establish connection, returning null object");
                return null;
            } else {
                try{
                    int responsecode = conn.getResponseCode();
                    if(responsecode != 200){
                        throw new RuntimeException("Connection Error!\n"
                                + "HttpsResponseCode: " + responsecode
                        );
                    } else {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(query.openStream()))) {
                            String line;

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
                    System.out.println("Connection error");
                }
            }
            conn.disconnect();

        } catch (MalformedURLException mue) {
            System.out.println("invalid url");
        }
        return parser(output);
    }

    private JSONObject parser(String s){
        JSONObject obj = null;
        try{
            JSONParser parser = new JSONParser();
            obj = (JSONObject)parser.parse(s);
        } catch (ParseException pe){
            System.out.println("Failed to parse JSON object");
        }
        return obj;
    }

    private HttpsURLConnection establishHttpsConnection(URL url){
        HttpsURLConnection conn = null;
        try{
            conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
        } catch(IOException ioe){
            System.out.println("Failed to establish connection with " + url.toString());
        }
        return conn;
    }
}
