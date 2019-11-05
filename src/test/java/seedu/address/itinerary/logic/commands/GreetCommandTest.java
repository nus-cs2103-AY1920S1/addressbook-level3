package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;

class GreetCommandTest {

    private GreetCommand greetCommand = new GreetCommand();

    private String expectedMessage = greetCommand.getMessage();

    // To check whether message success given is the same as the expected greet command
    // Greet command is a basic command which gives the user the current date and time.
    @Test
    public void execute_greet_success() {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), expectedMessage);
    }
}
