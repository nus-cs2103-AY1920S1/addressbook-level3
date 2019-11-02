package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.gmaps.Location;

class UrlUtilTest {

    @Test
    void generateUrl() throws MalformedURLException {
        URL actualUrl = UrlUtil.generateUrl("https://www.google.com.sg");
        URL expectedUrl = new URL("https://www.google.com.sg");
        assertEquals(actualUrl, expectedUrl);
    }

    @Test
    void splitQuery() throws UnsupportedEncodingException {
        URL url = UrlUtil.generateUrl("https://www.google.com.sg?key1=value1&key2=value2");
        Map<String, String> mapping = UrlUtil.splitQuery(url);
        assertEquals("value1", mapping.get("key1"));
        assertEquals("value2", mapping.get("key2"));
    }

    @Test
    void sanitizeApiKey() {
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&size"
                + "=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7C"
                + "label:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&key=blahblahblah"
                + "blahblahblahblah";
        String expectedUrl = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&size"
                + "=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7C"
                + "label:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&";
        assertTrue(UrlUtil.sanitizeApiKey(url).equals(expectedUrl));
    }

    @Test
    void generateGmapsPlacesUrl() {
        String expectedUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?location=.sg"
                + "&inputtype=textquery&fields=name,place_id&input=qwerty&key=&";
        assertEquals(UrlUtil.generateGmapsPlacesUrl("qwerty"), expectedUrl);
    }

    @Test
    void generateGmapsPlaceDetailsUrl() {
        String expected = "https://maps.googleapis.com/maps/api/place/details/json?"
                + "place_id=ChIJBeHqfnAb2jERL1OoMUzA7yE&key=&";
        assertEquals(UrlUtil.generateGmapsPlaceDetailsUrl("ChIJBeHqfnAb2jERL1OoMUzA7yE"), expected);
    }

    @Test
    void generateDistanceMatrixUrl() {
        ArrayList<Location> tooLongStringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tooLongStringList.add(new Location("qwert"));
        }
        assertThrows(InvalidParameterException.class, () ->
                UrlUtil.generateGmapsDistanceMatrixUrl(tooLongStringList, tooLongStringList));

        Location lt17 = new Location("LT17");
        lt17.setPlaceId("ChIJBeHqfnAb2jERL1OoMUzA7yE");
        Location lt13 = new Location("LT13");
        lt13.setPlaceId("ChIJoViMIbYb2jERQbzhdcWZAc4");
        Location lt14 = new Location("LT14");
        lt14.setPlaceId("ChIJBRIdw1Yb2jER_w4k6qhkwAk");
        ArrayList<Location> arg = new ArrayList<>(Arrays.asList(lt17, lt13, lt14));
        String expectedUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="
                + "place_id:ChIJBeHqfnAb2jERL1OoMUzA7yE|place_id:ChIJoViMIbYb2jERQbzhdcWZAc4|place_id:"
                + "ChIJBRIdw1Yb2jER_w4k6qhkwAk|&destinations=place_id:ChIJBeHqfnAb2jERL1OoMUzA7yE|place_id:"
                + "ChIJoViMIbYb2jERQbzhdcWZAc4|place_id:ChIJBRIdw1Yb2jER_w4k6qhkwAk|&key=";
        assertEquals(UrlUtil.generateGmapsDistanceMatrixUrl(arg, arg), expectedUrl);
    }

    @Test
    void generateGmapsStaticImage() {
        String expectedUrl = "https://maps.googleapis.com/maps/api/staticmap?size=500x500&zoom=17"
                + "&markers=color:red|size:large|label:L|1.2345,5.678&center=1.2345,5.678&key=&";
        assertEquals(UrlUtil.generateGmapsStaticImage("1.2345,5.678"), expectedUrl);
    }

    @Test
    void isGmapsKeyPresent() {
        assertFalse(UrlUtil.isGmapsKeyPresent());
    }
}

