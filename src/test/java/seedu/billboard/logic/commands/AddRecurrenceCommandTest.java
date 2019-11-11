package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalRecurrences.getTypicalBillboardWithRecurrenceExpenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.recurrence.Recurrence;
import seedu.billboard.testutil.RecurrenceBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddRecurrenceCommand}.
 */
public class AddRecurrenceCommandTest {

    @Test
    public void constructor_nullRecurrence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRecurrenceCommand(
                null, null, null, null, null, null, 0));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() throws Exception {
        Recurrence recurrence = new RecurrenceBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddRecurrenceCommand(recurrence.getName(),
                recurrence.getDescription(), recurrence.getAmount(), recurrence.getCreated(), recurrence.getTagNames(),
                recurrence.getInterval(), recurrence.getIterations()).execute(null));
    }

    @Test
    public void equals() {
        Recurrence firstRecurrence = new RecurrenceBuilder().build();
        Recurrence secondRecurrence = new RecurrenceBuilder().withName("pay tuition fees").build();

        AddRecurrenceCommand firstAddRecurrenceCommand = new AddRecurrenceCommand(firstRecurrence.getName(),
                firstRecurrence.getDescription(), firstRecurrence.getAmount(), firstRecurrence.getCreated(),
                firstRecurrence.getTagNames(), firstRecurrence.getInterval(), firstRecurrence.getIterations());
        AddRecurrenceCommand secondAddRecurrenceCommand = new AddRecurrenceCommand(secondRecurrence.getName(),
                secondRecurrence.getDescription(), secondRecurrence.getAmount(), secondRecurrence.getCreated(),
                secondRecurrence.getTagNames(), secondRecurrence.getInterval(), secondRecurrence.getIterations());

        // same object -> returns true
        assertEquals(firstAddRecurrenceCommand, firstAddRecurrenceCommand);

        // same values -> returns true
        AddRecurrenceCommand firstAddRecurrenceCommandCopy = new AddRecurrenceCommand(firstRecurrence.getName(),
                firstRecurrence.getDescription(), firstRecurrence.getAmount(), firstRecurrence.getCreated(),
                firstRecurrence.getTagNames(), firstRecurrence.getInterval(), firstRecurrence.getIterations());

        assertEquals(firstAddRecurrenceCommand, firstAddRecurrenceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, firstAddRecurrenceCommand);

        // null -> returns false
        assertNotEquals(null, firstAddRecurrenceCommand);

        // different expense -> returns false
        assertNotEquals(firstAddRecurrenceCommand, secondAddRecurrenceCommand);
    }
}
