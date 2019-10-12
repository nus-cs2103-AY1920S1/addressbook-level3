package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_NAME;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PLANS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.logic.commands.exceptions.CommandException;
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
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PLAN_NAME + "NAME] "
            + "[" + PREFIX_PLAN_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PLAN_DESCRIPTION + "future questions of CS2040 ";

    public static final String MESSAGE_EDIT_PLAN_SUCCESS = "Edited Plan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLAN = "This Plan already exists in the algobase.";

    private final Index index;
    private final EditPlanDescriptor editPlanDescriptor;

    /**
     * @param index of the Plan in the filtered Plan list to edit
     * @param editPlanDescriptor details to edit the Plan with
     */
    public EditPlanCommand(Index index, EditPlanDescriptor editPlanDescriptor) {
        requireNonNull(index);
        requireNonNull(editPlanDescriptor);

        this.index = index;
        this.editPlanDescriptor = new EditPlanDescriptor(editPlanDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Plan planToEdit = lastShownList.get(index.getZeroBased());
        Plan editedPlan = createEditedPlan(planToEdit, editPlanDescriptor);

        if (!planToEdit.isSamePlan(editedPlan) && model.hasPlan(editedPlan)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAN);
        }

        model.setPlan(planToEdit, editedPlan);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        return new CommandResult(String.format(MESSAGE_EDIT_PLAN_SUCCESS, editedPlan));
    }

    /**
     * Creates and returns a {@code Plan} with the details of {@code planToEdit}
     * edited with {@code editPlanDescriptor}.
     */
    private static Plan createEditedPlan(Plan planToEdit, EditPlanDescriptor editPlanDescriptor) {
        assert planToEdit != null;

        PlanName updatedName = editPlanDescriptor.getPlanName().orElse(planToEdit.getPlanName());
        PlanDescription updatedDescription = editPlanDescriptor.getPlanDescription().orElse(
                planToEdit.getPlanDescription());
        LocalDateTime startDate = editPlanDescriptor.getStartDate().orElse(planToEdit.getStartDate());
        LocalDateTime endDate = editPlanDescriptor.getEndDate().orElse(planToEdit.getEndDate());
        Set<Task> tasks = editPlanDescriptor.getTasks().orElse(planToEdit.getTasks());

        return new Plan(updatedName, updatedDescription, startDate, endDate, tasks);
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
        private LocalDateTime startDate;
        private LocalDateTime endDate;
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

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }

        public Optional<LocalDateTime> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }

        public Optional<LocalDateTime> getEndDate() {
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
