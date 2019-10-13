package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Begins a visit using an appointment identified using it's index in a patient's appointment list,
 * and said patient identified using his/her index in the patient list.
 */
public class BeginVisitCommand extends Command {

    public static final String COMMAND_WORD = "visit-start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Begins the appointment of a patient. The appointment is identified by the index number used "
            + "in the list of appointments, while the patient is identified by the index number used in the "
            + "displayed patient list.\n"
            + "Parameters: "
            + PREFIX_PATIENT_INDEX + "PATIENT_INDEX (must be a positive integer)\n"
            + PREFIX_APPOINTMENT_INDEX + "APPOINTMENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + PREFIX_PATIENT_INDEX + "1 "
            + PREFIX_APPOINTMENT_INDEX + "2";

    public static final String MESSAGE_START_APPOINTMENT_SUCCESS = "Started appointment "
            + "%1$s under person: %1$s";

    private final Index patientIndex;
    private final Index appointmentIndex;

    public BeginVisitCommand(Index patientIndex, Index appointmentIndex) {
        this.patientIndex = patientIndex;
        this.appointmentIndex = appointmentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> fullPatientList = model.getPersonList();

        //Verify Patient Index
        if (patientIndex.getZeroBased() >= fullPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        //Verify Appointment Index
        //TODO

        //Begin Appointment


        return new CommandResult(String.format(MESSAGE_START_APPOINTMENT_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BeginVisitCommand // instanceof handles nulls
                && patientIndex.equals(((BeginVisitCommand) other).patientIndex)); // state check
    }
}
