package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EATERIES;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Tag;

/**
 * Sets the isOpen field of an existing eatery in the address book to false.
 */
public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Closes the eatery identified by the index number used in the displayed eatery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DUPLICATE_EATERY = "This eatery is already closed in the address book.";
    public static final String MESSAGE_CLOSED_EATERY_SUCCESS = "Closed Eatery: %1$s";

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
        Eatery closedEatery = createClosedEatery(eateryToClose);

        if (eateryToClose.equals(closedEatery) && model.hasExactEatery(eateryToClose)) {
            throw new CommandException(MESSAGE_DUPLICATE_EATERY);
        }

        model.setEatery(eateryToClose, closedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);

        return new CommandResult(String.format(MESSAGE_CLOSED_EATERY_SUCCESS, closedEatery));
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

        return new Eatery(name, false, address, category, tags);
    }
}
