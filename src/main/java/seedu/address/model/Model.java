package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Problem.Problem;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Problem> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAlgoBaseFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAlgoBaseFilePath(Path algoBaseFilePath);

    /**
     * Replaces address book data with the data in {@code algoBase}.
     */
    void setAlgoBase(ReadOnlyAlgoBase algoBase);

    /** Returns the AlgoBase */
    ReadOnlyAlgoBase getAlgoBase();

    /**
     * Returns true if a Problem with the same identity as {@code Problem} exists in the address book.
     */
    boolean hasProblem(Problem problem);

    /**
     * Deletes the given Problem.
     * The Problem must exist in the address book.
     */
    void deleteProblem(Problem target);

    /**
     * Adds the given Problem.
     * {@code Problem} must not already exist in the address book.
     */
    void addProblem(Problem problem);

    /**
     * Replaces the given Problem {@code target} with {@code editedProblem}.
     * {@code target} must exist in the address book.
     * The Problem identity of {@code editedProblem} must not be the same as another existing Problem in the address book.
     */
    void setProblem(Problem target, Problem editedProblem);

    /** Returns an unmodifiable view of the filtered Problem list */
    ObservableList<Problem> getFilteredProblemList();

    /**
     * Updates the filter of the filtered Problem list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProblemList(Predicate<Problem> predicate);
}
