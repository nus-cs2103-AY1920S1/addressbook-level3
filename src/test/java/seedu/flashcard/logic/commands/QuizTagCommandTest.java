package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_CIVIL_ENGINEERING;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.TypicalFlashcard.CHANGI_AIRPORT;
import static seedu.flashcard.testutil.TypicalFlashcard.DAXING_AIRPORT;
import static seedu.flashcard.testutil.TypicalFlashcard.MOUNT_BLANC;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.tag.Tag;

public class QuizTagCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validTagWithCards_success() {
        Tag testTag = new Tag(VALID_TAG_CIVIL_ENGINEERING);
        Set<Tag> testTagSet = Collections.singleton(testTag);
        QuizTagCommand quizTagCommand = new QuizTagCommand(testTagSet, 1);

        Model expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        List<Flashcard> taggedList = new ArrayList<>(Arrays.asList(MOUNT_BLANC, DAXING_AIRPORT, CHANGI_AIRPORT));
        expectedModel.setQuiz(taggedList);
        expectedModel.updateFilteredFlashcardList(expectedModel.getHasTagPredicate(testTagSet));
        Flashcard firstCard = expectedModel.getQuiz().quizCard();
        String expectedMessage = QuizTagCommand.MESSAGE_SUCCESS;
        String expectedFlashcard = firstCard.toString();

        assertCommandSuccess(quizTagCommand, model,
                commandHistory,
                new CommandResult(expectedMessage, true, expectedFlashcard), expectedModel);
    }

    @Test
    public void execute_validTagWithNoCards_throwsCommandException() {
        Tag testTag = new Tag(VALID_TAG_LONG);
        Set<Tag> testTagSet = Collections.singleton(testTag);
        QuizTagCommand quizTagCommand = new QuizTagCommand(testTagSet, 1);

        Model expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        expectedModel.updateFilteredFlashcardList(expectedModel.getHasTagPredicate(testTagSet));
        expectedModel.setQuiz(Collections.emptyList());

        assertCommandFailure(quizTagCommand, model, QuizTagCommand.TAG_INVALID, expectedModel, commandHistory);
    }
}
