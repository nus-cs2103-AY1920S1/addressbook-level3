package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.task.NameContainsKeywordsPredicate;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /* Task related names will be split into four categories: Finance and Publicity */
    public static final String VALID_TASK_NAME_FINANCE = "Review Project Budget";
    public static final String VALID_TASK_NAME_PUBLICITY = "Build Project Website";
    public static final String VALID_TASK_STATUS_FINANCE = "unbegun";
    public static final String VALID_TASK_STATUS_PUBLICITY = "doing";
    public static final String VALID_TAG_FINANCE = "finance";
    public static final String VALID_TAG_PUBLICITY = "publicity";
    public static final String VALID_TAG_URGENCY = "urgent";

    public static final String TASK_NAME_DESC_FINANCE = " " + PREFIX_TASK_NAME + VALID_TASK_NAME_FINANCE;
    public static final String TASK_NAME_DESC_PUBLICITY = " " + PREFIX_TASK_NAME + VALID_TASK_NAME_PUBLICITY;
    public static final String TASK_STATUS_DESC_FINANCE = " " + PREFIX_TASK_STATUS + VALID_TASK_STATUS_FINANCE;
    public static final String TASK_STATUS_DESC_PUBLICITY = " " + PREFIX_TASK_STATUS + VALID_TASK_STATUS_PUBLICITY;
    public static final String TAG_DESC_PUBLICITY = " " + PREFIX_TASK_TAG + VALID_TAG_PUBLICITY;
    public static final String TAG_DESC_URGENCY = " " + PREFIX_TASK_TAG + VALID_TAG_URGENCY;
    public static final String TAG_DESC_FINANCE = " " + PREFIX_TASK_TAG + VALID_TAG_FINANCE;

    public static final String INVALID_TASK_NAME_DESC = " " + PREFIX_TASK_NAME + "Project Review&"; // '&' not allowed in names
    public static final String INVALID_TASK_STATUS_DESC = " " + PREFIX_TASK_STATUS + "reviewing"; // statuses are determined in {@code TaskStatus}
    public static final String INVALID_TAG_DESC = " " + PREFIX_TASK_TAG + "paused*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTaskDescriptor TASK_DESC_FINANCE;
    public static final EditCommand.EditTaskDescriptor TASK_DESC_PUBLICITY;

    static {
        TASK_DESC_FINANCE = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE)
                .withStatus(TaskStatus.UNBEGUN)
                .withTags(VALID_TAG_FINANCE).build();
        TASK_DESC_PUBLICITY = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_PUBLICITY)
                .withStatus(TaskStatus.DOING)
                .withTags(VALID_TAG_PUBLICITY).build();
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
     * - the address book, filtered task list and selected task in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTasksList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredTasksList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTasksList().size());

        Task task = model.getFilteredTasksList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTasksList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTasksList().size());
    }

}
