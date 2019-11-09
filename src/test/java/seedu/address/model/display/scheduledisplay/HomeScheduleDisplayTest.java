package seedu.address.model.display.scheduledisplay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.USER;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;

class HomeScheduleDisplayTest {

    private ModelManager modelManager;
    private HomeScheduleDisplay homeScheduleDisplay;

    @BeforeEach
    void init() {
        modelManager = TypicalModel.generateModelWithSchedules();
        modelManager.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.HOME);
        homeScheduleDisplay = (HomeScheduleDisplay) modelManager.getScheduleDisplay();
    }

    @Test
    void getState() {
        assertEquals(homeScheduleDisplay.getState(), ScheduleState.HOME);
    }

    @Test
    void getPersonSchedules() {
        assertEquals(homeScheduleDisplay.getPersonSchedules().size(), 1);
    }

    @Test
    void getPersonDisplays() {
        assertTrue(homeScheduleDisplay.getPersonDisplays().size() == 1);
    }

    @Test
    void getPersonTimeslot() {
        assertThrows(PersonNotFoundException.class, () ->
                homeScheduleDisplay.getPersonTimeslot(ZACK.getName(), 0, 1));
        assertDoesNotThrow(() ->
                homeScheduleDisplay.getPersonTimeslot(USER.getName(), 0, 1));
    }

    @Test
    void getPersonTimeslotForToday() {
        assertThrows(PersonNotFoundException.class, () ->
                homeScheduleDisplay.getPersonTimeslotForToday(ALICE.getName(), 1));

        assertThrows(PersonTimeslotNotFoundException.class, () ->
                homeScheduleDisplay.getPersonTimeslotForToday(USER.getName(), 500));

        assertDoesNotThrow(() ->
                homeScheduleDisplay.getPersonTimeslot(USER.getName(), 0, 1));
    }
}
