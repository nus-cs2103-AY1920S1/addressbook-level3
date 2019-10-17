package seedu.address.itinerary.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n" +
            "Your event has been deleted successfully. Yay! :^)";
    public static final String MESSAGE_USAGE = "";
    private final Index index;

    public DeleteEventCommand(Index index) {
        this.index = index;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.deleteEvent(index.getOneBased());
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
