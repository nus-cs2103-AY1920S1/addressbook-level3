package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import organice.commons.core.Messages;
import organice.model.Model;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Process a pair of donor and patient to provide a checklist to prepare for the organ transplant.
 * Keyword matching is case insensitive.
 */
public class ProcessingCommand extends Command {

    public static final String COMMAND_WORD = "processing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Process the pair of donor and patient identified "
            + "by their NRICs respectively and provide a checklist of the current status of the organ transplantation. \n"
            + "Parameters: icP/PATIENT NRIC icD/DONOR NRIC \n"
            + "Example: " + COMMAND_WORD + " icP/s4512345A icD/s7711123C";

    public static final String MESSAGE_NOT_PROCESSED = "Donor or patient NRIC must be valid";

    private String firstNRICString;
    private String secondNRICString;
    private Donor donor;
    private Patient patient;

    public ProcessingCommand(String firstNRICString, String secondNRICString) {
        requireNonNull(firstNRICString, secondNRICString);
        this.firstNRICString = firstNRICString;
        this.secondNRICString = secondNRICString;
    }

    public boolean isValidDonorPatientPair(Nric firstNRIC, Nric secondNRIC, Model model) {

        if (model.hasPatient(firstNRIC) && model.hasDonor(secondNRIC)
            || model.hasPatient(secondNRIC) && model.hasDonor(firstNRIC)) {
            return true;
            //todo: check if the pair matches or not
        } else {
            return false;
        }

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Nric patientNRIC;
        Nric donorNRIC;

        if (model.hasDonor(new Nric(firstNRICString))) {
            donorNRIC = new Nric(firstNRICString);
            patientNRIC = new Nric(secondNRICString);
        } else {
            patientNRIC = new Nric(firstNRICString);
            donorNRIC = new Nric(secondNRICString);
        }

        try {
            if (isValidDonorPatientPair(patientNRIC, donorNRIC, model)) {
                donor = model.getDonor(donorNRIC);
                model.getFilteredPersonList();
                return new CommandResult(donor.getProcessingList(patientNRIC).toString());
            } else {
                return new CommandResult("here");
            }
        } catch (PersonNotFoundException pne) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessingCommand // instanceof handles nulls
                && (firstNRICString.equals(((ProcessingCommand) other).firstNRICString))
                || (secondNRICString.equals(((ProcessingCommand) other).secondNRICString))); // state check
    }
}
