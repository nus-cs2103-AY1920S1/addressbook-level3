package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_PERSON_NOT_FOUND;

import java.util.ArrayList;

import organice.logic.commands.exceptions.CommandException;
import organice.model.Model;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Type;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the person identified by the input NRIC.\n"
        + "Parameters: NRIC\n"
        + "Example: " + COMMAND_WORD + " S2121503A";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person: %1$s";
    public static final String MESSAGE_PATIENT_UNDER_PROCESSING = "The patient you want to delete is under processing. "
            + "and thus can't be deleted";
    public static final String MESSAGE_DONOR_UNDER_PROCESSING = "The donor you want to delete is under processing. "
            + "and thus can't be deleted";
    public static final String MESSAGE_DOCTOR_HAS_PATIENTS = "The doctor you want to delete has patients attached to"
            + " him/her and thus can't be deleted";

    private final Nric targetNric;

    public DeleteCommand(Nric targetNric) {
        requireNonNull(targetNric);
        this.targetNric = targetNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(targetNric)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetNric));
        }

        Person targetPerson = model.getPerson(targetNric);
        Type targetType = targetPerson.getType();

        if (targetType.isPatient() && ((Patient) targetPerson).getStatus().isProcessing()) {
            throw new CommandException(MESSAGE_PATIENT_UNDER_PROCESSING);
        } else if (targetType.isDonor() && ((Donor) targetPerson).getStatus().isProcessing()) {
            throw new CommandException(MESSAGE_DONOR_UNDER_PROCESSING);
        } else if (targetType.isDoctor() && model.hasDoctorInCharge(new DoctorInCharge(targetNric.toString()))) {
            DoctorInCharge doctorIc = new DoctorInCharge(targetNric.toString());
            ArrayList<Patient> listOfPatients = model.getPatientsWithDoctorIc(doctorIc);
            String errorMsg = "\nPatients that are attached to the doctor are: ";
            for (Patient patient : listOfPatients) {
                errorMsg += "[NAME: " + patient.getName() + " NRIC: " + patient.getNric() + "], ";
            }
            errorMsg = errorMsg.substring(0, errorMsg.length() - 2); //Remove extra space and comma
            throw new CommandException(MESSAGE_DOCTOR_HAS_PATIENTS + errorMsg);
        }

        Person personToDelete = model.getPerson(targetNric);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCommand // instanceof handles nulls
            && targetNric.equals(((DeleteCommand) other).targetNric)); // state check
    }
}
