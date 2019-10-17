package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_ACTOR_BOB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.showShowAtIndex;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;
import seedu.ezwatchlist.testutil.ShowBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalWatchList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Show editedShow = new ShowBuilder().build();
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder(editedShow).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(0), editedShow);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastShow = Index.fromOneBased(model.getFilteredShowList().size());
        Show lastShow = model.getFilteredShowList().get(indexLastShow.getZeroBased());

        ShowBuilder showInList = new ShowBuilder(lastShow);
        Show editedShow = showInList.withName(VALID_NAME_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withActors(VALID_ACTOR_BOB).build();

        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDescription(VALID_DESCRIPTION_BOB).withActors(VALID_ACTOR_BOB).build();

        EditCommand editCommand = new EditCommand(indexLastShow, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), new UserPrefs());
        expectedModel.setShow(lastShow, editedShow);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW, new EditShowDescriptor());
        Show editedShow = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        Show showInFilteredList = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        Show editedShow = new ShowBuilder(showInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW,
                new EditShowDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SHOW_SUCCESS, editedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(0), editedShow);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateShowUnfilteredList_failure() {
        Show firstShow = model.getFilteredShowList().get(INDEX_FIRST_SHOW.getZeroBased());
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder(firstShow).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_SHOW, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_SHOW);
    }

    @Test
    public void execute_duplicateShowFilteredList_failure() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);

        // edit show in filtered list into a duplicate in watch list
        Show showInList = model.getWatchList().getShowList().get(INDEX_SECOND_SHOW.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SHOW,
                new EditShowDescriptorBuilder(showInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_SHOW);
    }

    @Test
    public void execute_invalidShowIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        EditShowDescriptor descriptor = new EditShowDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of watch list
     */
    @Test
    public void execute_invalidShowIndexFilteredList_failure() {
        showShowAtIndex(model, INDEX_FIRST_SHOW);
        Index outOfBoundIndex = INDEX_SECOND_SHOW;
        // ensures that outOfBoundIndex is still in bounds of watch list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWatchList().getShowList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditShowDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_SHOW, DESC_AMY);

        // same values -> returns true
        EditShowDescriptor copyDescriptor = new EditShowDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_SHOW, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_SHOW, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_SHOW, DESC_BOB)));
    }

}
