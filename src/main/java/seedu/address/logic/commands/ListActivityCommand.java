package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;

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
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
