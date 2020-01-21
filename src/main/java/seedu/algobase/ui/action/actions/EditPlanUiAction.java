package seedu.algobase.ui.action.actions;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PLANS;
import static seedu.algobase.model.searchrule.plansearchrule.TimeRange.ORDER_CONSTRAINTS;
import static seedu.algobase.model.searchrule.plansearchrule.TimeRange.isValidRange;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.model.plan.PlanName;
import seedu.algobase.model.task.Task;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Edits the details of an existing Plan in the algobase.
 */
public class EditPlanUiAction extends UiAction {

    public static final String MESSAGE_EDIT_PLAN_SUCCESS = "Plan [%1$s] edited.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLAN = "A plan of name [%1$s] already exists in AlgoBase.";

    private final Id id;
    private final EditPlanDescriptor editPlanDescriptor;

    /**
     * @param id of the Plan in the filtered Plan list to edit
     * @param editPlanDescriptor details to edit the Plan with
     */
    public EditPlanUiAction(Id id, EditPlanDescriptor editPlanDescriptor) {
        requireNonNull(id);
        requireNonNull(editPlanDescriptor);

        this.id = id;
        this.editPlanDescriptor = new EditPlanDescriptor(editPlanDescriptor);
    }

    /**
     * Retrieves the plan to be edited from the plan list.
     */
    private Plan retrievePlanToEdit(List<Plan> planList) throws NoSuchElementException {
        for (Plan plan : planList) {
            if (plan.getId().equals(id)) {
                return plan;
            }
        }
        throw new NoSuchElementException("No Plan Found");
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        Plan planToEdit = retrievePlanToEdit(lastShownList);
        Plan editedPlan = createEditedPlan(planToEdit, editPlanDescriptor);

        if (!isValidRange(editedPlan.getStartDate(), editedPlan.getEndDate())) {
            throw new UiActionException(ORDER_CONSTRAINTS);
        }

        if (!planToEdit.isSamePlan(editedPlan) && model.hasPlan(editedPlan)) {
            throw new UiActionException(String.format(MESSAGE_DUPLICATE_PLAN, editedPlan.getPlanName()));
        }

        model.setPlan(planToEdit, editedPlan);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        return new UiActionResult(
            true,
            Optional.of(String.format(MESSAGE_EDIT_PLAN_SUCCESS, editedPlan.getPlanName()))
        );
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
        if (!(other instanceof EditPlanUiAction)) {
            return false;
        }

        // state check
        EditPlanUiAction e = (EditPlanUiAction) other;
        return id.equals(e.id)
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
