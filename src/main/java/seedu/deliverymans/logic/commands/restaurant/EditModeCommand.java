package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.Context;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Goes into editing mode for a restaurant
 */
public class EditModeCommand extends Command {
    public static final String COMMAND_WORD = "editmode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the restaurant identified "
            + "by the index number used in the displayed restaurant list.\n"
            + "Goes into editing mode for the identified restaurant.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";


    public static final String MESSAGE_SUCCESS = "Entered editing mode for: %1$s";

    private final Index targetIndex;

    /**
     * Creates an AddCommand to add the specified {@code Restaurant}
     */
    public EditModeCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToEdit = lastShownList.get(targetIndex.getZeroBased());
        model.setEditingRestaurant(restaurantToEdit);

        return new CommandResult(String.format(MESSAGE_SUCCESS, restaurantToEdit), Context.EDITING);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditModeCommand // instanceof handles nulls
                && targetIndex.equals(((EditModeCommand) other).targetIndex));
    }
}
