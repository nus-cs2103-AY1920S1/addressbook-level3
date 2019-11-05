package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.itinerary.logic.commands.SearchCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.address.logic.commands.ClearCommand;
import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

class SearchCommandTest {

    @Test
    public void execute_search_command() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), MESSAGE_SUCCESS);
    }

    @Test
    public void null_descriptor_search() {
        assertThrows(NullPointerException.class, () -> new SearchCommand(null));
    }

    @Test
    public void search_command_success() {
        SearchCommand.SearchEventDescriptor eventDescriptor = new SearchCommand.SearchEventDescriptor();
        SearchCommand searchCommand = new SearchCommand(eventDescriptor);
        Model model = new Model();

        Model expectedModel = new Model();
        String expectedMessage = MESSAGE_SUCCESS;

        // Ensure that both model executes successfully and give the expected message
        assertCommandSuccess(searchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        SearchCommand.SearchEventDescriptor eventDescriptor = new SearchCommand.SearchEventDescriptor();
        final SearchCommand standardCommand = new SearchCommand(eventDescriptor);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        assertFalse(standardCommand.equals(new SearchCommand(eventDescriptor)));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SearchCommand(eventDescriptor)));

        // different object values
        SearchCommand.SearchEventDescriptor copyDescriptor = new SearchCommand.SearchEventDescriptor(eventDescriptor);
        SearchCommand commandWithSameValues = new SearchCommand(copyDescriptor);
        assertFalse(standardCommand.equals(commandWithSameValues));
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
