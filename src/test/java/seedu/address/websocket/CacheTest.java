package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import seedu.address.logic.internal.gmaps.GmapsJsonUtils;
import seedu.address.model.gmaps.Location;
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
                "{2014/2015=SEMESTER_1=2014-08-11, 2014/2015=SEMESTER_2=2015-01-12, 2018/2019=SEMESTER_1="
                        + "2018-08-13, 2018/2019=SEMESTER_2=2019-01-14, 2015/2016=SEMESTER_1=2015-08-10, 2015/2016"
                        + "=SEMESTER_2=2016-01-11, 2016/2017=SEMESTER_1=2016-08-08, 2016/2017=SEMESTER_2=2017-01-09, "
                        + "2019/2020=SEMESTER_1=2019-08-12, 2019/2020=SEMESTER_2=2020-01-13, 2017/2018=SEMESTER_1="
                        + "2017-08-14, 2017/2018=SEMESTER_2=2018-01-15}");
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
        assertTrue(moduleList.toString().contains("AY2019/2020 CS2103T Software Engineering\n"));
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
        JSONObject placeResponse2 = Cache.loadPlaces("FOOB");
        assertEquals("REQUEST_DENIED", GmapsJsonUtils.getStatus(placeResponse2));
    }

    @Test
    void loadDistanceMatrix() {
        ArrayList<Location> input = new ArrayList<Location>(
                Arrays.asList(new Location("Bar"), new Location("Bar"),
                        new Location("Bar")));
        JSONObject distanceMatrixResponse = Cache.loadDistanceMatrix(input, input);
        assertEquals(distanceMatrixResponse, new JSONObject());
    }

    @Test
    public void imagePath() {
        String expectedPath = Cache.imagePath("NUS_foo");
        assertTrue(expectedPath.contains("NUS_foo.png"));
    }
}
