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
            + "valid and task number must exist";

    private String firstNricString;
    private String secondNricString;
    private String taskNumberString;

    private Nric firstNric;
    private Nric secondNric;
    private int taskNumber;

    private Donor donor;
    private Patient patient;

    private Nric patientNric;
    private Nric donorNric;

    private TaskList processingList;

    public ProcessingMarkDoneCommand(String firstNricString, String secondNricString, String taskNumberString) {
        requireNonNull(firstNricString, secondNricString);
        firstNric = new Nric(firstNricString);
        secondNric = new Nric(secondNricString);
        taskNumber = Integer.parseInt(taskNumberString);
    }

    /**
     * To check if the Nrics given contains a patient and a donor from the database
     * @param firstNric
     * @param secondNric
     * @param model
     * @return boolean, to see whether the given Nrics are valid
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
                && match(donor,patient))  {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (isValidDonorPatientPair(firstNric, secondNric, model)
                && taskNumber < donor.getProcessingList(patientNric).size()) {
                    model.getFilteredPersonList();
                    donor.markTaskAsDone(taskNumber);
            }
            return new CommandResult(donor.getProcessingList(patientNric).display());
        } catch (PersonNotFoundException pne) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }

    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessingMarkDoneCommand // instanceof handles nulls
                && (firstNricString.equals(((ProcessingMarkDoneCommand) other).firstNricString))
                || (secondNric.equals(((ProcessingMarkDoneCommand) other).secondNric))); // state check
    }
}
