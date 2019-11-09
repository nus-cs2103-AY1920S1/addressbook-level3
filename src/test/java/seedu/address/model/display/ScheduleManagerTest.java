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
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Person;
import seedu.address.testutil.modelutil.TypicalModel;

class ScheduleManagerTest {

    private ScheduleManager scheduleManager;
    private TimeBook timeBook;

    @BeforeEach
    void init() {
        timeBook = TypicalModel.generateTypicalModel().getTimeBook();
        scheduleManager = new ScheduleManager(new GmapsModelManager());
    }

    @Test
    void getState_home() {
        scheduleManager.updateDisplayWithUser(LocalDateTime.now(),
                ScheduleState.HOME, timeBook);

        assertEquals(ScheduleState.HOME, scheduleManager.getState());
    }

    @Test
    void getState_person() {
        scheduleManager.updateDisplayWithUser(LocalDateTime.now(),
                ScheduleState.PERSON, timeBook);

        assertEquals(ScheduleState.PERSON, scheduleManager.getState());

        scheduleManager.updateDisplayWithPerson(ALICE.getName(), LocalDateTime.now(),
                ScheduleState.PERSON, timeBook);

        assertEquals(ScheduleState.PERSON, scheduleManager.getState());
    }

    @Test
    void getState_group() {
        scheduleManager.updateDisplayWithGroup(GROUP_NAME1, LocalDateTime.now(),
                ScheduleState.GROUP, timeBook);

        assertEquals(ScheduleState.GROUP, scheduleManager.getState());

        ArrayList<Person> persons = timeBook.getPersonList().getPersons();
        scheduleManager.updateDisplayWithPersons(persons, LocalDateTime.now(),
                ScheduleState.GROUP, timeBook);

        assertEquals(ScheduleState.GROUP, scheduleManager.getState());
    }

    @Test
    void updateDisplayWithPerson() {
        assertDoesNotThrow(() ->
                scheduleManager.updateDisplayWithPerson(ALICE.getName(), LocalDateTime.now(),
                        ScheduleState.PERSON, timeBook));
    }

    @Test
    void updateDisplayWithUser() {
        assertDoesNotThrow(() ->
                scheduleManager.updateDisplayWithUser(LocalDateTime.now(),
                        ScheduleState.PERSON, timeBook));

        assertDoesNotThrow(() ->
                scheduleManager.updateDisplayWithUser(LocalDateTime.now(),
                        ScheduleState.HOME, timeBook));
    }

    @Test
    void updateDisplayWithGroup() {
        assertDoesNotThrow(() ->
                scheduleManager.updateDisplayWithGroup(GROUP_NAME1, LocalDateTime.now(),
                        ScheduleState.GROUP, timeBook));
    }

    @Test
    void updateDisplayWithPersons() {
        ArrayList<Person> persons = timeBook.getPersonList().getPersons();
        assertDoesNotThrow(() ->
                scheduleManager.updateDisplayWithPersons(persons, LocalDateTime.now(),
                        ScheduleState.GROUP, timeBook));

    }

    @Test
    void updateSidePanelDisplay_person() {
        scheduleManager.updateSidePanelDisplay(SidePanelDisplayType.PERSON, timeBook);

        assertEquals(SidePanelDisplayType.PERSON,
                scheduleManager.getSidePanelDisplay().getSidePanelDisplayType());
    }

    @Test
    void updateSidePanelDisplay_group() {
        scheduleManager.updateSidePanelDisplay(SidePanelDisplayType.GROUP, timeBook);

        assertEquals(SidePanelDisplayType.GROUP,
                scheduleManager.getSidePanelDisplay().getSidePanelDisplayType());
    }

    @Test
    void getScheduleDisplay() {
        scheduleManager.updateDisplayWithUser(LocalDateTime.now(),
                ScheduleState.PERSON, timeBook);

        assertNotNull(scheduleManager.getScheduleDisplay());
    }

    @Test
    void getSidePanelDisplay() {

        scheduleManager.updateSidePanelDisplay(SidePanelDisplayType.PERSON, timeBook);

        assertNotNull(scheduleManager.getSidePanelDisplay());
    }
}
