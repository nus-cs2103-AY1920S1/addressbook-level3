package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EVENT_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalEvents.MUSICAL_COMPETITION;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventContainsKeyDatePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code DisplayScheduleForDateCommand}.
 */
public class DisplayScheduleForDateCommandTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Model model = new ModelManager(getTypicalEventBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventBook(), new UserPrefs());

    @Test
    public void equals() {
        EventContainsKeyDatePredicate firstPredicate =
                new EventContainsKeyDatePredicate(LocalDate.parse("10/12/2019", FORMATTER));
        EventContainsKeyDatePredicate secondPredicate =
                new EventContainsKeyDatePredicate(LocalDate.parse("10/12/2019", FORMATTER));

        DisplayScheduleForDateCommand displayFirstCommand = new DisplayScheduleForDateCommand(firstPredicate);
        DisplayScheduleForDateCommand displaySecondCommand = new DisplayScheduleForDateCommand(secondPredicate);

        // same object -> returns true
        assertTrue(displayFirstCommand.equals(displayFirstCommand));

        // same values -> returns true
        DisplayScheduleForDateCommand displayFirstCommandCopy =
                new DisplayScheduleForDateCommand(firstPredicate);
        assertTrue(displayFirstCommand.equals(displayFirstCommandCopy));

        // different types -> returns false
        assertFalse(displayFirstCommand.equals(1));

        // null -> returns false
        assertFalse(displayFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(displayFirstCommand.equals(displaySecondCommand));
    }

    @Test
    public void execute_zeroDateKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENT_LISTED_OVERVIEW, 0);
        EventContainsKeyDatePredicate predicate = preparePredicate("01/01/2019");
        DisplayScheduleForDateCommand command = new DisplayScheduleForDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_EVENT_LISTED_OVERVIEW, 2);
        EventContainsKeyDatePredicate predicate = preparePredicate("12/10/2019");
        DisplayScheduleForDateCommand command = new DisplayScheduleForDateCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIRTHDAY_PARTY, MUSICAL_COMPETITION), model.getFilteredEventList());
    }

    /**
     * Parses {@code userInput} into a {@code EventContainsKeyDatePredicate}.
     */
    private EventContainsKeyDatePredicate preparePredicate(String userInput) {
        return new EventContainsKeyDatePredicate(LocalDate.parse(userInput, FORMATTER));
    }
}
