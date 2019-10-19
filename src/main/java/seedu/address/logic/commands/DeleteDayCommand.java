package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.day.Day;

/**
 * Removes a Day from the itinerary.
 */
public class DeleteDayCommand extends DeleteCommand {
    public static final String SECOND_COMMAND_WORD = "day";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the day identified by the index number used in the displayed day list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DAY_SUCCESS = "Deleted day: %1$d";

    private final Index targetIndex;

    public DeleteDayCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownList = model.getFilteredDayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }

        Day dayToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteDay(dayToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_DAY_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDayCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDayCommand) other).targetIndex)); // state check
    }
}
