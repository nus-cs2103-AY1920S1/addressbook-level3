package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import seedu.address.model.Model;

/**
 * Lists all days in the itinerary.
 */
public class ListDayCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "day";

    public static final String MESSAGE_SUCCESS = "Listed all days";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
