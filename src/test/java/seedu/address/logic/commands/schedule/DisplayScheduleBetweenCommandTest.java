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

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventContainsKeyDateRangePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code DisplayScheduleBetweenCommand}.
 */
public class DisplayScheduleBetweenCommandTest {

    private Model model = new ModelManager(getTypicalEventBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventBook(), new UserPrefs());

    @Test
    public void equals() throws ParseException {
        EventContainsKeyDateRangePredicate firstPredicate =
                new EventContainsKeyDateRangePredicate(
                        ParserUtil.parseEventDate("10/12/2019"),
                        ParserUtil.parseEventDate("12/12/2019"));
        EventContainsKeyDateRangePredicate secondPredicate =
                new EventContainsKeyDateRangePredicate(
                        ParserUtil.parseEventDate("13/12/2019"),
                        ParserUtil.parseEventDate("14/12/2019"));

        DisplayScheduleBetweenCommand displayFirstCommand = new DisplayScheduleBetweenCommand(firstPredicate);
        DisplayScheduleBetweenCommand displaySecondCommand = new DisplayScheduleBetweenCommand(secondPredicate);

        // same object -> returns true
        assertTrue(displayFirstCommand.equals(displayFirstCommand));

        // same values/predicate -> returns true
        DisplayScheduleBetweenCommand displayFirstCommandCopy =
                new DisplayScheduleBetweenCommand(firstPredicate);
        assertTrue(displayFirstCommand.equals(displayFirstCommandCopy));

        // different types -> returns false
        assertFalse(displayFirstCommand.equals(1));

        // null -> returns false
        assertFalse(displayFirstCommand.equals(null));

        // different event with different predicate -> returns false
        assertFalse(displayFirstCommand.equals(displaySecondCommand));
    }

    @Test
    public void execute_validDateRangeKeywordInput_noEventFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeyDateRangePredicate predicate = preparePredicate("01/01/2019", "02/01/2019");
        DisplayScheduleBetweenCommand command = new DisplayScheduleBetweenCommand(predicate);
        expectedModel.updateFilteredScheduledEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredScheduledEventList());
    }

    @Test
    public void execute_validDateRangeKeywordInput_multipleEventsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventContainsKeyDateRangePredicate predicate = preparePredicate("12/10/2019", "13/10/2019");
        DisplayScheduleBetweenCommand command = new DisplayScheduleBetweenCommand(predicate);
        expectedModel.updateFilteredScheduledEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MUSICAL_COMPETITION, BIRTHDAY_PARTY), model.getFilteredScheduledEventList());
    }

    /**
     * Parses {@code inputDate1} and {@code inputDate2} into a {@code EventContainsKeyRangePredicate}.
     */
    private EventContainsKeyDateRangePredicate preparePredicate(String inputDate1, String inputDate2)
            throws ParseException {
        return new EventContainsKeyDateRangePredicate(
                ParserUtil.parseEventDate(inputDate1), ParserUtil.parseEventDate(inputDate1));
    }
}
