package seedu.address.inventory.commands;

import seedu.address.inventory.logic.commands.Command;
import seedu.address.inventory.logic.commands.CommandResult;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.util.InventoryList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model model,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(model);
            assertEquals(expectedCommandResult.getFeedbackToUser(), result.getFeedbackToUser());
            assertEquals(expectedModel, model);
        } catch (Exception ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model model,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the transaction list, filtered transaction list in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        InventoryList inventoryList = actualModel.getInventoryList();

        assertThrows(Exception.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(inventoryList, actualModel.getInventoryList());
    }
}
