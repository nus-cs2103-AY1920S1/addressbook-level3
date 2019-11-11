package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Tag;


/**
 * Contains integration tests and unit tests for
 * {@code SaveTodoCommand}.
 */
public class SaveTodoCommandTest {

    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
        if (model.isMainMode()) {
            model.toggle();
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Eatery eateryToSave = model.getFilteredTodoList().get(INDEX_FIRST_EATERY.getZeroBased());
        SaveTodoCommand saveTodoCommand = new SaveTodoCommand(INDEX_FIRST_EATERY);

        ModelManager expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.toggle();
        expectedModel.deleteEatery(eateryToSave);

        String name = eateryToSave.getName().fullName;
        String address = eateryToSave.getAddress().value;
        StringBuilder tags = new StringBuilder();
        for (Tag tag : eateryToSave.getTags()) {
            tags.append(PREFIX_TAG + " " + tag.getName() + " ");
        }

        String pendingCommand = String.format("add %s %s %s %s %s %s",
            PREFIX_NAME, name, PREFIX_ADDRESS, address, tags.toString().trim(), PREFIX_CATEGORY);
        String expectedMessage = SaveTodoCommand.MESSAGE_REMINDER_TO_USER + pendingCommand;

        assertCommandSuccess(saveTodoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTodoList().size() + 1);
        SaveTodoCommand saveTodoCommand = new SaveTodoCommand(outOfBoundIndex);

        assertCommandFailure(saveTodoCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidMode_throwsCommandException() {
        SaveTodoCommand saveTodoCommand = new SaveTodoCommand(INDEX_FIRST_EATERY);
        model.toggle();
        assertCommandFailure(saveTodoCommand, model, SaveTodoCommand.MESSAGE_INVALID_MODE);
    }

    @Test
    public void equals() {
        SaveTodoCommand saveFirstCommand = new SaveTodoCommand(INDEX_FIRST_EATERY);
        SaveTodoCommand saveSecondCommand = new SaveTodoCommand(INDEX_SECOND_EATERY);

        // same object -> returns true
        assertTrue(saveFirstCommand.equals(saveFirstCommand));

        // same values -> returns true
        SaveTodoCommand saveFirstCommandCopy = new SaveTodoCommand(INDEX_FIRST_EATERY);
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy));

        // different types -> returns false
        assertFalse(saveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(saveFirstCommand.equals(null));

        // different eatery -> returns false
        assertFalse(saveFirstCommand.equals(saveSecondCommand));
    }
}
