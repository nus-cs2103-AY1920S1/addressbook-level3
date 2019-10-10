package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Changes the visitList of an existing person in the address book.
 */
public class ProfileCommand extends Command {
    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the detailed profile of the patient identified"
            + "by the index number used in the last person listing. "
            + "The profile can be generated into a log file subsequently.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    // public static final String MESSAGE_VIEW_PROFILE_SUCCESS = "Generated profile view of : %1$s";
    public static final String MESSAGE_VIEW_PROFILE_SUCCESS = "Profile Command Recognized! Target: %1$s";

    private final Index index;

    /**
     * @param index of the person in the last listing whose profile is to be viewed
     */
    public ProfileCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_VIEW_PROFILE_SUCCESS, personToEdit), index.getOneBased());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCommand)) {
            return false;
        }

        // state check
        ProfileCommand e = (ProfileCommand) other;
        return index.equals(e.index);
    }
}
