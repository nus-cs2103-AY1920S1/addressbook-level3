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
 * Sets the isOpen field of an existing eatery in the address book to true.
 */
public class ReopenCommand extends Command {

    public static final String COMMAND_WORD = "reopen";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reopens the eatery identified by the index number used in the displayed eatery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EATERY_ALREADY_OPENED = "This eatery is already open in the address book.";
    public static final String MESSAGE_REOPENED_EATERY_SUCCESS = "Reopened Eatery: %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the eatery in the filtered eatery list to close
     */
    public ReopenCommand(Index targetIndex) {
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

        Eatery eateryToReopen = lastShownList.get(targetIndex.getZeroBased());
        if (eateryToReopen.getIsOpen()) {
            throw new CommandException(MESSAGE_EATERY_ALREADY_OPENED);
        }
        Eatery reopenedEatery = createReopenedEatery(eateryToReopen);

        model.setEatery(eateryToReopen, reopenedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);

        return new CommandResult(String.format(MESSAGE_REOPENED_EATERY_SUCCESS, reopenedEatery));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReopenCommand // instanceof handles nulls
                && targetIndex.equals(((ReopenCommand) other).targetIndex)); // state check
    }

    /**
     * Creates and returns a {@code Eatery} with the details of {@code eateryToEdit}
     * edited with {@code editEateryDescriptor}.
     */
    private static Eatery createReopenedEatery(Eatery eateryToReopen) {
        assert eateryToReopen != null;

        Name name = eateryToReopen.getName();
        Address address = eateryToReopen.getAddress();
        Category category = eateryToReopen.getCategory();
        Set<Tag> tags = eateryToReopen.getTags();
        Eatery reopenedEatery = new Eatery(name, address, category, tags);
        reopenedEatery.setReviews(eateryToReopen.getReviews());

        return reopenedEatery;
    }
}
