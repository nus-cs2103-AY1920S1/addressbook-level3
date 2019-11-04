package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.websocket.Cache;

public class NusModsDataTest {
    private NusModsData nusModsData;
    @BeforeEach
    void setUp() {
        nusModsData = new NusModsData();
        Optional<ModuleSummaryList> moduleSummaryListOptional = Cache.loadModuleSummaryList();
        moduleSummaryListOptional.ifPresent(moduleSummaryList -> nusModsData.setModuleSummaryList(moduleSummaryList));

        Optional<Holidays> holidaysOptional = Cache.loadHolidays();
        holidaysOptional.ifPresent(holidays -> nusModsData.setHolidays(holidays));

        Optional<AcadCalendar> acadCalendarOptional = Cache.loadAcadCalendar();
        acadCalendarOptional.ifPresent(acadCalendar -> nusModsData.setAcadCalendar(acadCalendar));
    }

    @Test
    void findModule_moduleIdPresent_returnsModule() {
        ModuleId moduleId = new ModuleId("2019/2020", "CS2103T");
        assertTrue(nusModsData.findModule(moduleId) instanceof Module);
    }

    @Test
    void findModule_moduleIdNotPresent_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () ->
                nusModsData.findModule(new ModuleId("9998/9999", "CS9999")));
    }
}
