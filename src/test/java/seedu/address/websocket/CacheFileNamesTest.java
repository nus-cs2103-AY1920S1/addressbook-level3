package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CacheFileNamesTest {
    @Test
    void testStaticVariable() {
        assertEquals("src/main/resources/ApiResponseCache/NusModsApi/academic_calendar.json",
                CacheFileNames.ACADEMIC_CALENDAR);
        assertEquals("src/main/resources/ApiResponseCache/", CacheFileNames.CACHE_FOLDER_PATH);
        assertEquals("src/main/resources/ApiResponseCache/GoogleMapsApi/DistanceMatrix.json",
                CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
        assertEquals("GoogleMapsApi/", CacheFileNames.GMAPS_FOLDER_PATH);
        assertEquals("src/main/resources/ApiResponseCache/GoogleMapsApi/GmapsImages/",
                CacheFileNames.GMAPS_IMAGE_DIR);
        assertEquals("src/main/resources/ApiResponseCache/GoogleMapsApi/Places.json",
                CacheFileNames.GMAPS_PLACES_PATH);
        assertEquals("src/main/resources/ApiResponseCache/NusModsApi/holidays.json",
                CacheFileNames.HOLIDAYS);
        assertEquals("src/main/resources/ApiResponseCache/NusModsApi/modules.json",
                CacheFileNames.MODULES);
        assertEquals("src/main/resources/ApiResponseCache/NusModsApi/modules_summary.json",
                CacheFileNames.MODULES_SUMMARY);
        assertEquals("NusModsApi/",
                CacheFileNames.NUSMODS_FOLDER_PATH);
        assertEquals("src/main/resources/ApiResponseCache/NusModsApi/Venues.json",
                CacheFileNames.VENUES_FULL_PATH);
    }
}
