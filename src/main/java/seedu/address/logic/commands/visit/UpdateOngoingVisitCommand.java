package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_FINISH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_INDEX_AND_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_UNFINISH;

import java.util.List;

import seedu.address.commons.util.VisitUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.visit.EndDateTime;
import seedu.address.model.visit.Remark;
import seedu.address.model.visit.StartDateTime;
import seedu.address.model.visit.Visit;
import seedu.address.model.visittask.VisitTask;

/**
 * Updates the details of the ongoing visit.
 */
public class UpdateOngoingVisitCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "visit-now-update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the current visit."
            + "Existing values will be overwritten by the input values.\n"
            + "New Visit Tasks cannot be added or removed from an ongoing visit.\n"
            + "Visit Tasks can only be updated but their descriptions cannot be modified.\n"
            + "Parameters: "
            + "[" + PREFIX_VISIT_TASK_INDEX_AND_DETAIL + "TASK_INDEX DETAIL]... "
            + "[" + PREFIX_VISIT_TASK_FINISH + "TASK_INDEX]... "
            + "[" + PREFIX_VISIT_TASK_UNFINISH + "TASK_INDEX]... "
            + "[" + PREFIX_VISIT_REMARKS + "REMARKS] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VISIT_TASK_UNFINISH + "1 "
            + PREFIX_VISIT_TASK_INDEX_AND_DETAIL + "1 "
            + PREFIX_VISIT_TASK_INDEX_AND_DETAIL + "2 140/90mmHg "
            + PREFIX_VISIT_TASK_FINISH + "2 "
            + PREFIX_VISIT_REMARKS + "Patient may be allergic to bacitracin";

    public static final String MESSAGE_UPDATE_ONGOING_VISIT_SUCCESS = "Updated Visit: %1$s";
    public static final String MESSAGE_NO_ONGOING_VISIT = "There is no ongoing visit to update.";
    public static final String MESSAGE_ONGOING_VISIT_NOT_MODIFIED = "No fields updated.";
    public static final String MESSAGE_INVALID_VISIT_TASK_INDEX =
            "There are no visit tasks that have the index %1$s";

    private final UpdateOngoingVisitDescriptor updateOngoingVisitDescriptor;

    /**
     * @param updateOngoingVisitDescriptor details to edit the person with
     */
    public UpdateOngoingVisitCommand(UpdateOngoingVisitDescriptor updateOngoingVisitDescriptor) {
        requireNonNull(updateOngoingVisitDescriptor);
        this.updateOngoingVisitDescriptor = new UpdateOngoingVisitDescriptor(updateOngoingVisitDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!this.updateOngoingVisitDescriptor.isAnyFieldUpdated()) {
            throw new CommandException(MESSAGE_ONGOING_VISIT_NOT_MODIFIED);
        }

        Visit visitToUpdate = VisitUtil.ensureOngoingVisitExistsAndGet(model, MESSAGE_NO_ONGOING_VISIT);
        Visit updatedVisit = createUpdated(visitToUpdate, updateOngoingVisitDescriptor);

        if (visitToUpdate.equals(updatedVisit)) {
            throw new CommandException(MESSAGE_ONGOING_VISIT_NOT_MODIFIED);
        }

        model.updateOngoingVisit(updatedVisit);

        return new CommandResult(String.format(MESSAGE_UPDATE_ONGOING_VISIT_SUCCESS, updatedVisit));
    }

    /**
     * Creates and returns a {@code Visit} with the details of {@code visitToUpdate}
     * edited with {@code updateVisitDescriptor}.
     */
    private static Visit createUpdated(Visit visitToUpdate,
                                       UpdateOngoingVisitDescriptor descriptor) throws CommandException {
        requireAllNonNull(visitToUpdate, descriptor);
        assert visitToUpdate != null;
        //Update fields
        Remark updatedRemark = descriptor.getRemark().orElse(visitToUpdate.getRemark());

        List<VisitTask> visitTaskList = visitToUpdate.getVisitTasks();
        VisitTaskUpdatesMultimap multimap = new VisitTaskUpdatesMultimap();

        multimap.saveTasksFinishedByIndexes(visitTaskList,
                descriptor.getUnfinishedVisitTaskIndexes(),
                VisitTaskUpdatesMultimapKeys.UNFINISH);

        multimap.saveTasksFinishedByIndexes(visitTaskList,
                descriptor.getFinishedVisitTaskIndexes(),
                VisitTaskUpdatesMultimapKeys.FINISH);

        multimap.saveTaskDetailsByIndexes(visitTaskList,
                descriptor.getUpdatedVisitTaskDetails());

        List<VisitTask> updatedVisitTaskList = multimap.buildUpdatedVisitTasks(visitTaskList);

        //Update, so some fields are not changeable
        StartDateTime startDateTime = visitToUpdate.getStartDateTime();
        EndDateTime endDateTime = null;
        if (visitToUpdate.getEndDateTime().isPresent()) {
            endDateTime = visitToUpdate.getEndDateTime().get();
        }
        Person patient = visitToUpdate.getPatient();

        return new Visit(updatedRemark, startDateTime, endDateTime, updatedVisitTaskList, patient);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateOngoingVisitCommand)) {
            return false;
        }

        // state check
        UpdateOngoingVisitCommand e = (UpdateOngoingVisitCommand) other;
        return updateOngoingVisitDescriptor.equals(e.updateOngoingVisitDescriptor);
    }
}
