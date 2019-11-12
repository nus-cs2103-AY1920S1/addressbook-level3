package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.eatme.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.NameContainsKeywordsPredicate;
import seedu.eatme.testutil.EditEateryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_NO_PREFIX_MAC = "Mcdonald";
    public static final String VALID_NAME_NO_PREFIX_KFC = "Kentucky Fried Chicken";
    public static final boolean VALID_ISOPEN_MAC = true;
    public static final boolean VALID_ISOPEN_KFC = true;
    public static final String VALID_ADDRESS_NO_PREFIX_MAC = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_NO_PREFIX_KFC = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_NO_PREFIX_CHEAP = "cheap";
    public static final String VALID_TAG_NO_PREFIX_NICE = "nice";
    public static final String VALID_CATEGORY_NO_PREFIX = "Western";
    public static final String VALID_FILE_NO_PREFIX_JOHN = "john.json";
    public static final String VALID_FILE_NO_PREFIX_ALICE = "alice.json";

    public static final String VALID_NAME_WITH_PREFIX_KFC = " " + PREFIX_NAME + " " + VALID_NAME_NO_PREFIX_KFC;
    public static final String VALID_NAME_WITH_PREFIX_MAC = " " + PREFIX_NAME + " " + VALID_NAME_NO_PREFIX_MAC;
    public static final String VALID_ADDRESS_WITH_PREFIX_KFC = " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_NO_PREFIX_KFC;
    public static final String VALID_ADDRESS_WITH_PREFIX_MAC = " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_NO_PREFIX_MAC;
    public static final String VALID_TAG_WITH_PREFIX_CHEAP = " " + PREFIX_TAG + " " + VALID_TAG_NO_PREFIX_CHEAP;
    public static final String VALID_TAG_WITH_PREFIX_NICE = " " + PREFIX_TAG + " " + VALID_TAG_NO_PREFIX_NICE;
    public static final String VALID_CATEGORY_WITH_PREFIX = " " + PREFIX_CATEGORY + " " + VALID_CATEGORY_NO_PREFIX;
    public static final String VALID_FILE_WITH_PREFIX_JOHN = " " + PREFIX_USER + " " + VALID_FILE_NO_PREFIX_JOHN;

    public static final String VALID_NAME_EATBOOK = "Eatbook";
    public static final String VALID_NAME_SETHLUI = "Seth Lui";
    public static final String VALID_ADDRESS_EATBOOK = "https://eatbook.sg/feed";
    public static final String VALID_ADDRESS_SETHLUI = "https://sethlui.com/feed";
    public static final String NAME_DESC_EATBOOK = " " + PREFIX_NAME + " " + VALID_NAME_EATBOOK;
    public static final String ADDRESS_DESC_EATBOOK = " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_EATBOOK;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " James&"; // '&' not allowed in names
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + " hubby*"; // '*' not allowed in tags
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY
            + " _ch1nese"; //"_ not allowed to prefix category name and numbers not allowed in name"

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEateryDescriptor DESC_KFC;
    public static final EditCommand.EditEateryDescriptor DESC_MAC;

    static {
        DESC_KFC = new EditEateryDescriptorBuilder().withName(VALID_NAME_NO_PREFIX_KFC)
                .withAddress(VALID_ADDRESS_NO_PREFIX_KFC)
                .withTags(VALID_TAG_NO_PREFIX_CHEAP).build();
        DESC_MAC = new EditEateryDescriptorBuilder().withName(VALID_NAME_NO_PREFIX_MAC)
                .withAddress(VALID_ADDRESS_NO_PREFIX_MAC)
                .withTags(VALID_TAG_NO_PREFIX_CHEAP, VALID_TAG_NO_PREFIX_NICE).build();
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
     * - the eatery list, filtered eatery list and selected eatery in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        EateryList expectedEateryList = new EateryList(actualModel.getEateryList());
        List<Eatery> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEateryList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedEateryList, actualModel.getEateryList());
        assertEquals(expectedFilteredList, actualModel.getFilteredEateryList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the eatery at the given {@code targetIndex} in the
     * {@code model}'s eatery list.
     */
    public static void showEateryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEateryList().size());

        Eatery eatery = model.getFilteredEateryList().get(targetIndex.getZeroBased());
        final String[] splitName = eatery.getName().fullName.split("\\s+");
        model.updateFilteredEateryList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEateryList().size());
    }

}
