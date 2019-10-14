package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all activities in the itinerary.
 */
public class ListActivityCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final String MESSAGE_SUCCESS = "Listed all activities";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //to be implemented
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
