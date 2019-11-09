package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.Flashcard;

public class QuizCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Flashcard flashcardToQuiz = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        QuizCommand quizCommand = new QuizCommand(INDEX_FIRST_FLASHCARD, 1);

        String expectedMessage = QuizCommand.MESSAGE_RESULT_SUCCESS;
        String expectedFlashcard = flashcardToQuiz.toString();

        ModelManager expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        List<Flashcard> quizList = new ArrayList<>();
        quizList.add(flashcardToQuiz);
        expectedModel.setQuiz(quizList);

        assertCommandSuccess(quizCommand, model,
                commandHistory,
                new CommandResult(expectedMessage, true, expectedFlashcard), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        QuizCommand quizCommand = new QuizCommand(outOfBoundIndex, 1);

        assertCommandFailure(quizCommand, model, commandHistory, QuizCommand.MESSAGE_INVALID_FLASHCARD_INDEX);
    }
}
