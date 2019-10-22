package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import organice.commons.core.Messages;
import organice.model.Model;

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

    private String firstNRIC;
    private String secondNRIC;

    public ProcessingCommand(String firstNRIC, String secondNRIC) {
        requireNonNull(firstNRIC, secondNRIC);
        this.firstNRIC = firstNRIC;
        this.secondNRIC = secondNRIC;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessingCommand // instanceof handles nulls
                && (firstNRIC.equals(((ProcessingCommand) other).firstNRIC))
                || (secondNRIC.equals(((ProcessingCommand) other).secondNRIC))); // state check
    }
}
