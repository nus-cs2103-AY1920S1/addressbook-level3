package seedu.address.logic.quiz.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.person.NameContainsKeywordsPredicate;
import seedu.address.model.quiz.person.Question;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "What is always coming, but never arrives?";
    public static final String VALID_NAME_BOB = "What is it that goes up, but never comes down?";
    public static final String VALID_ANSWER_AMY = "Tomorrow";
    public static final String VALID_ANSWER_BOB = "Age";
    public static final String VALID_CATEGORY_AMY = "CS2131";
    public static final String VALID_CATEGORY_BOB = "Leetcode";
    public static final String VALID_TYPE_AMY = "normal";
    public static final String VALID_TYPE_BOB = "high";
    public static final String VALID_TAG_LECTURE = "lecture";
    public static final String VALID_TAG_TUTORIAL = "tutorial";

    public static final String NAME_DESC_AMY = " " + PREFIX_QUESTION + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_QUESTION + VALID_NAME_BOB;
    public static final String ANSWER_DESC_AMY = " " + PREFIX_ANSWER + VALID_ANSWER_AMY;
    public static final String ANSWER_DESC_BOB = " " + PREFIX_ANSWER + VALID_ANSWER_BOB;
    public static final String CATEGORY_DESC_AMY = " " + PREFIX_CATEGORY + VALID_CATEGORY_AMY;
    public static final String CATEGORY_DESC_BOB = " " + PREFIX_CATEGORY + VALID_CATEGORY_BOB;
    public static final String TYPE_DESC_AMY = " " + PREFIX_TYPE + VALID_TYPE_AMY;
    public static final String TYPE_DESC_BOB = " " + PREFIX_TYPE + VALID_TYPE_BOB;
    public static final String TAG_DESC_TUTORIAL = " " + PREFIX_TAG + VALID_TAG_TUTORIAL;
    public static final String TAG_DESC_LECTURE = " " + PREFIX_TAG + VALID_TAG_LECTURE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_QUESTION + "James<qns>"; // '&' not allowed in names
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER + "911<ans>a"; // 'a' not allowed in phones
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY; // empty string not allowed
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "something"; // invalid type
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditQuestionDescriptor DESC_AMY;
    public static final EditCommand.EditQuestionDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditQuestionDescriptorBuilder().withName(VALID_NAME_AMY).withAnswer(VALID_ANSWER_AMY)
                .withCategory(VALID_CATEGORY_AMY).withType(VALID_TYPE_AMY).withTags(VALID_TAG_TUTORIAL).build();
        DESC_BOB = new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).withAnswer(VALID_ANSWER_BOB)
                .withCategory(VALID_CATEGORY_BOB).withType(VALID_TYPE_BOB)
                .withTags(VALID_TAG_LECTURE, VALID_TAG_TUTORIAL).build();
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
     * - the address book, filtered question list and selected question in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressQuizBook expectedAddressBook = new AddressQuizBook(actualModel.getAddressBook());
        List<Question> expectedFilteredList = new ArrayList<>(actualModel.getFilteredQuestionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredQuestionList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the question at the given {@code targetIndex} in the
     * {@code model}'s modulo.
     */
    public static void showQuestionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQuestionList().size());

        Question question = model.getFilteredQuestionList().get(targetIndex.getZeroBased());
        final String[] splitName = question.getName().fullName.split("\\s+");
        model.updateFilteredQuestionList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0]), false));

        assertEquals(1, model.getFilteredQuestionList().size());
    }

    /**
     * Deletes the first question in {@code model}'s filtered list from {@code model}'s modulo.
     */
    public static void deleteFirstQuestion(Model model) {
        Question firstQuestion = model.getFilteredQuestionList().get(0);
        model.deleteQuestion(firstQuestion);
        model.commitQuizBook();
    }

}
