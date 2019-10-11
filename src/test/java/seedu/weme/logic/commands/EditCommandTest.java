package seedu.weme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_CHARMANDER;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND_MEME;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;
import seedu.weme.testutil.EditMemeDescriptorBuilder;
import seedu.weme.testutil.MemeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Meme editedMeme = new MemeBuilder().build();
        EditCommand.EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder(editedMeme).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEME, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(model.getFilteredMemeList().get(0), editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    /* Logic about list filtering is not discussed yet.
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredMemeList().size());
        Meme lastMeme = model.getFilteredMemeList().get(indexLastPerson.getZeroBased());

        MemeBuilder memeInList = new MemeBuilder(lastMeme);
        Meme editedMeme = memeInList.withFilePath(VALID_FILEPATH_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER).withTags(VALID_TAG_CHARMANDER).build();

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withUrl(VALID_FILEPATH_JOKER)
                .withUrl(VALID_FILEPATH_JOKER).withTags(VALID_TAG_CHARMANDER).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(lastMeme, editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
     */

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEME, new EditMemeDescriptor());
        Meme editedMeme = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);

        Meme memeInFilteredList = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());
        Meme editedMeme = new MemeBuilder(memeInFilteredList).withDescription(VALID_DESCRIPTION_JOKER).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEME,
                new EditMemeDescriptorBuilder().withDescription(VALID_DESCRIPTION_JOKER).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(model.getFilteredMemeList().get(0), editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMemeUnfilteredList_failure() {
        Meme firstMeme = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder(firstMeme).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_MEME, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_duplicateMemeFilteredList_failure() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);

        // edit meme in filtered list into a duplicate in meme book
        Meme memeInList = model.getMemeBook().getMemeList().get(INDEX_SECOND_MEME.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEME,
                new EditMemeDescriptorBuilder(memeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_invalidMemeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_JOKER).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of meme book
     */
    @Test
    public void execute_invalidMemeIndexFilteredList_failure() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);
        Index outOfBoundIndex = INDEX_SECOND_MEME;
        // ensures that outOfBoundIndex is still in bounds of meme book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMemeBook().getMemeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_JOKER).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MEME, DESC_CHARMANDER);

        // same values -> returns true
        EditMemeDescriptor copyDescriptor = new EditMemeDescriptor(DESC_CHARMANDER);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MEME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MEME, DESC_CHARMANDER)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MEME, DESC_JOKER)));
    }

}
