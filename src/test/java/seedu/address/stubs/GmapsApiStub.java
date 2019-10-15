package seedu.address.stubs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import seedu.address.websocket.GmapsApi;

/**
 * This is for GmapsApi stubs
 */
public class GmapsApiStub extends GmapsApi {

    private JSONObject okApiResponse;
    private JSONObject zeroResultsApiResponse;
    private ArrayList<String> validLocationList = new ArrayList<>(Arrays.asList("NUS_FOO", "NUS_BAR"));

    public GmapsApiStub() {
        super();
        loadStubResponse();
    }

    @Override
    public JSONObject getLocation(String locationName) throws ConnectException {
        if (validLocationList.contains(locationName)) {
            return okApiResponse;
        } else {
            return zeroResultsApiResponse;
        }
    }

    /**
     * This method is used to load the stub api response
     */
    private void loadStubResponse() {
        try {
            JSONParser parser = new JSONParser();
            okApiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlacesOK.json"));
            zeroResultsApiResponse = (JSONObject) parser.parse(
                    new FileReader("/Users/tandeningklement/Desktop/codes/School/CS2103T_tP/main/src/"
                            + "test/data/ApiStubsTest/GmapsPlacesZERO_RESULTS.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
