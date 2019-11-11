package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.testutil.EditFlashcardDescriptorBuilder;
import seedu.flashcard.testutil.FlashcardBuilder;

public class EditCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        ShortAnswerFlashcard editedFlashcard = new FlashcardBuilder().buildShortAnswerFlashcard();
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder(editedFlashcard).build();
        EditCommand editCommand = new EditCommand(INDEX_THIRD_FLASHCARD, descriptor);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard);
        Model expectedModel = new ModelManager(new FlashcardList(model.getFlashcardList()), new UserPrefs());
        expectedModel.setFlashcard(model.getFilteredFlashcardList().get(2), editedFlashcard);
        assertCommandSuccess(editCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }
}
