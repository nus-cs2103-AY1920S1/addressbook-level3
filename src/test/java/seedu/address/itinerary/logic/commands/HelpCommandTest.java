package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.itinerary.logic.commands.HelpCommand.MESSAGE_SUCCESS;
import static seedu.address.itinerary.logic.commands.HelpCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;

class HelpCommandTest {

    @Test
    public void execute_help_usage() {
        // Shows how the help command is being used and how it should be called.
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_USAGE, false, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), MESSAGE_USAGE);
    }

    @Test
    public void execute_help_success() {
        // Success message shown upon calling the command and showing the help window.
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), HelpCommand.MESSAGE_SUCCESS);
    }

}
