package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.commands.MatchCommand.match;

import organice.model.Model;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Set the status of a donor and patient to Done and remove the pair from the model
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finish the processing of patients and donors"
            + "Parameters: ic/PATIENT NRIC ic/DONOR NRIC \n"
            + "Example: " + COMMAND_WORD + " ic/s4512345A ic/s7711123C";

    public static final String MESSAGE_SUCCESS = "Done processing the patient and donor";
    public static final String MESSAGE_NOT_PROCESSED = "Donor or patient NRIC must be valid";

    private String firstNricString;
    private String secondNricString;

    private Nric firstNric;
    private Nric secondNric;

    private Donor donor;
    private Patient patient;

    private Nric patientNric;
    private Nric donorNric;

    private String statusDone = "done";

    /**
     * creates a DoneCommand to remove the given patient and donor pair
     * @param firstNricString
     * @param secondNricString
     */
    public DoneCommand(String firstNricString, String secondNricString) {
        requireNonNull(firstNricString, secondNricString);
        firstNric = new Nric(firstNricString);
        secondNric = new Nric(secondNricString);
    }

    /**
     * check if the two Nric given are valid.
     * It needs to contain one patient and one donor and both of them must be matched in order to be valid
     * @param firstNric the first Nric given
     * @param secondNric the second Nric given
     * @param model
     * @return a boolean true false stating whether the inputs are valid
     */
    public boolean isValidDonorPatientPair(Nric firstNric, Nric secondNric, Model model) {
        if (model.hasDonor(firstNric)) {
            donorNric = firstNric;
            donor = model.getDonor(donorNric);

            patientNric = secondNric;
            patient = model.getPatient(patientNric);
        } else {
            patientNric = firstNric;
            patient = model.getPatient(patientNric);

            donorNric = secondNric;
            donor = model.getDonor(donorNric);
        }
        if (model.hasPatient(patientNric) && model.hasDonor(donorNric)
                && match(donor, patient)) {
            //todo check if tasklist isempty
            return true;
        } else {
            return false;
        }

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (isValidDonorPatientPair(firstNric, secondNric, model)) {
                model.getFilteredPersonList();

                donor.setStatus(statusDone);
                patient.setStatus(statusDone);

                model.deletePerson(donor);
                model.deletePerson(patient);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException pne) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && (firstNricString.equals(((DoneCommand) other).firstNricString))
                || (secondNric.equals(((DoneCommand) other).secondNric))); // state check
    }
}
