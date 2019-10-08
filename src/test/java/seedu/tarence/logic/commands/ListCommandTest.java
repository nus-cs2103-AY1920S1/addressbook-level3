package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.student.StudentsInTutorialPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final boolean SHOW_ALL_STUDENTS = true;

    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(SHOW_ALL_STUDENTS), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        StudentsInTutorialPredicate firstPredicate =
                new StudentsInTutorialPredicate(INDEX_FIRST_IN_LIST);
        StudentsInTutorialPredicate secondPredicate =
                new StudentsInTutorialPredicate(INDEX_SECOND_IN_LIST);

        ListCommand listFirstCommand = new ListCommand(firstPredicate);
        ListCommand listSecondCommand = new ListCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // same values -> returns true
        ListCommand listFirstCommandCopy = new ListCommand(firstPredicate);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        // different types -> returns false
        assertFalse(listFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        StudentsInTutorialPredicate outOfBoundsPredicate =
                new StudentsInTutorialPredicate(outOfBoundIndex);
        ListCommand listCommand = new ListCommand(outOfBoundsPredicate);

        assertCommandFailure(listCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
    }
}
