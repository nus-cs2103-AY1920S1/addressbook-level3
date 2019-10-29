package cs.f10.t1.nursetraverse.logic.commands.visit;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PATIENT_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.List;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.VisitTaskUtil;
import cs.f10.t1.nursetraverse.logic.commands.Command;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Remark;
import cs.f10.t1.nursetraverse.model.visit.Visit;

/**
 * Begins a visit using a patient identified using his/her index in the patient list.
 */
public class BeginVisitCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "visit-start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Begins a visit for a patient. The patient is identified by the index number used in the patient "
            + "list displayed using the list or find patient commands.\n"
            + "Parameters: "
            + PREFIX_PATIENT_INDEX + "PATIENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + PREFIX_PATIENT_INDEX + "1 ";

    public static final String MESSAGE_START_VISIT_SUCCESS = "Started a new visit under patient: %1$s";
    public static final String MESSAGE_START_VISIT_FAILURE = "There is already an ongoing visit.";

    private final Index patientIndex;

    public BeginVisitCommand(Index patientIndex) {
        requireNonNull(patientIndex);
        this.patientIndex = patientIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getOngoingVisit().isPresent()) {
            throw new CommandException(MESSAGE_START_VISIT_FAILURE);
        }
        List<Patient> fullPatientList = model.getStagedPatientList();

        //Verify Patient Index
        if (patientIndex.getZeroBased() >= fullPatientList.size()) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = fullPatientList.get(patientIndex.getZeroBased());

        //Begin Visit
        Date now = new Date();
        Visit visit = new Visit(
                new Remark(""),
                new StartDateTime(now),
                EndDateTime.UNFINISHED_VISIT_END_DATE_TIME,
                VisitTaskUtil.listFromPatient(patient), patient);

        patient.addVisit(visit);

        model.setNewOngoingVisit(visit);

        return new CommandResult(String.format(MESSAGE_START_VISIT_SUCCESS, patient));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BeginVisitCommand // instanceof handles nulls
                && patientIndex.equals(((BeginVisitCommand) other).patientIndex)); // state check
    }
}
