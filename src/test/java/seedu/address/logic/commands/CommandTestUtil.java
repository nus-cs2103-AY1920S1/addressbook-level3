package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.item.Item;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ItemModel;
import seedu.address.testutil.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DESCRIPTION_1 = "homework";
    public static final String VALID_DESCRIPTION_2 = "do all my homework";
    public static final String VALID_EVENT_1 = "2018-12-30T19:34:50.63";
    public static final String VALID_EVENT_2 = "2018-12-30T19:34";
    public static final String VALID_REMINDER_1 = "2018-12-30T19:34:50.63";
    public static final String VALID_REMINDER_2 = "2018-12-30T19:34";
    public static final String VALID_PRIORITY_HIGH = "hIgH";
    public static final String VALID_PRIORITY_MEDIUM = "mEDIUM";
    public static final String VALID_PRIORITY_LOW = "low";
    public static final String VALID_TAG_1 = "necessary";
    public static final String VALID_TAG_2 = "123necessary";
    public static final String VALID_DURATION_NULL = null;

    public static final String DESCRIPTION_DESC_1 = VALID_DESCRIPTION_1;
    public static final String DESCRIPTION_DESC_2 = VALID_DESCRIPTION_2;
    public static final String EVENT_DESC_1 = " " + PREFIX_DATETIME + VALID_EVENT_1;
    public static final String EVENT_DESC_2 = " " + PREFIX_DATETIME + VALID_EVENT_2;
    public static final String REMINDER_DESC_1 = " " + PREFIX_REMINDER + VALID_REMINDER_1;
    public static final String REMINDER_DESC_2 = " " + PREFIX_REMINDER + VALID_REMINDER_2;
    public static final String PRIORITY_DESC_HIGH = " " + PREFIX_PRIORITY + VALID_PRIORITY_HIGH;
    public static final String PRIORITY_DESC_MEDIUM = " " + PREFIX_PRIORITY + VALID_PRIORITY_MEDIUM;
    public static final String PRIORITY_DESC_LOW = " " + PREFIX_PRIORITY + VALID_PRIORITY_LOW;
    public static final String TAG_DESC_1 = " " + PREFIX_TAG + VALID_TAG_1;
    public static final String TAG_DESC_2 = " " + PREFIX_TAG + VALID_TAG_2;

    public static final String INVALID_EVENT_DESC = "monday"; // must be of the LocalDateTime.parse() format
    public static final String INVALID_REMINDER_DESC = "01/02/2018"; // must be of the LocalDateTime.parse() format
    public static final String INVALID_PRIORITY_DESC = "very very high"; // must be a single word high/medium/low
    public static final String INVALID_TAG_DESC = "@prepare-ahead"; // "@"/"-" symbols not allowed. Only alphanumeric.

    public static final String PREAMBLE_WHITESPACE = "-d  -r  -p  -t ";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditItemDescriptor ITEM_1;
    public static final EditCommand.EditItemDescriptor ITEM_2;

    static {
        ITEM_1 = new EditItemDescriptorBuilder().withDescription(VALID_DESCRIPTION_1)
                .withEvent(VALID_EVENT_1, VALID_DURATION_NULL, VALID_PRIORITY_HIGH)
                .withReminder(VALID_REMINDER_1)
                .withTags(VALID_TAG_1).build();
        ITEM_2 = new EditItemDescriptorBuilder().withDescription(VALID_DESCRIPTION_2)
                .withEvent(VALID_EVENT_2, VALID_DURATION_NULL, VALID_PRIORITY_LOW)
                .withReminder(VALID_REMINDER_2)
                .withTags(VALID_TAG_2).build();
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, ItemModel actualModel, CommandResult expectedCommandResult,
            ItemModel expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, ItemModel, CommandResult, ItemModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, ItemModel actualModel, String expectedMessage,
            ItemModel expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, ItemModel actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Item> expectedVisualList = new ArrayList<>(actualModel.getVisualList().getList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedVisualList, actualModel.getVisualList().getList());
    }

}
