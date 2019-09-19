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

/**
 * NUSMods API websocket
 * Refer to https://api.nusmods.com/v2/#/ for more detailed information
 */
public class NusmodApi {
    private final String BASEURL = "https://api.nusmods.com/v2";
    private final String BASEURL2 = "http://api.nusmods.com/v2";

    private final String ACAD_YEAR = "/2018-2019";
    private final String MODULES = "/modules";
    private final String SLASH = "/";
    private final String JSON_EXTENTION = ".json";
    private final String MODULE_LIST = "/moduleList";
    private final String MODULE_INFO = "/moduleInfo";
    private final String SEMESTERS = "/semesters";
    private final String VENUES = "/venues";
    private final String VENUE_INFO = "/venueInformation";

    public NusmodApi(){

    }

    /**
     * Returns a JSONObject of summaries of all modules in ACAD_YEAR
     * @return JSONObject
     */
    public JSONObject getModuleList(){
        JSONObject obj = null;
        try{
            URL query = new URL(BASEURL + ACAD_YEAR + MODULE_LIST + JSON_EXTENTION);
            System.out.println(query.toString());

            obj = parseQuery(query);
        } catch(MalformedURLException mue){
            System.out.println("Invalid URL");
        }
        return obj;
    }

    /**
     * Returns a JSONObject of detailed information about all modules in ACED_YEAR
     * @return
     */
    public JSONObject getModuleInfo(){
        JSONObject obj = null;
        try{
            URL query = new URL(BASEURL + ACAD_YEAR + MODULE_INFO + JSON_EXTENTION);
            System.out.println(query.toString());

            obj = parseQuery(query);
        } catch(MalformedURLException mue){
            System.out.println("Invalid URL");
        }
        return obj;
    }

    /**
     * Returns a JSONObject of all information about moduleCode in ACAD_YEAR
     * @param moduleCode    code of the specific module
     * @return JSONObject representing information of the module
     */
    public JSONObject getModules(String moduleCode){
        JSONObject obj = null;
        try{
            URL query = new URL(BASEURL + ACAD_YEAR + MODULES + SLASH + moduleCode + JSON_EXTENTION);
            System.out.println(query.toString());

            obj = parseQuery(query);
        } catch(MalformedURLException mue){
            System.out.println("Invalid URL");
        }
        return obj;
    }

    /**
     * Returns a JSONObject of summaries of all venues for the current semester
     * @param semester current semmester: 1 -> sem 1
     *                                    2 -> sem 2
     *                                    3 -> special term 1
     *                                    4 -> special term 2
     * @return JSONObject representing the venues
     */
    public JSONObject getVenues(String semester){
        JSONObject obj = null;
        try{
            URL query = new URL(BASEURL + ACAD_YEAR + SEMESTERS + semester + VENUES + JSON_EXTENTION);
            System.out.println(query.toString());

            obj = parseQuery(query);
        } catch(MalformedURLException mue){
            System.out.println("Invalid URL");
        }
        return obj;
    }

    /**
     * Returns a JSONObject of detailed infomation of all venues
     * @param semester current semmester: 1 -> sem 1
     *                                    2 -> sem 2
     *                                    3 -> special term 1
     *                                    4 -> special term 2
     * @return JSONObject representing the venues
     */
    public JSONObject getVenueInformation(String semester){
        JSONObject obj = null;
        try{
            URL query = new URL(BASEURL + ACAD_YEAR + SEMESTERS + semester + VENUE_INFO + JSON_EXTENTION);
            System.out.println(query.toString());

            obj = parseQuery(query);
        } catch(MalformedURLException mue){
            System.out.println("Invalid URL");
        }
        return obj;
    }

    private JSONObject parseQuery(URL query){
        String output = "";
        HttpsURLConnection conn = establishHttpsConnection(query);

        if(conn == null){
            System.out.println("Unable to establish connection, returning null object");
            return null;
        } else {
            try{
                int responsecode = conn.getResponseCode();
                if(responsecode != 200){
                    throw new RuntimeException("Connection Error! " + "HttpsResponseCode: " + responsecode);
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
