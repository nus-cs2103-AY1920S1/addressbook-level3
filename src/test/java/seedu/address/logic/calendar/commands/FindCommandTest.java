package seedu.address.logic.calendar.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.CARL;
import static seedu.address.testutil.TypicalTasks.ELLE;
import static seedu.address.testutil.TypicalTasks.FIONA;
import static seedu.address.testutil.TypicalTasks.getTypicalCalendarAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;
import seedu.address.model.calendar.CalendarUserPrefs;
import seedu.address.model.calendar.task.TaskTitleContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the CalendarModel) for {@code FindCommand}.
 */
public class FindCommandTest {
    private CalendarModel calendarModel =
        new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());
    private CalendarModel expectedModel =
        new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());

    @Test
    public void equals() {
        TaskTitleContainsKeywordsPredicate firstPredicate =
                new TaskTitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskTitleContainsKeywordsPredicate secondPredicate =
                new TaskTitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskTitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, calendarModel, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), calendarModel.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        TaskTitleContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, calendarModel, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), calendarModel.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code TaskTitleContainsKeywordsPredicate}.
     */
    private TaskTitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TaskTitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
