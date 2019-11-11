package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Flashcard;

public class FlipCommandTest {

    private static final String CORRECT_ANSWER = "Your answer: %1$s is correct.\n";
    private static final String INCORRECT_ANSWER = "Your answer: %1$s is incorrect.\n"
            + "The correct answer is: %2$s\n";

    private static final String QUIZ_END = "This quiz has ended.";
    private Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_flashcardsToQuizPresentCorrectAnswer_success() {
        Flashcard flashcardToQuiz = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        ArrayList<Flashcard> quizList = new ArrayList<>();
        quizList.add(flashcardToQuiz);
        model.setQuiz(quizList);
        Answer correctAnswer = flashcardToQuiz.getAnswer();
        FlipCommand flipCommand = new FlipCommand(new Answer("1"));

        Model expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        String expectedMessage = String.format(CORRECT_ANSWER, correctAnswer.toString());

        assertCommandSuccess(flipCommand, model,
                commandHistory,
                new CommandResult(expectedMessage, true, QUIZ_END), expectedModel);
    }

    @Test
    public void execute_flashcardsToQuizPresentWrongAnswer_success() {
        Flashcard flashcardToQuiz = model.getFilteredFlashcardList().get(INDEX_THIRD_FLASHCARD.getZeroBased());
        ArrayList<Flashcard> quizList = new ArrayList<>();
        quizList.add(flashcardToQuiz);
        model.setQuiz(quizList);
        Answer correctAnswer = flashcardToQuiz.getAnswer();
        Answer wrongAnswer = new Answer("Wrong Answer");
        FlipCommand flipCommand = new FlipCommand(wrongAnswer);

        Model expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        String expectedMessage = String.format(INCORRECT_ANSWER, wrongAnswer.toString(), correctAnswer.toString());

        assertCommandSuccess(flipCommand, model,
                commandHistory,
                new CommandResult(expectedMessage, true, QUIZ_END), expectedModel);
    }

    @Test
    public void execute_flashcardsToQuizAbsent_throwsCommandException() {
        FlipCommand flipCommand = new FlipCommand(new Answer("Invalid"));

        assertCommandFailure(flipCommand, model, commandHistory, FlipCommand.MESSAGE_NULL_QUIZ_FLASHCARD);
    }

    @Test
    public void execute_nonNumberInputForMcqFlashcard_throwsCommandException() {
        Flashcard flashcardToQuiz = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        ArrayList<Flashcard> quizList = new ArrayList<>();
        quizList.add(flashcardToQuiz);
        model.setQuiz(quizList);
        FlipCommand flipCommand = new FlipCommand(new Answer("Invalid"));

        assertCommandFailure(flipCommand, model,
                commandHistory, FlipCommand.MESSAGE_MCQ_INDEX);
    }

}
