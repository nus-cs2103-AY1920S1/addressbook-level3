package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import seedu.address.logic.internal.gmaps.GmapsJsonUtils;
import seedu.address.model.gmaps.Location;

class GmapsApiTest {

    @Test
    void getDistanceMatrix() throws ConnectException {
        ArrayList<Location> input = new ArrayList<>(Arrays.asList(new Location("foo"),
                new Location("foo"), new Location("foo"), new Location("foo")));
        assertThrows(ConnectException.class, () -> GmapsApi.getDistanceMatrix(input, input));
    }

    @Test
    void getLocation() throws ConnectException {
        JSONObject response = GmapsApi.getLocation("foo");
        assertEquals(GmapsJsonUtils.getStatus(response), "REQUEST_DENIED");
    }
}
