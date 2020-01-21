package seedu.algobase.model;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanList;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.UniqueProblemList;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.searchrule.problemsearchrule.UniqueFindRuleList;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.tag.UniqueTagList;
import seedu.algobase.model.task.Task;

/**
 * Wraps all data at the algobase level
 * Duplicates are not allowed (by .isSameProblem comparison)
 */
public class AlgoBase implements ReadOnlyAlgoBase {

    private final UniqueProblemList problems;
    private final UniqueTagList tags;
    private final PlanList plans;
    private final GuiState guiState;
    private final UniqueFindRuleList findRules;

    /*
    * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
    * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    *   among constructors.
    */
    {
        problems = new UniqueProblemList();
        plans = new PlanList();
        tags = new UniqueTagList();
        guiState = new GuiState();
        findRules = new UniqueFindRuleList();
    }

    public AlgoBase() {
    }

    /**
     * Creates an AlgoBase using the Problems in the {@code toBeCopied}
     */
    public AlgoBase(ReadOnlyAlgoBase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code AlgoBase} with {@code newData}.
     */
    void resetData(ReadOnlyAlgoBase newData) {
        requireNonNull(newData);

        setProblems(newData.getProblemList());
        setPlans(newData.getPlanList());
        setTags(newData.getTagList());
        setFindRules(newData.getFindRules());
        this.guiState.resetData(newData.getGuiState());
    }

    //========== Gui State ==============================================================
    public GuiState getGuiState() {
        return this.guiState;
    }

    public void setGuiState(GuiState guiState) {
        this.guiState.resetData(guiState);
    }

    //========== Problem ================================================================

    /**
     * Replaces the contents of the Problem list with {@code problems}.
     * {@code problems} must not contain duplicate problems.
     */
    public void setProblems(List<Problem> problems) {
        this.problems.setProblems(problems);
    }

    /**
     * Returns true if a Problem with the same identity as {@code Problem} exists in the algobase.
     */
    public boolean hasProblem(Problem problem) {
        requireNonNull(problem);
        return problems.contains(problem);
    }

    /**
     * Adds a Problem to the algobase.
     * The Problem must not already exist in the algobase.
     */
    public void addProblem(Problem p) {
        problems.add(p);
        for (Tag tag : p.getTags()) {
            if (!hasTag(tag)) {
                addTag(tag);
            }
        }
    }

    /**
     * Replaces the given Problem {@code target} in the list with {@code editedProblem}.
     * {@code target} must exist in the algobase.
     * The Problem identity of {@code editedProblem} must not be the same as another existing Problem in the algobase.
     */
    void setProblem(Problem target, Problem editedProblem) {
        requireNonNull(editedProblem);

        problems.setProblem(target, editedProblem);
    }

    /**
     * Removes {@code key} from this {@code AlgoBase}.
     * {@code key} must exist in the algobase.
     */
    void removeProblem(Problem key) {
        problems.remove(key);
    }

    @Override
    public ObservableList<Problem> getProblemList() {
        return problems.asUnmodifiableObservableList();
    }

    @Override
    public Problem findProblemById(Id problemId) throws IllegalValueException {
        requireNonNull(problemId);
        for (Problem problem : problems) {
            if (problem.getId().equals(problemId)) {
                return problem;
            }
        }
        throw new IllegalValueException("No problem found");
    }

    @Override
    public boolean checkIsProblemUsed(Problem problem) {
        return plans.containsProblem(problem);
    }

    /**
     * Removes the given problem from all plans
     *
     * @param problem the problem to be removed
     */
    void removeProblemFromAllPlans(Problem problem) {
        plans.removeProblem(problem);
    }

    /**
     * Updates the given problem in all plans
     *
     * @param oldProblem the existing problem to be updated
     * @param newProblem the new problem to be added
     */
    void updateProblemInAllPlans(Problem oldProblem, Problem newProblem) {
        plans.updateProblem(oldProblem, newProblem);
    }

    //========== Tag ====================================================================

    /**
     * Replaces the contents of the Tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Returns true if a Tag with the same identity as {@code Tag} exists in the algobase.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }

    /**
     * Adds a Tag to the algobase.
     * The Tag must not already exist in the algobase.
     */
    public void addTag(Tag p) {
        tags.add(p);
    }

    /**
     * Replaces the given Tag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the algobase.
     * The Tag identity of {@code editedTag} must not be the same as another existing Tag in the algobase.
     */
    void setTag(Tag target, Tag editedTag) {
        requireNonNull(editedTag);
        tags.setTag(target, editedTag);
    }

    /**
     * Removes {@code key} from this {@code AlgoBase}.
     * {@code key} must exist in the algobase.
     */
    void removeTag(Tag key) {
        tags.remove(key);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public Tag findTagById(Id tagId) throws IllegalValueException {
        requireNonNull(tagId);
        for (Tag tag : tags) {
            if (tag.getId().equals(tagId)) {
                return tag;
            }
        }
        throw new IllegalValueException("No tag found");
    }

    /**
     * reset tag for problems with tag
     * @param oldTag tag that will reset
     * @param newTag tag that reset to
     */
    public void resetTagForProblemsWithTag(Tag oldTag, Tag newTag) {
        for (Problem problem : problems) {
            if (problem.getTags().stream().anyMatch(x -> x.isSameTag(oldTag))) {
                Problem newProblem = new
                        Problem(problem.getId(),
                        problem.getName(),
                        problem.getAuthor(),
                        problem.getWebLink(),
                        problem.getDescription(),
                        problem.getTags(),
                        problem.getDifficulty(),
                        problem.getRemark(),
                        problem.getSource());
                newProblem.setTag(oldTag, newTag);
                problems.setProblem(problem, newProblem);
            }
        }
    }

    /**
     * delete tag for problems with tag
     */
    public void refreshTagForProblemsWithTag() {
        for (Problem problem : problems) {
            Problem newProblem = new
                    Problem(problem.getId(),
                    problem.getName(),
                    problem.getAuthor(),
                    problem.getWebLink(),
                    problem.getDescription(),
                    problem.getTags(),
                    problem.getDifficulty(),
                    problem.getRemark(),
                    problem.getSource());
            Tag newTag = new Tag("#forRefresh#");
            newProblem.addTag(newTag);
            newProblem.deleteTag(newTag);
            problems.setProblem(problem, newProblem);

        }
    }
    //========== Plan ===================================================================

    /**
     * Replaces the contents of the Plan list with {@code plans}.
     * {@code plans} must not contain duplicate plans.
     */
    public void setPlans(List<Plan> plans) {
        this.plans.setPlans(plans);
    }

    /**
     * Returns true if a Plan with the same identity as {@code Plan} exists in the algobase.
     */
    boolean hasPlan(Plan plan) {
        requireNonNull(plan);
        return plans.contains(plan);
    }

    /**
     * Adds a Plan to the algobase.
     * The Plan must not already exist in the algobase.
     */
    public void addPlan(Plan p) {
        plans.add(p);
    }

    /**
     * Replaces the given Plan {@code target} in the list with {@code editedPlan}.
     * {@code target} must exist in the algobase.
     * The Plan identity of {@code editedPlan} must not be the same as another existing Plan in the algobase.
     */
    public void setPlan(Plan target, Plan editedPlan) {
        requireNonNull(editedPlan);

        plans.setPlan(target, editedPlan);
    }

    /**
     * Removes a Plan from the algobase.
     */
    void removePlan(Plan key) {
        plans.remove(key);
    }

    @Override
    public ObservableList<Plan> getPlanList() {
        return plans.asUnmodifiableObservableList();
    }

    @Override
    public Plan findPlanById(Id planId) throws IllegalValueException {
        requireNonNull(planId);
        for (Plan plan : plans) {
            if (plan.getId().equals(planId)) {
                return plan;
            }
        }
        throw new IllegalValueException("No plan found");
    }

    //========== Task ===================================================================

    /**
     * Updates the task set in the given Plan.
     *
     * @param taskSet the task set to be updated
     * @param plan    the plan to be updated in
     */
    public void updateTasks(Set<Task> taskSet, Plan plan) {
        plans.setPlan(plan, plan.updateTasks(taskSet));
    }

    @Override
    public void setCurrentPlan(Plan plan) {
        plans.setCurrentPlan(plan);
    }

    @Override
    public void setCurrentPlan(int index) {
        plans.setCurrentPlan(index);
    }

    @Override
    public ObservableList<Task> getCurrentTaskList() {
        return plans.getUnmodifiableObservableTaskList();
    }

    @Override
    public StringProperty getCurrentPlan() {
        return plans.getCurrentPlan();
    }

    @Override
    public IntegerProperty getCurrentDoneCount() {
        return plans.getCurrentDoneCount();
    }

    @Override
    public IntegerProperty getCurrentUndoneCount() {
        return plans.getCurrentUndoneCount();
    }

    @Override
    public IntegerProperty getCurrentTaskCount() {
        return plans.getCurrentTaskCount();
    }

    //========== Find Rules =============================================================

    /**
     * Returns true of {@code rule} has the same identity as one {@code ProblemSearchRule} in AlgoBase.
     *
     * @param rule the rule to be checked
     */
    public boolean hasFindRule(ProblemSearchRule rule) {
        requireNonNull(rule);
        return findRules.contains(rule);
    }

    /**
     * Adds a {@code rule} into AlgoBase's list of {@code ProblemSearchRule}.
     *
     * @param rule the rule to be added
     */
    public void addFindRule(ProblemSearchRule rule) {
        requireNonNull(rule);
        findRules.add(rule);
    }

    /**
     * Replaces a given {@code ProblemSearchRule} in the AlgoBase with an edited {@code ProblemSearchRule}.
     * {@code target} must exist in the Algobase.
     * The identity of {@code editedRule} must not be the same as another existing {@code ProblemSearchRule}
     * in the algobase.
     *
     * @param target     the old rule to be updated
     * @param editedRule the new rule to be added
     */
    void setFindRule(ProblemSearchRule target, ProblemSearchRule editedRule) {
        requireAllNonNull(target, editedRule);
        findRules.setFindRule(target, editedRule);
    }

    /**
     * Replaces the contents of the FindRule list with {@code rules}.
     * {@code rules} must not contain duplicate problems.
     *
     * @param rules the list of rules to be be used to reset the database
     */
    private void setFindRules(List<ProblemSearchRule> rules) {
        this.findRules.setFindRules(rules);
    }

    /**
     * Removes a given {@code ProblemSearchRule} in the AlgoBase.
     * {@code toRemove} must exist in the Algobase.
     *
     * @param toRemove the rule to be removed
     */
    void removeFindRule(ProblemSearchRule toRemove) {
        requireNonNull(toRemove);
        findRules.remove(toRemove);
    }

    @Override
    public ObservableList<ProblemSearchRule> getFindRules() {
        return findRules.asUnmodifiableObservableList();
    }

    //========== Util ===================================================================

    @Override
    public String toString() {
        return problems.asUnmodifiableObservableList().size() + " problems";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AlgoBase // instanceof handles nulls
                && problems.equals(((AlgoBase) other).problems))
                && plans.equals(((AlgoBase) other).plans);
    }

    @Override
    public int hashCode() {
        return problems.hashCode();
    }

}
