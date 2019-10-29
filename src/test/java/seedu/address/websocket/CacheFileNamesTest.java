package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CacheFileNamesTest {
    @Test
    void testStaticVariable() {
        assertEquals("/ApiResponseCache/NusModsApi/academic_calendar.json",
                CacheFileNames.ACADEMIC_CALENDAR);
        assertEquals("/ApiResponseCache/", CacheFileNames.CACHE_FOLDER_PATH);
        assertEquals("/ApiResponseCache/GoogleMapsApi/DistanceMatrix.json",
                CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
        assertEquals("GoogleMapsApi/", CacheFileNames.GMAPS_FOLDER_PATH);
        assertEquals("/ApiResponseCache/GoogleMapsApi/GmapsImages/",
                CacheFileNames.GMAPS_IMAGE_DIR);
        assertEquals("/ApiResponseCache/GoogleMapsApi/Places.json",
                CacheFileNames.GMAPS_PLACES_PATH);
        assertEquals("/ApiResponseCache/NusModsApi/holidays.json",
                CacheFileNames.HOLIDAYS);
        assertEquals("/ApiResponseCache/NusModsApi/modules.json",
                CacheFileNames.MODULES);
        assertEquals("/ApiResponseCache/NusModsApi/modules_summary.json",
                CacheFileNames.MODULES_SUMMARY);
        assertEquals("NusModsApi/",
                CacheFileNames.NUSMODS_FOLDER_PATH);
        assertEquals("/ApiResponseCache/NusModsApi/Venues.json",
                CacheFileNames.VENUES_FULL_PATH);
    }
}
