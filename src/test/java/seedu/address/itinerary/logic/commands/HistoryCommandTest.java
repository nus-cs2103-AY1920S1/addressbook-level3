package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.itinerary.logic.commands.HistoryCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

class HistoryCommandTest {

    @Test
    public void execute_history_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), MESSAGE_SUCCESS);
    }

    @Test
    public void empty_history_list() {
        // Message given when there is an empty history list.
        HistoryCommand historyCommand = new HistoryCommand();
        Model model = new Model();

        Model expectedModel = new Model();
        String expectedMessage = HistoryCommand.MESSAGE_NO_REDO;

        assertCommandSuccess(historyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void with_action_historyList() {
        // Checking for similarity with action inserted into the action list
        HistoryCommand historyCommand = new HistoryCommand();
        Model model = new Model();
        model.addAction("done 1");

        Model expectedModel = new Model();
        String expectedMessage = historyCommand.execute(expectedModel).getFeedbackToUser();

        // Different action list check
        assertCommandFailure(historyCommand, model, new CommandResult(expectedMessage), expectedModel);

        // Adding the additional action into thee expected Model
        expectedModel.addAction("done 1");

        // Resetting the expected message since list is not empty.
        expectedMessage = historyCommand.execute(expectedModel).getFeedbackToUser();

        assertCommandSuccess(historyCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualItineraryModel} matches {@code expectedItineraryModel}
     */
    public static void assertCommandFailure(Command command, Model model,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(model);
            assertNotEquals(expectedCommandResult.getFeedbackToUser(), result.getFeedbackToUser());
            assertNotEquals(expectedModel.getSortedEventList().toArray(), model.getSortedEventList().toArray());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualItineraryModel} matches {@code expectedItineraryModel}
     */
    public static void assertCommandSuccess(Command command, Model model,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(model);
            assertEquals(expectedCommandResult.getFeedbackToUser(), result.getFeedbackToUser());
            assertArrayEquals(expectedModel.getSortedEventList().toArray(), model.getSortedEventList().toArray());
            assertArrayEquals(expectedModel.getActionList().toArray(), model.getActionList().toArray());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    public static void assertCommandSuccess(Command command, Model model,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

}
