package seedu.address.itinerary.commands;

//TODO import java time to know the current time and date

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class GreetCommand extends Command {
    public static final String COMMAND_WORD = "greet";

    public static final String MESSAGE_SUCCESS = "Hello! Welcome to the itinerary page!\nThe time now is "
            + "\nHere are the events that you have for the day (" + ")";

    public CommandResult execute(Model model) throws CommandException {
        //TODO return back a list with the events for the current day
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
