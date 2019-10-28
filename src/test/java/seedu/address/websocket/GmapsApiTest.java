package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import seedu.address.logic.internal.gmaps.GmapsJsonUtils;

class GmapsApiTest {

    @Test
    void getDistanceMatrix() throws ConnectException {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("foo", "foo", "foo", "foo", "foo"));
        JSONObject response = GmapsApi.getDistanceMatrix(input, input);
        assertEquals(GmapsJsonUtils.getStatus(response), "REQUEST_DENIED");
    }

    @Test
    void getLocation() throws ConnectException {
        JSONObject response = GmapsApi.getLocation("foo");
        assertEquals(GmapsJsonUtils.getStatus(response), "REQUEST_DENIED");
    }
}
