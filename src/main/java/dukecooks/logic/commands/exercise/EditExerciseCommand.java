package dukecooks.logic.commands.exercise;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INTENSITY;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PRIMARY_MUSCLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SECONDARY_MUSCLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SETS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TIMING;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.components.MusclesTrained;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.history.ExerciseHistory;

/**
 * Edits the details of an existing exercise in Duke Cooks.
 */
public class EditExerciseCommand extends EditCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the exercise identified "
            + "by the index number used in the displayed exercise list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PRIMARY_MUSCLE + "MUSCLE TYPE] "
            + "[" + PREFIX_SECONDARY_MUSCLE + "MUSCLE TYPE] "
            + "[" + PREFIX_INTENSITY + "INTENSITY] "
            + "[" + PREFIX_DISTANCE + "DISTANCE]..."
            + "[" + PREFIX_REPETITIONS + "REPS]..."
            + "[" + PREFIX_SETS + "SET]..."
            + "[" + PREFIX_WEIGHT + "WEIGHT]..."
            + "[" + PREFIX_TIMING + "TIMING]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Deadlifts";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited Exercise: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in Duke Cooks.";

    private final Index index;
    private final EditExerciseDescriptor editExerciseDescriptor;

    /**
     * @param index of the exercise in the filtered exercise list to edit
     * @param editExerciseDescriptor details to edit the exercise with
     */
    public EditExerciseCommand(Index index, EditExerciseDescriptor editExerciseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExerciseDescriptor);

        this.index = index;
        this.editExerciseDescriptor = new EditExerciseDescriptor(editExerciseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(index.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, editExerciseDescriptor);

        if (!exerciseToEdit.isSameExercise(editedExercise) && model.hasExercise(editedExercise)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        model.setExercise(exerciseToEdit, editedExercise);
        model.updateFilteredExerciseList(Model.PREDICATE_SHOW_ALL_EXERCISE);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
    }

    /**
     * Creates and returns a {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit,
                                                 EditExerciseDescriptor editExerciseDescriptor) {
        assert exerciseToEdit != null;

        ExerciseName updatedExerciseName = editExerciseDescriptor
                .getExerciseName().orElse(exerciseToEdit.getExerciseName());
        MuscleType updatedPrimaryMuscle = editExerciseDescriptor.getPrimaryMuscle()
                .orElse(exerciseToEdit.getMusclesTrained().getPrimaryMuscle());
        ArrayList<MuscleType> updatedSecondaryMuscles = editExerciseDescriptor.getSecondaryMuscles()
                .orElse(exerciseToEdit.getMusclesTrained().getSecondaryMuscles());
        MusclesTrained updatedMusclesTrained = new MusclesTrained(updatedPrimaryMuscle,
                updatedSecondaryMuscles);
        Intensity updatedIntensity = editExerciseDescriptor.getIntensity().orElse(exerciseToEdit.getIntensity());
        Set<ExerciseDetail> updatedExerciseDetails = editExerciseDescriptor
                .getExerciseDetails().orElse(exerciseToEdit.getExerciseDetails());
        ExerciseHistory updatedHistory = editExerciseDescriptor.getExerciseHistory()
                .orElse(exerciseToEdit.getHistory());

        return new Exercise(updatedExerciseName, updatedMusclesTrained, updatedIntensity, updatedExerciseDetails,
                updatedHistory);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExerciseCommand)) {
            return false;
        }

        // state check
        EditExerciseCommand e = (EditExerciseCommand) other;
        return index.equals(e.index)
                && editExerciseDescriptor.equals(e.editExerciseDescriptor);
    }

    /**
     * Stores the details to edit the exercise with. Each non-empty field value will replace the
     * corresponding field value of the exercise.
     */
    public static class EditExerciseDescriptor {
        private ExerciseName exerciseName;
        private MuscleType primaryMuscle;
        private ArrayList<MuscleType> secondaryMuscles;
        private Intensity intensity;
        private Set<ExerciseDetail> exerciseDetails;
        private ExerciseHistory exerciseHistory;

        public EditExerciseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExerciseDescriptor(EditExerciseDescriptor toCopy) {
            setExerciseName(toCopy.exerciseName);
            setPrimaryMuscle(toCopy.primaryMuscle);
            setSecondaryMuscles(toCopy.secondaryMuscles);
            setIntensity(toCopy.intensity);
            setExerciseDetails(toCopy.exerciseDetails);
            setExerciseHistory(toCopy.exerciseHistory);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(exerciseName, primaryMuscle, secondaryMuscles,
                    intensity, exerciseDetails);
        }

        public void setExerciseName(ExerciseName exerciseName) {
            this.exerciseName = exerciseName;
        }

        public void setIntensity(Intensity intensity) {
            this.intensity = intensity;
        }

        public void setPrimaryMuscle(MuscleType primaryMuscle) {
            this.primaryMuscle = primaryMuscle;
        }

        public void setSecondaryMuscles(ArrayList<MuscleType> secondaryMuscles) {
            this.secondaryMuscles = secondaryMuscles;
        }

        public void setExerciseHistory(ExerciseHistory history) {
            this.exerciseHistory = history;
        }

        public Optional<ExerciseName> getExerciseName() {
            return Optional.ofNullable(exerciseName);
        }

        public Optional<MuscleType> getPrimaryMuscle() {
            return Optional.ofNullable(primaryMuscle);
        }

        public Optional<Intensity> getIntensity() {
            return Optional.ofNullable(intensity);
        }

        public Optional<ArrayList<MuscleType>> getSecondaryMuscles() {
            return Optional.ofNullable(secondaryMuscles);
        }

        public Optional<ExerciseHistory> getExerciseHistory() {
            return Optional.ofNullable(exerciseHistory);
        }
        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setExerciseDetails(Set<ExerciseDetail> exerciseDetails) {
            this.exerciseDetails = (exerciseDetails != null) ? new HashSet<>(exerciseDetails) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<ExerciseDetail>> getExerciseDetails() {
            return (exerciseDetails != null)
                    ? Optional.of(Collections.unmodifiableSet(exerciseDetails)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExerciseDescriptor)) {
                return false;
            }

            // state check
            EditExerciseDescriptor e = (EditExerciseDescriptor) other;

            return getExerciseName().equals(e.getExerciseName())
                    && getExerciseDetails().equals(e.getExerciseDetails());
        }
    }
}
