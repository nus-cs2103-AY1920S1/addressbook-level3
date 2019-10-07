package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_WEBLINK;
import static seedu.algobase.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.NameContainsKeywordsPredicate;
import seedu.algobase.model.problem.Problem;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_AUTHOR_ONE = "Steven Halim";
    public static final String VALID_DESCRIPTION_ONE = "hello world";
    public static final String VALID_DIFFICULTY_ONE = "1.5";
    public static final String VALID_NAME_ONE = "Quick sort";
    public static final String VALID_REMARK_ONE = "ez";
    public static final String VALID_SOURCE_ONE = "open.kattis.com";
    public static final String VALID_WEBLINK_ONE = "open.kattis.com/12345";

    public static final String AUTHOR_DESC_ONE = " " + PREFIX_AUTHOR + VALID_AUTHOR_ONE;
    public static final String DESCRIPTION_DESC_ONE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ONE;
    public static final String DIFFICULTY_DESC_ONE = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_ONE;
    public static final String NAME_DESC_ONE = " " + PREFIX_NAME + VALID_NAME_ONE;
    public static final String REMARK_DESC_ONE = " " + PREFIX_REMARK + VALID_REMARK_ONE;
    public static final String SOURCE_DESC_ONE = " " + PREFIX_SOURCE + VALID_SOURCE_ONE;
    public static final String WEBLINK_DESC_ONE = " " + PREFIX_WEBLINK + VALID_WEBLINK_ONE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_AUTHOR_DESC = " " + PREFIX_AUTHOR + "James&"; // '&' not allowed in author
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION;
    // empty string not allowed for description
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK; // empty string not allowed for remarks
    public static final String INVALID_SOURCE_DESC = " " + PREFIX_SOURCE;
    public static final String INVALID_WEBLINK_DESC = " " + PREFIX_WEBLINK;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

//    public static final EditCommand.EditPersonDescriptor DESC_AMY;
//    public static final EditCommand.EditPersonDescriptor DESC_BOB;

//    static {
//        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
//                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
//                .withTags(VALID_TAG_FRIEND).build();
//        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
//                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
//                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
//    }

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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AlgoBase expectedAddressBook = new AlgoBase(actualModel.getAlgoBase());
        List<Problem> expectedFilteredList = new ArrayList<>(actualModel.getFilteredProblemList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAlgoBase());
        assertEquals(expectedFilteredList, actualModel.getFilteredProblemList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredProblemList().size());

        Problem problem = model.getFilteredProblemList().get(targetIndex.getZeroBased());
        final String[] splitName = problem.getName().fullName.split("\\s+");
        model.updateFilteredProblemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredProblemList().size());
    }

}
