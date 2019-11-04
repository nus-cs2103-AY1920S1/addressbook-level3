package seedu.algobase.model.searchrule.plansearchrule;

import static seedu.algobase.model.searchrule.plansearchrule.PlanDescriptionContainsKeywordsPredicate.DEFAULT_PLAN_DESCRIPTION_PREDICATE;
import static seedu.algobase.model.searchrule.plansearchrule.PlanNameContainsKeywordsPredicate.DEFAULT_PLAN_NAME_PREDICATE;
import static seedu.algobase.model.searchrule.plansearchrule.TasksContainsNamePredicate.DEFAULT_TASKS_CONTAINS_NAME_PREDICATE;
import static seedu.algobase.model.searchrule.plansearchrule.TimeRangePredicate.DEFAULT_TIME_RANGE_PREDICATE;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.model.plan.Plan;

/**
 * Stores the details to find the {@code Plan}. Each non-empty field value will replace the
 * corresponding field value of the {@code findPlanPredicate}.
 */
public class FindPlanDescriptor {

    private PlanNameContainsKeywordsPredicate planNamePredicate;
    private PlanDescriptionContainsKeywordsPredicate planDescriptionPredicate;
    private TimeRangePredicate timeRangePredicate;
    private TasksContainsNamePredicate tasksContainsNamePredicate;

    public FindPlanDescriptor() {
        planNamePredicate = null;
        planDescriptionPredicate = null;
        timeRangePredicate = null;
        tasksContainsNamePredicate = null;
    }

    public FindPlanDescriptor(PlanNameContainsKeywordsPredicate planNamePredicate,
                              PlanDescriptionContainsKeywordsPredicate planDescriptionPredicate,
                              TimeRangePredicate timeRangePredicate,
                              TasksContainsNamePredicate tasksContainsNamePredicate) {
        this.planNamePredicate = planNamePredicate;
        this.planDescriptionPredicate = planDescriptionPredicate;
        this.timeRangePredicate = timeRangePredicate;
        this.tasksContainsNamePredicate = tasksContainsNamePredicate;
    }

    /**
     * Copy constructor.
     */
    public FindPlanDescriptor(FindPlanDescriptor toCopy) {
        setPlanNamePredicate(toCopy.planNamePredicate);
        setPlanDescriptionPredicate(toCopy.planDescriptionPredicate);
        setTimeRangePredicate(toCopy.timeRangePredicate);
        setTasksContainsNamePredicate(toCopy.tasksContainsNamePredicate);
    }

    public boolean isAnyFieldProvided() {
        return CollectionUtil.isAnyNonNull(planNamePredicate, planDescriptionPredicate,
                timeRangePredicate, tasksContainsNamePredicate);
    }

    public void setPlanNamePredicate(PlanNameContainsKeywordsPredicate planNameContainsKeywordsPredicate) {
        this.planNamePredicate = planNameContainsKeywordsPredicate;
    }

    public Optional<PlanNameContainsKeywordsPredicate> getPlanNamePredicate() {
        return Optional.ofNullable(planNamePredicate);
    }

    public void setPlanDescriptionPredicate(PlanDescriptionContainsKeywordsPredicate planDescriptionPredicate) {
        this.planDescriptionPredicate = planDescriptionPredicate;
    }

    public Optional<PlanDescriptionContainsKeywordsPredicate> getPlanDescriptionPredicate() {
        return Optional.ofNullable(planDescriptionPredicate);
    }

    public void setTimeRangePredicate(TimeRangePredicate timeRangePredicate) {
        this.timeRangePredicate = timeRangePredicate;
    }

    public Optional<TimeRangePredicate> getTimeRangePredicate() {
        return Optional.ofNullable(timeRangePredicate);
    }

    public void setTasksContainsNamePredicate(TasksContainsNamePredicate tasksContainsNamePredicate) {
        this.tasksContainsNamePredicate = tasksContainsNamePredicate;
    }

    public Optional<TasksContainsNamePredicate> getTasksContainsNamePredicate() {
        return Optional.ofNullable(tasksContainsNamePredicate);
    }

    /**
     * Creates and returns a {@code findPlanPredicate} with the details of {@code findPlanDescriptor}.
     */
    public Predicate<Plan> getFindPlanPredicate() {
        PlanNameContainsKeywordsPredicate planNamePredicate = getPlanNamePredicate()
                .orElse(DEFAULT_PLAN_NAME_PREDICATE);
        PlanDescriptionContainsKeywordsPredicate planDescriptionPredicate =
                getPlanDescriptionPredicate().orElse(DEFAULT_PLAN_DESCRIPTION_PREDICATE);
        TimeRangePredicate timeRangePredicate = getTimeRangePredicate().orElse(DEFAULT_TIME_RANGE_PREDICATE);
        TasksContainsNamePredicate tasksContainsNamePredicate = getTasksContainsNamePredicate()
                .orElse(DEFAULT_TASKS_CONTAINS_NAME_PREDICATE);
        return planNamePredicate
                .and(planDescriptionPredicate)
                .and(timeRangePredicate)
                .and(tasksContainsNamePredicate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPlanDescriptor)) {
            return false;
        }

        // state check
        FindPlanDescriptor e = (FindPlanDescriptor) other;

        return getPlanNamePredicate().equals(e.getPlanNamePredicate())
                && getPlanDescriptionPredicate().equals(e.getPlanDescriptionPredicate())
                && getTimeRangePredicate().equals(e.getTimeRangePredicate())
                && getTasksContainsNamePredicate().equals((e.getTasksContainsNamePredicate()));
    }

}
