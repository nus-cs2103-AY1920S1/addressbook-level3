package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.commands.MatchCommand.match;

import organice.model.Model;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.TaskList;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Process a pair of donor and patient to provide a checklist to prepare for the organ transplant.
 * Keyword matching is case insensitive.
 */
public class ProcessingCommand extends Command {

    public static final String COMMAND_WORD = "processing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Process the pair of donor and patient identified "
            + "by their NRICs respectively and provide a checklist of"
            + "the current status of the organ transplantation. \n"
            + "Parameters: ic/PATIENT NRIC ic/DONOR NRIC \n"
            + "Example: " + COMMAND_WORD + " ic/s4512345A ic/s7711123C";

    public static final String MESSAGE_NOT_PROCESSED = "Donor or patient NRIC must be valid";

    protected TaskList taskList;

    private String firstNricString;
    private String secondNricString;

    private Nric firstNric;
    private Nric secondNric;

    private Donor donor;
    private Patient patient;

    private Nric patientNric;
    private Nric donorNric;

    public ProcessingCommand(String firstNricString, String secondNricString) {
        requireNonNull(firstNricString, secondNricString);
        firstNric = new Nric(firstNricString);
        secondNric = new Nric(secondNricString);
    }

    /**
     * To check if the Nrics given contains a patient and a donor from the database
     * @param firstNric
     * @param secondNric
     * @param model
     * @return boolean, to see whether the given Nrics are valid
     */
    public boolean isValidDonorPatientPair(Nric firstNric, Nric secondNric, Model model) {
        boolean canBePaired;

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

        if (donor.getProcessingList(patientNric) == null) {
            canBePaired = true;
        } else {
            if (donor.getProcessingList(patientNric).getPatient() == null
            || donor.getProcessingList(patientNric).getPatient().isSamePerson(patient)) {
                canBePaired = true;
            } else {
                canBePaired = false;
            }
        }



        if (model.hasPatient(patientNric) && model.hasDonor(donorNric)
                && match(donor, patient) && canBePaired) {
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

                if (donor.getStatus().isNotProcessing()) {
                    donor.setProcessingList(donor.getProcessingList(patientNric).defaultList().toString());
                }
                taskList = donor.getProcessingList(patientNric);

                taskList.setPatient(patient);

                donor.setStatus("processing");
                patient.setStatus("processing");
            }
            return new CommandResult(taskList.display());
        } catch (PersonNotFoundException | NullPointerException npe) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessingCommand // instanceof handles nulls
                && (firstNricString.equals(((ProcessingCommand) other).firstNricString))
                || (secondNric.equals(((ProcessingCommand) other).secondNric))); // state check
    }
}
