package calofit.logic.commands;

import static calofit.logic.parser.CliSyntax.PREFIX_CALORIES;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static calofit.model.Model.PREDICATE_SHOW_DEFAULT;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    public static final String MESSAGE_TAGS_DO_NOT_EXIST = "The following tag you want to remove do not "
            + "exist in the meal you want to edit.\n%s";
    public static final String MESSAGE_NO_SIMULTANEOUS_CLEAR_ADD = "Clearing and adding tags "
            + "simultaneously is not allowed.";

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

        if (editDishDescriptor.getTagsToRemove().isPresent()) {
            Set<Tag> unknownTags = new HashSet<>(editDishDescriptor.getTagsToRemove().get());
            unknownTags.removeAll(updatedTags);
            if (!unknownTags.isEmpty()) {
                String nonExistTags = "";
                List<Tag> listOfNonExistTags = new ArrayList<>(unknownTags);
                for (int i = 0; i < listOfNonExistTags.size(); i++) {
                    int iAtOne = i + 1;
                    if (i == listOfNonExistTags.size() - 1) {
                        nonExistTags = nonExistTags + iAtOne + ". " + listOfNonExistTags.get(i).toString();
                    } else {
                        nonExistTags = nonExistTags + iAtOne + ". " + listOfNonExistTags.get(i).toString() + "\n";
                    }
                }
                throw new CommandException(String.format(MESSAGE_TAGS_DO_NOT_EXIST, nonExistTags));
            }

            updatedTags.removeAll(editDishDescriptor.getTagsToRemove().get());
        }
        // Directly update the meal to the one in the dish db
        boolean isDishUpdated = false;
        Dish updatedDish = dishPointer;

        if (editDishDescriptor.getName().isPresent()
                && editDishDescriptor.getCalories().isPresent()) {
            // Check if both name tags and calorie tags are used.
            updatedName = editDishDescriptor.getName().get();
            updatedCalories = editDishDescriptor.getCalories().get();
        } else if (editDishDescriptor.getName().isPresent()) {
            // Check if only name tag is used
            updatedName = editDishDescriptor.getName().get();

        } else if (editDishDescriptor.getCalories().isPresent()) {
            // Check if only calorie tag is used
            updatedCalories = editDishDescriptor.getCalories().get();
        }

        Dish editedDish;
        if (isDishUpdated) {
            editedDish = updatedDish;
        } else {
            editedDish = new Dish(updatedName, updatedCalories, updatedTags);
        }

        Meal editedMeal = new Meal(editedDish, mealToEdit.getTimestamp());

        model.setMeal(mealToEdit, editedMeal);
        model.setDishFilterPredicate(PREDICATE_SHOW_DEFAULT);
        return new CommandResult(String.format(MESSAGE_EDIT_MEAL_SUCCESS, mealToEdit, editedMeal));
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
        private Set<Tag> tagsToRemove;

        public EditDishDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDishDescriptor(EditDishDescriptor toCopy) {
            setName(toCopy.name);
            setCalories(toCopy.calories);
            setTags(toCopy.tags);
            setTagsToRemove(toCopy.tagsToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calories, tags, tagsToRemove);
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
         * Sets {@code tagsToRemove} to this object's {@code tagsToRemove}.
         * A defensive copy of {@code tagsToRemove} is used internally.
         */
        public void setTagsToRemove(Set<Tag> tagsToRemove) {
            this.tagsToRemove = (tagsToRemove != null) ? new HashSet<>(tagsToRemove) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable set of tags to remove, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tagsToRemove} is null.
         */
        public Optional<Set<Tag>> getTagsToRemove() {
            return (tagsToRemove != null) ? Optional.of(Collections.unmodifiableSet(tagsToRemove)) : Optional.empty();
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
