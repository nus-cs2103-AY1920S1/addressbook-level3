package seedu.address.model.display.scheduledisplay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;

class PersonScheduleDisplayTest {

    private ModelManager modelManager;
    private PersonScheduleDisplay personScheduleDisplay;

    @BeforeEach
    void init() throws PersonNotFoundException {
        modelManager = TypicalModel.generateModelWithSchedules();
        modelManager.updateScheduleWithPerson(ALICE.getName(), LocalDateTime.now(), ScheduleState.PERSON);
        personScheduleDisplay = (PersonScheduleDisplay) modelManager.getScheduleDisplay();
    }

    @Test
    void getState() {
        assertEquals(personScheduleDisplay.getState(), ScheduleState.PERSON);
    }

    @Test
    void getPersonSchedules() {
        assertEquals(personScheduleDisplay.getPersonSchedules().size(), 1);
    }

    @Test
    void getPersonDisplays() {
        assertTrue(personScheduleDisplay.getPersonDisplays().size() == 1);
    }

    @Test
    void getPersonTimeslot() {
        assertThrows(PersonNotFoundException.class, () ->
                personScheduleDisplay.getPersonTimeslot(ZACK.getName(), 0, 1));
        assertDoesNotThrow(() ->
                personScheduleDisplay.getPersonTimeslot(ALICE.getName(), 0, 1));
    }
}
