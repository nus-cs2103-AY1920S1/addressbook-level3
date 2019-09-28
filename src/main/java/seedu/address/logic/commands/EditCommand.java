package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exercise.Calories;
import seedu.address.model.exercise.Date;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Name;
import seedu.address.model.exercise.Quantity;
import seedu.address.model.tag.Muscle;

/**
 * Edits the details of an existing exercise in the exercise book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_DATE + "PHONE] "
        + "[" + PREFIX_CALORIES + "EMAIL] "
        + "[" + PREFIX_QUANTITY + "ADDRESS] "
        + "[" + PREFIX_MUSCLE + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_DATE + "91234567 "
        + PREFIX_CALORIES + "johndoe@example.com";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited Exercise: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in the exercise book.";

    private final Index index;
    private final EditExerciseDescriptor editExerciseDescriptor;

    /**
     * @param index                  of the exercise in the filtered exercise list to edit
     * @param editExerciseDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditExerciseDescriptor editExerciseDescriptor) {
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
        model.updateFilteredExerciseList(Model.PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
    }

    /**
     * Creates and returns a {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(
        Exercise exerciseToEdit, EditExerciseDescriptor editExerciseDescriptor) {
        assert exerciseToEdit != null;

        Name updatedName = editExerciseDescriptor.getName().orElse(exerciseToEdit.getName());
        Calories updatedCalories = editExerciseDescriptor.getCalories().orElse(exerciseToEdit.getCalories());
        Date updatedDate = editExerciseDescriptor.getDate().orElse(exerciseToEdit.getDate());
        Quantity updatedQuantity = editExerciseDescriptor.getQuantity().orElse(exerciseToEdit.getQuantity());
        Set<Muscle> updatedMuscles = editExerciseDescriptor.getMuscles().orElse(exerciseToEdit.getMuscles());

        return new Exercise(updatedName, updatedDate, updatedCalories, updatedQuantity, updatedMuscles);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editExerciseDescriptor.equals(e.editExerciseDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditExerciseDescriptor {
        private Name name;
        private Calories calories;
        private Date date;
        private Quantity quantity;
        private Set<Muscle> muscles;

        public EditExerciseDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code muscles} is used internally.
         */
        public EditExerciseDescriptor(EditExerciseDescriptor toCopy) {
            setName(toCopy.name);
            setCalories(toCopy.calories);
            setDate(toCopy.date);
            setQuantity(toCopy.quantity);
            setMuscles(toCopy.muscles);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calories, date, quantity, muscles);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCalories(Calories calories) {
            this.calories = calories;
        }

        public Optional<Calories> getCalories() {
            return Optional.ofNullable(calories);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        /**
         * Sets {@code muscles} to this object's {@code muscles}.
         * A defensive copy of {@code muscles} is used internally.
         */
        public void setMuscles(Set<Muscle> muscles) {
            this.muscles = (muscles != null) ? new HashSet<>(muscles) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code muscles} is null.
         */
        public Optional<Set<Muscle>> getMuscles() {
            return (muscles != null) ? Optional.of(Collections.unmodifiableSet(muscles)) : Optional.empty();
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

            return getName().equals(e.getName())
                && getCalories().equals(e.getCalories())
                && getDate().equals(e.getDate())
                && getQuantity().equals(e.getQuantity())
                && getMuscles().equals(e.getMuscles());
        }
    }
}
