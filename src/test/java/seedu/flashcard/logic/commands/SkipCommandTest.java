package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.logic.commands.SkipCommand.MESSAGE_NULL_QUIZ_FLASHCARD;
import static seedu.flashcard.logic.commands.SkipCommand.MESSAGE_SUCCESS;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.Flashcard;



public class SkipCommandTest {
    private static final String CORRECT_ANSWER = "Your answer: %1$s is correct.\n";
    private static final String INCORRECT_ANSWER = "Your answer: %1$s is incorrect.\n"
            + "The correct answer is: %2$s\n";

    private static final String QUIZ_END = "This quiz has ended.";
    private Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_flashcardsToQuizPresentWrongAnswer_success() {
        Flashcard flashcardToQuiz = model.getFilteredFlashcardList().get(INDEX_THIRD_FLASHCARD.getZeroBased());
        ArrayList<Flashcard> quizList = new ArrayList<>();
        quizList.add(flashcardToQuiz);
        model.setQuiz(quizList);
        SkipCommand skipCommand = new SkipCommand();

        Model expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());

        assertCommandSuccess(skipCommand, model,
                commandHistory,
                new CommandResult(MESSAGE_SUCCESS, true, QUIZ_END), expectedModel);
    }

    @Test
    public void execute_flashcardsToQuizAbsent_throwsCommandException() {
        SkipCommand flipCommand = new SkipCommand();

        assertCommandFailure(flipCommand, model, commandHistory, MESSAGE_NULL_QUIZ_FLASHCARD);
    }

}
