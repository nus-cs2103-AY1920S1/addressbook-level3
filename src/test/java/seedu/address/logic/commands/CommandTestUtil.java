package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.TaskContainsAllKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.QuestionContainsAllKeywordsPredicate;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_1 = "When is WWII?";
    public static final String VALID_QUESTION_2 = "Where is NUS?";
    public static final String VALID_ANSWER_1 = "1 September 1939 – 2 September 1945";
    public static final String VALID_ANSWER_2 = "Kent Ridge";
    public static final String VALID_RATING_1 = "good";
    public static final String VALID_RATING_2 = "easy";
    public static final String VALID_CATEGORY_HISTORY = "history";
    public static final String VALID_CATEGORY_LOCATION = "location";
    public static final String VALID_DOCUMENT_PATH_1 = "cheat_sheet.docx";
    public static final String VALID_DOCUMENT_PATH_2 = "cs2105.docx";
    public static final String VALID_JSON_EXPORT_PATH_1 = "flashcards.json";

    public static final String QUESTION_DESC_1 =
            " " + PREFIX_QUESTION + VALID_QUESTION_1;

    public static final String QUESTION_DESC_2 =
            " " + PREFIX_QUESTION + VALID_QUESTION_2;

    public static final String ANSWER_DESC_1 =
            " " + PREFIX_ANSWER + VALID_ANSWER_1;

    public static final String ANSWER_DESC_2 =
            " " + PREFIX_ANSWER + VALID_ANSWER_2;

    public static final String RATING_DESC_1 =
            " " + PREFIX_RATING + VALID_RATING_1;

    public static final String RATING_DESC_2 =
            " " + PREFIX_RATING + VALID_RATING_2;

    public static final String CATEGORY_DESC_HISTORY =
            " " + PREFIX_CATEGORY + VALID_CATEGORY_HISTORY;

    public static final String CATEGORY_DESC_LOCATION =
            " " + PREFIX_CATEGORY + VALID_CATEGORY_LOCATION;

    public static final String INVALID_QUESTION_DESC =
            " " + PREFIX_QUESTION + " "; // ' ' not allowed in questions

    public static final String INVALID_ANSWER_DESC =
            " " + PREFIX_ANSWER + " "; // ' ' not allowed in answers

    public static final String INVALID_RATING_DESC =
            " " + PREFIX_RATING + "fly"; // empty string not allowed for rating

    public static final String INVALID_CATEGORY_DESC =
            " " + PREFIX_CATEGORY + "hubby*"; // '*' not allowed in categories

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditFlashCardDescriptor DESC_1;
    public static final EditFlashCardDescriptor DESC_2;

    static {
        DESC_1 = new EditFlashCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_1)
                .withAnswer(VALID_ANSWER_1)
                .withRating(VALID_RATING_1)
                .withCategories(VALID_CATEGORY_LOCATION)
                .build();
        DESC_2 = new EditFlashCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_2)
                .withAnswer(VALID_ANSWER_2)
                .withRating(VALID_RATING_2)
                .withCategories(VALID_CATEGORY_HISTORY, VALID_CATEGORY_LOCATION)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered flashCard list and selected flashCard in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        KeyboardFlashCards expectedKeyboardFlashCards = new KeyboardFlashCards(actualModel.getKeyboardFlashCards());
        List<FlashCard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashCardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedKeyboardFlashCards, actualModel.getKeyboardFlashCards());
        assertEquals(expectedFilteredList, actualModel.getFilteredFlashCardList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the flashCard at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFlashCardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashCardList().size());

        FlashCard flashCard = model.getFilteredFlashCardList().get(targetIndex.getZeroBased());
        final String[] question = flashCard.getQuestion().fullQuestion.split("\\s+");
        model.updateFilteredFlashCardList(new QuestionContainsAllKeywordsPredicate(Arrays.asList(question)));

        assertEquals(1, model.getFilteredFlashCardList().size());
    }

    //@@author dalsontws
    /**
     * Updates {@code model}'s filtered list to show only the deadline at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showDeadlineAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeadlineList().size());

        Deadline deadline = model.getFilteredDeadlineList().get(targetIndex.getZeroBased());
        final String[] task = deadline.getTask().fullTask.split("\\s+");
        model.updateFilteredDeadlineList(new TaskContainsAllKeywordsPredicate(Arrays.asList(task)));

        assertEquals(1, model.getFilteredDeadlineList().size());
    }

}
