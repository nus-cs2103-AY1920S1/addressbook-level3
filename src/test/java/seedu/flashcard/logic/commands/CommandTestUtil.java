package seedu.flashcard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.VersionedFlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.FlashcardContainsKeywordsPredicate;
import seedu.flashcard.testutil.EditFlashcardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_APPLE = "What color is an apple?";
    public static final String VALID_QUESTION_BANANA = "What color is a banana?";
    public static final String VALID_DEFINITION_APPLE = "It is a round sweet juicy fruit.";
    public static final String VALID_DEFINITION_BANANA = "It is a curly sweet fruit, not so juicy";
    public static final String VALID_TAG_ROUND = "round";
    public static final String VALID_TAG_LONG = "long";
    public static final String VALID_TAG_CIVIL_ENGINEERING = "Civil Engineering";
    public static final String VALID_ANSWER_APPLE = "Red";
    public static final String VALID_ANSWER_BANANA = "Yellow";
    public static final String VALID_CHOICE_RED = "Red";
    public static final String VALID_CHOICE_YELLOW = "Yellow";
    public static final String VALID_CHOICE_BLUE = "Blue";
    public static final String VALID_CHOICE_GREEN = "Green";

    public static final String QUESTION_DESC_APPLE = " " + PREFIX_QUESTION + VALID_QUESTION_APPLE;
    public static final String QUESTION_DESC_BANANA = " " + PREFIX_QUESTION + VALID_QUESTION_BANANA;
    public static final String DEFINITION_DESC_APPLE = " " + PREFIX_DEFINITION + VALID_DEFINITION_APPLE;
    public static final String DEFINITION_DESC_BANANA = " " + PREFIX_DEFINITION + VALID_DEFINITION_BANANA;
    public static final String ANSWER_DESC_ANSWER_APPLE = " " + PREFIX_ANSWER + VALID_ANSWER_APPLE;
    public static final String ANSWER_DESC_ANSWER_BANANA = " " + PREFIX_ANSWER + VALID_ANSWER_BANANA;
    public static final String TAG_DESC_ROUND = " " + PREFIX_TAG + VALID_TAG_ROUND;
    public static final String TAG_DESC_LONG = " " + PREFIX_TAG + VALID_TAG_LONG;
    public static final String CHOICE_DESC_RED = " " + PREFIX_CHOICE + VALID_CHOICE_RED;
    public static final String CHOICE_DESC_YELLOW = " " + PREFIX_CHOICE + VALID_CHOICE_YELLOW;
    public static final String CHOICE_DESC_GREEN = " " + PREFIX_CHOICE + VALID_CHOICE_GREEN;
    public static final String CHOICE_DESC_BLUE = " " + PREFIX_CHOICE + VALID_CHOICE_BLUE;

    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + " "; // Questions cannot be empty
    public static final String INVALID_DEFINITION_DESC = " " + PREFIX_DEFINITION + " "; // Definitions cannot be empty
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + " "; //Tags cannot be empty
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER + " "; //Answers cannot be empty

    public static final String PREAMBLE_WHITESPACE = "\t \r \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditFlashcardDescriptor DESC_APPLE;
    public static final EditFlashcardDescriptor DESC_BANANA;

    static {
        DESC_APPLE = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_APPLE)
                  .withAnswer(VALID_ANSWER_APPLE).withDefinition(VALID_DEFINITION_APPLE).withTag(VALID_TAG_ROUND)
                  .withChoices(VALID_CHOICE_RED, VALID_CHOICE_BLUE, VALID_CHOICE_GREEN, VALID_CHOICE_YELLOW).build();
        DESC_BANANA = new EditFlashcardDescriptorBuilder().withQuestion(VALID_QUESTION_BANANA)
                  .withAnswer(VALID_ANSWER_BANANA).withDefinition(VALID_DEFINITION_BANANA).withTag(VALID_TAG_LONG)
                  .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, CommandHistory commandHistory) {
        try {
            CommandResult result = command.execute(actualModel, commandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of the command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the flashcard list, filtered flashcard list and selected flashcard in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel,
                                            String expectedMessage, CommandHistory commandHistory) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        VersionedFlashcardList expectedFlashcardList = new VersionedFlashcardList(actualModel.getFlashcardList());
        List<Flashcard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashcardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, commandHistory));
        assertEquals(expectedFlashcardList, actualModel.getFlashcardList());
        assertEquals(expectedFilteredList, actualModel.getFilteredFlashcardList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the flashcard list, filtered flashcard list and selected flashcard in
     * {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel, CommandHistory commandHistory) {
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, commandHistory));
        assertEquals(actualModel, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFlashcardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Flashcard flashcard = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitQuestion = new String[] {flashcard.getQuestion().toString()};
        model.updateFilteredFlashcardList(new FlashcardContainsKeywordsPredicate(Arrays.asList(splitQuestion)));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }
}
