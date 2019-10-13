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
import seedu.weme.logic.commands.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;
import seedu.weme.testutil.EditMemeDescriptorBuilder;
import seedu.weme.testutil.MemeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * MemeEditCommand.
 */
public class MemeEditCommandTest {

    private Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Meme editedMeme = new MemeBuilder().build();
        MemeEditCommand.EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder(editedMeme).build();
        MemeEditCommand memeEditCommand = new MemeEditCommand(INDEX_FIRST_MEME, descriptor);

        String expectedMessage = String.format(MemeEditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(model.getFilteredMemeList().get(0), editedMeme);

        assertCommandSuccess(memeEditCommand, model, expectedMessage, expectedModel);
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
        MemeEditCommand editCommand = new MemeEditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(MemeEditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(lastMeme, editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
     */

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        MemeEditCommand memeEditCommand = new MemeEditCommand(INDEX_FIRST_MEME, new EditMemeDescriptor());
        Meme editedMeme = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());

        String expectedMessage = String.format(MemeEditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());

        assertCommandSuccess(memeEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);

        Meme memeInFilteredList = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());
        Meme editedMeme = new MemeBuilder(memeInFilteredList).withDescription(VALID_DESCRIPTION_JOKER).build();
        MemeEditCommand memeEditCommand = new MemeEditCommand(INDEX_FIRST_MEME,
                new EditMemeDescriptorBuilder().withDescription(VALID_DESCRIPTION_JOKER).build());

        String expectedMessage = String.format(MemeEditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(model.getFilteredMemeList().get(0), editedMeme);

        assertCommandSuccess(memeEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMemeUnfilteredList_failure() {
        Meme firstMeme = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder(firstMeme).build();
        MemeEditCommand memeEditCommand = new MemeEditCommand(INDEX_SECOND_MEME, descriptor);

        assertCommandFailure(memeEditCommand, model, MemeEditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_duplicateMemeFilteredList_failure() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);

        // edit meme in filtered list into a duplicate in meme book
        Meme memeInList = model.getMemeBook().getMemeList().get(INDEX_SECOND_MEME.getZeroBased());
        MemeEditCommand memeEditCommand = new MemeEditCommand(INDEX_FIRST_MEME,
                new EditMemeDescriptorBuilder(memeInList).build());

        assertCommandFailure(memeEditCommand, model, MemeEditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_invalidMemeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_JOKER).build();
        MemeEditCommand memeEditCommand = new MemeEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(memeEditCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
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

        MemeEditCommand memeEditCommand = new MemeEditCommand(outOfBoundIndex,
                new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_JOKER).build());

        assertCommandFailure(memeEditCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final MemeEditCommand standardCommand = new MemeEditCommand(INDEX_FIRST_MEME, DESC_CHARMANDER);

        // same values -> returns true
        EditMemeDescriptor copyDescriptor = new EditMemeDescriptor(DESC_CHARMANDER);
        MemeEditCommand commandWithSameValues = new MemeEditCommand(INDEX_FIRST_MEME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new MemeClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MemeEditCommand(INDEX_SECOND_MEME, DESC_CHARMANDER)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new MemeEditCommand(INDEX_FIRST_MEME, DESC_JOKER)));
    }

}
