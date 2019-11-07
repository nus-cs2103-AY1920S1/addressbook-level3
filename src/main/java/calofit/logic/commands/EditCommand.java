package calofit.logic.commands;

import static calofit.logic.parser.CliSyntax.PREFIX_CALORIES;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static calofit.model.Model.PREDICATE_SHOW_DEFAULT;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;

import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.commons.util.CollectionUtil;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.meal.Meal;
import calofit.model.tag.Tag;


/**
 * Edits the details of an existing dish in the dish database.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the dish identified "
            + "by the index number used in the displayed dish list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_MEAL_SUCCESS = "Edited Meal: from %1$s to %2$s";
    public static final String MESSAGE_EDIT_TAGS_SUCCESS = "Tags have been updated!";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEAL = "This dish already exists in the dish database.";

    private final Index index;
    private final EditDishDescriptor editDishDescriptor;

    /**
     * @param index of the dish in the filtered dish list to edit
     * @param editDishDescriptor details to edit the dish with
     */
    public EditCommand(Index index, EditDishDescriptor editDishDescriptor) {
        requireNonNull(index);
        requireNonNull(editDishDescriptor);

        this.index = index;
        this.editDishDescriptor = new EditDishDescriptor(editDishDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Meal> lastShownList = model.getMealLog().getTodayMeals();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
        }

        Meal mealToEdit = lastShownList.get(index.getZeroBased());
        Dish dishPointer = mealToEdit.getDish();

        // Original Information. If may be changed down the line
        Name updatedName = dishPointer.getName();
        Calorie updatedCalories = dishPointer.getCalories();
        Set<Tag> updatedTags = new HashSet<Tag>();
        updatedTags.addAll(dishPointer.getTags());

        boolean isTagsEmpty = false;

        if (editDishDescriptor.getTags().isPresent()) {
            if (!editDishDescriptor.getTags().get().isEmpty()) {
                updatedTags.addAll(editDishDescriptor.getTags().get());
            } else {
                isTagsEmpty = true;
                updatedTags = new HashSet<Tag>();
            }
        }

        // Directly update the meal to the one in the dish db
        boolean isDishUpdated = false;
        Dish updatedDish = dishPointer;


        if (editDishDescriptor.getName().isPresent()
                && editDishDescriptor.getCalories().isPresent()
                && editDishDescriptor.getTags().isPresent()) {
            updatedName = editDishDescriptor.getName().get();
            updatedCalories = editDishDescriptor.getCalories().get();
            updatedTags = editDishDescriptor.getTags().get();

            updatedDish = new Dish(updatedName, updatedCalories, updatedTags);
            isDishUpdated = true;

        } else if (editDishDescriptor.getName().isPresent()
                && editDishDescriptor.getCalories().isPresent()) {
            // Check if both name tags and calorie tags are used.
            updatedName = editDishDescriptor.getName().get();
            updatedCalories = editDishDescriptor.getCalories().get();
            //if (model.hasDish(new Dish(updatedName, updatedCalories))) {
            //    updatedDish = model.getDish(new Dish(updatedName, updatedCalories));
            //    isDishUpdated = true;
            //}
        } else if (editDishDescriptor.getName().isPresent()) {
            // Check if only name tag is used
            updatedName = editDishDescriptor.getName().get();
            // Check if name is present in the dishDB
            // If not present, do not do anything
            //if (model.hasDishName(updatedName)) {
            //    updatedDish = model.getDishByName(updatedName);
            //    updatedTags = new HashSet<Tag>();
            //    updatedTags.addAll(updatedDish.getTags());
            //    if (isTagsEmpty) {
            //        updatedTags = new HashSet<Tag>();
            //    } else if (editDishDescriptor.getTags().isPresent()) {
            //        updatedTags.addAll(editDishDescriptor.getTags().get());
            //    }
            //    updatedCalories = updatedDish.getCalories();
            //    updatedName = updatedDish.getName();
            //    updatedDish = new Dish(updatedName, updatedCalories, updatedTags);
            //    isDishUpdated = true;
            //}

        } else if (editDishDescriptor.getCalories().isPresent()) {
            // Check if only calorie tag is used
            updatedCalories = editDishDescriptor.getCalories().get();
            //if (model.hasDish(new Dish(updatedName, updatedCalories))) {
            //    updatedDish = model.getDish(new Dish(updatedName, updatedCalories));
            //    updatedTags = new HashSet<Tag>();
            //    updatedTags.addAll(updatedDish.getTags());
            //    if (isTagsEmpty) {
            //        updatedTags = new HashSet<Tag>();
            //    } else if (editDishDescriptor.getTags().isPresent()) {
            //        updatedTags.addAll(editDishDescriptor.getTags().get());
            //    }
            //    updatedCalories = updatedDish.getCalories();
            //    updatedName = updatedDish.getName();
            //    updatedDish = new Dish(updatedName, updatedCalories, updatedTags);
            //    isDishUpdated = true;
            //}
        }

        Dish editedDish;
        if (isDishUpdated) {
            editedDish = updatedDish;
        } else {
            editedDish = new Dish(updatedName, updatedCalories, updatedTags);
        }

        Meal editedMeal = new Meal(editedDish, mealToEdit.getTimestamp());

        if (!model.hasDish(editedDish)) {
            model.addDish(editedDish);
        }

        model.getMealLog().setMeal(mealToEdit, editedMeal);
        model.setDishFilterPredicate(PREDICATE_SHOW_DEFAULT);
        return new CommandResult(String.format(MESSAGE_EDIT_MEAL_SUCCESS, mealToEdit, editedMeal));
    }

    /**
     * Creates and returns a {@code Dish} with the details of {@code dishToEdit}
     * edited with {@code editDishDescriptor}.
     */
    private static Dish createEditedDish(Dish dishToEdit, EditDishDescriptor editDishDescriptor) {
        assert dishToEdit != null;

        Name updatedName;
        Calorie updatedCalories;
        Set<Tag> updatedTags;
        Optional<Name> updatedNameOptional = editDishDescriptor.getName();
        if (updatedNameOptional.isPresent()) {
            updatedName = updatedNameOptional.get();
            updatedCalories = editDishDescriptor.getCalories().orElse(dishToEdit.getCalories());
            updatedTags = editDishDescriptor.getTags().orElse(dishToEdit.getTags());

        } else {
            updatedName = dishToEdit.getName();
            updatedCalories = editDishDescriptor.getCalories().orElse(dishToEdit.getCalories());
            updatedTags = editDishDescriptor.getTags().orElse(dishToEdit.getTags());
        }

        return new Dish(updatedName, updatedCalories, updatedTags);
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
                && editDishDescriptor.equals(e.editDishDescriptor);
    }

    /**
     * Stores the details to edit the dish with. Each non-empty field value will replace the
     * corresponding field value of the dish.
     */
    public static class EditDishDescriptor {
        private Name name;
        private Calorie calories;
        private Set<Tag> tags;

        public EditDishDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDishDescriptor(EditDishDescriptor toCopy) {
            setName(toCopy.name);
            setCalories(toCopy.calories);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calories, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCalories(Calorie calories) {
            this.calories = calories;
        }

        public Optional<Calorie> getCalories() {
            return Optional.ofNullable(calories);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDishDescriptor)) {
                return false;
            }

            // state check
            EditDishDescriptor e = (EditDishDescriptor) other;

            return getName().equals(e.getName())
                    && getCalories().equals(e.getCalories())
                    && getTags().equals(e.getTags());
        }
    }
}
