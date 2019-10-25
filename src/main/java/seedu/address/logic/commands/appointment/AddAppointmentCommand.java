package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_VISIT_TODO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_MINUTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_MONTHS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_WEEKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_YEARS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to the appointment list
 */
public class AddAppointmentCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "add-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment to the appointment list with a patient identified by the index number used in the"
            + " displayed patient list.\n"
            + "Parameters: "
            + PREFIX_PATIENT_INDEX + "PATIENT_INDEX "
            + PREFIX_APPOINTMENT_START_DATE_AND_TIME + "APPOINTMENT_START_DATE_AND_TIME "
            + PREFIX_APPOINTMENT_END_DATE_AND_TIME + "APPOINTMENT_END_DATE_AND_TIME "
            + "[" + PREFIX_RECUR_YEARS + "RECUR_YEARS]...\n"
            + "[" + PREFIX_RECUR_MONTHS + "RECUR_MONTHS]...\n"
            + "[" + PREFIX_RECUR_WEEKS + "RECUR_WEEKS]...\n"
            + "[" + PREFIX_RECUR_DAYS + "RECUR_DAYS]...\n"
            + "[" + PREFIX_RECUR_HOURS + "RECUR_HOURS]...\n"
            + "[" + PREFIX_RECUR_MINUTES + "RECUR_MINUTES]...\n"
            + "[" + PREFIX_APPOINTMENT_DESCRIPTION + "APPOINTMENT_DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_INDEX + "1 "
            + PREFIX_APPOINTMENT_START_DATE_AND_TIME + "01-11-2019 1200 "
            + PREFIX_APPOINTMENT_END_DATE_AND_TIME + "01-11-2019 0200 "
            + PREFIX_RECUR_YEARS + "2 "
            + PREFIX_RECUR_MONTHS + "1 "
            + PREFIX_APPOINTMENT_DESCRIPTION + "Dental hygiene checkup ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";

    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }
}
