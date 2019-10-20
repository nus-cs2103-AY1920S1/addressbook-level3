package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UrlUtilTest {

    @Test
    void sanitizeApiKey() {
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&size" +
                "=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7C" +
                "label:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&key=blahblahblah" +
                "blahblahblahblah";
        String expectedUrl = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&size" +
                "=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7C" +
                "label:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&";
        assertTrue(UrlUtil.sanitizeApiKey(url).equals(expectedUrl));
    }
}