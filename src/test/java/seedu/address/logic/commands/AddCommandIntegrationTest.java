package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.FlashCardBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newFlashCard_success() {
        FlashCard validFlashCard = new FlashCardBuilder().build();

        Model expectedModel = new ModelManager(model.getKeyboardFlashCards(), new UserPrefs());
        expectedModel.addFlashCard(validFlashCard);

        assertCommandSuccess(new AddCommand(validFlashCard), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validFlashCard), expectedModel);
    }

    @Test
    public void execute_duplicateFlashCards_throwsCommandException() {
        FlashCard flashCardInList = model.getKeyboardFlashCards().getFlashcardList().get(0);
        assertCommandFailure(new AddCommand(flashCardInList), model, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

}
