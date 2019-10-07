package seedu.algobase.model;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;

/**
 * Represents the in-memory model of the algobase data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AlgoBase algoBase;
    private final UserPrefs userPrefs;
    private final FilteredList<Problem> filteredProblems;
    private final SortedList<Problem> sortedProblems;
    private final FilteredList<Plan> filteredPlans;

    /**
     * Initializes a ModelManager with the given algoBase and userPrefs.
     */
    public ModelManager(ReadOnlyAlgoBase algoBase, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(algoBase, userPrefs);

        logger.fine("Initializing with algobase: " + algoBase + " and user prefs " + userPrefs);

        this.algoBase = new AlgoBase(algoBase);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredProblems = new FilteredList<>(this.algoBase.getProblemList());
        sortedProblems = new SortedList<>(filteredProblems);
        filteredPlans = new FilteredList<>(this.algoBase.getPlanList());
    }

    public ModelManager() {
        this(new AlgoBase(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAlgoBaseFilePath() {
        return userPrefs.getAlgoBaseFilePath();
    }

    @Override
    public void setAlgoBaseFilePath(Path algoBaseFilePath) {
        requireNonNull(algoBaseFilePath);
        userPrefs.setAlgoBaseFilePath(algoBaseFilePath);
    }

    //=========== AlgoBase ================================================================================

    @Override
    public void setAlgoBase(ReadOnlyAlgoBase algoBase) {
        this.algoBase.resetData(algoBase);
    }

    @Override
    public ReadOnlyAlgoBase getAlgoBase() {
        return algoBase;
    }

    @Override
    public boolean hasProblem(Problem problem) {
        requireNonNull(problem);
        return algoBase.hasProblem(problem);
    }

    @Override
    public void deleteProblem(Problem target) {
        algoBase.removeProblem(target);
    }

    @Override
    public void addProblem(Problem problem) {
        algoBase.addProblem(problem);
        updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
    }

    @Override
    public void setProblem(Problem target, Problem editedProblem) {
        requireAllNonNull(target, editedProblem);
        algoBase.setProblem(target, editedProblem);
    }

    //=========== Plan List =============================================================
    @Override
    public boolean hasPlan(Plan plan) {
        requireNonNull(plan);
        return algoBase.hasPlan(plan);
    }

    @Override
    public void deletePlan(Plan target) {
        algoBase.removePlan(target);
    }

    @Override
    public void addPlan(Plan plan) {
        algoBase.addPlan(plan);
        updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
    }

    @Override
    public void setPlan(Plan target, Plan editedPlan) {
        requireAllNonNull(target, editedPlan);

        algoBase.setPlan(target, editedPlan);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Plan} backed by the internal list of
     */
    @Override
    public ObservableList<Plan> getFilteredPlanList() {
        return filteredPlans;
    }

    //=========== Filtered Problem List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Problem} backed by the internal list of
     * {@code versionedAlgoBase}
     */
    @Override
    public ObservableList<Problem> getFilteredProblemList() {
        return sortedProblems;
    }

    @Override
    public void updateFilteredProblemList(Predicate<Problem> predicate) {
        requireNonNull(predicate);
        filteredProblems.setPredicate(predicate);
    }

    /**
     * Updates the Problem list according to the given {@code problemComparator}.
     *
     * @param problemComparator a comparator of problems
     * @throws NullPointerException if {@code problemComparator} is null;
     */
    @Override
    public void updateSortedProblemList(Comparator<Problem> problemComparator) {
        requireNonNull(problemComparator);
        sortedProblems.setComparator(problemComparator);
    }

    //============== Util ==================================================================

    @Override
    public void updateFilteredPlanList(Predicate<Plan> predicate) {
        requireNonNull(predicate);
        filteredPlans.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return algoBase.equals(other.algoBase)
                && userPrefs.equals(other.userPrefs)
                && filteredProblems.equals(other.filteredProblems);
    }

}
