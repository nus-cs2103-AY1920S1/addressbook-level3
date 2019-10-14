package seedu.algobase.model;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;

/**
 * Represents the in-memory model of the algobase data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AlgoBase algoBase;
    private final UserPrefs userPrefs;
    private final FilteredList<Problem> filteredProblems;
    private final FilteredList<Tag> filteredTags;

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
        filteredTags = new FilteredList<>(this.algoBase.getTagList());
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

    //=========== Tag ================================================================================

    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return algoBase.hasTag(tag);
    }

    @Override
    public void deleteTag(Tag target) {
        algoBase.removeTag(target);
    }

    @Override
    public void deleteTags(Tag target) {
        for(Problem problem : filteredProblems) {
            Set<Tag> targetTags = problem.getTags();
            for(Tag tag : targetTags) {
                if(tag.getName().equals(target.getName())) {
                    problem.deleteTag(tag);
                }
            }
        }
    }

    @Override
    public void addTag(Tag tag) {
        algoBase.addTag(tag);
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    @Override
    public void addTags(Set<Tag> tags) {
        for(Tag tag : tags) {
            addTag(tag);
        }
    }

    @Override
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        algoBase.setTag(target, editedTag);
    }

    @Override
    public void setTags(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);
        for(Problem problem : filteredProblems) {
            Set<Tag> targetTags = problem.getTags();
            for(Tag tag : targetTags) {
                if(tag.getName().equals(target.getName())) {
                    problem.addTag(editedTag);
                    problem.deleteTag(tag);
                }
            }
        }
    }

    //=========== Filtered Problem List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Problem} backed by the internal list of
     * {@code versionedAlgoBase}
     */
    @Override
    public ObservableList<Problem> getFilteredProblemList() {
        return filteredProblems;
    }

    @Override
    public void updateFilteredProblemList(Predicate<Problem> predicate) {
        requireNonNull(predicate);
        filteredProblems.setPredicate(predicate);
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

    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code versionedAlgoBase}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }
}
