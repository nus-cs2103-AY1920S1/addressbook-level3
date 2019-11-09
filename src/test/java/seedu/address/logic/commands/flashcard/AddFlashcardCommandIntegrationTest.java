package seedu.address.logic.commands.flashcard;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.testutil.FlashcardBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddFlashcardCommand}.
 */
public class AddFlashcardCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Flashcard validFlashcard = new FlashcardBuilder().build();

        Model expectedModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
        expectedModel.addFlashcard(validFlashcard);

        assertCommandSuccess(new AddFlashcardCommand(validFlashcard), model,
                String.format(AddFlashcardCommand.MESSAGE_SUCCESS, validFlashcard), expectedModel);
    }


    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard flashcardInList = model.getStudyBuddyPro().getFlashcardList().get(0);
        assertCommandFailure(new AddFlashcardCommand(flashcardInList), model,
                AddFlashcardCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

}
