package seedu.address.logic.commands.itinerary.days;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.itinerary.day.Day;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeleteDayCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a day from trip list.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_DAY_FAILURE = "Failed to delete your day, "
            + "the index you specified is likely out of bounds!";
    public static final String MESSAGE_DELETE_DAY_SUCCESS = "Deleted your day : %1$s!";

    private final Index indexToDelete;

    public DeleteDayCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes enter trip has been called first
        List<Day> lastShownList = model.getPageStatus().getTrip().getDayList().internalList;

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        // References preserved by pagestatus
        Day dayToDelete = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.getPageStatus().getTrip().getDayList().remove(indexToDelete);
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_DELETE_DAY_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_DAY_SUCCESS, dayToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteDayCommand;
    }

}
