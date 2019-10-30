package seedu.address.itinerary.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * clear event command for itinerary.
 */
public class ClearEventCommand extends Command<Model> {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Your events has been wiped off from the face of this Earth. Yay! =U\n"
            + "Now you are ready to travel to outer space! :3";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all event entries in the itinerary list\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.clearEvent();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
