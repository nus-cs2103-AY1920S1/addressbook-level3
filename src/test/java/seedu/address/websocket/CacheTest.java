package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import seedu.address.logic.internal.gmaps.GmapsJsonUtils;
import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleList;
import seedu.address.model.module.ModuleSummaryList;

class CacheTest {

    private String placesJsonPath = "src/test/resources/ApiResponseCache/GoogleMapsApi/PlacesTest.json";

    @Test
    void loadHoliday() {
        Holidays holidays = Cache.loadHolidays().get();
        assertTrue(holidays.toString().contains("2019-01-01"));
    }

    @Test
    void loadAcadCalendar() {
        AcadCalendar acadCalendar = Cache.loadAcadCalendar().get();
        assertEquals(acadCalendar.toString(),
                "{2014/2015 Sem 1=2014-8-11, 2014/2015 Sem 2=2015-1-12, 2018/2019 Sem 1=2018-8-13, 2018/2019 "
                        + "Sem 2=2019-1-14, 2015/2016 Sem 1=2015-8-10, 2015/2016 Sem 2=2016-1-11, 2016/2017 "
                        + "Sem 1=2016-8-8, 2016/2017 Sem 2=2017-1-9, 2019/2020 Sem 1=2019-8-12, 2019/2020 "
                        + "Sem 2=2020-1-13, 2017/2018 Sem 1=2017-8-14, 2017/2018 Sem 2=2018-1-15}");
    }

    @Test
    void loadModuleSummaryList() {
        ModuleSummaryList moduleSummaryList = Cache.loadModuleSummaryList().get();
        assertTrue(moduleSummaryList.toString()
                .contains("AY2019/2020 ZB4199 Honours Project in Computational Biology"));
    }

    @Test
    void loadModuleList() {
        ModuleList moduleList = Cache.loadModuleList().get();
        assertTrue(moduleList.toString().contains("AY2019/2020 LL4287V ASEAN Law and Policy"));
    }

    @Test
    void loadModule() {
        Module module = Cache.loadModule(new ModuleId("AY2019/2020", "CS2103T")).get();
        assertEquals(module.toString(), "AY2019/2020 CS2103T Software Engineering");
    }

    @Test
    void loadVenues() {
        JSONArray venues = Cache.loadVenues();
        assertEquals(556, venues.size());
    }

    @Test
    void loadPlaces() {
        JSONObject placeResponse1 = Cache.loadPlaces("NUS_LT17");
        assertEquals("OK", GmapsJsonUtils.getStatus(placeResponse1));
        JSONObject placeResponse2 = Cache.loadPlaces("LT17");
        assertEquals("REQUEST_DENIED", GmapsJsonUtils.getStatus(placeResponse2));
    }

    @Test
    void loadDistanceMatrix() {
        ArrayList<String> input = new ArrayList<String>(Arrays.asList("Foo", "Foo", "Foo"));
        JSONObject distanceMatrixResponse = Cache.loadDistanceMatrix(input, input);
        assertEquals("REQUEST_DENIED", GmapsJsonUtils.getStatus(distanceMatrixResponse));
    }

    @Test
    void saveToJson() {
        Cache.saveToJson("key", "value", placesJsonPath);
        assertEquals(Cache.loadFromJson("key", placesJsonPath), "value");
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
