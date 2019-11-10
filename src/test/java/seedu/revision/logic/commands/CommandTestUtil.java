package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_WRONG;
import static seedu.revision.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.predicates.QuestionContainsKeywordsPredicate;
import seedu.revision.testutil.EditAnswerableDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_ALPHA = "Amy Bee";
    public static final String VALID_QUESTION_BETA = "Bob Choo";
    public static final String VALID_QUESTION_TYPE = "mcq";
    public static final String VALID_DIFFICULTY_ALPHA = "1";
    public static final String VALID_DIFFICULTY_BETA = "3";
    public static final String VALID_CATEGORY_ALPHA = "Block 312, Amy Street 1";
    public static final String VALID_CATEGORY_GREENFIELD = "greenfield";
    public static final String VALID_CATEGORY_UML = "UML";

    public static final String QUESTION_TYPE_MCQ = " " + PREFIX_QUESTION_TYPE + "mcq";
    public static final String QUESTION_DESC_ALPHA = " " + PREFIX_QUESTION + VALID_QUESTION_ALPHA;
    public static final String QUESTION_DESC_BETA = " " + PREFIX_QUESTION + VALID_QUESTION_BETA;
    public static final String CORRECT_ANSWER_DESC_BROWNFIELD = " " + PREFIX_CORRECT + "Brownfield";
    public static final String CORRECT_ANSWER_DESC_GREENFIELD = " " + PREFIX_CORRECT + "Greenfield";
    public static final String MCQ_WRONG_ANSWER_DESC = " " + PREFIX_WRONG + "Greenfield " + PREFIX_WRONG + "Blackfield "
            + PREFIX_WRONG + "Whitefield";
    public static final String QUESTION_TYPE_DESC = " " + PREFIX_QUESTION_TYPE + VALID_QUESTION_TYPE;
    public static final String DIFFICULTY_DESC_ALPHA = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_ALPHA;
    public static final String DIFFICULTY_DESC_BETA = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_BETA;
    public static final String CATEGORY_DESC_UML = " " + PREFIX_CATEGORY + VALID_CATEGORY_UML;
    public static final String CATEGORY_DESC_GREENFIELD = " " + PREFIX_CATEGORY + VALID_CATEGORY_GREENFIELD;

    // empty string not allowed for questions
    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + "";
    // 'a' not allowed in difficulty
    public static final String INVALID_DIFFICULTY_DESC = " " + PREFIX_DIFFICULTY + "911a";
    // category cannot just be whitespace
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditAnswerableDescriptor DESC_ALPHA;
    public static final EditCommand.EditAnswerableDescriptor DESC_BETA;

    private static final Answer correctAnswer = new Answer("CORRECT");
    private static final ArrayList<Answer> defaultCorrectAnswerList = new ArrayList<>(Arrays.asList(correctAnswer));
    private static final Answer wrongAnswer = new Answer("WRONG");
    private static final ArrayList<Answer> defaultWrongAnswerList = new ArrayList<>(Arrays.asList(wrongAnswer));

    static {
        DESC_ALPHA = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_ALPHA)
                .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList)
                .withDifficulty(VALID_DIFFICULTY_ALPHA).withCategories(VALID_CATEGORY_UML).build();
        DESC_BETA = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_BETA)
                .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList)
                .withDifficulty(VALID_DIFFICULTY_BETA)
                .withCategories(VALID_CATEGORY_GREENFIELD, VALID_CATEGORY_UML).build();
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
        } catch (CommandException | ParseException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws ParseException {
        CommandResult expectedCommandResult = new CommandResultBuilder().withFeedBack(expectedMessage).build();
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the revision tool, filtered answerable list and selected answerable in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RevisionTool expectedAddressBook = new RevisionTool(actualModel.getRevisionTool());
        List<Answerable> expectedFilteredList = new ArrayList<>(actualModel.getFilteredAnswerableList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getRevisionTool());
        assertEquals(expectedFilteredList, actualModel.getFilteredAnswerableList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the answerable at the given {@code targetIndex} in the
     * {@code model}'s revision tool.
     */
    public static void showAnswerableAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAnswerableList().size());

        Answerable answerable = model.getFilteredAnswerableList().get(targetIndex.getZeroBased());
        final String[] splitName = answerable.getQuestion().question.split("\\s+");
        model.updateFilteredAnswerableList(new QuestionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredAnswerableList().size());
    }

}
