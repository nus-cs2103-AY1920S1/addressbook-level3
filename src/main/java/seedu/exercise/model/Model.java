package seedu.exercise.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Regime> PREDICATE_SHOW_ALL_REGIMES = unused -> true;

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
     * Returns the user prefs' exercise book file path.
     */
    Path getExerciseBookFilePath();

    /**
     * Sets the user prefs' exercise book file path.
     */
    void setExerciseBookFilePath(Path exerciseBookFilePath);

    /**
     * Replaces exercise book data with the data in {@code anotherBook}.
     */
    void setExerciseBook(ReadOnlyResourceBook<Exercise> anotherBook);

    /**
     * Returns the data in the exercise book
     */
    ReadOnlyResourceBook<Exercise> getExerciseBookData();

    /**
     * Returns true if an exercise with the same identity as {@code exercise} exists in the exercise book.
     */
    boolean hasExercise(Exercise exercise);

    /**
     * Deletes the given exercise.
     * The exercise must exist in the exercise book.
     */
    void deleteExercise(Exercise target);

    /**
     * Adds the given exercise.
     * {@code exercise} must not already exist in exercise book.
     */
    void addExercise(Exercise exercise);

    /**
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     * {@code target} must exist in exercise book.
     * The exercise identity of {@code editedExercise} must not be the same as another existing exercise in
     * the exercise book.
     */
    void setExercise(Exercise target, Exercise editedExercise);

    /**
     * Returns an unmodifiable view of the filtered exercise list
     */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Returns an unmodifiable view of the filtered regime list
     */
    ObservableList<Regime> getFilteredRegimeList();

    /**
     * Returns an unmodifiable view of the filtered schedule list
     */
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);

    /**
     * Returns the user prefs' regime book file path.
     */
    Path getRegimeBookFilePath();

    /**
     * Sets the user prefs' regime book file path.
     */
    void setRegimeBookFilePath(Path regimeBookFilePath);

    /**
     * Replaces regime book data with the data in {@code anotherBook}.
     */
    void setRegimeBook(ReadOnlyResourceBook<Regime> anotherBook);

    /**
     * Returns the data in the regime book
     */
    ReadOnlyResourceBook<Regime> getAllRegimeData();

    /**
     * Returns true if an regime with the same identity as {@code regime} exists in the regime book.
     */
    boolean hasRegime(Regime regime);

    /**
     * Adds the given regime.
     * {@code regime} must not already exist in regime book.
     */
    void addRegime(Regime regime);

    /**
     * Replaces the given regime {@code target} with {@code editedRegime}.
     * {@code target} must exist in regime book.
     * The regime identity of {@code editedRegime} must not be the same as another existing regime in
     * the regime book.
     */
    void setRegime(Regime target, Regime editedRegime);

    /**
     * Deletes the given regime.
     * The regime must exist in the regime book.
     */
    void deleteRegime(Regime regime);

    /**
     * Returns the index of regime in regime book.
     */
    int getRegimeIndex(Regime regime);

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRegimeList(Predicate<Regime> predicate);

    /**
     * Returns true if another schedule has been scheduled on the same date as {@code schedule}.
     */
    boolean hasSchedule(Schedule schedule);

    /**
     * Schedules a {@code schedule} for the user.
     * It must be guaranteed that there is no existing schedule in the {@code ReadOnlyResourceBook<Schedule>}
     */
    void addSchedule(Schedule schedule);

    /**
     * Returns the data in the regime book
     */
    ReadOnlyResourceBook<Schedule> getAllScheduleData();

    /**
     * Deletes a Schedule and adds it to {@code ReadOnlyResourceBook<Exercise>} for tracking.
     * <p>
     * If the schedule has some exercises that are duplicates exercises as
     * specified by {@link Exercise#isSameResource}, that exercise will
     * be ignored and not be added into the exercise tracker.
     * </p>
     * All exercises added will have their dates changed to be the date
     * of the schedule itself.
     *
     * @param schedule to complete
     */
    void completeSchedule(Schedule schedule);

    /**
     * Returns the {@code PropertyBook} object that is contained in {@code Model}.
     */
    PropertyBook getPropertyBook();

    /**
     * Returns true if a prefix with the same identity as {@code prefix} is present in the PropertyBook.
     */
    boolean isPrefixUsed(Prefix prefix);

    /**
     * Returns true if {@code fullName} is present in the PropertyBook.
     */
    boolean isFullNameUsed(String fullName);

    /**
     * Adds the given {@code customProperty} into the PropertyBook.
     */
    void addCustomProperty(CustomProperty customProperty);

    /**
     * Returns an unmodifiable view of the list of suggested exercises
     */
    ObservableList<Exercise> getSuggestedExerciseList();

    /**
     * Updates the filter of the filtered exercise list to filter by the give {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateSuggestedExerciseList(Predicate<Exercise> predicateShowAllExercises);

    /**
     * Returns the data of all exercises in the database
     */
    ReadOnlyResourceBook<Exercise> getDatabaseBook();
}
