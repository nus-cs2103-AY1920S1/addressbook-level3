package seedu.address.model.display.schedulewindow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.USER;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;
import seedu.address.model.person.exceptions.InvalidTimeslotException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;

class ScheduleWindowDisplayTest {

    private ScheduleWindowDisplay scheduleWindowDisplayHome;
    private ScheduleWindowDisplay scheduleWindowDisplayUser;
    private ScheduleWindowDisplay scheduleWindowDisplayPerson;
    private ScheduleWindowDisplay scheduleWindowDisplayGroup;

    @BeforeEach
    void init() {
        ModelManager modelManager = TypicalModel.generateModelWithSchedules();

        modelManager.updateDisplayWithUser(LocalDateTime.now(), ScheduleWindowDisplayType.HOME);
        scheduleWindowDisplayHome = modelManager.getScheduleWindowDisplay();

        modelManager.updateDisplayWithUser(LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);
        scheduleWindowDisplayUser = modelManager.getScheduleWindowDisplay();

        modelManager.updateDisplayWithPerson(ALICE.getName(), LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);
        scheduleWindowDisplayPerson = modelManager.getScheduleWindowDisplay();

        modelManager.updateDisplayWithGroup(GROUP_NAME1, LocalDateTime.now(), ScheduleWindowDisplayType.GROUP);
        scheduleWindowDisplayGroup = modelManager.getScheduleWindowDisplay();
    }

    @Test
    void getScheduleWindowDisplayType() {
        assertEquals(ScheduleWindowDisplayType.HOME,
                scheduleWindowDisplayHome.getScheduleWindowDisplayType());

        assertEquals(ScheduleWindowDisplayType.PERSON,
                scheduleWindowDisplayUser.getScheduleWindowDisplayType());

        assertEquals(ScheduleWindowDisplayType.PERSON,
                scheduleWindowDisplayPerson.getScheduleWindowDisplayType());

        assertEquals(ScheduleWindowDisplayType.GROUP,
                scheduleWindowDisplayGroup.getScheduleWindowDisplayType());

    }

    @Test
    void getPersonSchedules() {
        assertEquals(scheduleWindowDisplayHome.getPersonSchedules().size(), 1);
        assertEquals(scheduleWindowDisplayUser.getPersonSchedules().size(), 1);
        assertEquals(scheduleWindowDisplayPerson.getPersonSchedules().size(), 1);
        assertEquals(scheduleWindowDisplayGroup.getPersonSchedules().size(), 3);
    }

    @Test
    void getGroupDisplay() {
        assertNull(scheduleWindowDisplayHome.getGroupDisplay());
        assertNull(scheduleWindowDisplayUser.getGroupDisplay());
        assertNull(scheduleWindowDisplayPerson.getGroupDisplay());
        assertNotNull(scheduleWindowDisplayGroup.getGroupDisplay());
    }

    @Test
    void getFreeSchedule() {
        assertNull(scheduleWindowDisplayHome.getFreeSchedule());
        assertNull(scheduleWindowDisplayUser.getFreeSchedule());
        assertNull(scheduleWindowDisplayPerson.getFreeSchedule());
        assertNotNull(scheduleWindowDisplayGroup.getFreeSchedule());

    }

    @Test
    void getPersonDisplays() {
        assertTrue(scheduleWindowDisplayHome.getPersonDisplays().size() == 1);
        assertTrue(scheduleWindowDisplayUser.getPersonDisplays().size() == 1);
        assertTrue(scheduleWindowDisplayPerson.getPersonDisplays().size() == 1);
        assertTrue(scheduleWindowDisplayGroup.getPersonDisplays().size() == 3);

    }

    @Test
    void getFreeTimeslot() {
        assertThrows(InvalidTimeslotException.class, () ->
                scheduleWindowDisplayGroup.getFreeTimeslot(1, 123));

        assertDoesNotThrow(() ->
                scheduleWindowDisplayGroup.getFreeTimeslot(1, 1));
    }

    @Test
    void getPersonTimeslot() {
        assertThrows(PersonNotFoundException.class, () ->
                scheduleWindowDisplayGroup.getPersonTimeslot(ZACK.getName(), 0, 1));

        assertThrows(PersonTimeslotNotFoundException.class, () ->
                scheduleWindowDisplayGroup.getPersonTimeslot(ALICE.getName(), 0, 12));

        assertDoesNotThrow(() ->
                scheduleWindowDisplayGroup.getPersonTimeslot(USER.getName(), 0, 1));

    }

    @Test
    void getPersonTimeslotForToday() {
        assertThrows(PersonNotFoundException.class, () ->
                scheduleWindowDisplayHome.getPersonTimeslotForToday(ALICE.getName(), 1));

        assertThrows(PersonTimeslotNotFoundException.class, () ->
                scheduleWindowDisplayHome.getPersonTimeslotForToday(USER.getName(), 500));

        assertDoesNotThrow(() ->
                scheduleWindowDisplayGroup.getPersonTimeslot(USER.getName(), 0, 1));

    }

    @Test
    void freeScheduleToString() {
        assertNotNull(scheduleWindowDisplayGroup.toString());
    }

    @Test
    void personScheduleToString() {
        assertNotNull(scheduleWindowDisplayPerson.toString());
    }
}
