package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.MatchCommand.match;

import java.util.ArrayList;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finish the processing of patients and donors \n"
            + "Parameters: ic/PATIENT NRIC ic/DONOR NRIC res/[pass/fail] \n"
            + "Example: " + COMMAND_WORD + " ic/s4512345A ic/s7711123C res/pass";

    public static final String MESSAGE_SUCCESS = "Done processing the patient and donor";
    public static final String MESSAGE_NOT_PROCESSED = "Donor or patient NRIC must be valid,"
            + " result of cross-matching can only be pass or fail.";

    private Nric firstNric;
    private Nric secondNric;

    private Donor donor;
    private Patient patient;

    private Nric patientNric;
    private Nric donorNric;

    private String statusDone = "done";
    private String statusNotProcessing = "not processing";
    private String result;

    /**
     * Creates a DoneCommand to determine if the given patient and donor can be deleted from the system.
     * The method will first convert the Nrics given in String to an actual Nric type.
     * If the cross-matching fails, the patient and donor will need to be in the system for further matching.
     * If the cross-matching pass, the patient and donor can be deleted from ORGANice.
     * @param firstNricString the first Nric given by the user, in String.
     * @param secondNricString the second Nric given by the user, in String.
     */
    public DoneCommand(String firstNricString, String secondNricString, String result) {
        requireNonNull(firstNricString, secondNricString);
        firstNric = new Nric(firstNricString);
        secondNric = new Nric(secondNricString);
        this.result = result;
    }

    /**
     * Method to check if the two Nrics given are valid.
     * It needs to contain one patient and one donor.
     * Both of them must be matched and is processing in order to be valid.
     * The method will create the donor and patient with the respective Nrics in ORAGANice
     * from the parameters.
     * @param firstNric the first Nric given by the user.
     * @param secondNric the second Nric given by the user.
     * @param model
     * @return a boolean true false stating whether the inputs are valid.
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
            return donor.getStatus().isProcessing() && patient.getStatus().isProcessing();
        } else {
            return false;
        }
    }

    /**
     * A boolean to determine the value of the String, result
     * @param result a String from the user input
     * @return true if the result shows a pass, false if the result shows a fail.
     */
    public boolean isPass(String result) throws IllegalArgumentException {
        if (result.equalsIgnoreCase("pass")) {
            return true;
        } else if (result.equalsIgnoreCase("fail")) {
            return false;
        } else {
            throw new IllegalArgumentException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Set the status of the patient and donor to done if the result of the cross-matching is pass.
     * Set the status of the patient and donor to not processing if the result of the cross-matching
     * fail. Then it will be taken into consideration when MatchCommand is called.
     * Currently, if the status of patient and donor is done, we will remove it from ORGANice.
     * However, in future development, we hope to archive it for data analysis.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult object.
     */

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (isValidDonorPatientPair(firstNric, secondNric, model) && isPass(result)) {

                model.getFilteredPersonList();

                donor.setStatus(statusDone);
                patient.setStatus(statusDone);

                model.deletePerson(donor);
                model.deletePerson(patient);
            } else if (isValidDonorPatientPair(firstNric, secondNric, model) && !isPass(result)) {
                model.getFilteredPersonList();

                ArrayList<Nric> patientsMatchedBefore = donor.getPatientMatchedBefore();
                patientsMatchedBefore.add(patientNric);
                donor.setPatientsMatchedBefore(patientsMatchedBefore.toString());

                donor.setStatus(statusNotProcessing);
                patient.setStatus(statusNotProcessing);

                donor.setProcessingList("");

            } else {
                return new CommandResult(MESSAGE_NOT_PROCESSED);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException | IllegalArgumentException e) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && (firstNric.equals(((DoneCommand) other).firstNric))
                || (secondNric.equals(((DoneCommand) other).secondNric))); // state check
    }
}
