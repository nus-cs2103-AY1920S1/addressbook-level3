package seedu.address.logic.commands.cardcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_ABRA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
class EditCommandTest {

    private Model model = new ModelManager();

    @Test
    void execute_allFieldsSpecifiedUnfilteredList_success() {
        Card editedCard = new CardBuilder().build();
        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager();
        expectedModel.setCard(model.getFilteredCardList().get(0), editedCard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList.withWord(VALID_WORD_BUTTERFREE).withTags(VALID_TAG_BUG).build();

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withWord(VALID_WORD_BUTTERFREE).withTags(VALID_TAG_BUG).build();
        EditCommand editCommand = new EditCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager();
        expectedModel.setCard(lastCard, editedCard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, new EditCommand.EditCardDescriptor());
        Card editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_filteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withWord(VALID_WORD_BUTTERFREE).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder().withWord(VALID_WORD_BUTTERFREE).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager();
        expectedModel.setCard(model.getFilteredCardList().get(0), editedCard);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCommand.EditCardDescriptor descriptor =
                new EditCardDescriptorBuilder().withWord(VALID_WORD_BUTTERFREE).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CARD, DESC_ABRA);

        // same values -> returns true
        EditCommand.EditCardDescriptor copyDescriptor = new EditCommand.EditCardDescriptor(DESC_ABRA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CARD, DESC_ABRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CARD, DESC_BUTTERFREE)));
    }

}
