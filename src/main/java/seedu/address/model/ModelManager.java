package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.exercise.Exercise;

/**
 * Represents the in-memory model of Duke Cooks data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WorkoutPlanner dukeCooks;
    private final WorkoutPlannerUserPrefs workoutPlannerUserPrefs;
    private final FilteredList<Exercise> filteredExercises;

    /**
     * Initializes a ModelManager with the given dukeCooks and userPrefs.
     */
    public ModelManager(ReadOnlyWorkoutPlanner dukeCooks, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(dukeCooks, userPrefs);

        logger.fine("Initializing with Duke Cooks: " + dukeCooks + " and user prefs " + userPrefs);

        this.dukeCooks = new WorkoutPlanner(dukeCooks);
        this.workoutPlannerUserPrefs = new WorkoutPlannerUserPrefs(userPrefs);
        filteredExercises = new FilteredList<>(this.dukeCooks.getExerciseList());
    }

    public ModelManager() {
        this(new WorkoutPlanner(), new WorkoutPlannerUserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setWorkoutPlannerUserPrefs(ReadOnlyUserPrefs workoutPlannerUserPrefs) {
        requireNonNull(workoutPlannerUserPrefs);
        this.workoutPlannerUserPrefs.resetData(workoutPlannerUserPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getWorkoutPlannerUserPrefs() {
        return workoutPlannerUserPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return workoutPlannerUserPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        workoutPlannerUserPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getDukeCooksFilePath() {
        return workoutPlannerUserPrefs.getExercisesFilePath();
    }

    @Override
    public void setDukeCooksFilePath(Path dukeCooksFilePath) {
        requireNonNull(dukeCooksFilePath);
        workoutPlannerUserPrefs.setExercisesFilePath(dukeCooksFilePath);
    }

    //=========== DukeBooks ================================================================================

    @Override
    public void setDukeCooks(ReadOnlyWorkoutPlanner dukeCooks) {
        this.dukeCooks.resetData(dukeCooks);
    }

    @Override
    public ReadOnlyWorkoutPlanner getDukeCooks() {
        return dukeCooks;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return dukeCooks.hasExercise(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        dukeCooks.removePerson(target);
    }

    @Override
    public void addExercise(Exercise exercise) {
        dukeCooks.addExercise(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISE);
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);

        dukeCooks.setExercise(target, editedExercise);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedDukeCooks}
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
        return dukeCooks.equals(other.dukeCooks)
                && workoutPlannerUserPrefs.equals(other.workoutPlannerUserPrefs)
                && filteredExercises.equals(other.filteredExercises);
    }

}
