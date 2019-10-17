package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_FINISH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_INDEX_AND_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_TASK_UNFINISH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.util.Pair;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
import seedu.address.model.visittask.Detail;
import seedu.address.model.visittask.VisitTask;

/**
 * Updates the details of the ongoing visit.
 */
public class UpdateOngoingVisitCommand extends Command implements MutatorCommand {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final String COMMAND_WORD = "visit-now-update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the current visit."
            + "Existing values will be overwritten by the input values.\n"
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
        Visit visitToUpdate = VisitUtil.verifyOngoingVisitExistsAndGet(model, MESSAGE_NO_ONGOING_VISIT);
        Visit updatedVisit = createUpdated(visitToUpdate, updateOngoingVisitDescriptor);

        if (visitToUpdate.equals(updatedVisit)) {
            throw new CommandException(MESSAGE_ONGOING_VISIT_NOT_MODIFIED);
        }

        model.updateOngoingVisit(visitToUpdate, updatedVisit);

        return new CommandResult(String.format(MESSAGE_UPDATE_ONGOING_VISIT_SUCCESS, updatedVisit));
    }

    /**
     * Creates and returns a {@code Visit} with the details of {@code visitToUpdate}
     * edited with {@code updateVisitDescriptor}.
     */
    private static Visit createUpdated(Visit visitToUpdate,
                                       UpdateOngoingVisitDescriptor descriptor) throws CommandException {
        assert visitToUpdate != null;

        Remark updatedRemark = descriptor.getRemark().orElse(visitToUpdate.getRemark());
        List<VisitTask> visitTaskList = visitToUpdate.getVisitTasks();
        //Update
        VisitTaskUpdatesMultimap multimap = new VisitTaskUpdatesMultimap();
        multimap.saveTasksFinishedByIndexes(visitTaskList,
                descriptor.getUnfinishedVisitTaskIndexes(), VISIT_TASK_UPDATES_MULTIMAP_KEYS.UNFINISH);
        multimap.saveTasksFinishedByIndexes(visitTaskList,
                descriptor.getFinishedVisitTaskIndexes(), VISIT_TASK_UPDATES_MULTIMAP_KEYS.FINISH);
        multimap.saveTaskDetailsByIndexes(visitTaskList, descriptor.getUpdatedVisitTaskDetails());
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

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UpdateOngoingVisitDescriptor {
        private Remark remark;
        private List<Index> finishedVisitTaskIndexes;
        private List<Index> unfinishedVisitTaskIndexes;
        private List<Pair<Index, String>> updatedVisitTaskDetails;

        public UpdateOngoingVisitDescriptor() {
            finishedVisitTaskIndexes = new ArrayList<>();
            unfinishedVisitTaskIndexes = new ArrayList<>();
            updatedVisitTaskDetails = new ArrayList<>();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateOngoingVisitDescriptor(UpdateOngoingVisitDescriptor toCopy) {
            this();
            setRemark(toCopy.remark);
            setFinishedVisitTaskIndexes(toCopy.finishedVisitTaskIndexes);
            setUnfinishedVisitTaskIndexes(toCopy.unfinishedVisitTaskIndexes);
            setUpdatedVisitTaskDetails(toCopy.updatedVisitTaskDetails);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return remark != null ||
                    !finishedVisitTaskIndexes.isEmpty() ||
                    !unfinishedVisitTaskIndexes.isEmpty() ||
                    !updatedVisitTaskDetails.isEmpty();
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public List<Index> getFinishedVisitTaskIndexes() {
            return finishedVisitTaskIndexes;
        }

        public void setFinishedVisitTaskIndexes(List<Index> finishedVisitTaskIndexes) {
            this.finishedVisitTaskIndexes.addAll(finishedVisitTaskIndexes);
        }

        public List<Index> getUnfinishedVisitTaskIndexes() {
            return unfinishedVisitTaskIndexes;
        }

        public void setUnfinishedVisitTaskIndexes(List<Index> unfinishedVisitTaskIndexes) {
            this.unfinishedVisitTaskIndexes.addAll(unfinishedVisitTaskIndexes);
        }

        public List<Pair<Index, String>> getUpdatedVisitTaskDetails() {
            return updatedVisitTaskDetails;
        }

        public void setUpdatedVisitTaskDetails(List<Pair<Index, String>> updatedVisitTaskDetails) {
            this.updatedVisitTaskDetails.addAll(updatedVisitTaskDetails);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateOngoingVisitDescriptor)) {
                return false;
            }

            // state check
            UpdateOngoingVisitDescriptor e = (UpdateOngoingVisitDescriptor) other;

            return getRemark().equals(e.getRemark())
                    && CollectionUtil.checkEqual(getFinishedVisitTaskIndexes(), e.getFinishedVisitTaskIndexes())
                    && CollectionUtil.checkEqual(getUnfinishedVisitTaskIndexes(), e.getUnfinishedVisitTaskIndexes())
                    && CollectionUtil.checkEqual(getUpdatedVisitTaskDetails(), e.getUpdatedVisitTaskDetails());
        }
    }

    private enum VISIT_TASK_UPDATES_MULTIMAP_KEYS {
        FINISH,
        UNFINISH
    }

    /**
     * Record the visit task updates into a multimap so that all changes can be made in one go.
     */
    private static class VisitTaskUpdatesMultimap {
        private final Map<Integer, Set<Object>> map = new HashMap<>();

        /**
         * Associates the specified argument value with {@code prefix} key in this map.
         * If the map previously contained a mapping for the key,
         * the new value is appended to the list of existing values unless it already exists.
         *
         * @param index Index key with which the specified argument value is to be associated
         * @param detail Argument value to be associated with the specified Index key
         */
        public void put(Index index, String detail) {
            Set<Object> values = getAllValues(index);
            values.add(detail);
            map.put(index.getZeroBased(), values);
        }

        /**
         * Associates the specified argument value with {@code prefix} key in this map.
         * If the map previously contained a mapping for the key,
         * the new value is appended to the list of existing values unless it already exists.
         * If a FINISH and UNFINISH exist, UNFINISH will take priority.
         *
         * @param index Index key with which the specified argument value is to be associated
         * @param key Argument value to be associated with the specified Index key
         */
        public void put(Index index, VISIT_TASK_UPDATES_MULTIMAP_KEYS key) {
            Set<Object> values = getAllValues(index);
            if (!values.contains(VISIT_TASK_UPDATES_MULTIMAP_KEYS.UNFINISH)) {
                values.add(key);
                map.put(index.getZeroBased(), values);
            }
        }

        /**
         * Returns all values of {@code index}.
         * If the prefix does not exist or has no values, this will return an empty list.
         * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
         */
        public Set<Object> getAllValues(Index index) {
            if (!map.containsKey(index)) {
                return new HashSet<>();
            }
            return new HashSet<>(map.get(index));
        }

        /**
         * Record in a multimap which Visit Tasks finished / unfinished using a list of indexes.
         */
        private void saveTasksFinishedByIndexes(
                List<VisitTask> taskList,
                List<Index> indexList,
                VISIT_TASK_UPDATES_MULTIMAP_KEYS isMarkFinish) throws CommandException {
            for (Index targetIndex : indexList) {
                if (targetIndex.getZeroBased() >= taskList.size()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_VISIT_TASK_INDEX, targetIndex.getOneBased()));
                }
                this.put(targetIndex, isMarkFinish);
            }
        }

        /**
         * Record in a multimap the details of a VisitTask using a list of indexes.
         */
        private void saveTaskDetailsByIndexes(
                List<VisitTask> taskList,
                List<Pair<Index, String>> indexDetailPairList) throws CommandException {
            for (Pair<Index, String> indexStringPair : indexDetailPairList) {
                if (indexStringPair.getKey().getZeroBased() >= taskList.size()) {
                    throw new CommandException(String.format(MESSAGE_INVALID_VISIT_TASK_INDEX,
                            indexStringPair.getKey()));
                }
                this.put(indexStringPair.getKey(), indexStringPair.getValue());
            }
        }

        /**
         * Builds and returns an updated visit task list using changes recorded in the multimap.
         */
        private List<VisitTask> buildUpdatedVisitTasks(final List<VisitTask> visitTaskList) {
            List<VisitTask> updatedVisitTaskList = new ArrayList<>();
            for (int i = 0; i < visitTaskList.size(); i++) {
                updatedVisitTaskList.add(updateVisitTask(visitTaskList.get(i), i));
            }
            return updatedVisitTaskList;
        }

        /**
         * Returns an updated visit task using changes recorded in the multimap.
         */
        private VisitTask updateVisitTask(VisitTask visitTask, int index) {
            if (map.containsKey(index)) {
                Detail detail = visitTask.getDetail();
                boolean isDone = visitTask.isDone();
                for (Object change : map.get(index)) {
                    //Detail
                    if (change instanceof String) {
                        detail = new Detail((String) change);
                    } else if (change == VISIT_TASK_UPDATES_MULTIMAP_KEYS.FINISH) {
                        isDone = true;
                    } else if (change == VISIT_TASK_UPDATES_MULTIMAP_KEYS.UNFINISH) {
                        isDone = false;
                    } else {
                        logger.severe("Change is not string or finish/unfinish. Bug in code.");
                    }
                    visitTask = new VisitTask(visitTask.getVisitTodo(), detail, isDone);
                }
            }
            return visitTask;
        }
    }
}
