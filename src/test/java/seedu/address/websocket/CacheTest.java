package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class CacheTest {

    private String placesJsonPath = "src/test/resources/ApiResponseCache/GoogleMapsApi/PlacesTest.json";

    @Test
    void saveToJson() {
    }

    @Test
    void loadFromJson() {
        assertThrows(NullPointerException.class, () -> Cache.loadFromJson("foo", placesJsonPath));
        String key = "https://maps.googleapis.com/maps/api/place/textsearch/json?location=.sg&query=NUS_AKI5B&";
        String expectedValue = "";
        JSONParser parser;
        parser = new JSONParser();
        try (Reader reader = new FileReader(placesJsonPath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            expectedValue = jsonObject.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(expectedValue, Cache.loadFromJson(key, placesJsonPath));

    }
}