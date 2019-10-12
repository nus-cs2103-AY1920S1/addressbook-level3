package seedu.exercise.model;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.regime.Regime;

/**
 * Represents the in-memory model of the exercise book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ExerciseBook exerciseBook;
    private final RegimeBook regimeBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Exercise> filteredExercises;
    private final FilteredList<Regime> filteredRegimes;

    /**
     * Initializes a ModelManager with the given exerciseBook and userPrefs.
     */
    public ModelManager(ReadOnlyExerciseBook exerciseBook, ReadOnlyRegimeBook regimeBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(exerciseBook, userPrefs);

        logger.fine("Initializing with exercise book: " + exerciseBook + " and user prefs " + userPrefs);

        this.exerciseBook = new ExerciseBook(exerciseBook);
        this.regimeBook = new RegimeBook(regimeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExercises = new FilteredList<>(this.exerciseBook.getExerciseList());
        filteredRegimes = new FilteredList<>(this.regimeBook.getRegimeList());

    }

    public ModelManager() {
        this(new ExerciseBook(), new RegimeBook(), new UserPrefs());
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
    public Path getExerciseBookFilePath() {
        return userPrefs.getExerciseBookFilePath();
    }

    @Override
    public void setExerciseBookFilePath(Path exerciseBookFilePath) {
        requireNonNull(exerciseBookFilePath);
        userPrefs.setExerciseBookFilePath(exerciseBookFilePath);
    }

    @Override
    public Path getRegimeBookFilePath() {
        return userPrefs.getRegimeBookFilePath();
    }

    @Override
    public void setRegimeBookFilePath(Path regimeBookFilePath) {
        requireNonNull(regimeBookFilePath);
        userPrefs.setRegimeBookFilePath(regimeBookFilePath);
    }

    //=========== ExerciseBook ================================================================================

    @Override
    public void setExerciseBook(ReadOnlyExerciseBook anotherBook) {
        this.exerciseBook.resetData(anotherBook);
    }

    @Override
    public ReadOnlyExerciseBook getAllExerciseData() {
        return exerciseBook;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exerciseBook.hasExercise(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        exerciseBook.removeExercise(target);
    }

    /**
     * Adds an {@code Exercise} object into the exercise book.
     */
    public void addExercise(Exercise exercise) {
        exerciseBook.addExercise(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
    }

    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);

        exerciseBook.setExercise(target, editedExercise);
    }
    //===================RegimeBook==============================================================================
    @Override
    public void setRegimeBook(ReadOnlyRegimeBook anotherBook) {
        this.regimeBook.resetData(anotherBook);
    }

    @Override
    public ReadOnlyRegimeBook getAllRegimeData() {
        return regimeBook;
    }

    /**
     * Adds a {@code Regime} object into the regime book.
     */
    @Override
    public void addRegime(Regime regime) {
        regimeBook.addRegime(regime);
    }

    @Override
    public void deleteRegime(Regime target) {
        regimeBook.removeRegime(target);
    }

    @Override
    public void setRegime(Regime target, Regime editedRegime) {
        regimeBook.setRegime(target, editedRegime);
    }

    @Override
    public boolean hasRegime(Regime regime) {
        requireNonNull(regime);
        return regimeBook.hasRegime(regime);
    }

    @Override
    public int getRegimeIndex(Regime regime) {
        return regimeBook.getRegimeIndex(regime);
    }

    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code versionedExerciseBook}
     */
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return filteredExercises;
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }

    //=========== Filtered Regime List Accessors ===============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Regime} backed by the internal list of
     * {@code versionedRegimeBook}
     */
    public ObservableList<Regime> getFilteredRegimeList() {
        return filteredRegimes;
    }

    @Override
    public void updateFilteredRegimeList(Predicate<Regime> predicate) {
        requireNonNull(predicate);
        filteredRegimes.setPredicate(predicate);
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
        return exerciseBook.equals(other.exerciseBook)
                && userPrefs.equals(other.userPrefs)
                && filteredExercises.equals(other.filteredExercises)
                && filteredRegimes.equals(other.filteredRegimes);
    }
}
