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
    void getDistanceMatrixInvalidUrl() {
        ArrayList<Location> input = new ArrayList<>(Arrays.asList(new Location("foo"),
                new Location("foo"), new Location("foo"), new Location("foo")));
        assertThrows(ConnectException.class, () -> GmapsApi.getDistanceMatrix(input, input));
    }

    @Test
    void getDistanceMatrixNoApiKey() throws ConnectException {
        Location location = new Location("foo");
        location.setPlaceId("ChIJc9Y2SFca2jERUd33B_8nY6s");
        ArrayList<Location> input = new ArrayList<>(Arrays.asList(location, location, location, location));
        JSONObject response = GmapsApi.getDistanceMatrix(input, input);
        assertEquals(GmapsJsonUtils.getStatus(response), "REQUEST_DENIED");
    }

    @Test
    void getLocation() throws ConnectException {
        JSONObject response = GmapsApi.getLocation("foo");
        assertEquals(GmapsJsonUtils.getStatus(response), "REQUEST_DENIED");
    }

    @Test
    void getPlaceDetailsNoApiKey() throws ConnectException {
        JSONObject response = GmapsApi.getPlaceDetails("HEXADECIMAL_HASH");
        assertEquals(GmapsJsonUtils.getStatus(response), "REQUEST_DENIED");
    }

    @Test
    void getPlaceDetailsInvalidUrl() {
        assertThrows(ConnectException.class, ()-> GmapsApi.getPlaceDetails("HEXADECIMAL HASH"));
    }
}
