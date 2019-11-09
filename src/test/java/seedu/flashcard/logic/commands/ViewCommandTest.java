package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.Flashcard;

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Flashcard flashcardToView = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_FLASHCARD);

        String expectedMessage = flashcardToView.fullString();

        ModelManager expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());

        assertCommandSuccess(viewCommand, model,
                commandHistory,
                new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, commandHistory, ViewCommand.MESSAGE_INVALID_FLASHCARD_INDEX);
    }
}
