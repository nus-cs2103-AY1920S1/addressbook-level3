package seedu.address.websocket;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.AppSettings;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.ModuleCode;

class NusModsApiTest {
    private static NusModsApi api;
    private static AcadYear validAcadYear = AppSettings.DEFAULT_ACAD_YEAR;
    private static AcadYear invalidAcadYear = new AcadYear("9998/9999");

    @BeforeAll
    static void setUp() {
        api = new NusModsApi(validAcadYear);
    }

    @Test
    void getModuleList_success() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getModuleList().isPresent());
    }

    @Test
    void getModuleList_invalidYear_failure() {
        api.setAcadYear(invalidAcadYear);
        assertTrue(api.getModuleList().isEmpty());
    }

    @Test
    void getModuleInfo_success() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getModuleInfo().isPresent());
    }

    @Test
    void getModuleInfo_invalidYear_failure() {
        api.setAcadYear(invalidAcadYear);
        assertTrue(api.getModuleInfo().isEmpty());
    }

    @Test
    void getModule_success() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getModule(new ModuleCode("CS2103T")).isPresent());
    }

    @Test
    void getModule_invalidModuleCode_failure() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getModule(new ModuleCode("INVALID")).isEmpty());
    }

    @Test
    void getModule_invalidYear_failure() {
        api.setAcadYear(invalidAcadYear);
        assertTrue(api.getModule(new ModuleCode("CS2103T")).isEmpty());
    }

    @Test
    void getVenues_success() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getVenues("1").isPresent());
    }

    @Test
    void getVenues_invalidYear_failure() {
        api.setAcadYear(invalidAcadYear);
        assertTrue(api.getVenues("1").isEmpty());
    }

    @Test
    void getVenues_invalidSemester_failure() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getVenues("99").isEmpty());
    }

    @Test
    void getVenueInformation_success() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getVenueInformation("1").isPresent());
    }

    @Test
    void getVenueInformation_invalidYear_failure() {
        api.setAcadYear(invalidAcadYear);
        assertTrue(api.getVenueInformation("1").isEmpty());
    }

    @Test
    void getVenueInformation_invalidSemester_failure() {
        api.setAcadYear(validAcadYear);
        assertTrue(api.getVenueInformation("99").isEmpty());
    }

    @Test
    void getAcademicCalendar_success() {
        assertTrue(api.getAcademicCalendar().isPresent());
    }

    @Test
    void getHolidays_success() {
        assertTrue(api.getHolidays().isPresent());
    }
}
