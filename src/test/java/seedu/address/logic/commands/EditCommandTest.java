package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAnswerableAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ANSWERABLE;
import static seedu.address.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditAnswerableDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.answerable.Answerable;
import seedu.address.testutil.EditAnswerableDescriptorBuilder;
import seedu.address.testutil.AnswerableBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Answerable editedAnswerable = new AnswerableBuilder().build();
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder(editedAnswerable).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAnswerable(model.getFilteredAnswerableList().get(0), editedAnswerable);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAnswerable = Index.fromOneBased(model.getFilteredAnswerableList().size());
        Answerable lastAnswerable = model.getFilteredAnswerableList().get(indexLastAnswerable.getZeroBased());

        AnswerableBuilder answerableInList = new AnswerableBuilder(lastAnswerable);
        Answerable editedAnswerable = answerableInList.withQuestion(VALID_QUESTION_BOB).withDifficulty(VALID_DIFFICULTY_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_BOB)
                .withDifficulty(VALID_DIFFICULTY_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastAnswerable, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAnswerable(lastAnswerable, editedAnswerable);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE, new EditAnswerableDescriptor());
        Answerable editedAnswerable = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        Answerable answerableInFilteredList = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        Answerable editedAnswerable = new AnswerableBuilder(answerableInFilteredList).withQuestion(VALID_QUESTION_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE,
                new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAnswerable(model.getFilteredAnswerableList().get(0), editedAnswerable);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAnswerableUnfilteredList_failure() {
        Answerable firstAnswerable = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder(firstAnswerable).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ANSWERABLE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ANSWERABLE);
    }

    @Test
    public void execute_duplicateAnswerableFilteredList_failure() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        // edit answerable in filtered list into a duplicate in address book
        Answerable answerableInList = model.getAddressBook().getAnswerableList().get(INDEX_SECOND_ANSWERABLE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE,
                new EditAnswerableDescriptorBuilder(answerableInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ANSWERABLE);
    }

    @Test
    public void execute_invalidAnswerableIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnswerableList().size() + 1);
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidAnswerableIndexFilteredList_failure() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);
        Index outOfBoundIndex = INDEX_SECOND_ANSWERABLE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getAnswerableList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ANSWERABLE, DESC_AMY);

        // same values -> returns true
        EditAnswerableDescriptor copyDescriptor = new EditAnswerableDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ANSWERABLE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ANSWERABLE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ANSWERABLE, DESC_BOB)));
    }

}
