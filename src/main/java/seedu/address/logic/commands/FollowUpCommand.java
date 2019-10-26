package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a follow-up to VISIT.
 */
public class FollowUpCommand extends Command {

    public static final String COMMAND_WORD = "followup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new follow-up against a patient for the user to keep track "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAYS + "7";

    public static final String MESSAGE_ADD_FOLLOWUP_SUCCESS = "Added followup to Person: %s - %d day(s)";

    private final Index index;
    private final int days;

    /**
     * @param index of the person in the filtered person list to target
     * @param days before appointment
     */
    public FollowUpCommand(Index index, int days) {
        requireNonNull(index);

        this.index = index;
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(index.getZeroBased());
        model.addReminder(1, person.getName().toString(), days);

        return new CommandResult(String.format(MESSAGE_ADD_FOLLOWUP_SUCCESS, person.getName().toString()), days);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FollowUpCommand)) {
            return false;
        }

        // state check
        FollowUpCommand e = (FollowUpCommand) other;
        return index.equals(e.index)
                && days == e.days;
    }
}
