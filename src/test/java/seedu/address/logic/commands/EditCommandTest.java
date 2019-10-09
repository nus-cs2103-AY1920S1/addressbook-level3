package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashCardAtIndex;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;
import seedu.address.testutil.FlashCardBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        FlashCard editedFlashCard = new FlashCardBuilder().build();
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(editedFlashCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(model.getFilteredFlashCardList().get(0), editedFlashCard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFlashCard = Index.fromOneBased(model.getFilteredFlashCardList().size());
        FlashCard lastFlashCard = model.getFilteredFlashCardList().get(indexLastFlashCard.getZeroBased());

        FlashCardBuilder flashCardInList = new FlashCardBuilder(lastFlashCard);
        FlashCard editedFlashCard = flashCardInList.withQuestion(VALID_QUESTION_2).withAnswer(VALID_ANSWER_2)
                .withCatgeories(VALID_CATEGORY_HISTORY).build();

        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder().withQuestion(VALID_QUESTION_2)
                .withAnswer(VALID_ANSWER_2).withCategories(VALID_CATEGORY_HISTORY).build();
        EditCommand editCommand = new EditCommand(indexLastFlashCard, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(lastFlashCard, editedFlashCard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD, new EditFlashCardDescriptor());
        FlashCard editedFlashCard = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFlashCardAtIndex(model, INDEX_FIRST_FLASHCARD);

        FlashCard flashCardInFilteredList =
                model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());

        FlashCard editedFlashCard =
                new FlashCardBuilder(flashCardInFilteredList).withQuestion(VALID_QUESTION_2).build();

        EditCommand editCommand = new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashCardDescriptorBuilder().withQuestion(VALID_QUESTION_2).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setFlashCard(model.getFilteredFlashCardList().get(0), editedFlashCard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFlashCardUnfilteredList_failure() {
        FlashCard firstFlashCard = model.getFilteredFlashCardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(firstFlashCard).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_FLASHCARD, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_duplicateFlashCardFilteredList_failure() {
        showFlashCardAtIndex(model, INDEX_FIRST_FLASHCARD);

        // edit flashCard in filtered list into a duplicate in address book
        FlashCard flashCardInList =
                model.getAddressBook().getFlashcardList().get(INDEX_SECOND_FLASHCARD.getZeroBased());
        EditCommand editCommand =
                new EditCommand(INDEX_FIRST_FLASHCARD,
                new EditFlashCardDescriptorBuilder(flashCardInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_invalidFlashCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashCardList().size() + 1);
        EditFlashCardDescriptor descriptor =
                new EditFlashCardDescriptorBuilder().withQuestion(VALID_QUESTION_2).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidFlashCardIndexFilteredList_failure() {
        showFlashCardAtIndex(model, INDEX_FIRST_FLASHCARD);
        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFlashcardList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFlashCardDescriptorBuilder().withQuestion(VALID_QUESTION_2).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_FLASHCARD, DESC_1);

        // same values -> returns true
        EditFlashCardDescriptor copyDescriptor = new EditFlashCardDescriptor(DESC_1);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_FLASHCARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_FLASHCARD, DESC_1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_FLASHCARD, DESC_2)));
    }

}
