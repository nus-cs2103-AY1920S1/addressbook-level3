package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.model.Model.PREDICATE_SHOW_ALL_EATERIES;

import java.util.List;
import java.util.Set;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;

/**
 * Sets the isOpen field of an existing eatery in the eatery list to false.
 */
public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Closes the eatery identified by the index number used in the displayed eatery list.\n"
            + "Parameters: [index] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EATERY_ALREADY_CLOSED = "This eatery is already closed!";
    public static final String MESSAGE_CLOSED_EATERY_SUCCESS = "Eatery successfully closed: %s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the eatery in the filtered eatery list to close
     */
    public CloseCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Eatery> lastShownList = model.getFilteredEateryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToClose = lastShownList.get(targetIndex.getZeroBased());
        if (!eateryToClose.getIsOpen()) {
            throw new CommandException(MESSAGE_EATERY_ALREADY_CLOSED);
        }
        Eatery closedEatery = createClosedEatery(eateryToClose);

        model.setEatery(eateryToClose, closedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);

        return new CommandResult(String.format(MESSAGE_CLOSED_EATERY_SUCCESS, closedEatery.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CloseCommand // instanceof handles nulls
                && targetIndex.equals(((CloseCommand) other).targetIndex)); // state check
    }

    /**
     * Creates and returns a {@code Eatery} with the details of {@code eateryToEdit}
     * edited with {@code editEateryDescriptor}.
     */
    private static Eatery createClosedEatery(Eatery eateryToClose) {
        assert eateryToClose != null;

        Name name = eateryToClose.getName();
        Address address = eateryToClose.getAddress();
        Category category = eateryToClose.getCategory();
        Set<Tag> tags = eateryToClose.getTags();
        Eatery closedEatery = new Eatery(name, false, address, category, tags);
        closedEatery.setReviews(eateryToClose.getReviews());

        return closedEatery;
    }
}
