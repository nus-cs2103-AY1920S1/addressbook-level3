package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import organice.commons.core.Messages;
import organice.model.Model;
import organice.model.person.Nric;
import organice.model.person.Person;

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

    public ProcessingCommand(String firstNRIC, String secondNRIC) {
        requireNonNull(firstNRIC, secondNRIC);
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

        Nric firstNRIC = new Nric(firstNRICString);
        Nric secondNRIC = new Nric(secondNRICString);

        try {
            if (isValidDonorPatientPair(firstNRIC, secondNRIC, model)) {


            }
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
