package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.answerable.QuestionContainsKeywordsPredicate;
import seedu.address.model.answerable.Answerable;
import seedu.address.testutil.EditAnswerableDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_AMY = "Amy Bee";
    public static final String VALID_QUESTION_BOB = "Bob Choo";
    public static final String VALID_DIFFICULTY_AMY = "11111111";
    public static final String VALID_DIFFICULTY_BOB = "22222222";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String QUESTION_DESC_AMY = " " + PREFIX_QUESTION + VALID_QUESTION_AMY;
    public static final String QUESTION_DESC_BOB = " " + PREFIX_QUESTION + VALID_QUESTION_BOB;
    public static final String DIFFICULTY_DESC_AMY = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_AMY;
    public static final String DIFFICULTY_DESC_BOB = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_CATEGORY + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_CATEGORY + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + "James&"; // '&' not allowed in names
    public static final String INVALID_DIFFICULTY_DESC = " " + PREFIX_DIFFICULTY + "911a"; // 'a' not allowed in difficulty
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_CATEGORY; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditAnswerableDescriptor DESC_AMY;
    public static final EditCommand.EditAnswerableDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_AMY)
                .withDifficulty(VALID_DIFFICULTY_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditAnswerableDescriptorBuilder().withQuestion(VALID_QUESTION_BOB)
                .withDifficulty(VALID_DIFFICULTY_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the address book, filtered answerable list and selected answerable in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Answerable> expectedFilteredList = new ArrayList<>(actualModel.getFilteredAnswerableList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredAnswerableList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the answerable at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showAnswerableAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAnswerableList().size());

        Answerable answerable = model.getFilteredAnswerableList().get(targetIndex.getZeroBased());
        final String[] splitName = answerable.getQuestion().fullQuestion.split("\\s+");
        model.updateFilteredAnswerableList(new QuestionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredAnswerableList().size());
    }

}
