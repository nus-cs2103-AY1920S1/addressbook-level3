package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.MatchCommand.match;

import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Mark a task on the checklist for the donor and patient to prepare for the organ transplant.
 * Keyword matching is case insensitive.
 */
public class ProcessingMarkDoneCommand extends Command {

    public static final String COMMAND_WORD = "processingMarkDone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Given the pair of donor and patient identified "
            + "by their NRICs respectively, this command would mark the task identified as done in their checklist. \n"
            + "Parameters: ic/PATIENT NRIC ic/DONOR NRIC TASK_NUMBER \n"
            + "Example: " + COMMAND_WORD + " ic/s4512345A ic/s7711123C 1";

    public static final String MESSAGE_NOT_PROCESSED = "Donor and Patient NRIC must be "
            + "valid and a valid task number must exist";


    private Nric firstNric;
    private Nric secondNric;
    private int taskNumber;

    private Donor donor;
    private Patient patient;

    private Nric patientNric;
    private Nric donorNric;

    public ProcessingMarkDoneCommand(
            String firstNricString, String secondNricString, String taskNumberString) throws ParseException {
        requireNonNull(firstNricString, secondNricString);
        try {
            firstNric = new Nric(firstNricString);
            secondNric = new Nric(secondNricString);
            taskNumber = Integer.parseInt(taskNumberString);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessingMarkDoneCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Method to check if the two Nrics given are valid.
     * It needs to contain one patient and one donor.
     * Both of them must be matched and their status must be processing already.
     * The method will first convert the Nrics given in String to an actual Nric type,
     * then it will create the donor and patient with the respective Nric in ORAGANice.
     * @param firstNric the first Nric given by the user in String.
     * @param secondNric the second Nric given by the user in String.
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
                && patient.getStatus().isProcessing() && donor.getStatus().isProcessing()
                && match(donor, patient)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mark a particular task on the donor's Tasklist as done.
     * The tasklist will show a tick beside the task number and description.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult object.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (isValidDonorPatientPair(firstNric, secondNric, model)
                && taskNumber < donor.getProcessingList(patientNric).size() + 1) {
                model.getFilteredPersonList();
                donor.markTaskAsDone(taskNumber);
                return new CommandResult(donor.getProcessingList(patientNric).display());
            } else {
                return new CommandResult(MESSAGE_NOT_PROCESSED);
            }
        } catch (PersonNotFoundException | NullPointerException | IllegalArgumentException e) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessingMarkDoneCommand // instanceof handles nulls
                && (firstNric.equals(((ProcessingMarkDoneCommand) other).firstNric))
                || (secondNric.equals(((ProcessingMarkDoneCommand) other).secondNric))); // state check
    }
}
