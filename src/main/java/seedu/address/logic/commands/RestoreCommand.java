package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Restores an item from the bin identified using it's displayed index to the address book.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restores an item inside the bin.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESTORE_SUCCESS = "Restored item: %1$s";

    private final Index targetIndex;

    public RestoreCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BinItem> lastShownList = model.getFilteredBinItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BIN_ITEM_DISPLAYED_INDEX);
        }

        BinItem itemToRestore = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBinItem(itemToRestore);
        if (itemToRestore.getItem() instanceof Person) {
            Person p = (Person) itemToRestore.getItem();
            // Building person without tags and policies in case they don't exist anymore
            // Undo redo takes care of accidental deletions
            model.addPerson(new PersonBuilder(p).withTags().withPolicies().build());
        } else {
            Policy p = (Policy) itemToRestore.getItem();
            // Same as above
            model.addPolicy(new PolicyBuilder(p).withTags().withCriteria().build());
        }

        // to maintain the model's state for undo/redo
        model.saveAddressBookState();
        return new CommandResult(String.format(MESSAGE_RESTORE_SUCCESS, itemToRestore));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RestoreCommand // instanceof handles nulls
            && targetIndex.equals(((RestoreCommand) other).targetIndex)); // state check
    }

}
