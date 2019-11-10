package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DAYS;

import java.util.List;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.appointment.Appointment;
import unrealunity.visit.model.person.Person;

/**
 * Adds a follow-up appointment to VISIT.
 */
public class FollowUpCommand extends Command {

    /**
     * Word to call the Follow-Up Command.
     */
    public static final String COMMAND_WORD = "followup";

    /**
     * Help message on usage.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new follow-up against a patient for the user to keep track "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DAYS + "EXPIRY IN DAYS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAYS + "7";

    /**
     * Success message when executed.
     */
    public static final String MESSAGE_ADD_FOLLOWUP_SUCCESS = "Added follow-up to Person: %s - %d day(s)";

    /**
     * The index of the patient in the AddressBook.
     */
    private final Index index;

    /**
     * Optional number of days for when the follow-up occurs.
     */
    private final int days;

    /**
     * Creates a FollowUpCommand to add the specified {@code Appointment}.
     *
     * @param index of the person in the filtered person list to target.
     * @param days Number of days for when the follow-up occurs.
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
        model.addAppointment(Appointment.Type.FOLLOWUP, person.getName().toString(), days);

        return new CommandResult(String.format(MESSAGE_ADD_FOLLOWUP_SUCCESS, person.getName().toString(), days));
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
