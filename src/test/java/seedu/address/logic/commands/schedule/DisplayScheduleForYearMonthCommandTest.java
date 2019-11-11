/*
@@author shihaoyap
 */

package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalEvents.MUSICAL_COMPETITION;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventContainsKeyYearMonthPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code DisplayScheduleForYearMonthCommand}.
 */
public class DisplayScheduleForYearMonthCommandTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    private Model model = new ModelManager(getTypicalEventBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventBook(), new UserPrefs());

    @Test
    public void equals() {
        EventContainsKeyYearMonthPredicate firstPredicate =
                new EventContainsKeyYearMonthPredicate(YearMonth.parse("12/2019", FORMATTER));
        EventContainsKeyYearMonthPredicate secondPredicate =
                new EventContainsKeyYearMonthPredicate(YearMonth.parse("11/2019", FORMATTER));

        DisplayScheduleForYearMonthCommand displayFirstCommand =
                new DisplayScheduleForYearMonthCommand(firstPredicate);
        DisplayScheduleForYearMonthCommand displaySecondCommand =
                new DisplayScheduleForYearMonthCommand(secondPredicate);

        // same object -> returns true
        assertTrue(displayFirstCommand.equals(displayFirstCommand));

        // same values -> returns true
        DisplayScheduleForYearMonthCommand displayFirstCommandCopy =
                new DisplayScheduleForYearMonthCommand(firstPredicate);
        assertTrue(displayFirstCommand.equals(displayFirstCommandCopy));

        // different types -> returns false
        assertFalse(displayFirstCommand.equals(1));

        // null -> returns false
        assertFalse(displayFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(displayFirstCommand.equals(displaySecondCommand));
    }

    @Test
    public void execute_validYearMonthKeywordInput_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeyYearMonthPredicate predicate = preparePredicate("01/2019");
        DisplayScheduleForYearMonthCommand command = new DisplayScheduleForYearMonthCommand(predicate);
        expectedModel.updateFilteredScheduledEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredScheduledEventList());
    }

    @Test
    public void execute_validYearMonthKeywordInput_multipleEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventContainsKeyYearMonthPredicate predicate = preparePredicate("10/2019");
        DisplayScheduleForYearMonthCommand command = new DisplayScheduleForYearMonthCommand(predicate);
        expectedModel.updateFilteredScheduledEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MUSICAL_COMPETITION, BIRTHDAY_PARTY), model.getFilteredScheduledEventList());
    }

    /**
     * Parses {@code userInput} into a {@code EventContainsKeyYearMonthPredicate}.
     */
    private EventContainsKeyYearMonthPredicate preparePredicate(String userInput) {
        return new EventContainsKeyYearMonthPredicate(YearMonth.parse(userInput, FORMATTER));
    }
}
