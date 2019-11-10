package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PLANS;
import static seedu.algobase.model.searchrule.plansearchrule.TimeRange.ORDER_CONSTRAINTS;
import static seedu.algobase.model.searchrule.plansearchrule.TimeRange.isValidRange;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.model.plan.PlanName;
import seedu.algobase.model.task.Task;


/**
 * Edits the details of an existing Plan in the algobase.
 */
public class EditPlanCommand extends Command {

    public static final String COMMAND_WORD = "editplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Plan identified "
            + "by the index number used in the displayed Plan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE]\n"
            + "Example:\n"
            + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "future questions of CS2040 "
            + PREFIX_START_DATE + "2019/01/01 "
            + PREFIX_END_DATE + "3019/12/12";

    public static final String MESSAGE_EDIT_PLAN_SUCCESS = "Plan [%1$s] edited.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLAN = "A plan of name [%1$s] already exists in AlgoBase.";
    public static final String MESSAGE_INVALID_TIME_RANGE =
            "The time range of a plan should cover due dates of its tasks.";

    private final Index index;
    private final EditPlanDescriptor editPlanDescriptor;
    private final boolean isForced;

    /**
     * @param index of the Plan in the filtered Plan list to edit
     * @param editPlanDescriptor details to edit the Plan with
     */
    public EditPlanCommand(Index index, EditPlanDescriptor editPlanDescriptor, boolean isForced) {
        requireNonNull(index);
        requireNonNull(editPlanDescriptor);
        requireNonNull(isForced);

        this.index = index;
        this.editPlanDescriptor = new EditPlanDescriptor(editPlanDescriptor);
        this.isForced = isForced;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Plan planToEdit = lastShownList.get(index.getZeroBased());
        Plan prototypePlan = createEditedPlan(planToEdit, editPlanDescriptor);
        PlanName planName = prototypePlan.getPlanName();
        PlanDescription planDescription = prototypePlan.getPlanDescription();
        LocalDate startDate = prototypePlan.getStartDate();
        LocalDate endDate = prototypePlan.getEndDate();
        Set<Task> tasks = prototypePlan.getTasks();
        Plan editedPlan;

        if (!isValidRange(prototypePlan.getStartDate(), prototypePlan.getEndDate())) {
            throw new CommandException(ORDER_CONSTRAINTS);
        }

        Stream<LocalDate> dueDates = prototypePlan.getTasks().stream().map(Task::getTargetDate);
        boolean existsUnmatchDueDates = !dueDates.allMatch(prototypePlan::checkWithinDateRange);

        if (!isForced && existsUnmatchDueDates) {
            throw new CommandException(MESSAGE_INVALID_TIME_RANGE);
        } else if (isForced && existsUnmatchDueDates) {
            Set<Task> forcedTasks = tasks
                .stream()
                .map(
                    task -> prototypePlan.checkWithinDateRange(task.getTargetDate())
                        ? task
                        : task.updateDueDate(prototypePlan.getEndDate()))
                .collect(Collectors.toSet());
            editedPlan = new Plan(planName, planDescription, startDate, endDate, forcedTasks);
        } else {
            editedPlan = new Plan(planName, planDescription, startDate, endDate, tasks);
        }

        if (!planToEdit.isSamePlan(editedPlan) && model.hasPlan(editedPlan)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PLAN, editedPlan.getPlanName()));
        }

        model.setPlan(planToEdit, editedPlan);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        return new CommandResult(String.format(MESSAGE_EDIT_PLAN_SUCCESS, editedPlan.getPlanName()));
    }

    /**
     * Creates and returns a {@code Plan} with the details of {@code planToEdit}
     * edited with {@code editPlanDescriptor}.
     */
    private static Plan createEditedPlan(Plan planToEdit, EditPlanDescriptor editPlanDescriptor) {
        assert planToEdit != null;

        Id id = planToEdit.getId();
        PlanName updatedName = editPlanDescriptor.getPlanName().orElse(planToEdit.getPlanName());
        PlanDescription updatedDescription = editPlanDescriptor.getPlanDescription().orElse(
                planToEdit.getPlanDescription());
        LocalDate startDate = editPlanDescriptor.getStartDate().orElse(planToEdit.getStartDate());
        LocalDate endDate = editPlanDescriptor.getEndDate().orElse(planToEdit.getEndDate());
        Set<Task> tasks = editPlanDescriptor.getTasks().orElse(planToEdit.getTasks());

        return new Plan(id, updatedName, updatedDescription, startDate, endDate, tasks);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPlanCommand)) {
            return false;
        }

        // state check
        EditPlanCommand e = (EditPlanCommand) other;
        return index.equals(e.index)
                && editPlanDescriptor.equals(e.editPlanDescriptor);
    }

    /**
     * Stores the details to edit the Plan with. Each non-empty field value will replace the
     * corresponding field value of the Plan.
     */
    public static class EditPlanDescriptor {
        private PlanName name;
        private PlanDescription description;
        private LocalDate startDate;
        private LocalDate endDate;
        private Set<Task> tasks;


        public EditPlanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPlanDescriptor(EditPlanDescriptor toCopy) {
            setPlanName(toCopy.name);
            setPlanDescription(toCopy.description);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setTasks(toCopy.tasks);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, startDate, endDate, tasks);
        }

        public void setPlanName(PlanName name) {
            this.name = name;
        }

        public Optional<PlanName> getPlanName() {
            return Optional.ofNullable(name);
        }

        public void setPlanDescription(PlanDescription description) {
            this.description = description;
        }

        public Optional<PlanDescription> getPlanDescription() {
            return Optional.ofNullable(description);
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public Optional<LocalDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public Optional<LocalDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        /**
         * Sets {@code tasks} to this object's {@code tasks}.
         * A defensive copy of {@code tasks} is used internally.
         */
        public void setTasks(Set<Task> tasks) {
            this.tasks = (tasks != null) ? new HashSet<>(tasks) : null;
        }

        /**
         * Returns an unmodifiable task set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tasks} is null.
         */
        public Optional<Set<Task>> getTasks() {
            return (tasks != null) ? Optional.of(Collections.unmodifiableSet(tasks)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPlanDescriptor)) {
                return false;
            }

            // state check
            EditPlanDescriptor e = (EditPlanDescriptor) other;

            return getPlanName().equals(e.getPlanName())
                    && getPlanDescription().equals(e.getPlanDescription())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getTasks().equals(e.getTasks());
        }
    }
}
