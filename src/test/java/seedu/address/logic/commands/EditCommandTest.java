package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEME;
import static seedu.address.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.address.model.MemeBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meme.Meme;
import seedu.address.testutil.EditMemeDescriptorBuilder;
import seedu.address.testutil.MemeBuilder;

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

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredMemeList().size());
        Meme lastMeme = model.getFilteredMemeList().get(indexLastPerson.getZeroBased());

        MemeBuilder personInList = new MemeBuilder(lastMeme);
        Meme editedMeme = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(lastMeme, editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

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
        Meme editedMeme = new MemeBuilder(memeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEME,
                new EditMemeDescriptorBuilder().withName(VALID_NAME_BOB).build());

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

        // edit meme in filtered list into a duplicate in address book
        Meme memeInList = model.getMemeBook().getMemeList().get(INDEX_SECOND_MEME.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEME,
                new EditMemeDescriptorBuilder(memeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_invalidMemeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMemeIndexFilteredList_failure() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);
        Index outOfBoundIndex = INDEX_SECOND_MEME;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMemeBook().getMemeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMemeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MEME, DESC_AMY);

        // same values -> returns true
        EditMemeDescriptor copyDescriptor = new EditMemeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MEME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MEME, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MEME, DESC_BOB)));
    }

}
