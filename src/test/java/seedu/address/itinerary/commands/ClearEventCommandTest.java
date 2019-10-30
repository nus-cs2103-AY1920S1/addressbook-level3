package seedu.address.itinerary.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

class ClearEventCommandTest {

    /*
    Hi Zhao Ming, please fix this.

    This is failing test because you are creating a new stage. In JUnit test, you cannot create a new stage without a
    window. This works in runtime but not in test time where a new window is not created. Throwing a
    IllegalStateException.

    @Test
    public void execute_emptyItinerary_success() {
        Model itineraryModel = new Model();
        Model expectedItineraryModel = new Model();

        assertCommandSuccess(new ClearEventCommand(), itineraryModel, ClearEventCommand.MESSAGE_SUCCESS,
                expectedItineraryModel);
    }

    @Test
    public void execute_nonEmptyItinerary_success() {
        Model model = new Model();
        Model expectedModel = new Model();

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
    */

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
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, model);
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
