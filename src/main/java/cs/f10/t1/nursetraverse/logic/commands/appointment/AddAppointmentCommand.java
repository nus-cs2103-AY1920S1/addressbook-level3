package cs.f10.t1.nursetraverse.logic.commands.appointment;

import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PATIENT_INDEX;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_DAYS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_HOURS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_MINUTES;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_MONTHS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_WEEKS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_YEARS;
import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;

/**
 * Adds an appointment to the appointment list
 */
public class AddAppointmentCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "appt-add";

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
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists "
            + "in the appointment book";
    public static final String MESSAGE_CLASHING_APPOINTMENT = "This appointment clashes with another appointment "
            + "in the appointment book";

    private Appointment toAdd;

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

        toAdd.setPatient(model.getPatientByIndex(toAdd.getPatientIndex()));

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
        if (model.hasClashingAppointment(toAdd)) {
            throw new CommandException(MESSAGE_CLASHING_APPOINTMENT);
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
