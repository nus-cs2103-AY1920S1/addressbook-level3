package seedu.address.model.display;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.GmapsModelManager;
import seedu.address.model.TimeBook;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Person;
import seedu.address.testutil.modelutil.TypicalModel;

class DisplayModelManagerTest {

    private DisplayModelManager displayModelManager;
    private TimeBook timeBook;

    @BeforeEach
    void init() {
        timeBook = TypicalModel.generateTypicalModel().getTimeBook();
        displayModelManager = new DisplayModelManager(new GmapsModelManager());
    }

    @Test
    void getState_home() {
        displayModelManager.updateDisplayWithUser(LocalDateTime.now(),
                ScheduleWindowDisplayType.HOME, timeBook);

        assertEquals(ScheduleWindowDisplayType.HOME, displayModelManager.getState());
    }

    @Test
    void getState_person() {
        displayModelManager.updateDisplayWithUser(LocalDateTime.now(),
                ScheduleWindowDisplayType.PERSON, timeBook);

        assertEquals(ScheduleWindowDisplayType.PERSON, displayModelManager.getState());

        displayModelManager.updateDisplayWithPerson(ALICE.getName(), LocalDateTime.now(),
                ScheduleWindowDisplayType.PERSON, timeBook);

        assertEquals(ScheduleWindowDisplayType.PERSON, displayModelManager.getState());
    }

    @Test
    void getState_group() {
        displayModelManager.updateDisplayWithGroup(GROUP_NAME1, LocalDateTime.now(),
                ScheduleWindowDisplayType.GROUP, timeBook);

        assertEquals(ScheduleWindowDisplayType.GROUP, displayModelManager.getState());

        ArrayList<Person> persons = timeBook.getPersonList().getPersons();
        displayModelManager.updateDisplayWithPersons(persons, LocalDateTime.now(),
                ScheduleWindowDisplayType.GROUP, timeBook);

        assertEquals(ScheduleWindowDisplayType.GROUP, displayModelManager.getState());
    }

    @Test
    void updateDisplayWithPerson() {
        assertDoesNotThrow(() ->
                displayModelManager.updateDisplayWithPerson(ALICE.getName(), LocalDateTime.now(),
                        ScheduleWindowDisplayType.PERSON, timeBook));
    }

    @Test
    void updateDisplayWithUser() {
        assertDoesNotThrow(() ->
                displayModelManager.updateDisplayWithUser(LocalDateTime.now(),
                        ScheduleWindowDisplayType.PERSON, timeBook));

        assertDoesNotThrow(() ->
                displayModelManager.updateDisplayWithUser(LocalDateTime.now(),
                        ScheduleWindowDisplayType.HOME, timeBook));
    }

    @Test
    void updateDisplayWithGroup() {
        assertDoesNotThrow(() ->
                displayModelManager.updateDisplayWithGroup(GROUP_NAME1, LocalDateTime.now(),
                        ScheduleWindowDisplayType.GROUP, timeBook));
    }

    @Test
    void updateDisplayWithPersons() {
        ArrayList<Person> persons = timeBook.getPersonList().getPersons();
        assertDoesNotThrow(() ->
                displayModelManager.updateDisplayWithPersons(persons, LocalDateTime.now(),
                        ScheduleWindowDisplayType.GROUP, timeBook));

    }

    @Test
    void updateSidePanelDisplay_person() {
        displayModelManager.updateSidePanelDisplay(SidePanelDisplayType.PERSON, timeBook);

        assertEquals(SidePanelDisplayType.PERSON,
                displayModelManager.getSidePanelDisplay().getSidePanelDisplayType());
    }

    @Test
    void updateSidePanelDisplay_group() {
        displayModelManager.updateSidePanelDisplay(SidePanelDisplayType.GROUP, timeBook);

        assertEquals(SidePanelDisplayType.GROUP,
                displayModelManager.getSidePanelDisplay().getSidePanelDisplayType());
    }

    @Test
    void getScheduleWindowDisplay() {
        displayModelManager.updateDisplayWithUser(LocalDateTime.now(),
                ScheduleWindowDisplayType.PERSON, timeBook);

        assertNotNull(displayModelManager.getScheduleWindowDisplay());
    }

    @Test
    void getSidePanelDisplay() {

        displayModelManager.updateSidePanelDisplay(SidePanelDisplayType.PERSON, timeBook);

        assertNotNull(displayModelManager.getSidePanelDisplay());
    }
}
